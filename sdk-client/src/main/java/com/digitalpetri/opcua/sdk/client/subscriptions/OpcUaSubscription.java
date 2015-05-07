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
import com.google.common.collect.ImmutableList;
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

    /**
     * Create one or more {@link OpcUaMonitoredItem}s.
     * <p>
     * Callers must check the quality of each of the returned {@link OpcUaMonitoredItem}s; it is not to be assumed that
     * all items were created successfully. Any item with a bad quality will not be updated nor will it be part of the
     * subscription's bookkeeping.
     *
     * @param timestampsToReturn the {@link TimestampsToReturn}.
     * @param itemsToCreate      a list of {@link MonitoredItemCreateRequest}s.
     * @return a list of {@link OpcUaMonitoredItem}s.
     */
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

    /**
     * Modify one or more {@link OpcUaMonitoredItem}s.
     *
     * @param timestampsToReturn the {@link TimestampsToReturn} to set for each item.
     * @param itemsToModify      a list of {@link MonitoredItemModifyRequest}s.
     * @return a {@link CompletableFuture} containing a list of {@link StatusCode}s, the size and order matching that
     * of {@code itemsToModify}.
     */
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

    /**
     * Delete on or more {@link OpcUaMonitoredItem}s.
     *
     * @param itemsToDelete the items to delete.
     * @return a {@link CompletableFuture} containing a list of {@link StatusCode}s, the size and order matching that
     * of {@code itemsToDelete}.
     */
    public CompletableFuture<List<StatusCode>> deleteMonitoredItems(List<OpcUaMonitoredItem> itemsToDelete) {

        List<UInteger> monitoredItemIds = itemsToDelete.stream()
                .map(OpcUaMonitoredItem::getMonitoredItemId)
                .collect(Collectors.toList());

        return client.deleteMonitoredItems(subscriptionId, monitoredItemIds).thenApply(response -> {
            StatusCode[] results = response.getResults();

            return Arrays.asList(results);
        });
    }

    /**
     * Set the {@link MonitoringMode} for one or more {@link OpcUaMonitoredItem}s.
     *
     * @param monitoringMode the {@link MonitoringMode} to set.
     * @param items          the {@link OpcUaMonitoredItem}s to set the mode on.
     * @return a {@link CompletableFuture} containing a list of {@link StatusCode}s, the size and order matching that
     * of {@code items}.
     */
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

    /**
     * Set the publishing mode for this subscription.
     *
     * @param publishingEnabled {@code true} if publishing should be enabled.
     * @return a {@link CompletableFuture} containing a {@link StatusCode} representing the result of this operation.
     */
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

    /**
     * @return the server-assigned id for this {@link OpcUaSubscription}.
     */
    public UInteger getSubscriptionId() {
        return subscriptionId;
    }

    /**
     * @return the actual/revised publishing interval of this {@link OpcUaSubscription}.
     */
    public double getRevisedPublishingInterval() {
        return revisedPublishingInterval;
    }

    /**
     * @return the actual/revised lifetime count of this {@link OpcUaSubscription}.
     */
    public UInteger getRevisedLifetimeCount() {
        return revisedLifetimeCount;
    }

    /**
     * @return the actual/revised max keep-alive count of this {@link OpcUaSubscription}.
     */
    public UInteger getRevisedMaxKeepAliveCount() {
        return revisedMaxKeepAliveCount;
    }

    /**
     * @return the maximum number of notifications that will be returned in any publish response.
     */
    public UInteger getMaxNotificationsPerPublish() {
        return maxNotificationsPerPublish;
    }

    /**
     * @return {@code true} if publishing is enabled.
     */
    public boolean isPublishingEnabled() {
        return publishingEnabled;
    }

    /**
     * @return the relative priority assigned to this {@link OpcUaSubscription}.
     */
    public UByte getPriority() {
        return priority;
    }

    /**
     * @return an {@link ImmutableList} of this {@link OpcUaSubscription}'s {@link OpcUaMonitoredItem}s.
     */
    public ImmutableList<OpcUaMonitoredItem> getMonitoredItems() {
        return ImmutableList.copyOf(itemsByClientHandle.values());
    }

    Map<UInteger, OpcUaMonitoredItem> getItemsByClientHandle() {
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
