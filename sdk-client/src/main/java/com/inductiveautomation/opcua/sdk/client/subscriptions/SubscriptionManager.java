/*
 * Copyright 2015
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.inductiveautomation.opcua.sdk.client.subscriptions;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.Lists;
import com.inductiveautomation.opcua.sdk.client.OpcUaClient;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExtensionObject;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSubscriptionResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.DataChangeNotification;
import com.inductiveautomation.opcua.stack.core.types.structured.EventFieldList;
import com.inductiveautomation.opcua.stack.core.types.structured.EventNotificationList;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoredItemNotification;
import com.inductiveautomation.opcua.stack.core.types.structured.NotificationMessage;
import com.inductiveautomation.opcua.stack.core.types.structured.PublishRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.PublishResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.RequestHeader;
import com.inductiveautomation.opcua.stack.core.types.structured.StatusChangeNotification;
import com.inductiveautomation.opcua.stack.core.types.structured.SubscriptionAcknowledgement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class SubscriptionManager {

    private static final int MAX_PENDING_PUBLISHES = 2;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final List<SubscriptionAcknowledgement> acknowledgements = Lists.newArrayList();

    private final List<OpcUaSubscription> subscriptions = Lists.newCopyOnWriteArrayList();
    private final AtomicInteger pendingPublishes = new AtomicInteger(0);

    private final OpcUaClient client;

    public SubscriptionManager(OpcUaClient client) {
        this.client = client;
    }

    public CompletableFuture<OpcUaSubscription> addSubscription(OpcUaSubscription subscription) {
        CompletableFuture<CreateSubscriptionResponse> future = client.createSubscription(
                subscription.getRequestedPublishingInterval(),
                subscription.getRequestedLifetimeCount(),
                subscription.getRequestedMaxKeepAliveCount(),
                subscription.getMaxNotificationsPerPublish(),
                subscription.isPublishingEnabled(),
                subscription.getPriority());

        return future.thenApply(response -> {
            subscription.setSubscriptionId(response.getSubscriptionId());
            subscription.setRevisedPublishingInterval(response.getRevisedPublishingInterval());
            subscription.setRevisedLifetimeCount(response.getRevisedLifetimeCount());
            subscription.setRevisedMaxKeepAliveCount(response.getRevisedMaxKeepAliveCount());

            subscriptions.add(subscription);

            maybeSendPublishRequest();

            return subscription;
        });
    }

    public void removeSubscription(OpcUaSubscription subscription) {

    }

    private int getMaxPendingPublishes() {
        return subscriptions.isEmpty() ? 0 : MAX_PENDING_PUBLISHES;
    }

    private void maybeSendPublishRequest() {
        if (pendingPublishes.incrementAndGet() <= getMaxPendingPublishes()) {
            SubscriptionAcknowledgement[] subscriptionAcknowledgements;

            synchronized (acknowledgements) {
                subscriptionAcknowledgements = acknowledgements.toArray(
                        new SubscriptionAcknowledgement[acknowledgements.size()]);

                acknowledgements.clear();
            }

            client.getSession().thenCompose(session -> {
                RequestHeader requestHeader = new RequestHeader(
                        session.getAuthToken(),
                        DateTime.now(),
                        uint(0), // TODO requestHandle
                        uint(0),
                        null,
                        uint((long) (MAX_PENDING_PUBLISHES * 5 * 1.25 * 60000)), // TODO timeoutHint
                        null);

                PublishRequest request = new PublishRequest(
                        requestHeader,
                        subscriptionAcknowledgements);

                return client.<PublishResponse>sendRequest(request);
            }).whenComplete((response, ex) -> {
                pendingPublishes.decrementAndGet();

                if (ex != null) {
                    logger.warn("Publish service failure: {}", ex.getMessage(), ex);
                    // TODO Re-book-keep the SubscriptionAcknowledgements
                    // TODO Log a warning? Notify someone?
                } else {
                    onPublishComplete(response);
                }

                maybeSendPublishRequest();
            });
        } else {
            pendingPublishes.decrementAndGet();
        }
    }

    private void onPublishComplete(PublishResponse response) {
        response.getResults(); // TODO

        UInteger subscriptionId = response.getSubscriptionId();

        for (UInteger sequenceNumber : response.getAvailableSequenceNumbers()) {
            acknowledgements.add(new SubscriptionAcknowledgement(subscriptionId, sequenceNumber));
        }

        NotificationMessage notificationMessage = response.getNotificationMessage();
        DateTime publishTime = notificationMessage.getPublishTime();
        boolean moreNotifications = response.getMoreNotifications();

        logger.info("PublishResponse, subscriptionId={}, publishTime={}, moreNotifications={}",
                subscriptionId, publishTime, moreNotifications);

        for (ExtensionObject xo : notificationMessage.getNotificationData()) {
            Object o = xo.getObject();

            if (o instanceof DataChangeNotification) {
                DataChangeNotification dcn = (DataChangeNotification) o;

                for (MonitoredItemNotification min : dcn.getMonitoredItems()) {
                    logger.info("MonitoredItemNotification: clientHandle={}, value={}",
                            min.getClientHandle(), min.getValue());
                }
            } else if (o instanceof EventNotificationList) {
                EventNotificationList enl = (EventNotificationList) o;

                for (EventFieldList efl : enl.getEvents()) {
                    logger.info("EventFieldList: clientHandle={}, values={}",
                            efl.getClientHandle(), Arrays.toString(efl.getEventFields()));
                }
            } else if (o instanceof StatusChangeNotification) {
                StatusChangeNotification scn = (StatusChangeNotification) o;

                logger.info("StatusChangeNotification: {}", scn.getStatus());
            }
        }
    }

}
