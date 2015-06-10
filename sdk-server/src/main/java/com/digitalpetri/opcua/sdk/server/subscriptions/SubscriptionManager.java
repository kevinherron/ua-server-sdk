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

package com.digitalpetri.opcua.sdk.server.subscriptions;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.digitalpetri.opcua.sdk.core.AccessLevel;
import com.digitalpetri.opcua.sdk.core.AttributeId;
import com.digitalpetri.opcua.sdk.core.AttributeIds;
import com.digitalpetri.opcua.sdk.core.NumericRange;
import com.digitalpetri.opcua.sdk.server.DiagnosticsContext;
import com.digitalpetri.opcua.sdk.server.OpcUaServer;
import com.digitalpetri.opcua.sdk.server.Session;
import com.digitalpetri.opcua.sdk.server.api.AttributeManager.ReadContext;
import com.digitalpetri.opcua.sdk.server.api.DataItem;
import com.digitalpetri.opcua.sdk.server.api.EventItem;
import com.digitalpetri.opcua.sdk.server.api.MonitoredItem;
import com.digitalpetri.opcua.sdk.server.api.Namespace;
import com.digitalpetri.opcua.sdk.server.items.BaseMonitoredItem;
import com.digitalpetri.opcua.sdk.server.items.MonitoredDataItem;
import com.digitalpetri.opcua.sdk.server.items.MonitoredEventItem;
import com.digitalpetri.opcua.sdk.server.subscriptions.Subscription.State;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.application.services.ServiceRequest;
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;
import com.digitalpetri.opcua.stack.core.types.enumerated.MonitoringMode;
import com.digitalpetri.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.digitalpetri.opcua.stack.core.types.structured.CreateMonitoredItemsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CreateMonitoredItemsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.CreateSubscriptionRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CreateSubscriptionResponse;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteMonitoredItemsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteMonitoredItemsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteSubscriptionsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteSubscriptionsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.ModifyMonitoredItemsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.ModifyMonitoredItemsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.ModifySubscriptionRequest;
import com.digitalpetri.opcua.stack.core.types.structured.ModifySubscriptionResponse;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoredItemCreateResult;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoredItemModifyRequest;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoredItemModifyResult;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoringParameters;
import com.digitalpetri.opcua.stack.core.types.structured.NotificationMessage;
import com.digitalpetri.opcua.stack.core.types.structured.PublishRequest;
import com.digitalpetri.opcua.stack.core.types.structured.PublishResponse;
import com.digitalpetri.opcua.stack.core.types.structured.ReadValueId;
import com.digitalpetri.opcua.stack.core.types.structured.RepublishRequest;
import com.digitalpetri.opcua.stack.core.types.structured.RepublishResponse;
import com.digitalpetri.opcua.stack.core.types.structured.ResponseHeader;
import com.digitalpetri.opcua.stack.core.types.structured.SetMonitoringModeRequest;
import com.digitalpetri.opcua.stack.core.types.structured.SetMonitoringModeResponse;
import com.digitalpetri.opcua.stack.core.types.structured.SetPublishingModeRequest;
import com.digitalpetri.opcua.stack.core.types.structured.SetPublishingModeResponse;
import com.digitalpetri.opcua.stack.core.types.structured.SetTriggeringRequest;
import com.digitalpetri.opcua.stack.core.types.structured.SetTriggeringResponse;
import com.digitalpetri.opcua.stack.core.types.structured.SubscriptionAcknowledgement;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.jooq.lambda.tuple.Tuple3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.digitalpetri.opcua.sdk.core.util.ConversionUtil.a;
import static com.digitalpetri.opcua.sdk.server.util.FutureUtils.sequence;
import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.ubyte;
import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newArrayListWithCapacity;
import static java.util.stream.Collectors.toList;

public class SubscriptionManager {

    private static final QualifiedName DEFAULT_BINARY_ENCODING = new QualifiedName(0, "DefaultBinary");
    private static final QualifiedName DEFAULT_XML_ENCODING = new QualifiedName(0, "DefaultXML");

    private static final AtomicLong SUBSCRIPTION_IDS = new AtomicLong(0L);

    private static UInteger nextSubscriptionId() {
        return uint(SUBSCRIPTION_IDS.incrementAndGet());
    }

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<UInteger, StatusCode[]> acknowledgeResults = Maps.newConcurrentMap();

    private final PublishQueue publishQueue = new PublishQueue();

    private final Map<UInteger, Subscription> subscriptions = Maps.newConcurrentMap();
    private final List<Subscription> transferred = Lists.newCopyOnWriteArrayList();

    private final Session session;
    private final OpcUaServer server;

    public SubscriptionManager(Session session, OpcUaServer server) {
        this.session = session;
        this.server = server;
    }

    public Session getSession() {
        return session;
    }

    public PublishQueue getPublishQueue() {
        return publishQueue;
    }

    public OpcUaServer getServer() {
        return server;
    }

    public void createSubscription(ServiceRequest<CreateSubscriptionRequest, CreateSubscriptionResponse> service) {
        CreateSubscriptionRequest request = service.getRequest();

        UInteger subscriptionId = nextSubscriptionId();

        Subscription subscription = new Subscription(
                this,
                subscriptionId,
                request.getRequestedPublishingInterval(),
                request.getRequestedMaxKeepAliveCount().longValue(),
                request.getRequestedLifetimeCount().longValue(),
                request.getMaxNotificationsPerPublish().longValue(),
                request.getPublishingEnabled(),
                request.getPriority().intValue()
        );

        subscriptions.put(subscriptionId, subscription);
        server.getSubscriptions().put(subscriptionId, subscription);

        subscription.setStateListener((s, ps, cs) -> {
            if (cs == State.Closed) {
                subscriptions.remove(s.getId());
                server.getSubscriptions().remove(s.getId());
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

                results[i] = StatusCode.GOOD;
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
                results[i] = StatusCode.GOOD;
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

            List<BaseMonitoredItem<?>> createdItems = newArrayListWithCapacity(itemsToCreate.length);

            List<PendingItemCreation> pending = Arrays.stream(itemsToCreate)
                    .map(PendingItemCreation::new)
                    .collect(toList());

            for (PendingItemCreation p : pending) {
                MonitoredItemCreateRequest r = p.getRequest();
                NodeId nodeId = r.getItemToMonitor().getNodeId();
                UInteger attributeId = r.getItemToMonitor().getAttributeId();
                QualifiedName dataEncoding = r.getItemToMonitor().getDataEncoding();

                if (dataEncoding.isNotNull()) {
                    if (attributeId.intValue() != AttributeIds.Value) {
                        MonitoredItemCreateResult result = new MonitoredItemCreateResult(
                                new StatusCode(StatusCodes.Bad_DataEncodingInvalid),
                                uint(0), 0d, uint(0), null);

                        p.getResultFuture().complete(result);
                        continue;
                    }
                    if (!dataEncoding.equals(DEFAULT_BINARY_ENCODING) &&
                            !dataEncoding.equals(DEFAULT_XML_ENCODING)) {
                        MonitoredItemCreateResult result = new MonitoredItemCreateResult(
                                new StatusCode(StatusCodes.Bad_DataEncodingUnsupported),
                                uint(0), 0d, uint(0), null);

                        p.getResultFuture().complete(result);
                        continue;
                    }
                }

                Namespace namespace = server.getNamespaceManager().getNamespace(nodeId.getNamespaceIndex());

                if (attributeId.equals(AttributeId.EVENT_NOTIFIER.uid())) {
                    readEventAttributes(namespace, nodeId).thenAccept(as -> {
                        Optional<UByte> eventNotifier = as.v3();

                        try {
                            if (!eventNotifier.isPresent()) {
                                throw new UaException(StatusCodes.Bad_AttributeIdInvalid);
                            }

                            MonitoredEventItem item = new MonitoredEventItem(
                                    uint(subscription.nextItemId()),
                                    r.getItemToMonitor(),
                                    r.getMonitoringMode(),
                                    timestamps,
                                    r.getRequestedParameters().getClientHandle(),
                                    0.0,
                                    r.getRequestedParameters().getQueueSize(),
                                    r.getRequestedParameters().getDiscardOldest(),
                                    r.getRequestedParameters().getFilter());

                            createdItems.add(item);

                            MonitoredItemCreateResult result = new MonitoredItemCreateResult(
                                    StatusCode.GOOD,
                                    item.getId(),
                                    item.getSamplingInterval(),
                                    uint(item.getQueueSize()),
                                    item.getFilterResult());

                            p.getResultFuture().complete(result);
                        } catch (UaException e) {
                            MonitoredItemCreateResult result =
                                    new MonitoredItemCreateResult(e.getStatusCode(), uint(0), 0d, uint(0), null);

                            p.getResultFuture().complete(result);
                        }
                    });
                } else {
                    readDataAttributes(namespace, nodeId).thenAccept(as -> {
                        EnumSet<AccessLevel> accessLevels = as.v1();
                        EnumSet<AccessLevel> userAccessLevels = as.v2();
                        double minimumSamplingInterval = as.v3();

                        double samplingInterval = r.getRequestedParameters().getSamplingInterval();
                        double minSupportedSampleRate = server.getConfig().getLimits().getMinSupportedSampleRate();
                        double maxSupportedSampleRate = server.getConfig().getLimits().getMaxSupportedSampleRate();

                        if (samplingInterval < 0) samplingInterval = subscription.getPublishingInterval();
                        if (samplingInterval < minimumSamplingInterval) samplingInterval = minimumSamplingInterval;
                        if (samplingInterval < minSupportedSampleRate) samplingInterval = minSupportedSampleRate;
                        if (samplingInterval > maxSupportedSampleRate) samplingInterval = maxSupportedSampleRate;

                        try {
                            if (!accessLevels.contains(AccessLevel.CurrentRead)) {
                                throw new UaException(StatusCodes.Bad_NotReadable);
                            }
                            if (!userAccessLevels.contains(AccessLevel.CurrentRead)) {
                                // TODO We didn't read with the session, so this isn't right.
                                throw new UaException(StatusCodes.Bad_UserAccessDenied);
                            }

                            String indexRange = r.getItemToMonitor().getIndexRange();
                            if (indexRange != null) NumericRange.parse(indexRange);

                            MonitoredDataItem item = new MonitoredDataItem(
                                    uint(subscription.nextItemId()),
                                    r.getItemToMonitor(),
                                    r.getMonitoringMode(),
                                    timestamps,
                                    r.getRequestedParameters().getClientHandle(),
                                    samplingInterval,
                                    r.getRequestedParameters().getFilter(),
                                    r.getRequestedParameters().getQueueSize(),
                                    r.getRequestedParameters().getDiscardOldest());

                            createdItems.add(item);

                            MonitoredItemCreateResult result = new MonitoredItemCreateResult(
                                    StatusCode.GOOD,
                                    item.getId(),
                                    item.getSamplingInterval(),
                                    uint(item.getQueueSize()),
                                    item.getFilterResult());

                            p.getResultFuture().complete(result);
                        } catch (Throwable t) {
                            StatusCode statusCode = UaException.extract(t)
                                    .map(UaException::getStatusCode)
                                    .orElse(StatusCode.BAD);

                            MonitoredItemCreateResult result =
                                    new MonitoredItemCreateResult(statusCode, uint(0), 0d, uint(0), null);

                            p.getResultFuture().complete(result);
                        }
                    });
                }
            }

            List<CompletableFuture<MonitoredItemCreateResult>> futures = pending.stream()
                    .map(PendingItemCreation::getResultFuture)
                    .collect(toList());

            sequence(futures).thenAccept(results -> {
                subscription.addMonitoredItems(createdItems);

                // Notify namespaces of the items we just created.
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

                ResponseHeader header = service.createResponseHeader();

                CreateMonitoredItemsResponse response = new CreateMonitoredItemsResponse(
                        header, a(results, MonitoredItemCreateResult.class), new DiagnosticInfo[0]);

                service.setResponse(response);
            });
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

            List<PendingItemModification> pending = Arrays.stream(itemsToModify)
                    .map(PendingItemModification::new)
                    .collect(toList());

            List<BaseMonitoredItem<?>> modifiedItems = newArrayListWithCapacity(itemsToModify.length);

            /*
             * Modify requested items and prepare results.
             */

            for (PendingItemModification p : pending) {
                MonitoredItemModifyRequest r = p.getRequest();
                UInteger itemId = r.getMonitoredItemId();
                MonitoringParameters parameters = r.getRequestedParameters();

                BaseMonitoredItem<?> item = subscription.getMonitoredItems().get(itemId);

                if (item == null) {
                    MonitoredItemModifyResult result = new MonitoredItemModifyResult(
                            new StatusCode(StatusCodes.Bad_MonitoredItemIdInvalid),
                            0d, uint(0), null);

                    p.getResultFuture().complete(result);
                } else {
                    NodeId nodeId = item.getReadValueId().getNodeId();
                    Namespace namespace = server.getNamespaceManager().getNamespace(nodeId.getNamespaceIndex());

                    readDataAttributes(namespace, nodeId).thenAccept(as -> {
                        double minimumSamplingInterval = as.v3();

                        double samplingInterval = parameters.getSamplingInterval();
                        double minSupportedSampleRate = server.getConfig().getLimits().getMinSupportedSampleRate();
                        double maxSupportedSampleRate = server.getConfig().getLimits().getMaxSupportedSampleRate();

                        if (samplingInterval < 0) samplingInterval = subscription.getPublishingInterval();
                        if (samplingInterval < minimumSamplingInterval) samplingInterval = minimumSamplingInterval;
                        if (samplingInterval < minSupportedSampleRate) samplingInterval = minSupportedSampleRate;
                        if (samplingInterval > maxSupportedSampleRate) samplingInterval = maxSupportedSampleRate;

                        try {
                            item.modify(
                                    timestamps,
                                    parameters.getClientHandle(),
                                    samplingInterval,
                                    parameters.getFilter(),
                                    parameters.getQueueSize(),
                                    parameters.getDiscardOldest());

                            modifiedItems.add(item);

                            MonitoredItemModifyResult result = new MonitoredItemModifyResult(
                                    StatusCode.GOOD,
                                    item.getSamplingInterval(),
                                    uint(item.getQueueSize()),
                                    item.getFilterResult());

                            p.getResultFuture().complete(result);
                        } catch (Throwable t) {
                            StatusCode statusCode = UaException.extract(t)
                                    .map(UaException::getStatusCode)
                                    .orElse(StatusCode.BAD);

                            MonitoredItemModifyResult result = new MonitoredItemModifyResult(
                                    statusCode,
                                    item.getSamplingInterval(),
                                    uint(item.getQueueSize()),
                                    item.getFilterResult());

                            p.getResultFuture().complete(result);
                        }
                    });
                }
            }

            subscription.resetLifetimeCounter();

            /*
             * Notify namespaces of the items we just modified.
             */

            List<CompletableFuture<MonitoredItemModifyResult>> futures = pending.stream()
                    .map(PendingItemModification::getResultFuture)
                    .collect(toList());

            sequence(futures).thenAccept(results -> {
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
                        header, a(results, MonitoredItemModifyResult.class), new DiagnosticInfo[0]);

                service.setResponse(response);
            });
        } catch (UaException e) {
            service.setServiceFault(e);
        }
    }

    private CompletableFuture<DataAttributes> readDataAttributes(Namespace namespace, NodeId nodeId) {
        Function<AttributeId, ReadValueId> f = id ->
                new ReadValueId(nodeId, id.uid(), null, QualifiedName.NULL_VALUE);

        ReadContext readContext = new ReadContext(
                server, null, new DiagnosticsContext<>());

        List<ReadValueId> readValueIds = newArrayList(
                f.apply(AttributeId.ACCESS_LEVEL),
                f.apply(AttributeId.USER_ACCESS_LEVEL),
                f.apply(AttributeId.MINIMUM_SAMPLING_INTERVAL));

        namespace.read(readContext, 0.0, TimestampsToReturn.Neither, readValueIds);

        return readContext.getFuture().thenApply(values -> {
            UByte accessLevel = Optional.ofNullable((UByte) values.get(0).getValue().getValue()).orElse(ubyte(1));
            UByte userAccessLevel = Optional.ofNullable((UByte) values.get(1).getValue().getValue()).orElse(ubyte(1));
            Double minimumSamplingInterval = Optional.ofNullable((Double) values.get(2).getValue().getValue()).orElse(0.0);

            return new DataAttributes(
                    AccessLevel.fromMask(accessLevel),
                    AccessLevel.fromMask(userAccessLevel),
                    minimumSamplingInterval);
        });
    }

    private CompletableFuture<EventAttributes> readEventAttributes(Namespace namespace, NodeId nodeId) {
        Function<AttributeId, ReadValueId> f = id ->
                new ReadValueId(nodeId, id.uid(), null, QualifiedName.NULL_VALUE);

        ReadContext readContext = new ReadContext(
                server, null, new DiagnosticsContext<>());

        List<ReadValueId> readValueIds = newArrayList(
                f.apply(AttributeId.ACCESS_LEVEL),
                f.apply(AttributeId.USER_ACCESS_LEVEL),
                f.apply(AttributeId.EVENT_NOTIFIER));

        namespace.read(readContext, 0.0, TimestampsToReturn.Neither, readValueIds);

        return readContext.getFuture().thenApply(values -> {
            UByte accessLevel = Optional.ofNullable((UByte) values.get(0).getValue().getValue()).orElse(ubyte(1));
            UByte userAccessLevel = Optional.ofNullable((UByte) values.get(1).getValue().getValue()).orElse(ubyte(1));
            Optional<UByte> eventNotifier = Optional.ofNullable((UByte) values.get(2).getValue().getValue());

            return new EventAttributes(
                    AccessLevel.fromMask(accessLevel),
                    AccessLevel.fromMask(userAccessLevel),
                    eventNotifier);
        });
    }

    private static class DataAttributes extends Tuple3<EnumSet<AccessLevel>, EnumSet<AccessLevel>, Double> {
        public DataAttributes(EnumSet<AccessLevel> v1, EnumSet<AccessLevel> v2, Double v3) {
            super(v1, v2, v3);
        }
    }

    private static class EventAttributes extends Tuple3<EnumSet<AccessLevel>, EnumSet<AccessLevel>, Optional<UByte>> {
        public EventAttributes(EnumSet<AccessLevel> v1, EnumSet<AccessLevel> v2, Optional<UByte> v3) {
            super(v1, v2, v3);
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
            List<BaseMonitoredItem<?>> deletedItems = newArrayListWithCapacity(itemsToDelete.length);

            synchronized (subscription) {
                for (int i = 0; i < itemsToDelete.length; i++) {
                    UInteger itemId = itemsToDelete[i];
                    BaseMonitoredItem<?> item = subscription.getMonitoredItems().get(itemId);

                    if (item == null) {
                        deleteResults[i] = new StatusCode(StatusCodes.Bad_MonitoredItemIdInvalid);
                    } else {
                        deletedItems.add(item);

                        deleteResults[i] = StatusCode.GOOD;
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
            List<BaseMonitoredItem<?>> modified = newArrayListWithCapacity(itemsToModify.length);

            for (int i = 0; i < itemsToModify.length; i++) {
                UInteger itemId = itemsToModify[i];
                BaseMonitoredItem<?> item = subscription.getMonitoredItems().get(itemId);

                if (item != null) {
                    item.setMonitoringMode(monitoringMode);

                    modified.add(item);

                    results[i] = StatusCode.GOOD;
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

        if (!transferred.isEmpty()) {
            Subscription subscription = transferred.remove(0);
            subscription.returnStatusChangeNotification(service);
            return;
        }

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
        SetTriggeringRequest request = service.getRequest();

        UInteger subscriptionId = request.getSubscriptionId();
        Subscription subscription = subscriptions.get(subscriptionId);

        if (subscription == null) {
            service.setServiceFault(StatusCodes.Bad_SubscriptionIdInvalid);
            return;
        }

        if (request.getLinksToAdd().length == 0 && request.getLinksToRemove().length == 0) {
            service.setServiceFault(StatusCodes.Bad_NothingToDo);
            return;
        }

        UInteger triggerId = request.getTriggeringItemId();
        UInteger[] linksToAdd = request.getLinksToAdd();
        UInteger[] linksToRemove = request.getLinksToRemove();


        synchronized (subscription) {
            Map<UInteger, BaseMonitoredItem<?>> itemsById = subscription.getMonitoredItems();

            BaseMonitoredItem<?> triggerItem = itemsById.get(triggerId);
            if (triggerItem == null) {
                service.setServiceFault(StatusCodes.Bad_MonitoredItemIdInvalid);
                return;
            }

            List<StatusCode> removeResults = Arrays.stream(linksToRemove)
                    .map(linkedItemId -> {
                        BaseMonitoredItem<?> item = itemsById.get(linkedItemId);
                        if (item != null) {
                            if (triggerItem.getTriggeredItems().remove(linkedItemId) != null) {
                                return StatusCode.GOOD;
                            } else {
                                return new StatusCode(StatusCodes.Bad_MonitoredItemIdInvalid);
                            }
                        } else {
                            return new StatusCode(StatusCodes.Bad_MonitoredItemIdInvalid);
                        }
                    })
                    .collect(toList());

            List<StatusCode> addResults = Arrays.stream(linksToAdd)
                    .map(linkedItemId -> {
                        BaseMonitoredItem<?> linkedItem = itemsById.get(linkedItemId);
                        if (linkedItem != null) {
                            triggerItem.getTriggeredItems().put(linkedItemId, linkedItem);
                            return StatusCode.GOOD;
                        } else {
                            return new StatusCode(StatusCodes.Bad_MonitoredItemIdInvalid);
                        }
                    })
                    .collect(toList());

            SetTriggeringResponse response = new SetTriggeringResponse(
                    service.createResponseHeader(),
                    addResults.toArray(new StatusCode[addResults.size()]),
                    new DiagnosticInfo[0],
                    removeResults.toArray(new StatusCode[removeResults.size()]),
                    new DiagnosticInfo[0]
            );

            service.setResponse(response);
        }
    }

    public void sessionClosed(boolean deleteSubscriptions) {
        Iterator<Subscription> iterator = subscriptions.values().iterator();

        while (iterator.hasNext()) {
            Subscription s = iterator.next();
            s.setStateListener(null);

            if (deleteSubscriptions) {
                server.getSubscriptions().remove(s.getId());
            }

            iterator.remove();
        }
    }

    public Subscription removeSubscription(UInteger subscriptionId) {
        Subscription subscription = subscriptions.remove(subscriptionId);
        if (subscription != null) subscription.setStateListener(null);
        return subscription;
    }

    public void addSubscription(Subscription subscription) {
        subscriptions.put(subscription.getId(), subscription);

        subscription.setStateListener((s, ps, cs) -> {
            if (cs == State.Closed) {
                subscriptions.remove(s.getId());
                server.getSubscriptions().remove(s.getId());
            }
        });
    }

    StatusCode[] getAcknowledgeResults(UInteger requestHandle) {
        return acknowledgeResults.remove(requestHandle);
    }

    public void sendStatusChangeNotification(Subscription subscription) {
        ServiceRequest<PublishRequest, PublishResponse> service = publishQueue.poll();

        if (service != null) {
            subscription.returnStatusChangeNotification(service);
        } else {
            transferred.add(subscription);
        }
    }

}
