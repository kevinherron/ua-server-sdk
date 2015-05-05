/*
 * digitalpetri OPC-UA SDK
 *
 * Copyright (C) 2015 Kevin Herron
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.digitalpetri.opcua.sdk.client.subscriptions;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.digitalpetri.opcua.sdk.client.OpcUaClient;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.ExtensionObject;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.digitalpetri.opcua.stack.core.types.structured.CreateSubscriptionResponse;
import com.digitalpetri.opcua.stack.core.types.structured.DataChangeNotification;
import com.digitalpetri.opcua.stack.core.types.structured.EventFieldList;
import com.digitalpetri.opcua.stack.core.types.structured.EventNotificationList;
import com.digitalpetri.opcua.stack.core.types.structured.ModifySubscriptionResponse;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoredItemNotification;
import com.digitalpetri.opcua.stack.core.types.structured.NotificationMessage;
import com.digitalpetri.opcua.stack.core.types.structured.PublishRequest;
import com.digitalpetri.opcua.stack.core.types.structured.PublishResponse;
import com.digitalpetri.opcua.stack.core.types.structured.ReadValueId;
import com.digitalpetri.opcua.stack.core.types.structured.RepublishResponse;
import com.digitalpetri.opcua.stack.core.types.structured.RequestHeader;
import com.digitalpetri.opcua.stack.core.types.structured.StatusChangeNotification;
import com.digitalpetri.opcua.stack.core.types.structured.SubscriptionAcknowledgement;
import com.digitalpetri.opcua.stack.core.util.ExecutionQueue;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.ubyte;
import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;
import static com.google.common.collect.Lists.newArrayList;

public class OpcUaSubscriptionManager {

    public static final UInteger DEFAULT_MAX_NOTIFICATIONS_PER_PUBLISH = uint(65535);

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<UInteger, OpcUaSubscription> subscriptions = Maps.newConcurrentMap();

    private final AtomicInteger pendingPublishes = new AtomicInteger(0);

    private final List<SubscriptionAcknowledgement> acknowledgements = newArrayList();

    private final ExecutionQueue deliveryQueue;
    private final ExecutionQueue processingQueue;

    private final OpcUaClient client;

    public OpcUaSubscriptionManager(OpcUaClient client) {
        this.client = client;

        deliveryQueue = new ExecutionQueue(client.getConfig().getExecutorService());
        processingQueue = new ExecutionQueue(client.getConfig().getExecutorService());
    }

    /**
     * Create a {@link OpcUaSubscription} using default parameters.
     *
     * @param requestedPublishingInterval the requested publishing interval of the subscription.
     * @return a {@link CompletableFuture} containing the {@link OpcUaSubscription}.
     */
    public CompletableFuture<OpcUaSubscription> createSubscription(double requestedPublishingInterval) {
        // Keep-alive every ~10-12s or every publishing interval if longer.
        UInteger maxKeepAliveCount = uint(Math.max(1, (int) Math.ceil(10000.0 / requestedPublishingInterval)));

        // Lifetime must be 3x (or greater) the keep-alive count.
        UInteger maxLifetimeCount = uint(maxKeepAliveCount.intValue() * 3);

        return createSubscription(
                requestedPublishingInterval,
                maxLifetimeCount,
                maxKeepAliveCount,
                DEFAULT_MAX_NOTIFICATIONS_PER_PUBLISH,
                true, ubyte(0));
    }

    /**
     * Create a {@link OpcUaSubscription}.
     *
     * @param requestedPublishingInterval
     * @param requestedLifetimeCount
     * @param requestedMaxKeepAliveCount
     * @param maxNotificationsPerPublish
     * @param publishingEnabled
     * @param priority
     * @return a {@link CompletableFuture} containing the {@link OpcUaSubscription}.
     */
    public CompletableFuture<OpcUaSubscription> createSubscription(double requestedPublishingInterval,
                                                                   UInteger requestedLifetimeCount,
                                                                   UInteger requestedMaxKeepAliveCount,
                                                                   UInteger maxNotificationsPerPublish,
                                                                   boolean publishingEnabled,
                                                                   UByte priority) {

        CompletableFuture<CreateSubscriptionResponse> future = client.createSubscription(
                requestedPublishingInterval,
                requestedLifetimeCount,
                requestedMaxKeepAliveCount,
                maxNotificationsPerPublish,
                publishingEnabled, priority);

        return future.thenApply(response -> {
            OpcUaSubscription subscription = new OpcUaSubscription(
                    client,
                    response.getSubscriptionId(),
                    response.getRevisedPublishingInterval(),
                    response.getRevisedLifetimeCount(),
                    response.getRevisedMaxKeepAliveCount(),
                    maxNotificationsPerPublish,
                    publishingEnabled, priority);

            subscriptions.put(subscription.getSubscriptionId(), subscription);

            maybeSendPublishRequest();

            return subscription;
        });
    }


    /**
     * Request a new publishing interval for a {@link OpcUaSubscription}.
     * <p>
     * The requested max keep-alive count and lifetime count will be derived from the requested publishing interval.
     *
     * @param subscription                the {@link OpcUaSubscription} to modify.
     * @param requestedPublishingInterval the requested publishing interval.
     * @return a {@link CompletableFuture} containing the {@link OpcUaSubscription}.
     */
    public CompletableFuture<OpcUaSubscription> modifySubscription(OpcUaSubscription subscription,
                                                                   double requestedPublishingInterval) {

        // Keep-alive every ~10-12s or every publishing interval if longer.
        UInteger requestedMaxKeepAliveCount = uint(Math.max(1, (int) Math.ceil(10000.0 / requestedPublishingInterval)));

        // Lifetime must be 3x (or greater) the keep-alive count.
        UInteger requestedLifetimeCount = uint(requestedMaxKeepAliveCount.intValue() * 3);

        CompletableFuture<OpcUaSubscription> future = modifySubscription(
                subscription,
                requestedPublishingInterval,
                requestedLifetimeCount,
                requestedMaxKeepAliveCount,
                subscription.getMaxNotificationsPerPublish(),
                subscription.getPriority());

        future.thenRun(this::maybeSendPublishRequest);

        return future;
    }

    /**
     * Modify a {@link OpcUaSubscription}.
     *
     * @param subscription
     * @param requestedPublishingInterval
     * @param requestedLifetimeCount
     * @param requestedMaxKeepAliveCount
     * @param maxNotificationsPerPublish
     * @param priority
     * @return
     */
    public CompletableFuture<OpcUaSubscription> modifySubscription(OpcUaSubscription subscription,
                                                                   double requestedPublishingInterval,
                                                                   UInteger requestedLifetimeCount,
                                                                   UInteger requestedMaxKeepAliveCount,
                                                                   UInteger maxNotificationsPerPublish,
                                                                   UByte priority) {

        CompletableFuture<ModifySubscriptionResponse> future = client.modifySubscription(
                subscription.getSubscriptionId(),
                requestedPublishingInterval,
                requestedLifetimeCount,
                requestedMaxKeepAliveCount,
                maxNotificationsPerPublish,
                priority);

        return future.thenApply(response -> {
            subscription.setRevisedPublishingInterval(response.getRevisedPublishingInterval());
            subscription.setRevisedLifetimeCount(response.getRevisedLifetimeCount());
            subscription.setRevisedMaxKeepAliveCount(response.getRevisedMaxKeepAliveCount());
            subscription.setMaxNotificationsPerPublish(maxNotificationsPerPublish);
            subscription.setPriority(priority);

            maybeSendPublishRequest();

            return subscription;
        });
    }

    public CompletableFuture<OpcUaSubscription> deleteSubscription(OpcUaSubscription subscription) {
        List<UInteger> subscriptionIds = newArrayList(subscription.getSubscriptionId());

        return client.deleteSubscriptions(subscriptionIds).thenApply(r -> {
            subscriptions.remove(subscription.getSubscriptionId());

            maybeSendPublishRequest();

            return subscription;
        });
    }

    private int getMaxPendingPublishes() {
        return subscriptions.size() * 2;
    }

    private UInteger getTimeoutHint() {
        double minKeepAlive = subscriptions.values().stream()
                .map(s -> s.getRevisedPublishingInterval() * s.getRevisedMaxKeepAliveCount().doubleValue())
                .min(Comparator.<Double>naturalOrder())
                .orElse(client.getConfig().getRequestTimeout());

        long timeoutHint = (long) (getMaxPendingPublishes() * minKeepAlive * 1.25) * 2;

        return uint(timeoutHint);
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
                        session.getAuthenticationToken(),
                        DateTime.now(),
                        client.nextRequestHandle(),
                        uint(0),
                        null,
                        getTimeoutHint(),
                        null);

                PublishRequest request = new PublishRequest(
                        requestHeader,
                        subscriptionAcknowledgements);

                return client.<PublishResponse>sendRequest(request);
            }).whenCompleteAsync((response, ex) -> {
                pendingPublishes.decrementAndGet();

                if (ex != null) {
                    logger.warn("Publish service failure: {}", ex.getMessage(), ex);

                    StatusCode statusCode = StatusCode.BAD;
                    if (ex instanceof UaException) {
                        statusCode = ((UaException) ex).getStatusCode();
                    } else if (ex.getCause() instanceof UaException) {
                        statusCode = ((UaException) ex.getCause()).getStatusCode();
                    }

                    if (statusCode.getValue() != StatusCodes.Bad_NoSubscription &&
                            statusCode.getValue() != StatusCodes.Bad_TooManyPublishRequests) {
                        maybeSendPublishRequest();
                    }

                    // TODO Re-book-keep the SubscriptionAcknowledgements
                } else {
                    processingQueue.submit(() -> onPublishComplete(response));

                    maybeSendPublishRequest();
                }

            }, client.getConfig().getExecutorService());
        } else {
            pendingPublishes.decrementAndGet();
        }
    }

    private void onPublishComplete(PublishResponse response) {
        UInteger subscriptionId = response.getSubscriptionId();
        OpcUaSubscription subscription = subscriptions.get(subscriptionId);

        if (subscription == null) return;

        NotificationMessage notificationMessage = response.getNotificationMessage();

        long sequenceNumber = notificationMessage.getSequenceNumber().longValue();
        long expectedSequenceNumber = subscription.getLastSequenceNumber() + 1;

        if (sequenceNumber > expectedSequenceNumber) {
            logger.warn("[id={}] expected sequence={}, received sequence={}. Calling Republish service...",
                    subscriptionId, expectedSequenceNumber, sequenceNumber);

            processingQueue.pause();
            processingQueue.submitToHead(() -> onPublishComplete(response));

            republish(subscriptionId, expectedSequenceNumber, sequenceNumber).whenComplete((v, ex) -> {
                if (ex != null) {
                    logger.warn("Republish service failed; reading values for subscriptionId={}: {}",
                            subscriptionId, ex.getMessage(), ex);

                    List<OpcUaMonitoredItem> items = Optional.ofNullable(subscriptions.get(subscriptionId))
                            .map(s -> newArrayList(s.getItems().values()))
                            .orElse(newArrayList());

                    List<ReadValueId> values = items.stream()
                            .map(OpcUaMonitoredItem::getReadValueId)
                            .collect(Collectors.toList());

                    // TODO Use Server's time + publishTime in queued responses to figure out what can be ignored?
                    client.read(0.0d, TimestampsToReturn.Both, values).whenComplete((rr, rx) -> {
                        if (rr != null) {
                            DataValue[] results = rr.getResults();

                            for (int i = 0; i < items.size(); i++) {
                                OpcUaMonitoredItem item = items.get(i);
                                DataValue value = results[i];

                                item.onValueArrived(value);
                            }
                        } else {
                            // TODO re-reading nodes failed, reconnect?
                        }

                        // We've read the latest values, resume processing.
                        subscription.setLastSequenceNumber(sequenceNumber - 1);
                        processingQueue.resume();
                    });
                } else {
                    // Republish succeeded, resume processing.
                    subscription.setLastSequenceNumber(sequenceNumber - 1);
                    processingQueue.resume();
                }
            });

            return;
        }

        subscription.setLastSequenceNumber(sequenceNumber);

        response.getResults(); // TODO

        synchronized (acknowledgements) {
            for (UInteger available : response.getAvailableSequenceNumbers()) {
                acknowledgements.add(new SubscriptionAcknowledgement(subscriptionId, available));
            }
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

        logger.info("onNotificationMessage(), subscriptionId={}, sequenceNumber={}, publishTime={}",
                subscriptionId, notificationMessage.getSequenceNumber(), publishTime);

        Map<UInteger, OpcUaMonitoredItem> items = Optional.ofNullable(subscriptions.get(subscriptionId))
                .map(OpcUaSubscription::getItems)
                .orElse(Maps.newHashMap());

        for (ExtensionObject xo : notificationMessage.getNotificationData()) {
            Object o = xo.getObject();

            if (o instanceof DataChangeNotification) {
                DataChangeNotification dcn = (DataChangeNotification) o;

                logger.info("Received {} MonitoredItemNotifications", dcn.getMonitoredItems().length);

                for (MonitoredItemNotification min : dcn.getMonitoredItems()) {
                    logger.trace("MonitoredItemNotification: clientHandle={}, value={}",
                            min.getClientHandle(), min.getValue());

                    OpcUaMonitoredItem item = items.get(min.getClientHandle());
                    if (item != null) item.onValueArrived(min.getValue());
                    else logger.warn("no item for clientHandle=" + min.getClientHandle());
                }
            } else if (o instanceof EventNotificationList) {
                EventNotificationList enl = (EventNotificationList) o;

                for (EventFieldList efl : enl.getEvents()) {
                    logger.trace("EventFieldList: clientHandle={}, values={}",
                            efl.getClientHandle(), Arrays.toString(efl.getEventFields()));

                    OpcUaMonitoredItem item = items.get(efl.getClientHandle());
                    if (item != null) item.onEventArrived(efl.getEventFields());
                }
            } else if (o instanceof StatusChangeNotification) {
                StatusChangeNotification scn = (StatusChangeNotification) o;

                logger.info("StatusChangeNotification: {}", scn.getStatus());
            }
        }
    }

}
