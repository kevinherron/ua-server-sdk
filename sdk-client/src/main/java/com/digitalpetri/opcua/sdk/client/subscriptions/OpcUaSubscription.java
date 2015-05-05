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
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.digitalpetri.opcua.sdk.client.OpcUaClient;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.MonitoringMode;
import com.digitalpetri.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.digitalpetri.opcua.stack.core.types.structured.ModifyMonitoredItemsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoredItemCreateResult;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoredItemModifyRequest;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoredItemModifyResult;
import com.digitalpetri.opcua.stack.core.types.structured.SetMonitoringModeResponse;
import com.google.common.collect.Maps;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;
import static com.google.common.collect.Lists.newArrayList;

public class OpcUaSubscription {

    private final Map<UInteger, OpcUaMonitoredItem> itemsByClientHandle = Maps.newConcurrentMap();
    private final Map<UInteger, OpcUaMonitoredItem> itemsByServerHandle = Maps.newConcurrentMap();

    private volatile long lastSequenceNumber = 0L;
    private volatile double revisedPublishingInterval = 0.0;

    private volatile UInteger revisedLifetimeCount = uint(0);

    private volatile UInteger revisedMaxKeepAliveCount = uint(0);
    private volatile UInteger maxNotificationsPerPublish;
    private volatile boolean publishingEnabled;
    private volatile UByte priority;

    private final OpcUaClient client;
    private final UInteger subscriptionId;

    OpcUaSubscription(OpcUaClient client, UInteger subscriptionId,
                      double revisedPublishingInterval,
                      UInteger revisedLifetimeCount,
                      UInteger revisedMaxKeepAliveCount,
                      UInteger maxNotificationsPerPublish,
                      boolean publishingEnabled,
                      UByte priority) {

        this.client = client;
        this.subscriptionId = subscriptionId;
        this.revisedPublishingInterval = revisedPublishingInterval;
        this.revisedLifetimeCount = revisedLifetimeCount;
        this.revisedMaxKeepAliveCount = revisedMaxKeepAliveCount;
        this.maxNotificationsPerPublish = maxNotificationsPerPublish;
        this.publishingEnabled = publishingEnabled;
        this.priority = priority;
    }

    public CompletableFuture<List<OpcUaMonitoredItem>> createMonitoredItems(TimestampsToReturn timestampsToReturn,
                                                                            List<MonitoredItemCreateRequest> itemsToCreate) {

        return client.createMonitoredItems(
                subscriptionId,
                timestampsToReturn,
                itemsToCreate).thenApply(response -> {

            List<OpcUaMonitoredItem> createdItems = newArrayList();

            MonitoredItemCreateResult[] results = response.getResults();

            for (int i = 0; i < itemsToCreate.size(); i++) {
                MonitoredItemCreateRequest request = itemsToCreate.get(i);
                MonitoredItemCreateResult result = results[i];

                OpcUaMonitoredItem item = new OpcUaMonitoredItem(
                        request.getRequestedParameters().getClientHandle(),
                        request.getItemToMonitor(),
                        result.getMonitoredItemId(),
                        result.getStatusCode(),
                        result.getRevisedSamplingInterval(),
                        result.getRevisedQueueSize(),
                        result.getFilterResult(),
                        request.getMonitoringMode());

                if (item.getStatusCode().isGood()) {
                    itemsByClientHandle.put(item.getClientHandle(), item);
                    itemsByServerHandle.put(item.getMonitoredItemId(), item);
                }

                createdItems.add(item);
            }

            return createdItems;
        });
    }

    public CompletableFuture<List<StatusCode>> modifyMonitoredItems(TimestampsToReturn timestampsToReturn,
                                                                    List<MonitoredItemModifyRequest> itemsToModify) {

        CompletableFuture<ModifyMonitoredItemsResponse> future =
                client.modifyMonitoredItems(subscriptionId, timestampsToReturn, itemsToModify);

        return future.thenApply(response -> {
            List<StatusCode> statusCodes = newArrayList();

            MonitoredItemModifyResult[] results = response.getResults();

            for (int i = 0; i < results.length; i++) {
                MonitoredItemModifyRequest request = itemsToModify.get(i);
                MonitoredItemModifyResult result = results[i];
                StatusCode statusCode = result.getStatusCode();

                OpcUaMonitoredItem item = itemsByServerHandle.get(request.getMonitoredItemId());

                if (item != null) {
                    item.setStatusCode(statusCode);
                    item.setRevisedSamplingInterval(result.getRevisedSamplingInterval());
                    item.setRevisedQueueSize(result.getRevisedQueueSize());
                    item.setFilterResult(result.getFilterResult());
                }

                statusCodes.add(statusCode);
            }

            return statusCodes;
        });
    }

    public CompletableFuture<List<StatusCode>> deleteMonitoredItems(List<OpcUaMonitoredItem> itemsToDelete) {

        List<UInteger> monitoredItemIds = itemsToDelete.stream()
                .map(OpcUaMonitoredItem::getMonitoredItemId)
                .collect(Collectors.toList());

        return client.deleteMonitoredItems(subscriptionId, monitoredItemIds).thenApply(response -> {
            StatusCode[] results = response.getResults();

            return Arrays.asList(results);
        });
    }

    public CompletableFuture<List<StatusCode>> setMonitoringMode(MonitoringMode monitoringMode,
                                                                 List<OpcUaMonitoredItem> items) {

        List<UInteger> monitoredItemIds = items.stream()
                .map(OpcUaMonitoredItem::getMonitoredItemId)
                .collect(Collectors.toList());

        CompletableFuture<SetMonitoringModeResponse> future =
                client.setMonitoringMode(subscriptionId, monitoringMode, monitoredItemIds);

        return future.thenApply(response -> {
            StatusCode[] results = response.getResults();

            for (int i = 0; i < items.size(); i++) {
                OpcUaMonitoredItem item = items.get(i);
                StatusCode result = results[i];
                if (result.isGood()) {
                    item.setMonitoringMode(monitoringMode);
                }
            }

            return Arrays.asList(results);
        });
    }

    public CompletableFuture<StatusCode> setPublishingMode(boolean publishingEnabled) {
        return client.setPublishingMode(publishingEnabled, newArrayList(subscriptionId))
                .thenApply(response -> {
                    StatusCode statusCode = response.getResults()[0];

                    if (statusCode.isGood()) {
                        setPublishingEnabled(publishingEnabled);
                    }

                    return statusCode;
                });
    }

    public UInteger getSubscriptionId() {
        return subscriptionId;
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

    public UInteger getMaxNotificationsPerPublish() {
        return maxNotificationsPerPublish;
    }

    public boolean isPublishingEnabled() {
        return publishingEnabled;
    }

    public UByte getPriority() {
        return priority;
    }

    public Map<UInteger, OpcUaMonitoredItem> getItems() {
        return itemsByClientHandle;
    }

    long getLastSequenceNumber() {
        return lastSequenceNumber;
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

    void setMaxNotificationsPerPublish(UInteger maxNotificationsPerPublish) {
        this.maxNotificationsPerPublish = maxNotificationsPerPublish;
    }

    void setPublishingEnabled(boolean publishingEnabled) {
        this.publishingEnabled = publishingEnabled;
    }

    void setPriority(UByte priority) {
        this.priority = priority;
    }

    void setLastSequenceNumber(long lastSequenceNumber) {
        this.lastSequenceNumber = lastSequenceNumber;
    }

}
