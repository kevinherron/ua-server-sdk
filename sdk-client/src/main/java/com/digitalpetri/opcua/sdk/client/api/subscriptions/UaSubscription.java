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

package com.digitalpetri.opcua.sdk.client.api.subscriptions;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.MonitoringMode;
import com.digitalpetri.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoredItemModifyRequest;
import com.google.common.collect.ImmutableList;

public interface UaSubscription {

    /**
     * @return the server-assigned id for this {@link UaSubscription}.
     */
    UInteger getSubscriptionId();

    /**
     * @return the actual/revised publishing interval of this {@link UaSubscription}.
     */
    double getRevisedPublishingInterval();

    /**
     * @return the actual/revised lifetime count of this {@link UaSubscription}.
     */
    UInteger getRevisedLifetimeCount();

    /**
     * @return the actual/revised max keep-alive count of this {@link UaSubscription}.
     */
    UInteger getRevisedMaxKeepAliveCount();

    /**
     * @return the maximum number of notifications that will be returned in any publish response.
     */
    UInteger getMaxNotificationsPerPublish();

    /**
     * @return {@code true} if publishing is enabled.
     */
    boolean isPublishingEnabled();

    /**
     * @return the relative priority assigned to this {@link UaSubscription}.
     */
    UByte getPriority();

    /**
     * @return an {@link ImmutableList} of this {@link UaSubscription}'s {@link UaMonitoredItem}s.
     */
    ImmutableList<UaMonitoredItem> getMonitoredItems();

    /**
     * Create one or more {@link UaMonitoredItem}s.
     * <p>
     * Callers must check the quality of each of the returned {@link UaMonitoredItem}s; it is not to be assumed that
     * all items were created successfully. Any item with a bad quality will not be updated nor will it be part of the
     * subscription's bookkeeping.
     *
     * @param timestampsToReturn the {@link TimestampsToReturn}.
     * @param itemsToCreate      a list of {@link MonitoredItemCreateRequest}s.
     * @return a list of {@link UaMonitoredItem}s.
     */
    CompletableFuture<List<UaMonitoredItem>> createMonitoredItems(TimestampsToReturn timestampsToReturn,
                                                                  List<MonitoredItemCreateRequest> itemsToCreate);

    /**
     * Modify one or more {@link UaMonitoredItem}s.
     *
     * @param timestampsToReturn the {@link TimestampsToReturn} to set for each item.
     * @param itemsToModify      a list of {@link MonitoredItemModifyRequest}s.
     * @return a {@link CompletableFuture} containing a list of {@link StatusCode}s, the size and order matching that
     * of {@code itemsToModify}.
     */
    CompletableFuture<List<StatusCode>> modifyMonitoredItems(TimestampsToReturn timestampsToReturn,
                                                             List<MonitoredItemModifyRequest> itemsToModify);

    /**
     * Delete on or more {@link UaMonitoredItem}s.
     *
     * @param itemsToDelete the items to delete.
     * @return a {@link CompletableFuture} containing a list of {@link StatusCode}s, the size and order matching that
     * of {@code itemsToDelete}.
     */
    CompletableFuture<List<StatusCode>> deleteMonitoredItems(List<UaMonitoredItem> itemsToDelete);

    /**
     * Set the {@link MonitoringMode} for one or more {@link UaMonitoredItem}s.
     *
     * @param monitoringMode the {@link MonitoringMode} to set.
     * @param items          the {@link UaMonitoredItem}s to set the mode on.
     * @return a {@link CompletableFuture} containing a list of {@link StatusCode}s, the size and order matching that
     * of {@code items}.
     */
    CompletableFuture<List<StatusCode>> setMonitoringMode(MonitoringMode monitoringMode, List<UaMonitoredItem> items);

    /**
     * Set the publishing mode for this subscription.
     *
     * @param publishingEnabled {@code true} if publishing should be enabled.
     * @return a {@link CompletableFuture} containing a {@link StatusCode} representing the result of this operation.
     */
    CompletableFuture<StatusCode> setPublishingMode(boolean publishingEnabled);

}
