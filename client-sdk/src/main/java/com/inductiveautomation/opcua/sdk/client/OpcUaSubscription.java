package com.inductiveautomation.opcua.sdk.client;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import com.inductiveautomation.opcua.sdk.client.api.UaMonitoredItem;
import com.inductiveautomation.opcua.sdk.client.api.UaSubscription;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

public class OpcUaSubscription implements UaSubscription {

    private volatile UInteger subscriptionId;
    private volatile double publishingInterval;
    private volatile UInteger lifetimeCount;
    private volatile UInteger maxKeepAliveCount;
    private volatile UInteger maxNotificationsPerPublish;
    private volatile boolean publishingEnabled;
    private volatile UByte priority;

    public OpcUaSubscription(UInteger subscriptionId,
                             double publishingInterval,
                             UInteger lifetimeCount,
                             UInteger maxKeepAliveCount,
                             UInteger maxNotificationsPerPublish,
                             boolean publishingEnabled, UByte priority) {

        this.subscriptionId = subscriptionId;
        this.publishingInterval = publishingInterval;
        this.lifetimeCount = lifetimeCount;
        this.maxKeepAliveCount = maxKeepAliveCount;
        this.maxNotificationsPerPublish = maxNotificationsPerPublish;
        this.publishingEnabled = publishingEnabled;
        this.priority = priority;
    }

    @Override
    public UInteger getSubscriptionId() {
        return null;
    }

    @Override
    public double getPublishingInterval() {
        return 0;
    }

    @Override
    public UInteger getLifetimeCount() {
        return null;
    }

    @Override
    public UInteger getMaxKeepAliveCount() {
        return null;
    }

    @Override
    public UInteger getMaxNotificationsPerPublish() {
        return null;
    }

    @Override
    public boolean isPublishingEnabled() {
        return false;
    }

    @Override
    public UByte getPriority() {
        return null;
    }

    @Override
    public CompletableFuture<List<StatusCode>> createItems(List<UaMonitoredItem> items) {
        return null;
    }

    @Override
    public CompletableFuture<List<StatusCode>> createItems(Supplier<List<UaMonitoredItem>> items) {
        return null;
    }

    @Override
    public CompletableFuture<List<StatusCode>> deleteItems(List<UaMonitoredItem> items) {
        return null;
    }

    @Override
    public CompletableFuture<List<StatusCode>> deleteItems(Supplier<List<UaMonitoredItem>> items) {
        return null;
    }

}
