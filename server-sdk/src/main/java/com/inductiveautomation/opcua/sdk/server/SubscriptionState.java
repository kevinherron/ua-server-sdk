/*
 * Copyright 2014 Inductive Automation
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

package com.inductiveautomation.opcua.sdk.server;

import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.inductiveautomation.opcua.sdk.server.api.MonitoredDataItem;
import com.inductiveautomation.opcua.sdk.server.items.BaseMonitoredItem;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequest;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExtensionObject;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.structured.DataChangeNotification;
import com.inductiveautomation.opcua.stack.core.types.structured.EventFieldList;
import com.inductiveautomation.opcua.stack.core.types.structured.EventNotificationList;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoredItemNotification;
import com.inductiveautomation.opcua.stack.core.types.structured.NotificationMessage;
import com.inductiveautomation.opcua.stack.core.types.structured.PublishRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.PublishResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.ResponseHeader;
import com.inductiveautomation.opcua.stack.core.types.structured.StatusChangeNotification;
import com.inductiveautomation.opcua.stack.core.util.ExecutionQueue;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import com.google.common.math.DoubleMath;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages the subscription state table and variables defined in OPC-UA Part 4, 5.13.1.2 and 5.13.1.3.
 */
public class SubscriptionState {

    private static final ThreadFactory ThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("shared-subscription-timer-%d")
            .setDaemon(true)
            .build();

    private static final ScheduledExecutorService SharedScheduledExecutor =
            Executors.newSingleThreadScheduledExecutor(ThreadFactory);

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AtomicLong sequenceNumber = new AtomicLong(1L);

    private final List<StateListener> stateListeners = Lists.newCopyOnWriteArrayList();
    private volatile State state = State.Normal;

    private volatile boolean messageSent = true;
    private volatile boolean moreNotifications = false;
    private volatile long keepAliveCounter;
    private volatile long lifetimeCounter;

    private volatile PeekingIterator<BaseMonitoredItem<?>> lastIterator =
            Iterators.peekingIterator(Iterators.emptyIterator());

    private final PublishHandler publishHandler = new PublishHandler();
    private final TimerHandler timerHandler = new TimerHandler();

    private final Subscription subscription;
    private final ExecutionQueue<Runnable> executionQueue;

    public SubscriptionState(Subscription subscription, ExecutionQueue<Runnable> executionQueue) {
        this.subscription = subscription;
        this.executionQueue = executionQueue;

        keepAliveCounter = subscription.getMaxKeepAliveCount();
        lifetimeCounter = subscription.getLifetimeCount();
    }

    void addStateListener(StateListener listener) {
        stateListeners.add(listener);
    }

    /**
     * Handle an incoming {@link PublishRequest}.
     * <p>
     * Must be called on the {@link ExecutionQueue}.
     *
     * @param service The service request that contains the {@link PublishRequest}.
     */
    void onPublish(ServiceRequest<PublishRequest, PublishResponse> service) {
        logger.trace("onPublish(), state=" + state);

        if (state == State.Normal) publishHandler.whenNormal(service);
        else if (state == State.KeepAlive) publishHandler.whenKeepAlive(service);
        else if (state == State.Late) publishHandler.whenLate(service);
        else if (state == State.Closing) publishHandler.whenClosing(service);
        else if (state == State.Closed) logger.debug("onPublish(), state=" + state); // No-op.
        else throw new RuntimeException("Unhandled subscription state: " + state);
    }

    /**
     * The publishing timer has elapsed.
     * <p>
     * Must be called on the {@link ExecutionQueue}.
     */
    private void onPublishingTimer() {
        logger.trace("onPublishingTimer(), state=" + state);

        if (state == State.Normal) timerHandler.whenNormal();
        else if (state == State.KeepAlive) timerHandler.whenKeepAlive();
        else if (state == State.Late) timerHandler.whenLate();
        else if (state == State.Closed) logger.debug("onPublish(), state=" + state); // No-op.
        else throw new RuntimeException("unhandled subscription state: " + state);
    }

    private long currentSequenceNumber() {
        return sequenceNumber.get();
    }

    private long nextSequenceNumber() {
        return sequenceNumber.getAndIncrement();
    }

    private boolean publishingEnabled() {
        return subscription.isPublishingEnabled();
    }

    private boolean notificationsAvailable() {
        return subscription.getItemsById().values().stream().anyMatch(BaseMonitoredItem::hasNotifications);
    }

    private boolean publishRequestQueued() {
        return !subscription.getPublishingQueue().isEmpty();
    }

    private void enqueue(ServiceRequest<PublishRequest, PublishResponse> service) {
        subscription.getPublishingQueue().add(service);
    }

    private Optional<ServiceRequest<PublishRequest, PublishResponse>> dequeue() {
        return Optional.ofNullable(subscription.getPublishingQueue().poll());
    }

    private void resetKeepAliveCounter() {
        keepAliveCounter = subscription.getMaxKeepAliveCount();
    }

    private void resetLifetimeCounter() {
        lifetimeCounter = subscription.getLifetimeCount();
    }

    private void returnKeepAlive(ServiceRequest<PublishRequest, PublishResponse> serviceRequest) {
        ResponseHeader header = serviceRequest.createResponseHeader();

        Set<Long> keys = subscription.getSentNotifications().keySet();
        Long[] available = keys.toArray(new Long[keys.size()]);

        NotificationMessage notificationMessage = new NotificationMessage(
                currentSequenceNumber(), DateTime.now(), new ExtensionObject[0]);

        long requestHandle = serviceRequest.getRequest().getRequestHeader().getRequestHandle();
        StatusCode[] results = subscription.getAcknowledgements().get(requestHandle);

        PublishResponse response = new PublishResponse(
                header, subscription.getSubscriptionId(),
                available, moreNotifications, notificationMessage,
                results, null);

        serviceRequest.setResponse(response);

        logger.debug("Sent KeepAlive (sequence={}) PublishResponse.", notificationMessage.getSequenceNumber());
    }

    private void returnNotifications(ServiceRequest<PublishRequest, PublishResponse> service) {
        PeekingIterator<BaseMonitoredItem<?>> currentIterator = Iterators.peekingIterator(
                subscription.getItemsById().values().stream()
                        .filter(BaseMonitoredItem::hasNotifications)
                        .collect(Collectors.toList())
                        .iterator()
        );

        moreNotifications = gatherAndSend(currentIterator, Optional.of(service));

        lastIterator = lastIterator.hasNext() ? lastIterator :
                (currentIterator.hasNext() ? currentIterator : Iterators.peekingIterator(Iterators.emptyIterator()));
    }


    /**
     * Gather {@link MonitoredItemNotification}s and send them using {@code service}, if present.
     *
     * @param currentIterator an {@link Iterator} of the current {@link MonitoredDataItem}s.
     * @param service         a {@link ServiceRequest}, if available.
     * @return {@code true} if there are more notifications available for sending.
     */
    private boolean gatherAndSend(PeekingIterator<BaseMonitoredItem<?>> currentIterator,
                                  Optional<ServiceRequest<PublishRequest, PublishResponse>> service) {

        if (service.isPresent()) {
            List<UaStructure> notifications = Lists.newArrayList();

            while (notifications.size() < maxNotifications() && (lastIterator.hasNext() || currentIterator.hasNext())) {
                BaseMonitoredItem<?> item = lastIterator.hasNext() ? lastIterator.peek() : currentIterator.peek();

                int remaining = gather(item, notifications);

                TriggeringLinks link = subscription.getLinksById().get(item.getId());

                if (link != null) {
                    int triggeredRemaining = link.getTriggeredItems().values().stream()
                            .collect(Collectors.summingInt(i -> gather(i, notifications)));

                    remaining += triggeredRemaining;
                }

                if (remaining == 0) {
                    if (lastIterator.hasNext()) lastIterator.next();
                    else if (currentIterator.hasNext()) currentIterator.next();
                }
            }

            boolean more = lastIterator.hasNext() || currentIterator.hasNext();
            sendPublishResponse(service.get(), notifications, more);

            return more && gatherAndSend(currentIterator, dequeue());
        } else {
            return true;
        }
    }

    private int gather(BaseMonitoredItem<?> item, List<UaStructure> notifications) {
        int max = maxNotifications() - notifications.size();

        return item.getNotifications(notifications, max);
    }

    private int maxNotifications() {
        int iv = subscription.getMaxNotificationsPerPublish().intValue();
        return iv <= 0 ? Integer.MAX_VALUE : iv;
    }

    private void sendPublishResponse(ServiceRequest<PublishRequest, PublishResponse> service,
                                     List<UaStructure> notifications,
                                     boolean more) {

        Map<NodeId, List<UaStructure>> byTypeId = notifications.stream()
                .collect(Collectors.groupingBy(UaStructure::getTypeId));

        Object[] dataNotifications = byTypeId.get(MonitoredItemNotification.TypeId).stream()
                .map(s -> (MonitoredItemNotification) s).toArray();

        Object[] eventNotifications = byTypeId.get(EventFieldList.TypeId).stream()
                .map(s -> (EventFieldList) s).toArray();

        List<ExtensionObject> notificationData = Lists.newArrayList();

        if (dataNotifications.length > 0) {
            DataChangeNotification dataChange = new DataChangeNotification(
                    (MonitoredItemNotification[]) dataNotifications, new DiagnosticInfo[0]);

            notificationData.add(new ExtensionObject(dataChange));
        }

        if (eventNotifications.length > 0) {
            EventNotificationList eventChange = new EventNotificationList((EventFieldList[]) eventNotifications);

            notificationData.add(new ExtensionObject(eventChange));
        }

        NotificationMessage notificationMessage = new NotificationMessage(
                nextSequenceNumber(),
                new DateTime(),
                notificationData.toArray(new ExtensionObject[notificationData.size()])
        );

        subscription.getSentNotifications().put(notificationMessage.getSequenceNumber(), notificationMessage);

        Set<Long> keys = subscription.getSentNotifications().keySet();
        Long[] available = keys.toArray(new Long[keys.size()]);

        Long requestHandle = service.getRequest().getRequestHeader().getRequestHandle();
        StatusCode[] results = subscription.getAcknowledgements().get(requestHandle);

        ResponseHeader header = service.createResponseHeader();

        PublishResponse response = new PublishResponse(
                header, subscription.getSubscriptionId(),
                available, more, notificationMessage,
                results, new DiagnosticInfo[0]);

        service.setResponse(response);

        logger.trace("Sent NotificationMessage (sequence={}) PublishResponse.",
                notificationMessage.getSequenceNumber());
    }

    private void sendStatusChangeNotification(ServiceRequest<PublishRequest, PublishResponse> service) {
        logger.debug("Subscription (id={}) issuing StatusChangeNotification.", subscription.getSubscriptionId());

        StatusChangeNotification statusChange = new StatusChangeNotification(
                new StatusCode(StatusCodes.Bad_Timeout), null);

        NotificationMessage notificationMessage = new NotificationMessage(
                nextSequenceNumber(),
                new DateTime(),
                new ExtensionObject[]{new ExtensionObject(statusChange)}
        );

        ResponseHeader header = service.createResponseHeader();

        PublishResponse response = new PublishResponse(
                header, subscription.getSubscriptionId(),
                new Long[0], false, notificationMessage,
                new StatusCode[0], new DiagnosticInfo[0]);

        service.setResponse(response);
    }

    State getState() {
        return this.state;
    }

    void setState(State state) {
        State previousState = this.state;
        this.state = state;

        for (StateListener stateListener : stateListeners) {
            stateListener.onStateChange(subscription, previousState, state);
        }
    }

    void startPublishingTimer() {
        if (state == State.Closed) return;

        lifetimeCounter--;

        if (lifetimeCounter <= 1) {
            logger.debug("Subscription (id={}) lifetime expired.", subscription.getSubscriptionId());

            setState(State.Closing);
        } else {
            long interval = DoubleMath.roundToLong(subscription.getPublishingInterval(), RoundingMode.UP);

            SharedScheduledExecutor.schedule(
                    () -> executionQueue.submit(this::onPublishingTimer),
                    interval,
                    TimeUnit.MILLISECONDS);
        }
    }

    private class PublishHandler {
        private void whenNormal(ServiceRequest<PublishRequest, PublishResponse> service) {
            /* Subscription State Table Row 4 */
            if (!publishingEnabled() || (publishingEnabled() && !moreNotifications)) {
                enqueue(service);
            }
            /* Subscription State Table Row 5 */
            else if (publishingEnabled() && moreNotifications) {
                resetLifetimeCounter();
                returnNotifications(service);
                messageSent = true;
            } else {
                throw new IllegalStateException("unhandled subscription state");
            }
        }

        private void whenLate(ServiceRequest<PublishRequest, PublishResponse> service) {
            /* Subscription State Table Row 10 */
            if (publishingEnabled() && (notificationsAvailable() || moreNotifications)) {
                setState(State.Normal);
                resetLifetimeCounter();
                returnNotifications(service);
                messageSent = true;
            }
            /* Subscription State Table Row 11 */
            else if (!publishingEnabled() ||
                    (publishingEnabled() && !notificationsAvailable() && !moreNotifications)) {
                setState(State.KeepAlive);
                resetLifetimeCounter();
                returnKeepAlive(service);
                messageSent = true;
            }
        }

        private void whenKeepAlive(ServiceRequest<PublishRequest, PublishResponse> service) {
            /* Subscription State Table Row 13 */
            enqueue(service);
        }

        private void whenClosing(ServiceRequest<PublishRequest, PublishResponse> service) {
            sendStatusChangeNotification(service);

            setState(State.Closed);
        }
    }

    private class TimerHandler {
        private void whenNormal() {
            /* Subscription State Table Row 6 */
            if (publishRequestQueued() && publishingEnabled() && notificationsAvailable()) {
                Optional<ServiceRequest<PublishRequest, PublishResponse>> service = dequeue();

                if (service.isPresent()) {
                    resetLifetimeCounter();
                    startPublishingTimer();
                    returnNotifications(service.get());
                    messageSent = true;
                } else {
                    whenNormal();
                }
            }
            /* Subscription State Table Row 7 */
            else if (publishRequestQueued() && !messageSent &&
                    (!publishingEnabled() || (publishingEnabled() && !notificationsAvailable()))) {
                Optional<ServiceRequest<PublishRequest, PublishResponse>> service = dequeue();

                if (service.isPresent()) {
                    resetLifetimeCounter();
                    startPublishingTimer();
                    returnKeepAlive(service.get());
                    messageSent = true;
                } else {
                    whenNormal();
                }
            }
            /* Subscription State Table Row 8 */
            else if (!publishRequestQueued() && (!messageSent || (publishingEnabled() && notificationsAvailable()))) {
                setState(State.Late);
                startPublishingTimer();
            }
            /* Subscription State Table Row 9 */
            else if (messageSent && (!publishingEnabled() || (publishingEnabled() && !notificationsAvailable()))) {
                setState(State.KeepAlive);
                resetKeepAliveCounter();
                startPublishingTimer();
            }

        }

        private void whenLate() {
            /* Subscription State Table Row 12 */
            startPublishingTimer();
        }

        private void whenKeepAlive() {
            /* Subscription State Table Row 14 */
            if (publishingEnabled() && notificationsAvailable() && publishRequestQueued()) {
                Optional<ServiceRequest<PublishRequest, PublishResponse>> service = dequeue();

                if (service.isPresent()) {
                    setState(State.Normal);
                    resetLifetimeCounter();
                    startPublishingTimer();
                    returnNotifications(service.get());
                    messageSent = true;
                } else {
                    whenKeepAlive();
                }
            }
            /* Subscription State Table Row 15 */
            else if (publishRequestQueued() && keepAliveCounter == 1 &&
                    (!publishingEnabled() || (publishingEnabled() && !notificationsAvailable()))) {

                Optional<ServiceRequest<PublishRequest, PublishResponse>> service = dequeue();

                if (service.isPresent()) {
                    startPublishingTimer();
                    returnKeepAlive(service.get());
                    resetKeepAliveCounter();
                } else {
                    whenKeepAlive();
                }
            }
            /* Subscription State Table Row 16 */
            else if (keepAliveCounter > 1 &&
                    (!publishingEnabled() || (publishingEnabled() && !notificationsAvailable()))) {

                startPublishingTimer();
                keepAliveCounter--;
            }
            /* Subscription State Table Row 17 */
            else if (!publishRequestQueued() &&
                    (keepAliveCounter == 1 ||
                            (keepAliveCounter > 1 && publishingEnabled() && notificationsAvailable()))) {

                setState(State.Late);
                startPublishingTimer();
            }
        }
    }

    public static enum State {
        Closing,
        Closed,
        Normal,
        KeepAlive,
        Late
    }

    public static interface StateListener {
        void onStateChange(Subscription subscription, State previousState, State currentState);
    }

}
