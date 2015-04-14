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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import com.google.common.collect.Lists;
import com.inductiveautomation.opcua.sdk.client.OpcUaClient;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExtensionObject;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSubscriptionResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.DataChangeNotification;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteSubscriptionsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.EventFieldList;
import com.inductiveautomation.opcua.stack.core.types.structured.EventNotificationList;
import com.inductiveautomation.opcua.stack.core.types.structured.ModifySubscriptionResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoredItemNotification;
import com.inductiveautomation.opcua.stack.core.types.structured.NotificationMessage;
import com.inductiveautomation.opcua.stack.core.types.structured.PublishRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.PublishResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.RepublishResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.RequestHeader;
import com.inductiveautomation.opcua.stack.core.types.structured.StatusChangeNotification;
import com.inductiveautomation.opcua.stack.core.types.structured.SubscriptionAcknowledgement;
import com.inductiveautomation.opcua.stack.core.util.ExecutionQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.collect.Lists.newArrayList;
import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class SubscriptionManager {

    private static final int MAX_PENDING_PUBLISHES = 2;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ExecutionQueue deliveryQueue;
    private final ExecutionQueue processingQueue;

    private volatile long lastSequenceNumber = 0L;
    private final List<SubscriptionAcknowledgement> acknowledgements = newArrayList();

    private final List<OpcUaSubscription> subscriptions = Lists.newCopyOnWriteArrayList();
    private final AtomicInteger pendingPublishes = new AtomicInteger(0);

    private final OpcUaClient client;

    public SubscriptionManager(OpcUaClient client) {
        this.client = client;

        deliveryQueue = new ExecutionQueue(client.getConfig().getExecutorService());
        processingQueue = new ExecutionQueue(client.getConfig().getExecutorService());
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

    public CompletableFuture<OpcUaSubscription> modifySubscription(OpcUaSubscription subscription,
                                                                   Consumer<ModifySubscriptionContext> consumer) {

        ModifySubscriptionContext context = new ModifySubscriptionContext(subscription);

        consumer.accept(context);

        CompletableFuture<ModifySubscriptionResponse> future = client.modifySubscription(
                subscription.getSubscriptionId(),
                context.getRequestedPublishingInterval(),
                context.getRequestedLifetimeCount(),
                context.getRequestedMaxKeepAliveCount(),
                context.getMaxNotificationsPerPublish(),
                context.getPriority());

        return future.thenApply(response -> {
            subscription.setRevisedPublishingInterval(response.getRevisedPublishingInterval());
            subscription.setRevisedLifetimeCount(response.getRevisedLifetimeCount());
            subscription.setRevisedMaxKeepAliveCount(response.getRevisedMaxKeepAliveCount());

            return subscription;
        });
    }

    public CompletableFuture<OpcUaSubscription> removeSubscription(OpcUaSubscription subscription) {
        List<UInteger> subscriptionIds = newArrayList(subscription.getSubscriptionId());

        CompletableFuture<DeleteSubscriptionsResponse> future = client.deleteSubscriptions(subscriptionIds);

        return future.thenApply(response -> {
            subscriptions.remove(subscription);

            maybeSendPublishRequest();

            return subscription;
        });
    }

    public CompletableFuture<OpcUaSubscription> setPublishingMode(OpcUaSubscription subscription,
                                                                  boolean publishingEnabled) {

        ArrayList<UInteger> subscriptionIds = newArrayList(subscription.getSubscriptionId());

        return client.setPublishingMode(publishingEnabled, subscriptionIds).thenApply(r -> subscription);
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
                    processingQueue.submit(() -> onPublishComplete(response));
                }

                maybeSendPublishRequest();
            });
        } else {
            pendingPublishes.decrementAndGet();
        }
    }

    private void onPublishComplete(PublishResponse response) {
        UInteger subscriptionId = response.getSubscriptionId();

        NotificationMessage notificationMessage = response.getNotificationMessage();

        long sequenceNumber = notificationMessage.getSequenceNumber().longValue();
        long expectedSequenceNumber = lastSequenceNumber + 1;

        if (sequenceNumber > expectedSequenceNumber) {
            logger.warn("Expected sequence={}, received sequence={}. Calling Republish service...",
                    expectedSequenceNumber, sequenceNumber);

            processingQueue.pause();
            processingQueue.submitToHead(() -> onPublishComplete(response));

            republish(subscriptionId, expectedSequenceNumber, sequenceNumber).whenComplete((v, ex) -> {
                if (ex != null) {
                    logger.warn("Republish service failed; reading values for subscriptionId={}: {}",
                            subscriptionId, ex.getMessage(), ex);

                    // TODO Re-read the value for all items belonging to subscriptionId.
                    // Use Server's time + publishTime in queued responses to figure out what can be ignored?
                }

                lastSequenceNumber = sequenceNumber - 1;

                processingQueue.resume();
            });

            return;
        } else if (sequenceNumber < expectedSequenceNumber) {
            logger.warn("Expected sequence={}, received sequence={}. Ignoring...",
                    expectedSequenceNumber, sequenceNumber);

            return;
        }

        lastSequenceNumber = sequenceNumber;

        response.getResults(); // TODO

        for (UInteger available : response.getAvailableSequenceNumbers()) {
            acknowledgements.add(new SubscriptionAcknowledgement(subscriptionId, available));
        }

        deliveryQueue.submit(() -> onNotificationMessage(subscriptionId, notificationMessage));
    }

    private CompletableFuture<Void> republish(UInteger subscriptionId, long fromSequence, long toSequence) {
        logger.info("republish() subscriptionId={}, fromSequence={}, toSequence={}",
                subscriptionId, fromSequence, toSequence);

        if (fromSequence == toSequence) {
            return CompletableFuture.completedFuture(null);
        } else {
            return client.republish(subscriptionId, uint(fromSequence)).thenCompose(response -> {
                try {
                    onRepublishComplete(subscriptionId, response, uint(fromSequence));

                    return republish(subscriptionId, fromSequence + 1, toSequence);
                } catch (UaException e) {
                    CompletableFuture<Void> failed = new CompletableFuture<>();
                    failed.completeExceptionally(e);
                    return failed;
                }
            });
        }
    }

    private void onRepublishComplete(UInteger subscriptionId,
                                     RepublishResponse response,
                                     UInteger expectedSequenceNumber) throws UaException {

        NotificationMessage notificationMessage = response.getNotificationMessage();
        UInteger sequenceNumber = notificationMessage.getSequenceNumber();

        if (!sequenceNumber.equals(expectedSequenceNumber)) {
            throw new UaException(StatusCodes.Bad_SequenceNumberInvalid,
                    "expected sequence=" + expectedSequenceNumber + ", received sequence=" + sequenceNumber);
        }

        deliveryQueue.submit(() -> onNotificationMessage(subscriptionId, notificationMessage));
    }

    private void onNotificationMessage(UInteger subscriptionId, NotificationMessage notificationMessage) {
        DateTime publishTime = notificationMessage.getPublishTime();

        logger.info("onNotificationMessage(), sequenceNumber={}, subscriptionId={}, publishTime={}",
                notificationMessage.getSequenceNumber(), subscriptionId, publishTime);

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

    public static class ModifySubscriptionContext {

        private double requestedPublishingInterval;
        private UInteger requestedLifetimeCount;
        private UInteger requestedMaxKeepAliveCount;
        private UInteger maxNotificationsPerPublish;
        private UByte priority;

        private ModifySubscriptionContext(OpcUaSubscription subscription) {
            this.requestedPublishingInterval = subscription.getRevisedPublishingInterval();
            this.requestedLifetimeCount = subscription.getRevisedLifetimeCount();
            this.requestedMaxKeepAliveCount = subscription.getRequestedMaxKeepAliveCount();
            this.maxNotificationsPerPublish = subscription.getMaxNotificationsPerPublish();
            this.priority = subscription.getPriority();
        }

        public double getRequestedPublishingInterval() {
            return requestedPublishingInterval;
        }

        public void setRequestedPublishingInterval(double requestedPublishingInterval) {
            this.requestedPublishingInterval = requestedPublishingInterval;
        }

        public UInteger getRequestedLifetimeCount() {
            return requestedLifetimeCount;
        }

        public void setRequestedLifetimeCount(UInteger requestedLifetimeCount) {
            this.requestedLifetimeCount = requestedLifetimeCount;
        }

        public UInteger getRequestedMaxKeepAliveCount() {
            return requestedMaxKeepAliveCount;
        }

        public void setRequestedMaxKeepAliveCount(UInteger requestedMaxKeepAliveCount) {
            this.requestedMaxKeepAliveCount = requestedMaxKeepAliveCount;
        }

        public UInteger getMaxNotificationsPerPublish() {
            return maxNotificationsPerPublish;
        }

        public void setMaxNotificationsPerPublish(UInteger maxNotificationsPerPublish) {
            this.maxNotificationsPerPublish = maxNotificationsPerPublish;
        }

        public UByte getPriority() {
            return priority;
        }

        public void setPriority(UByte priority) {
            this.priority = priority;
        }

    }

}
