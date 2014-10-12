/*
 * OPC-UA SDK
 *
 * Copyright (C) 2014 Kevin Herron
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

package com.inductiveautomation.opcua.sdk.server;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.server.api.EventItem;
import com.inductiveautomation.opcua.sdk.server.api.MonitoredItem;
import com.inductiveautomation.opcua.sdk.server.api.SampledItem;
import com.inductiveautomation.opcua.sdk.server.api.nodes.Node;
import com.inductiveautomation.opcua.sdk.server.items.BaseMonitoredItem;
import com.inductiveautomation.opcua.sdk.server.items.EventMonitoredItem;
import com.inductiveautomation.opcua.sdk.server.items.SampledMonitoredItem;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequest;
import com.inductiveautomation.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.enumerated.MonitoringMode;
import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateMonitoredItemsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateMonitoredItemsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSubscriptionRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSubscriptionResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteMonitoredItemsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteMonitoredItemsResponse;
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
import com.inductiveautomation.opcua.stack.core.types.structured.SetTriggeringRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.SetTriggeringResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.SubscriptionAcknowledgement;
import com.inductiveautomation.opcua.stack.core.util.ExecutionQueue;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import static com.inductiveautomation.opcua.sdk.server.util.ConversionUtil.a;

public class Subscription {

    private static final double FastestPublishingInterval = 100.0;

    private final Map<Long, BaseMonitoredItem<?>> itemsById = Maps.newConcurrentMap();
    private final Map<Long, TriggeringLinks> linksById = Maps.newConcurrentMap();

    private final Map<Long, NotificationMessage> sentNotifications = Maps.newConcurrentMap();

    private final AtomicLong itemIds = new AtomicLong(1L);

    private final NamespaceManager namespaceManager;
    private final ExecutionQueue<Runnable> executionQueue;
    private final AtomicReference<SubscriptionState> subscriptionState = new AtomicReference<>();

    private volatile double publishingInterval;
    private volatile long lifetimeCount;
    private volatile long maxKeepAliveCount;
    private volatile long maxNotificationsPerPublish;
    private volatile boolean publishingEnabled;

    private final long subscriptionId;
    private final Queue<ServiceRequest<PublishRequest, PublishResponse>> publishingQueue;
    private final Map<Long, StatusCode[]> acknowledgements;

    public Subscription(Long subscriptionId,
                        Queue<ServiceRequest<PublishRequest, PublishResponse>> publishingQueue,
                        Map<Long, StatusCode[]> acknowledgements,
                        OpcUaServer server) {

        this.subscriptionId = subscriptionId;
        this.publishingQueue = publishingQueue;
        this.acknowledgements = acknowledgements;

        namespaceManager = server.getNamespaceManager();
        executionQueue = new ExecutionQueue<>(ExecutionQueue.RUNNABLE_EXECUTOR, server.getExecutorService());
    }

    void addStateListener(SubscriptionState.StateListener listener) {
        executionQueue.submit(() -> {
            SubscriptionState state = subscriptionState.get();
            state.addStateListener(listener);
        });
    }

    public void acknowledge(List<SubscriptionAcknowledgement> pending, CompletableFuture<List<StatusCode>> future) {
        executionQueue.submit(() -> {
            List<StatusCode> results = pending.stream().map(ack -> {
                long sequenceNumber = ack.getSequenceNumber();
                NotificationMessage notification = sentNotifications.remove(sequenceNumber);

                if (notification != null) {
                    return StatusCode.Good;
                } else {
                    return new StatusCode(StatusCodes.Bad_SequenceNumberUnknown);
                }
            }).collect(Collectors.toList());

            future.complete(results);
        });
    }

    public void createSubscription(ServiceRequest<CreateSubscriptionRequest, CreateSubscriptionResponse> serviceRequest) {
        executionQueue.submit(() -> {
            CreateSubscriptionRequest request = serviceRequest.getRequest();

            this.publishingInterval = Math.max(request.getRequestedPublishingInterval(), FastestPublishingInterval);
            this.lifetimeCount = request.getRequestedLifetimeCount();
            this.maxKeepAliveCount = request.getRequestedMaxKeepAliveCount();
            this.maxNotificationsPerPublish = request.getMaxNotificationsPerPublish();
            this.publishingEnabled = request.getPublishingEnabled();

            subscriptionState.set(new SubscriptionState(this, executionQueue));
            subscriptionState.get().startPublishingTimer();

            ResponseHeader header = serviceRequest.createResponseHeader();

            CreateSubscriptionResponse response = new CreateSubscriptionResponse(
                    header, subscriptionId,
                    getPublishingInterval(),
                    getLifetimeCount(),
                    getMaxKeepAliveCount()
            );

            serviceRequest.setResponse(response);
        });
    }

    public void modifySubscription(ServiceRequest<ModifySubscriptionRequest, ModifySubscriptionResponse> serviceRequest) {
        executionQueue.submit(() -> {
            ModifySubscriptionRequest request = serviceRequest.getRequest();

            this.lifetimeCount = request.getRequestedLifetimeCount();
            this.maxKeepAliveCount = request.getRequestedMaxKeepAliveCount();
            this.publishingInterval = Math.max(request.getRequestedPublishingInterval(), FastestPublishingInterval);

            ResponseHeader header = serviceRequest.createResponseHeader();

            ModifySubscriptionResponse response = new ModifySubscriptionResponse(
                    header,
                    getPublishingInterval(),
                    getLifetimeCount(),
                    getMaxKeepAliveCount()
            );

            serviceRequest.setResponse(response);
        });
    }

    public void deleteSubscription() {
        executionQueue.submit(() -> {
            subscriptionState.get().setState(SubscriptionState.State.Closed);

        	/*
             * Notify namespaces that all items have been deleted.
             */
            Map<Integer, List<BaseMonitoredItem<?>>> byNamespace = itemsById.values().stream()
                    .collect(Collectors.groupingBy(item -> item.getReadValueId().getNodeId().getNamespaceIndex()));

            byNamespace.entrySet().forEach(entry -> {
                int namespaceIndex = entry.getKey();
                List<BaseMonitoredItem<?>> items = entry.getValue();

                List<SampledItem> sampled = items.stream()
                        .filter(i -> i instanceof SampledItem)
                        .map(i -> (SampledItem) i)
                        .collect(Collectors.toList());

                List<EventItem> event = items.stream()
                        .filter(i -> i instanceof EventItem)
                        .map(i -> (EventItem) i)
                        .collect(Collectors.toList());

                namespaceManager.getNamespace(namespaceIndex).onSampledItemsDeleted(sampled);
                namespaceManager.getNamespace(namespaceIndex).onEventItemsDeleted(event);
            });
        });
    }

    public void setPublishingMode(boolean publishingEnabled, CompletableFuture<StatusCode> result) {
        executionQueue.submit(() -> {
            this.publishingEnabled = publishingEnabled;
            result.complete(StatusCode.Good);
        });
    }

    public void publish(ServiceRequest<PublishRequest, PublishResponse> service) {
        executionQueue.submit(() -> subscriptionState.get().onPublish(service));
    }

    public void republish(ServiceRequest<RepublishRequest, RepublishResponse> service) {
        executionQueue.submit(() -> {
            long sequenceNumber = service.getRequest().getRetransmitSequenceNumber();
            NotificationMessage message = sentNotifications.get(sequenceNumber);

            if (message != null) {
                ResponseHeader header = service.createResponseHeader();
                RepublishResponse response = new RepublishResponse(header, message);
                service.setResponse(response);
            } else {
                service.setServiceFault(StatusCodes.Bad_MessageNotAvailable);
            }
        });
    }

    public void createMonitoredItems(ServiceRequest<CreateMonitoredItemsRequest, CreateMonitoredItemsResponse> service) {
        executionQueue.submit(() -> {
            TimestampsToReturn timestamps = service.getRequest().getTimestampsToReturn();
            MonitoredItemCreateRequest[] itemsToCreate = service.getRequest().getItemsToCreate();

            /*
             * Create items and store the partially-created results.
             */
            List<BaseMonitoredItem<?>> createdItems = Lists.newArrayList();

            List<Supplier<MonitoredItemCreateResult>> resultSuppliers = Arrays.stream(itemsToCreate)
                    .map(request -> createItem(request, timestamps, createdItems))
                    .collect(Collectors.toList());

            /*
             * Notify namespaces of the items we just created.
             */
            Map<Integer, List<BaseMonitoredItem<?>>> byNamespace = createdItems.stream()
                    .collect(Collectors.groupingBy(item -> item.getReadValueId().getNodeId().getNamespaceIndex()));

            byNamespace.entrySet().forEach(entry -> {
                int namespaceIndex = entry.getKey();
                List<BaseMonitoredItem<?>> items = entry.getValue();

                List<SampledItem> sampled = items.stream()
                        .filter(i -> i instanceof SampledMonitoredItem)
                        .map(i -> (SampledMonitoredItem) i)
                        .collect(Collectors.toList());

                List<EventItem> event = items.stream()
                        .filter(i -> i instanceof EventMonitoredItem)
                        .map(i -> (EventMonitoredItem) i)
                        .collect(Collectors.toList());

                namespaceManager.getNamespace(namespaceIndex).onSampledItemsCreated(sampled);
                namespaceManager.getNamespace(namespaceIndex).onEventItemsCreated(event);
            });

            /*
             * Build and return the final results now that namespaces have had a chance to revise items.
             */
            createdItems.stream().forEach(item -> itemsById.put(item.getId(), item));

            List<MonitoredItemCreateResult> results = resultSuppliers.stream()
                    .map(Supplier::get).collect(Collectors.toList());

            ResponseHeader header = service.createResponseHeader();
            CreateMonitoredItemsResponse response = new CreateMonitoredItemsResponse(
                    header, a(results, MonitoredItemCreateResult.class), new DiagnosticInfo[0]);

            service.setResponse(response);
        });
    }

    private Supplier<MonitoredItemCreateResult> createItem(MonitoredItemCreateRequest request,
                                                           TimestampsToReturn timestamps,
                                                           List<BaseMonitoredItem<?>> createdItems) {

        NodeId nodeId = request.getItemToMonitor().getNodeId();
        Long attributeId = request.getItemToMonitor().getAttributeId();

        try {
            Node node = namespaceManager.getNode(nodeId)
                    .orElseThrow(() -> new UaException(StatusCodes.Bad_NodeIdUnknown));

            if (node.hasAttribute(attributeId)) {
                BaseMonitoredItem<?> item;

                if (attributeId == AttributeIds.EventNotifier) {
                    throw new UaException(StatusCodes.Bad_AttributeIdInvalid); // TODO Create event item
                } else {
                    item = SampledMonitoredItem.create(
                            itemIds.getAndIncrement(),
                            request.getItemToMonitor(),
                            request.getMonitoringMode(),
                            timestamps,
                            request.getRequestedParameters()
                    );
                }

                createdItems.add(item);

                return () -> new MonitoredItemCreateResult(
                        StatusCode.Good,
                        item.getId(),
                        item.getSamplingInterval(),
                        item.getQueueSize(),
                        item.getFilterResult()
                );
            } else {
                throw new UaException(StatusCodes.Bad_AttributeIdInvalid);
            }
        } catch (UaException e) {
            return () -> new MonitoredItemCreateResult(e.getStatusCode(), 0L, 0d, 0L, null);
        }
    }

    public void modifyMonitoredItems(ServiceRequest<ModifyMonitoredItemsRequest, ModifyMonitoredItemsResponse> service) {
        executionQueue.submit(() -> {
            TimestampsToReturn timestamps = service.getRequest().getTimestampsToReturn();
            MonitoredItemModifyRequest[] itemsToModify = service.getRequest().getItemsToModify();

            /*
             * Modify items and store the partially-created results.
             */
            List<Supplier<MonitoredItemModifyResult>> resultSuppliers = Arrays.stream(itemsToModify)
                    .map(request -> modifyItem(request, timestamps))
                    .collect(Collectors.toList());

            /*
             * Notify namespaces of the items we just modified.
             */
            List<BaseMonitoredItem<?>> modifiedItems = Arrays.stream(itemsToModify)
                    .map(request -> itemsById.get(request.getMonitoredItemId()))
                    .filter(item -> item != null)
                    .collect(Collectors.toList());

            Map<Integer, List<BaseMonitoredItem<?>>> byNamespace = modifiedItems.stream()
                    .collect(Collectors.groupingBy(item -> item.getReadValueId().getNodeId().getNamespaceIndex()));

            byNamespace.entrySet().forEach(entry -> {
                int namespaceIndex = entry.getKey();
                List<BaseMonitoredItem<?>> items = entry.getValue();

                List<SampledItem> sampled = items.stream()
                        .filter(i -> i instanceof SampledMonitoredItem)
                        .map(i -> (SampledMonitoredItem) i)
                        .collect(Collectors.toList());

                List<EventItem> event = items.stream()
                        .filter(i -> i instanceof EventMonitoredItem)
                        .map(i -> (EventMonitoredItem) i)
                        .collect(Collectors.toList());

                namespaceManager.getNamespace(namespaceIndex).onSampledItemsModified(sampled);
                namespaceManager.getNamespace(namespaceIndex).onEventItemsModified(event);
            });

            /*
             * Build and return the final results now that namespaces have had a chance to revise item modifications.
             */
            List<MonitoredItemModifyResult> results = resultSuppliers.stream()
                    .map(Supplier::get).collect(Collectors.toList());

            ResponseHeader header = service.createResponseHeader();
            ModifyMonitoredItemsResponse response = new ModifyMonitoredItemsResponse(
                    header, a(results, MonitoredItemModifyResult.class), new DiagnosticInfo[0]);

            service.setResponse(response);
        });
    }

    private Supplier<MonitoredItemModifyResult> modifyItem(MonitoredItemModifyRequest request, TimestampsToReturn timestamps) {
        long itemId = request.getMonitoredItemId();
        MonitoringParameters parameters = request.getRequestedParameters();

        try {
            BaseMonitoredItem<?> item = Optional.ofNullable(itemsById.get(itemId))
                    .orElseThrow(() -> new UaException(StatusCodes.Bad_MonitoredItemIdInvalid));

            item.modify(parameters, timestamps);

            return () -> new MonitoredItemModifyResult(
                    StatusCode.Good,
                    item.getSamplingInterval(),
                    item.getQueueSize(),
                    item.getFilterResult()
            );
        } catch (UaException e) {
            return () -> new MonitoredItemModifyResult(e.getStatusCode(), 0d, 0L, null);
        }
    }

    public void deleteMonitoredItems(ServiceRequest<DeleteMonitoredItemsRequest, DeleteMonitoredItemsResponse> service) {
        executionQueue.submit(() -> {
            Long[] itemsToDelete = service.getRequest().getMonitoredItemIds();

            /*
             * Remove each monitored item, if it exists.
             */
            List<StatusCode> results = Lists.newArrayList();
            List<BaseMonitoredItem<?>> deleted = Lists.newArrayList();

            for (Long itemId : itemsToDelete) {
                BaseMonitoredItem<?> item = itemsById.remove(itemId);
                if (item != null) {
                    deleted.add(item);
                    results.add(StatusCode.Good);
                } else {
                    results.add(new StatusCode(StatusCodes.Bad_MonitoredItemIdInvalid));
                }
            }

            /*
             * Notify namespaces of the items that have been deleted.
             */
            Map<Integer, List<BaseMonitoredItem<?>>> byNamespace = deleted.stream()
                    .collect(Collectors.groupingBy(item -> item.getReadValueId().getNodeId().getNamespaceIndex()));

            byNamespace.entrySet().forEach(entry -> {
                int namespaceIndex = entry.getKey();
                List<BaseMonitoredItem<?>> items = entry.getValue();

                List<SampledItem> sampled = items.stream()
                        .filter(i -> i instanceof SampledMonitoredItem)
                        .map(i -> (SampledMonitoredItem) i)
                        .collect(Collectors.toList());

                List<EventItem> event = items.stream()
                        .filter(i -> i instanceof EventMonitoredItem)
                        .map(i -> (EventMonitoredItem) i)
                        .collect(Collectors.toList());

                namespaceManager.getNamespace(namespaceIndex).onSampledItemsDeleted(sampled);
                namespaceManager.getNamespace(namespaceIndex).onEventItemsDeleted(event);
            });

            /*
             * Build and return results.
             */
            ResponseHeader header = service.createResponseHeader();
            DeleteMonitoredItemsResponse response = new DeleteMonitoredItemsResponse(
                    header, a(results, StatusCode.class), new DiagnosticInfo[0]);

            service.setResponse(response);
        });
    }

    public void setMonitoringMode(ServiceRequest<SetMonitoringModeRequest, SetMonitoringModeResponse> service) {
        executionQueue.submit(() -> {
            Long[] itemsToModify = service.getRequest().getMonitoredItemIds();
            MonitoringMode monitoringMode = service.getRequest().getMonitoringMode();

            /*
             * Set MonitoringMode on each monitored item, if it exists.
             */
            List<StatusCode> results = Lists.newArrayList();
            List<BaseMonitoredItem<?>> modified = Lists.newArrayList();

            for (Long itemId : itemsToModify) {
                BaseMonitoredItem<?> item = itemsById.get(itemId);
                if (item != null) {
                    item.setMonitoringMode(monitoringMode);
                    modified.add(item);
                    results.add(StatusCode.Good);
                } else {
                    results.add(new StatusCode(StatusCodes.Bad_MonitoredItemIdInvalid));
                }
            }

            /*
             * Notify namespaces of the items whose MonitoringMode has been modified.
             */
            Map<Integer, List<MonitoredItem>> byNamespace = modified.stream()
                    .collect(Collectors.groupingBy(item -> item.getReadValueId().getNodeId().getNamespaceIndex()));

            byNamespace.keySet().forEach(namespaceIndex -> {
                List<MonitoredItem> items = byNamespace.get(namespaceIndex);
                namespaceManager.getNamespace(namespaceIndex).onMonitoringModeChanged(items);
            });

            /*
             * Build and return results.
             */
            ResponseHeader header = service.createResponseHeader();
            SetMonitoringModeResponse response = new SetMonitoringModeResponse(
                    header, a(results, StatusCode.class), new DiagnosticInfo[0]);

            service.setResponse(response);
        });
    }

    public void setTriggering(ServiceRequest<SetTriggeringRequest, SetTriggeringResponse> service) {
        executionQueue.submit(() -> {
            long triggerId = service.getRequest().getTriggeringItemId();

            try {
                BaseMonitoredItem<?> trigger = itemsById.get(triggerId);

                if (trigger == null) {
                    throw new UaException(StatusCodes.Bad_MonitoredItemIdInvalid);
                }

                TriggeringLinks links = linksById.computeIfAbsent(triggerId, id -> new TriggeringLinks(trigger));

                List<StatusCode> addResults = Arrays.stream(service.getRequest().getLinksToAdd())
                        .map(id -> {
                            BaseMonitoredItem<?> item = itemsById.get(id);
                            if (item != null) {
                                links.getTriggeredItems().put(id, item);
                                return StatusCode.Good;
                            } else {
                                return new StatusCode(StatusCodes.Bad_MonitoredItemIdInvalid);
                            }
                        })
                        .collect(Collectors.toList());

                List<StatusCode> removeResults = Arrays.stream(service.getRequest().getLinksToRemove())
                        .map(id -> {
                            BaseMonitoredItem<?> item = links.getTriggeredItems().remove(id);
                            if (item != null) {
                                return StatusCode.Good;
                            } else {
                                return new StatusCode(StatusCodes.Bad_MonitoredItemIdInvalid);
                            }
                        })
                        .collect(Collectors.toList());

                if (links.isEmpty()) {
                    linksById.remove(triggerId);
                }

                SetTriggeringResponse response = new SetTriggeringResponse(
                        service.createResponseHeader(),
                        addResults.toArray(new StatusCode[addResults.size()]),
                        new DiagnosticInfo[0],
                        removeResults.toArray(new StatusCode[removeResults.size()]),
                        new DiagnosticInfo[0]
                );

                service.setResponse(response);
            } catch (UaException e) {
                service.setServiceFault(e.getStatusCode());
            }
        });
    }

    public Queue<ServiceRequest<PublishRequest, PublishResponse>> getPublishingQueue() {
        return publishingQueue;
    }

    public Map<Long, StatusCode[]> getAcknowledgements() {
        return acknowledgements;
    }

    public Map<Long, BaseMonitoredItem<?>> getItemsById() {
        return itemsById;
    }

    public Map<Long, TriggeringLinks> getLinksById() {
        return linksById;
    }

    public Map<Long, NotificationMessage> getSentNotifications() {
        return sentNotifications;
    }

    Long getSubscriptionId() {
        return subscriptionId;
    }

    double getPublishingInterval() {
        return publishingInterval;
    }

    Long getLifetimeCount() {
        return lifetimeCount;
    }

    Long getMaxKeepAliveCount() {
        return maxKeepAliveCount;
    }

    Long getMaxNotificationsPerPublish() {
        return maxNotificationsPerPublish;
    }

    boolean isPublishingEnabled() {
        return publishingEnabled;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "subscriptionId=" + subscriptionId +
                ", publishingInterval=" + publishingInterval +
                ", itemCount=" + itemsById.size() +
                ", state=" + subscriptionState.get().getState() +
                '}';
    }
}
