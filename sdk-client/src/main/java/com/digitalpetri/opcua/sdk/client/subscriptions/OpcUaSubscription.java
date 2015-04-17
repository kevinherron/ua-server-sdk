/*
 * Copyright 2015
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

    private final UInteger subscriptionId;

    private volatile double revisedPublishingInterval = 0.0;
    private volatile UInteger revisedLifetimeCount = uint(0);
    private volatile UInteger revisedMaxKeepAliveCount = uint(0);
    private volatile UInteger maxNotificationsPerPublish;
    private volatile boolean publishingEnabled;
    private volatile UByte priority;

    public OpcUaSubscription(UInteger subscriptionId,
                             double revisedPublishingInterval,
                             UInteger revisedLifetimeCount,
                             UInteger revisedMaxKeepAliveCount,
                             UInteger maxNotificationsPerPublish,
                             boolean publishingEnabled,
                             UByte priority) {

        this.subscriptionId = subscriptionId;
        this.revisedPublishingInterval = revisedPublishingInterval;
        this.revisedLifetimeCount = revisedLifetimeCount;
        this.revisedMaxKeepAliveCount = revisedMaxKeepAliveCount;
        this.maxNotificationsPerPublish = maxNotificationsPerPublish;
        this.publishingEnabled = publishingEnabled;
        this.priority = priority;
    }

    public CompletableFuture<List<OpcUaMonitoredItem>> createMonitoredItems(OpcUaClient client,
                                                                            TimestampsToReturn timestampsToReturn,
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

    public CompletableFuture<List<StatusCode>> modifyMonitoredItems(OpcUaClient client,
                                                                    TimestampsToReturn timestampsToReturn,
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

    public CompletableFuture<List<StatusCode>> deleteMonitoredItems(OpcUaClient client,
                                                                    List<OpcUaMonitoredItem> itemsToDelete) {

        List<UInteger> monitoredItemIds = itemsToDelete.stream()
                .map(OpcUaMonitoredItem::getMonitoredItemId)
                .collect(Collectors.toList());

        return client.deleteMonitoredItems(subscriptionId, monitoredItemIds).thenApply(response -> {
            StatusCode[] results = response.getResults();

            return Arrays.asList(results);
        });
    }

    public CompletableFuture<List<StatusCode>> setMonitoringMode(OpcUaClient client,
                                                                 MonitoringMode monitoringMode,
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

    public CompletableFuture<StatusCode> setPublishingMode(OpcUaClient client, boolean publishingEnabled) {
        return client.setPublishingMode(publishingEnabled, newArrayList(subscriptionId))
                .thenApply(response -> {
                    StatusCode statusCode = response.getResults()[0];

                    if (statusCode.isGood()) {
                        this.publishingEnabled = publishingEnabled;
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

}
