/*
 * Copyright 2014
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

package com.inductiveautomation.opcua.sdk.server.subscriptions;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.core.NumericRange;
import com.inductiveautomation.opcua.sdk.server.OpcUaServer;
import com.inductiveautomation.opcua.sdk.server.Session;
import com.inductiveautomation.opcua.sdk.server.api.DataItem;
import com.inductiveautomation.opcua.sdk.server.api.EventItem;
import com.inductiveautomation.opcua.sdk.server.api.MonitoredItem;
import com.inductiveautomation.opcua.sdk.server.api.nodes.Node;
import com.inductiveautomation.opcua.sdk.server.items.BaseMonitoredItem;
import com.inductiveautomation.opcua.sdk.server.items.MonitoredEventItem;
import com.inductiveautomation.opcua.sdk.server.items.MonitoredDataItem;
import com.inductiveautomation.opcua.sdk.server.subscriptions.Subscription.State;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequest;
import com.inductiveautomation.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UShort;
import com.inductiveautomation.opcua.stack.core.types.enumerated.MonitoringMode;
import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;
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
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoredItemCreateResult;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoredItemModifyRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoredItemModifyResult;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoringParameters;
import com.inductiveautomation.opcua.stack.core.types.structured.NotificationMessage;
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

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class SubscriptionManager {

    public static final double MIN_SAMPLING_INTERVAL = 100;
    public static final double MAX_SAMPLING_INTERVAL = 60 * 60 * 1000;

    private static final AtomicLong SUBSCRIPTION_IDS = new AtomicLong(0L);

    private static UInteger nextSubscriptionId() {
        return uint(SUBSCRIPTION_IDS.incrementAndGet());
    }

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<UInteger, StatusCode[]> acknowledgeResults = Maps.newConcurrentMap();

    private final PublishQueue publishQueue = new PublishQueue();

    private final Map<UInteger, Subscription> subscriptions = Maps.newConcurrentMap();

    private final Session session;
    private final OpcUaServer server;

    public SubscriptionManager(Session session, OpcUaServer server) {
        this.session = session;
        this.server = server;
    }

    public void createSubscription(ServiceRequest<CreateSubscriptionRequest, CreateSubscriptionResponse> service) {
        CreateSubscriptionRequest request = service.getRequest();

        UInteger subscriptionId = nextSubscriptionId();

        Subscription subscription = new Subscription(
                this,
                publishQueue,
                subscriptionId,
                request.getRequestedPublishingInterval(),
                request.getRequestedMaxKeepAliveCount().longValue(),
                request.getRequestedLifetimeCount().longValue(),
                request.getMaxNotificationsPerPublish().longValue(),
                request.getPublishingEnabled(),
                request.getPriority().intValue()
        );

        subscriptions.put(subscriptionId, subscription);

        subscription.addStateListener((s, ps, cs) -> {
            if (cs == State.Closed) {
                subscriptions.remove(s.getId());
            }
        });

        subscription.startPublishingTimer();

        ResponseHeader header = service.createResponseHeader();

        CreateSubscriptionResponse response = new CreateSubscriptionResponse(
                header, subscriptionId,
                subscription.getPublishingInterval(),
                uint(subscription.getLifetimeCount()),
                uint(subscription.getMaxKeepAliveCount())
        );

        service.setResponse(response);
    }

    public void modifySubscription(ServiceRequest<ModifySubscriptionRequest, ModifySubscriptionResponse> service) {
        ModifySubscriptionRequest request = service.getRequest();
        UInteger subscriptionId = request.getSubscriptionId();

        try {
            Subscription subscription = subscriptions.get(subscriptionId);

            if (subscription == null) {
                throw new UaException(StatusCodes.Bad_SubscriptionIdInvalid);
            }

            subscription.modifySubscription(request);

            ResponseHeader header = service.createResponseHeader();

            ModifySubscriptionResponse response = new ModifySubscriptionResponse(
                    header,
                    subscription.getPublishingInterval(),
                    uint(subscription.getLifetimeCount()),
                    uint(subscription.getMaxKeepAliveCount())
            );

            service.setResponse(response);
        } catch (UaException e) {
            service.setServiceFault(e);
        }
    }

    public void deleteSubscription(ServiceRequest<DeleteSubscriptionsRequest, DeleteSubscriptionsResponse> service) {
        DeleteSubscriptionsRequest request = service.getRequest();
        UInteger[] subscriptionIds = request.getSubscriptionIds();

        if (subscriptionIds.length == 0) {
            service.setServiceFault(StatusCodes.Bad_NothingToDo);
            return;
        }

        StatusCode[] results = new StatusCode[subscriptionIds.length];

        for (int i = 0; i < subscriptionIds.length; i++) {
            Subscription subscription = subscriptions.remove(subscriptionIds[i]);

            if (subscription != null) {
                List<BaseMonitoredItem<?>> deletedItems = subscription.deleteSubscription();

                /*
                * Notify namespaces of the items we just deleted.
                */

                Map<UShort, List<BaseMonitoredItem<?>>> byNamespace = deletedItems.stream()
                        .collect(Collectors.groupingBy(item -> item.getReadValueId().getNodeId().getNamespaceIndex()));

                byNamespace.entrySet().forEach(entry -> {
                    UShort namespaceIndex = entry.getKey();

                    List<BaseMonitoredItem<?>> items = entry.getValue();
                    List<DataItem> dataItems = Lists.newArrayList();
                    List<EventItem> eventItems = Lists.newArrayList();


                    for (BaseMonitoredItem<?> item : items) {
                        if (item instanceof MonitoredDataItem) {
                            dataItems.add((DataItem) item);
                        } else if (item instanceof MonitoredEventItem) {
                            eventItems.add((EventItem) item);
                        }
                    }

                    if (!dataItems.isEmpty()) {
                        server.getNamespaceManager().getNamespace(namespaceIndex).onDataItemsDeleted(dataItems);
                    }
                    if (!eventItems.isEmpty()) {
                        server.getNamespaceManager().getNamespace(namespaceIndex).onEventItemsDeleted(eventItems);
                    }
                });

                results[i] = StatusCode.Good;
            } else {
                results[i] = new StatusCode(StatusCodes.Bad_SubscriptionIdInvalid);
            }
        }

        ResponseHeader header = service.createResponseHeader();
        DeleteSubscriptionsResponse response = new DeleteSubscriptionsResponse(
                header, results, new DiagnosticInfo[0]);

        service.setResponse(response);

        while (subscriptions.isEmpty() && publishQueue.isNotEmpty()) {
            ServiceRequest<PublishRequest, PublishResponse> publishService = publishQueue.poll();
            if (publishService != null) {
                publishService.setServiceFault(StatusCodes.Bad_NoSubscription);
            }
        }
    }

    public void setPublishingMode(ServiceRequest<SetPublishingModeRequest, SetPublishingModeResponse> service) {
        SetPublishingModeRequest request = service.getRequest();
        UInteger[] subscriptionIds = request.getSubscriptionIds();
        StatusCode[] results = new StatusCode[subscriptionIds.length];

        for (int i = 0; i < subscriptionIds.length; i++) {
            Subscription subscription = subscriptions.get(subscriptionIds[i]);
            if (subscription == null) {
                results[i] = new StatusCode(StatusCodes.Bad_SubscriptionIdInvalid);
            } else {
                subscription.setPublishingMode(request);
                results[i] = StatusCode.Good;
            }
        }

        ResponseHeader header = service.createResponseHeader();
        SetPublishingModeResponse response = new SetPublishingModeResponse(
                header, results, new DiagnosticInfo[0]);

        service.setResponse(response);
    }

    public void createMonitoredItems(ServiceRequest<CreateMonitoredItemsRequest, CreateMonitoredItemsResponse> service) {
        CreateMonitoredItemsRequest request = service.getRequest();
        UInteger subscriptionId = request.getSubscriptionId();

        try {
            Subscription subscription = subscriptions.get(subscriptionId);
            TimestampsToReturn timestamps = service.getRequest().getTimestampsToReturn();
            MonitoredItemCreateRequest[] itemsToCreate = service.getRequest().getItemsToCreate();

            if (subscription == null) {
                throw new UaException(StatusCodes.Bad_SubscriptionIdInvalid);
            }
            if (timestamps == null) {
                throw new UaException(StatusCodes.Bad_TimestampsToReturnInvalid);
            }
            if (itemsToCreate.length == 0) {
                throw new UaException(StatusCodes.Bad_NothingToDo);
            }

            MonitoredItemCreateResult[] createResults = new MonitoredItemCreateResult[itemsToCreate.length];
            List<BaseMonitoredItem<?>> createdItems = Lists.newArrayListWithCapacity(itemsToCreate.length);

            synchronized (subscription) {
                for (int i = 0; i < itemsToCreate.length; i++) {
                    MonitoredItemCreateRequest createRequest = itemsToCreate[i];
                    NodeId nodeId = createRequest.getItemToMonitor().getNodeId();
                    UInteger attributeId = createRequest.getItemToMonitor().getAttributeId();

                    try {
                        Node node = server.getNamespaceManager().getNode(nodeId)
                                .orElseThrow(() -> new UaException(StatusCodes.Bad_NodeIdUnknown));

                        if (node.hasAttribute(attributeId)) {
                            BaseMonitoredItem<?> item;

                            if (attributeId.intValue() == AttributeIds.EventNotifier) {
                                throw new UaException(StatusCodes.Bad_AttributeIdInvalid); // TODO Create event item
                            } else {
                                MonitoringParameters parameters = createRequest.getRequestedParameters();

                                double samplingInterval = parameters.getSamplingInterval();
                                if (samplingInterval < 0) samplingInterval = subscription.getPublishingInterval();
                                if (samplingInterval < MIN_SAMPLING_INTERVAL) samplingInterval = MIN_SAMPLING_INTERVAL;
                                if (samplingInterval > MAX_SAMPLING_INTERVAL) samplingInterval = MAX_SAMPLING_INTERVAL;

                                String indexRange = createRequest.getItemToMonitor().getIndexRange();
                                if (indexRange != null) {
                                    NumericRange.parse(indexRange);
                                }

                                item = new MonitoredDataItem(
                                        uint(subscription.nextItemId()),
                                        createRequest.getItemToMonitor(),
                                        createRequest.getMonitoringMode(),
                                        timestamps,
                                        parameters.getClientHandle(),
                                        samplingInterval,
                                        parameters.getFilter(),
                                        parameters.getQueueSize(),
                                        parameters.getDiscardOldest()
                                );
                            }

                            createdItems.add(item);

                            createResults[i] = new MonitoredItemCreateResult(
                                    StatusCode.Good,
                                    item.getId(),
                                    item.getSamplingInterval(),
                                    uint(item.getQueueSize()),
                                    item.getFilterResult()
                            );
                        } else {
                            throw new UaException(StatusCodes.Bad_AttributeIdInvalid);
                        }
                    } catch (UaException e) {
                        createResults[i] = new MonitoredItemCreateResult(e.getStatusCode(), uint(0), 0d, uint(0), null);
                    }
                }

                subscription.addMonitoredItems(createdItems);
            }

            /*
             * Notify namespaces of the items we just created.
             */

            Map<UShort, List<BaseMonitoredItem<?>>> byNamespace = createdItems.stream()
                    .collect(Collectors.groupingBy(item -> item.getReadValueId().getNodeId().getNamespaceIndex()));

            byNamespace.entrySet().forEach(entry -> {
                UShort namespaceIndex = entry.getKey();

                List<BaseMonitoredItem<?>> items = entry.getValue();
                List<DataItem> dataItems = Lists.newArrayList();
                List<EventItem> eventItems = Lists.newArrayList();


                for (BaseMonitoredItem<?> item : items) {
                    if (item instanceof MonitoredDataItem) {
                        dataItems.add((DataItem) item);
                    } else if (item instanceof MonitoredEventItem) {
                        eventItems.add((EventItem) item);
                    }
                }

                if (!dataItems.isEmpty()) {
                    server.getNamespaceManager().getNamespace(namespaceIndex).onDataItemsCreated(dataItems);
                }
                if (!eventItems.isEmpty()) {
                    server.getNamespaceManager().getNamespace(namespaceIndex).onEventItemsCreated(eventItems);
                }
            });

            /*
             * Build and return the final results now that namespaces have had a chance to revise items.
             */

            ResponseHeader header = service.createResponseHeader();
            CreateMonitoredItemsResponse response = new CreateMonitoredItemsResponse(
                    header, createResults, new DiagnosticInfo[0]);

            service.setResponse(response);
        } catch (UaException e) {
            service.setServiceFault(e);
        }
    }

    public void modifyMonitoredItems(ServiceRequest<ModifyMonitoredItemsRequest, ModifyMonitoredItemsResponse> service) {
        ModifyMonitoredItemsRequest request = service.getRequest();
        UInteger subscriptionId = request.getSubscriptionId();

        try {
            Subscription subscription = subscriptions.get(subscriptionId);
            TimestampsToReturn timestamps = service.getRequest().getTimestampsToReturn();
            MonitoredItemModifyRequest[] itemsToModify = service.getRequest().getItemsToModify();

            if (subscription == null) {
                throw new UaException(StatusCodes.Bad_SubscriptionIdInvalid);
            }
            if (timestamps == null) {
                throw new UaException(StatusCodes.Bad_TimestampsToReturnInvalid);
            }
            if (itemsToModify.length == 0) {
                throw new UaException(StatusCodes.Bad_NothingToDo);
            }

            /*
             * Modify requested items and prepare results.
             */

            MonitoredItemModifyResult[] modifyResults = new MonitoredItemModifyResult[itemsToModify.length];
            List<BaseMonitoredItem<?>> modifiedItems = Lists.newArrayListWithCapacity(itemsToModify.length);

            synchronized (subscription) {
                for (int i = 0; i < itemsToModify.length; i++) {
                    MonitoredItemModifyRequest modifyRequest = itemsToModify[i];
                    UInteger itemId = modifyRequest.getMonitoredItemId();
                    MonitoringParameters parameters = modifyRequest.getRequestedParameters();

                    BaseMonitoredItem<?> item = subscription.getMonitoredItems().get(itemId);

                    if (item == null) {
                        modifyResults[i] = new MonitoredItemModifyResult(
                                new StatusCode(StatusCodes.Bad_MonitoredItemIdInvalid),
                                0d, uint(0), null
                        );
                    } else {
                        double samplingInterval = parameters.getSamplingInterval();
                        if (samplingInterval < 0) samplingInterval = subscription.getPublishingInterval();
                        if (samplingInterval < MIN_SAMPLING_INTERVAL) samplingInterval = MIN_SAMPLING_INTERVAL;
                        if (samplingInterval > MAX_SAMPLING_INTERVAL) samplingInterval = MAX_SAMPLING_INTERVAL;

                        item.modify(
                                timestamps,
                                parameters.getClientHandle(),
                                samplingInterval,
                                parameters.getFilter(),
                                parameters.getQueueSize(),
                                parameters.getDiscardOldest()
                        );

                        modifiedItems.add(item);

                        modifyResults[i] = new MonitoredItemModifyResult(
                                StatusCode.Good,
                                item.getSamplingInterval(),
                                uint(item.getQueueSize()),
                                item.getFilterResult()
                        );
                    }
                }

                subscription.resetLifetimeCounter();
            }

            /*
             * Notify namespaces of the items we just modified.
             */

            Map<UShort, List<BaseMonitoredItem<?>>> byNamespace = modifiedItems.stream()
                    .collect(Collectors.groupingBy(item -> item.getReadValueId().getNodeId().getNamespaceIndex()));

            byNamespace.entrySet().forEach(entry -> {
                UShort namespaceIndex = entry.getKey();

                List<BaseMonitoredItem<?>> items = entry.getValue();
                List<DataItem> dataItems = Lists.newArrayList();
                List<EventItem> eventItems = Lists.newArrayList();


                for (BaseMonitoredItem<?> item : items) {
                    if (item instanceof MonitoredDataItem) {
                        dataItems.add((DataItem) item);
                    } else if (item instanceof MonitoredEventItem) {
                        eventItems.add((EventItem) item);
                    }
                }

                if (!dataItems.isEmpty()) {
                    server.getNamespaceManager().getNamespace(namespaceIndex).onDataItemsModified(dataItems);
                }
                if (!eventItems.isEmpty()) {
                    server.getNamespaceManager().getNamespace(namespaceIndex).onEventItemsModified(eventItems);
                }
            });

            /*
             * Namespaces have been notified; send response.
             */

            ResponseHeader header = service.createResponseHeader();
            ModifyMonitoredItemsResponse response = new ModifyMonitoredItemsResponse(
                    header, modifyResults, new DiagnosticInfo[0]);

            service.setResponse(response);
        } catch (UaException e) {
            service.setServiceFault(e);
        }
    }

    public void deleteMonitoredItems(ServiceRequest<DeleteMonitoredItemsRequest, DeleteMonitoredItemsResponse> service) {
        DeleteMonitoredItemsRequest request = service.getRequest();
        UInteger subscriptionId = request.getSubscriptionId();

        try {
            Subscription subscription = subscriptions.get(subscriptionId);
            UInteger[] itemsToDelete = service.getRequest().getMonitoredItemIds();

            if (subscription == null) {
                throw new UaException(StatusCodes.Bad_SubscriptionIdInvalid);
            }
            if (itemsToDelete.length == 0) {
                throw new UaException(StatusCodes.Bad_NothingToDo);
            }

            StatusCode[] deleteResults = new StatusCode[itemsToDelete.length];
            List<BaseMonitoredItem<?>> deletedItems = Lists.newArrayListWithCapacity(itemsToDelete.length);

            synchronized (subscription) {
                for (int i = 0; i < itemsToDelete.length; i++) {
                    UInteger itemId = itemsToDelete[i];
                    BaseMonitoredItem<?> item = subscription.getMonitoredItems().get(itemId);

                    if (item == null) {
                        deleteResults[i] = new StatusCode(StatusCodes.Bad_MonitoredItemIdInvalid);
                    } else {
                        deletedItems.add(item);

                        deleteResults[i] = StatusCode.Good;
                    }
                }

                subscription.removeMonitoredItems(deletedItems);
            }

            /*
             * Notify namespaces of the items that have been deleted.
             */

            Map<UShort, List<BaseMonitoredItem<?>>> byNamespace = deletedItems.stream()
                    .collect(Collectors.groupingBy(item -> item.getReadValueId().getNodeId().getNamespaceIndex()));

            byNamespace.entrySet().forEach(entry -> {
                UShort namespaceIndex = entry.getKey();

                List<BaseMonitoredItem<?>> items = entry.getValue();
                List<DataItem> dataItems = Lists.newArrayList();
                List<EventItem> eventItems = Lists.newArrayList();

                for (BaseMonitoredItem<?> item : items) {
                    if (item instanceof MonitoredDataItem) {
                        dataItems.add((DataItem) item);
                    } else if (item instanceof MonitoredEventItem) {
                        eventItems.add((EventItem) item);
                    }
                }

                if (!dataItems.isEmpty()) {
                    server.getNamespaceManager().getNamespace(namespaceIndex).onDataItemsDeleted(dataItems);
                }
                if (!eventItems.isEmpty()) {
                    server.getNamespaceManager().getNamespace(namespaceIndex).onEventItemsDeleted(eventItems);
                }
            });

            /*
             * Build and return results.
             */
            ResponseHeader header = service.createResponseHeader();
            DeleteMonitoredItemsResponse response = new DeleteMonitoredItemsResponse(
                    header, deleteResults, new DiagnosticInfo[0]);

            service.setResponse(response);
        } catch (UaException e) {
            service.setServiceFault(e);
        }
    }

    public void setMonitoringMode(ServiceRequest<SetMonitoringModeRequest, SetMonitoringModeResponse> service) {
        SetMonitoringModeRequest request = service.getRequest();
        UInteger subscriptionId = request.getSubscriptionId();

        try {
            Subscription subscription = subscriptions.get(subscriptionId);
            UInteger[] itemsToModify = request.getMonitoredItemIds();

            if (subscription == null) {
                throw new UaException(StatusCodes.Bad_SubscriptionIdInvalid);
            }
            if (itemsToModify.length == 0) {
                throw new UaException(StatusCodes.Bad_NothingToDo);
            }

            /*
             * Set MonitoringMode on each monitored item, if it exists.
             */

            MonitoringMode monitoringMode = request.getMonitoringMode();
            StatusCode[] results = new StatusCode[itemsToModify.length];
            List<BaseMonitoredItem<?>> modified = Lists.newArrayListWithCapacity(itemsToModify.length);

            for (int i = 0; i < itemsToModify.length; i++) {
                UInteger itemId = itemsToModify[i];
                BaseMonitoredItem<?> item = subscription.getMonitoredItems().get(itemId);

                if (item != null) {
                    item.setMonitoringMode(monitoringMode);

                    modified.add(item);

                    results[i] = StatusCode.Good;
                } else {
                    results[i] = new StatusCode(StatusCodes.Bad_MonitoredItemIdInvalid);
                }
            }

            /*
             * Notify namespaces of the items whose MonitoringMode has been modified.
             */

            Map<UShort, List<MonitoredItem>> byNamespace = modified.stream()
                    .collect(Collectors.groupingBy(item -> item.getReadValueId().getNodeId().getNamespaceIndex()));

            byNamespace.keySet().forEach(namespaceIndex -> {
                List<MonitoredItem> items = byNamespace.get(namespaceIndex);
                server.getNamespaceManager().getNamespace(namespaceIndex).onMonitoringModeChanged(items);
            });

            /*
             * Build and return results.
             */

            ResponseHeader header = service.createResponseHeader();
            SetMonitoringModeResponse response = new SetMonitoringModeResponse(
                    header, results, new DiagnosticInfo[0]);

            service.setResponse(response);
        } catch (UaException e) {
            service.setServiceFault(e);
        }
    }

    public void publish(ServiceRequest<PublishRequest, PublishResponse> service) {
        PublishRequest request = service.getRequest();

        if (subscriptions.isEmpty()) {
            service.setServiceFault(StatusCodes.Bad_NoSubscription);
            return;
        }

        SubscriptionAcknowledgement[] acknowledgements = request.getSubscriptionAcknowledgements();
        StatusCode[] results = new StatusCode[acknowledgements.length];

        for (int i = 0; i < acknowledgements.length; i++) {
            SubscriptionAcknowledgement acknowledgement = acknowledgements[i];


            UInteger sequenceNumber = acknowledgement.getSequenceNumber();
            UInteger subscriptionId = acknowledgement.getSubscriptionId();

            logger.debug("Acknowledging sequenceNumber={} on subscriptionId={}", sequenceNumber, subscriptionId);

            Subscription subscription = subscriptions.get(subscriptionId);

            if (subscription == null) {
                results[i] = new StatusCode(StatusCodes.Bad_SubscriptionIdInvalid);
            } else {
                results[i] = subscription.acknowledge(sequenceNumber);
            }
        }

        acknowledgeResults.put(request.getRequestHeader().getRequestHandle(), results);

        publishQueue.addRequest(service);
    }

    public void republish(ServiceRequest<RepublishRequest, RepublishResponse> service) {
        RepublishRequest request = service.getRequest();

        if (subscriptions.isEmpty()) {
            service.setServiceFault(StatusCodes.Bad_SubscriptionIdInvalid);
            return;
        }

        UInteger subscriptionId = request.getSubscriptionId();
        Subscription subscription = subscriptions.get(subscriptionId);

        if (subscription == null) {
            service.setServiceFault(StatusCodes.Bad_SubscriptionIdInvalid);
            return;
        }

        UInteger sequenceNumber = request.getRetransmitSequenceNumber();
        NotificationMessage notificationMessage = subscription.republish(sequenceNumber);

        if (notificationMessage == null) {
            service.setServiceFault(StatusCodes.Bad_MessageNotAvailable);
            return;
        }

        ResponseHeader header = service.createResponseHeader();
        RepublishResponse response = new RepublishResponse(header, notificationMessage);

        service.setResponse(response);
    }

    public void setTriggering(ServiceRequest<SetTriggeringRequest, SetTriggeringResponse> service) {
        // TODO
        service.setServiceFault(StatusCodes.Bad_ServiceUnsupported);
    }

    public void sessionClosed(boolean deleteSubscriptions) {

    }

    StatusCode[] getAcknowledgeResults(UInteger requestHandle) {
        return acknowledgeResults.remove(requestHandle);
    }

}
