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

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.inductiveautomation.opcua.sdk.server.services.ServiceAttributes;
import com.inductiveautomation.opcua.sdk.server.util.Pending;
import com.inductiveautomation.opcua.sdk.server.util.PendingAck;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequest;
import com.inductiveautomation.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateMonitoredItemsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateMonitoredItemsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSubscriptionRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSubscriptionResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteMonitoredItemsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteMonitoredItemsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteSubscriptionsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteSubscriptionsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.ModifyMonitoredItemsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ModifyMonitoredItemsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.ModifySubscriptionRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ModifySubscriptionResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.PublishRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.PublishResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.RepublishRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.RepublishResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.ResponseHeader;
import com.inductiveautomation.opcua.stack.core.types.structured.SetMonitoringModeRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.SetMonitoringModeResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.SetPublishingModeRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.SetPublishingModeResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.SetTriggeringRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.SetTriggeringResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.SubscriptionAcknowledgement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.inductiveautomation.opcua.sdk.server.util.ConversionUtil.a;
import static com.inductiveautomation.opcua.sdk.server.util.FutureUtils.sequence;
import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class SubscriptionManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Queue<ServiceRequest<PublishRequest, PublishResponse>> publishingQueue = new ConcurrentLinkedQueue<>();
    private final Map<UInteger, StatusCode[]> acknowledgements = Maps.newConcurrentMap();

    private final Subscriptions subscriptions = new Subscriptions();

    private final SubscriptionState.StateListener stateListener = (s, previousState, currentState) -> {
        logger.debug("Subscription id={} changed state: {} -> {}",
                s.getSubscriptionId(), previousState, currentState);

        if (currentState == SubscriptionState.State.Closed) {
            subscriptions.remove(s.getSubscriptionId());
        }
    };

    public void createSubscription(ServiceRequest<CreateSubscriptionRequest, CreateSubscriptionResponse> service) {
        OpcUaServer server = service.attr(ServiceAttributes.ServerKey).get();

        UInteger subscriptionId = nextSubscriptionId();

        Subscription subscription = new Subscription(
                subscriptionId,
                publishingQueue,
                acknowledgements,
                server
        );

        subscription.createSubscription(service);
        subscription.addStateListener(stateListener);

        subscriptions.add(subscription);
    }

    public void modifySubscription(ServiceRequest<ModifySubscriptionRequest, ModifySubscriptionResponse> service) {
        ModifySubscriptionRequest request = service.getRequest();
        UInteger subscriptionId = request.getSubscriptionId();

        try {
            Subscription subscription = subscriptions
                    .get(subscriptionId)
                    .orElseThrow(() -> new UaException(StatusCodes.Bad_SubscriptionIdInvalid));

            subscription.modifySubscription(service);
        } catch (UaException e) {
            service.setServiceFault(e);
        }
    }

    public void deleteSubscription(ServiceRequest<DeleteSubscriptionsRequest, DeleteSubscriptionsResponse> service) {

        DeleteSubscriptionsRequest request = service.getRequest();
        UInteger[] subscriptionIds = request.getSubscriptionIds();

        List<StatusCode> results = Arrays.stream(subscriptionIds)
                .map(subscriptions::remove)
                .map(subscription -> {
                    subscription.ifPresent(Subscription::deleteSubscription);

                    return subscription.isPresent() ?
                            StatusCode.Good : new StatusCode(StatusCodes.Bad_SubscriptionIdInvalid);
                })
                .collect(Collectors.toList());

        ResponseHeader header = service.createResponseHeader();
        DeleteSubscriptionsResponse response = new DeleteSubscriptionsResponse(
                header, a(results, StatusCode.class), new DiagnosticInfo[0]);

        service.setResponse(response);
    }

    public void setPublishingMode(ServiceRequest<SetPublishingModeRequest, SetPublishingModeResponse> service) {

        SetPublishingModeRequest request = service.getRequest();

        List<CompletableFuture<StatusCode>> results = Arrays.stream(request.getSubscriptionIds())
                .map(id -> {
                    try {
                        Subscription subscription = subscriptions.get(id)
                                .orElseThrow(() -> new UaException(StatusCodes.Bad_SubscriptionIdInvalid));

                        CompletableFuture<StatusCode> result = new CompletableFuture<>();
                        subscription.setPublishingMode(request.getPublishingEnabled(), result);

                        return result;
                    } catch (UaException e) {
                        return CompletableFuture.completedFuture(e.getStatusCode());
                    }
                })
                .collect(Collectors.toList());

        sequence(results).thenAccept(rs -> {
            ResponseHeader header = service.createResponseHeader();
            SetPublishingModeResponse response = new SetPublishingModeResponse(
                    header, a(rs, StatusCode.class), new DiagnosticInfo[0]);

            service.setResponse(response);
        });
    }

    public void publish(ServiceRequest<PublishRequest, PublishResponse> service) {
        PublishRequest request = service.getRequest();
        UInteger requestHandle = request.getRequestHeader().getRequestHandle();

        if (acknowledgements.containsKey(requestHandle)) {
            deliverPublish(service);
        } else {
            processPublish(service);
        }
    }

    private void processPublish(ServiceRequest<PublishRequest, PublishResponse> service) {
        PublishRequest request = service.getRequest();

        List<PendingAck> pendingAcks = Arrays.stream(request.getSubscriptionAcknowledgements())
                .map(PendingAck::new)
                .collect(Collectors.toList());

        Map<UInteger, List<PendingAck>> bySubscription =
                pendingAcks.stream().collect(Collectors.groupingBy(ack -> ack.getInput().getSubscriptionId()));

        bySubscription.keySet().forEach(id -> {
            List<PendingAck> pending = bySubscription.get(id);

            List<SubscriptionAcknowledgement> acks = pending.stream()
                    .map(PendingAck::getInput)
                    .collect(Collectors.toList());

            CompletableFuture<List<StatusCode>> future = Pending.callback(pending);

            try {
                subscriptions.get(id)
                        .orElseThrow(() -> new UaException(StatusCodes.Bad_SubscriptionIdInvalid))
                        .acknowledge(acks, future);

            } catch (UaException e) {
                List<StatusCode> values = Collections.nCopies(acks.size(), e.getStatusCode());
                future.complete(values);
            }
        });

        List<CompletableFuture<StatusCode>> futures = pendingAcks.stream()
                .map(PendingAck::getFuture)
                .collect(Collectors.toList());

        sequence(futures).thenAccept(values -> {
            UInteger requestHandle = request.getRequestHeader().getRequestHandle();

            acknowledgements.put(requestHandle, values.toArray(new StatusCode[values.size()]));

            publish(service);
        });
    }

    private void deliverPublish(ServiceRequest<PublishRequest, PublishResponse> service) {
        Iterator<Subscription> iterator = subscriptions.iterator();

        if (iterator.hasNext()) {
            iterator.next().publish(service);
        } else {
            service.setServiceFault(StatusCodes.Bad_NoSubscription);
        }
    }

    public void republish(ServiceRequest<RepublishRequest, RepublishResponse> service) {
        RepublishRequest request = service.getRequest();
        UInteger subscriptionId = request.getSubscriptionId();

        try {
            Subscription subscription = subscriptions
                    .get(subscriptionId)
                    .orElseThrow(() -> new UaException(StatusCodes.Bad_SubscriptionIdInvalid));

            subscription.republish(service);
        } catch (UaException e) {
            service.setServiceFault(e);
        }
    }

    public void createMonitoredItems(ServiceRequest<CreateMonitoredItemsRequest, CreateMonitoredItemsResponse> service) {
        CreateMonitoredItemsRequest request = service.getRequest();
        UInteger subscriptionId = request.getSubscriptionId();

        try {
            Subscription subscription = subscriptions
                    .get(subscriptionId)
                    .orElseThrow(() -> new UaException(StatusCodes.Bad_SubscriptionIdInvalid));

            subscription.createMonitoredItems(service);
        } catch (UaException e) {
            service.setServiceFault(e);
        }
    }

    public void modifyMonitoredItems(ServiceRequest<ModifyMonitoredItemsRequest, ModifyMonitoredItemsResponse> service) {
        ModifyMonitoredItemsRequest request = service.getRequest();
        UInteger subscriptionId = request.getSubscriptionId();

        try {
            Subscription subscription = subscriptions
                    .get(subscriptionId)
                    .orElseThrow(() -> new UaException(StatusCodes.Bad_SubscriptionIdInvalid));

            subscription.modifyMonitoredItems(service);
        } catch (UaException e) {
            service.setServiceFault(e);
        }
    }

    public void deleteMonitoredItems(ServiceRequest<DeleteMonitoredItemsRequest, DeleteMonitoredItemsResponse> service) {
        DeleteMonitoredItemsRequest request = service.getRequest();
        UInteger subscriptionId = request.getSubscriptionId();

        try {
            Subscription subscription = subscriptions
                    .get(subscriptionId)
                    .orElseThrow(() -> new UaException(StatusCodes.Bad_SubscriptionIdInvalid));

            subscription.deleteMonitoredItems(service);
        } catch (UaException e) {
            service.setServiceFault(e);
        }
    }

    public void setMonitoringMode(ServiceRequest<SetMonitoringModeRequest, SetMonitoringModeResponse> service) {
        SetMonitoringModeRequest request = service.getRequest();
        UInteger subscriptionId = request.getSubscriptionId();

        try {
            Subscription subscription = subscriptions
                    .get(subscriptionId)
                    .orElseThrow(() -> new UaException(StatusCodes.Bad_SubscriptionIdInvalid));

            subscription.setMonitoringMode(service);
        } catch (UaException e) {
            service.setServiceFault(e);
        }
    }

    public void setTriggering(ServiceRequest<SetTriggeringRequest, SetTriggeringResponse> service) {
        SetTriggeringRequest request = service.getRequest();
        UInteger subscriptionId = request.getSubscriptionId();

        try {
            Subscription subscription = subscriptions
                    .get(subscriptionId)
                    .orElseThrow(() -> new UaException(StatusCodes.Bad_SubscriptionIdInvalid));

            subscription.setTriggering(service);
        } catch (UaException e) {
            service.setServiceFault(e);
        }
    }

    void sessionClosed(boolean deleteSubscriptions) {
        logger.debug("Session closed, deleteSubscriptions={}", deleteSubscriptions);

        if (deleteSubscriptions) {
            subscriptions.copyOfSubscriptions().forEach(Subscription::deleteSubscription);
        } else {
            // TODO do something with the orphaned subscriptions so they can be potentially transferred?
        }
    }

    private static final AtomicLong SubscriptionIds = new AtomicLong(0L);

    private static UInteger nextSubscriptionId() {
        return uint(SubscriptionIds.incrementAndGet());
    }

    private static class Subscriptions {

        private final Map<UInteger, Subscription> subscriptions = Maps.newConcurrentMap();
        private volatile Iterator<Subscription> iterator = Iterators.cycle(subscriptions.values());

        void add(Subscription subscription) {
            subscriptions.put(subscription.getSubscriptionId(), subscription);
            iterator = Iterators.cycle(subscriptions.values());
        }

        Optional<Subscription> get(UInteger subscriptionId) {
            return Optional.ofNullable(subscriptions.get(subscriptionId));
        }

        Optional<Subscription> remove(UInteger subscriptionId) {
            Subscription subscription = subscriptions.remove(subscriptionId);
            iterator = Iterators.cycle(subscriptions.values());

            return Optional.ofNullable(subscription);
        }

        Iterator<Subscription> iterator() {
            return iterator;
        }

        List<Subscription> copyOfSubscriptions() {
            return Lists.newArrayList(subscriptions.values());
        }

    }
}
