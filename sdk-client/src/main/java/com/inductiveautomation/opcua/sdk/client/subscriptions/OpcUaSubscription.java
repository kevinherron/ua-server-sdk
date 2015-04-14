package com.inductiveautomation.opcua.sdk.client.subscriptions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.codepoetics.protonpack.StreamUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.inductiveautomation.opcua.sdk.client.OpcUaClient;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.MonitoringMode;
import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateMonitoredItemsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteMonitoredItemsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.ModifyMonitoredItemsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoredItemCreateResult;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoredItemModifyRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoredItemModifyResult;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoringParameters;

import static com.google.common.collect.Lists.newArrayList;
import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class OpcUaSubscription {

    private volatile UInteger subscriptionId = uint(0);

    private volatile double revisedPublishingInterval = 0.0;
    private volatile UInteger revisedLifetimeCount = uint(0);
    private volatile UInteger revisedMaxKeepAliveCount = uint(0);

    private final Map<UInteger, OpcUaMonitoredItem> monitoredItems = Maps.newConcurrentMap();

    private final OpcUaClient client;

    private volatile double requestedPublishingInterval;
    private volatile UInteger requestedLifetimeCount;
    private volatile UInteger requestedMaxKeepAliveCount;
    private volatile UInteger maxNotificationsPerPublish;
    private volatile boolean publishingEnabled;
    private volatile UByte priority;

    public OpcUaSubscription(OpcUaClient client,
                             double requestedPublishingInterval,
                             UInteger requestedLifetimeCount,
                             UInteger requestedMaxKeepAliveCount,
                             UInteger maxNotificationsPerPublish,
                             boolean publishingEnabled,
                             UByte priority) {

        this.client = client;
        this.requestedPublishingInterval = requestedPublishingInterval;
        this.requestedLifetimeCount = requestedLifetimeCount;
        this.requestedMaxKeepAliveCount = requestedMaxKeepAliveCount;
        this.maxNotificationsPerPublish = maxNotificationsPerPublish;
        this.publishingEnabled = publishingEnabled;
        this.priority = priority;
    }

    public UInteger getSubscriptionId() {
        return subscriptionId;
    }

    public double getRequestedPublishingInterval() {
        return requestedPublishingInterval;
    }

    public UInteger getRequestedLifetimeCount() {
        return requestedLifetimeCount;
    }

    public UInteger getRequestedMaxKeepAliveCount() {
        return requestedMaxKeepAliveCount;
    }

    public UInteger getMaxNotificationsPerPublish() {
        return maxNotificationsPerPublish;
    }

    public boolean isPublishingEnabled() {
        return publishingEnabled;
    }

    public UByte getPriority() {
        return priority;
    }

    public double getRevisedPublishingInterval() {
        return revisedPublishingInterval;
    }

    public UInteger getRevisedLifetimeCount() {
        return revisedLifetimeCount;
    }

    public UInteger getRevisedMaxKeepAliveCount() {
        return revisedMaxKeepAliveCount;
    }

    Map<UInteger, OpcUaMonitoredItem> getMonitoredItems() {
        return monitoredItems;
    }

    public CompletableFuture<List<StatusCode>> createItems(TimestampsToReturn timestampsToReturn,
                                                           Consumer<CreateItemsContext> contextConsumer) {

        CreateItemsContext context = new CreateItemsContext();

        contextConsumer.accept(context);

        List<MonitoredItemCreateRequest> itemsToCreate = StreamUtils.zip(
                context.itemList.stream(),
                context.parametersList.stream(),
                context.monitoringModeList.stream(),
                (item, parameters, mode) ->
                        new MonitoredItemCreateRequest(item.getReadValueId(), mode, parameters))
                .collect(Collectors.toList());

        CompletableFuture<CreateMonitoredItemsResponse> future =
                client.createMonitoredItems(subscriptionId, timestampsToReturn, itemsToCreate);

        return future.thenApply(response -> {
            Stream<OpcUaMonitoredItem> itemStream = context.itemList.stream();
            Stream<MonitoredItemCreateResult> resultStream = Arrays.stream(response.getResults());

            return StreamUtils.zip(itemStream, resultStream, (item, result) -> {
                StatusCode statusCode = result.getStatusCode();

                if (statusCode.isGood()) {
                    item.setMonitoredItemId(result.getMonitoredItemId());

                    item.setFilterResult(result.getFilterResult());
                    item.setRevisedQueueSize(result.getRevisedQueueSize());
                    item.setRevisedSamplingInterval(result.getRevisedSamplingInterval());

                    monitoredItems.put(item.getClientHandle(), item);
                }

                item.setStatusCode(statusCode);

                return statusCode;
            }).collect(Collectors.toList());
        });
    }

    public CompletableFuture<List<StatusCode>> modifyItems(TimestampsToReturn timestampsToReturn,
                                                           Consumer<ModifyItemsContext> contextConsumer) {

        ModifyItemsContext context = new ModifyItemsContext();

        contextConsumer.accept(context);

        List<MonitoredItemModifyRequest> itemsToModify = StreamUtils.zip(
                context.itemList.stream(),
                context.parametersList.stream(),
                (item, parameters) ->
                        new MonitoredItemModifyRequest(item.getMonitoredItemId(), parameters))
                .collect(Collectors.toList());

        CompletableFuture<ModifyMonitoredItemsResponse> future =
                client.modifyMonitoredItems(subscriptionId, timestampsToReturn, itemsToModify);

        return future.thenApply(response -> {
            Stream<OpcUaMonitoredItem> itemStream = context.itemList.stream();
            Stream<MonitoredItemModifyResult> resultStream = Arrays.stream(response.getResults());

            return StreamUtils.zip(itemStream, resultStream, (item, result) -> {
                StatusCode statusCode = result.getStatusCode();

                if (statusCode.isGood()) {
                    item.setFilterResult(result.getFilterResult());
                    item.setRevisedQueueSize(result.getRevisedQueueSize());
                    item.setRevisedSamplingInterval(result.getRevisedSamplingInterval());
                }

                item.setStatusCode(statusCode);

                return statusCode;
            }).collect(Collectors.toList());
        });
    }

    public CompletableFuture<List<StatusCode>> deleteItems(List<OpcUaMonitoredItem> items) {
        List<UInteger> itemsToDelete = items.stream()
                .map(OpcUaMonitoredItem::getMonitoredItemId)
                .collect(Collectors.toList());

        CompletableFuture<DeleteMonitoredItemsResponse> future =
                client.deleteMonitoredItems(subscriptionId, itemsToDelete);

        return future.thenApply(response -> {
            monitoredItems.values().removeAll(items);

            return newArrayList(response.getResults());
        });
    }

    void setSubscriptionId(UInteger subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    void setRequestedPublishingInterval(double requestedPublishingInterval) {
        this.requestedPublishingInterval = requestedPublishingInterval;
    }

    void setRequestedLifetimeCount(UInteger requestedLifetimeCount) {
        this.requestedLifetimeCount = requestedLifetimeCount;
    }

    void setRequestedMaxKeepAliveCount(UInteger requestedMaxKeepAliveCount) {
        this.requestedMaxKeepAliveCount = requestedMaxKeepAliveCount;
    }

    void setMaxNotificationsPerPublish(UInteger maxNotificationsPerPublish) {
        this.maxNotificationsPerPublish = maxNotificationsPerPublish;
    }

    void setPublishingEnabled(boolean publishingEnabled) {
        this.publishingEnabled = publishingEnabled;
    }

    void setRevisedPublishingInterval(double revisedPublishingInterval) {
        this.revisedPublishingInterval = revisedPublishingInterval;
    }

    void setRevisedLifetimeCount(UInteger revisedLifetimeCount) {
        this.revisedLifetimeCount = revisedLifetimeCount;
    }

    void setRevisedMaxKeepAliveCount(UInteger revisedMaxKeepAliveCount) {
        this.revisedMaxKeepAliveCount = revisedMaxKeepAliveCount;
    }

    public static class CreateItemsContext {

        private final List<OpcUaMonitoredItem> itemList = Lists.newCopyOnWriteArrayList();
        private final List<MonitoringParameters> parametersList = Lists.newCopyOnWriteArrayList();
        private final List<MonitoringMode> monitoringModeList = Lists.newCopyOnWriteArrayList();

        private CreateItemsContext() {
        }

        public void addItem(OpcUaMonitoredItem item, MonitoringParameters parameters, MonitoringMode monitoringMode) {
            itemList.add(item);
            parametersList.add(parameters);
            monitoringModeList.add(monitoringMode);
        }

        public void addItems(List<OpcUaMonitoredItem> itemList,
                             List<MonitoringParameters> parametersList,
                             List<MonitoringMode> monitoringModeList) {

            this.itemList.addAll(itemList);
            this.parametersList.addAll(parametersList);
            this.monitoringModeList.addAll(monitoringModeList);
        }

    }

    public static class ModifyItemsContext {

        private final List<OpcUaMonitoredItem> itemList = Lists.newCopyOnWriteArrayList();
        private final List<MonitoringParameters> parametersList = Lists.newCopyOnWriteArrayList();

        private ModifyItemsContext() {
        }

        public void addItem(OpcUaMonitoredItem item, MonitoringParameters parameters) {
            itemList.add(item);
            parametersList.add(parameters);
        }

        public void addItems(List<OpcUaMonitoredItem> itemList, List<MonitoringParameters> parametersList) {
            this.itemList.addAll(itemList);
            this.parametersList.addAll(parametersList);
        }

    }

}
