package com.inductiveautomation.opcua.sdk.client;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.inductiveautomation.opcua.sdk.client.api.UaMonitoredItem;
import com.inductiveautomation.opcua.sdk.client.api.UaSubscription;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateMonitoredItemsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateMonitoredItemsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoredItemCreateResult;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class OpcUaSubscription implements UaSubscription {

    private volatile UInteger subscriptionId;
    private volatile StatusCode subscriptionStatus;

    private volatile double revisedPublishingInterval = 0.0;
    private volatile UInteger revisedLifetimeCount = uint(0);
    private volatile UInteger revisedKeepAliveCount = uint(0);

    private final OpcUaClient client;

    private volatile double requestedPublishingInterval;
    private volatile UInteger requestedLifetimeCount;
    private volatile UInteger requestedKeepAliveCount;
    private volatile UInteger maxNotificationsPerPublish;
    private volatile boolean publishingEnabled;
    private volatile UByte priority;

    public OpcUaSubscription(OpcUaClient client,
                             double requestedPublishingInterval,
                             UInteger requestedLifetimeCount,
                             UInteger requestedKeepAliveCount,
                             UInteger maxNotificationsPerPublish,
                             boolean publishingEnabled,
                             UByte priority) {

        this.client = client;
        this.requestedPublishingInterval = requestedPublishingInterval;
        this.requestedLifetimeCount = requestedLifetimeCount;
        this.requestedKeepAliveCount = requestedKeepAliveCount;
        this.maxNotificationsPerPublish = maxNotificationsPerPublish;
        this.publishingEnabled = publishingEnabled;
        this.priority = priority;
    }

    @Override
    public UInteger getSubscriptionId() {
        return subscriptionId;
    }

    @Override
    public double getRequestedPublishInterval() {
        return requestedPublishingInterval;
    }

    @Override
    public UInteger getRequestedLifetimeCount() {
        return requestedLifetimeCount;
    }

    @Override
    public UInteger getRequestedKeepAliveCount() {
        return requestedKeepAliveCount;
    }

    @Override
    public UInteger getMaxNotificationsPerPublish() {
        return maxNotificationsPerPublish;
    }

    @Override
    public boolean isPublishingEnabled() {
        return publishingEnabled;
    }

    @Override
    public UByte getPriority() {
        return priority;
    }

    public double getRevisedPublishingInterval() {
        return revisedPublishingInterval;
    }

    public UInteger getRevisedLifetimeCount() {
        return revisedLifetimeCount;
    }

    public UInteger getRevisedKeepAliveCount() {
        return revisedKeepAliveCount;
    }

    @Override
    public CompletableFuture<List<StatusCode>> createItems(List<UaMonitoredItem> items) {
        return null;
    }

    @Override
    public CompletableFuture<List<StatusCode>> createItems(Supplier<List<UaMonitoredItem>> itemSupplier) {
        CompletableFuture<List<StatusCode>> future = new CompletableFuture<>();

        client.getStateContext().getSession().whenComplete((session, ex) -> {
            if (session != null) {
                List<UaMonitoredItem> items = itemSupplier.get();

                MonitoredItemCreateRequest[] itemsToCreate = items.stream()
                        .map(item -> new MonitoredItemCreateRequest(
                                item.getReadValueId(),
                                item.getMonitoringMode(),
                                item.getMonitoringParameters()))
                        .toArray(MonitoredItemCreateRequest[]::new);

                CreateMonitoredItemsRequest request = new CreateMonitoredItemsRequest(
                        client.newRequestHeader(),
                        subscriptionId,
                        TimestampsToReturn.Both,
                        itemsToCreate
                );

                Consumer<CreateMonitoredItemsResponse> consumer = response -> {
                    MonitoredItemCreateResult[] results = response.getResults();

                    for (MonitoredItemCreateResult result : results) {
                        result.getMonitoredItemId();
                        result.getFilterResult();
                        result.getRevisedQueueSize();
                        result.getRevisedSamplingInterval();
                        result.getStatusCode();
                    }

                    future.complete(Collections.nCopies(items.size(), StatusCode.GOOD));
                };

                client.sendRequest(future, request, consumer);
            } else {
                future.completeExceptionally(ex);
            }
        });

        return future;
    }

    @Override
    public CompletableFuture<List<StatusCode>> deleteItems(List<UaMonitoredItem> items) {
        return null;
    }

    @Override
    public CompletableFuture<List<StatusCode>> deleteItems(Supplier<List<UaMonitoredItem>> items) {
        return null;
    }

    void setSubscriptionId(UInteger subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    void setSubscriptionStatus(StatusCode subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public void setRequestedPublishingInterval(double requestedPublishingInterval) {
        this.requestedPublishingInterval = requestedPublishingInterval;
    }

    void setRequestedLifetimeCount(UInteger requestedLifetimeCount) {
        this.requestedLifetimeCount = requestedLifetimeCount;
    }

    void setRequestedKeepAliveCount(UInteger requestedKeepAliveCount) {
        this.requestedKeepAliveCount = requestedKeepAliveCount;
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

    void setRevisedKeepAliveCount(UInteger revisedKeepAliveCount) {
        this.revisedKeepAliveCount = revisedKeepAliveCount;
    }

}
