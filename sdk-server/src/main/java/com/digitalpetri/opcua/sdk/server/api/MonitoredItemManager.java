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

package com.digitalpetri.opcua.sdk.server.api;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.MonitoringMode;

public interface MonitoredItemManager {

    /**
     * The client is creating a {@link MonitoredItem}.
     * <p>
     * If the {@code nodeId} and {@code attributeId} are valid, complete {@code revisedSamplingInterval} with any
     * necessary revisions to the sampling interval.
     * <p>
     * Otherwise, complete exceptionally with the appropriate {@link UaException} and {@link StatusCode}.
     *
     * @param nodeId                    the requested {@link NodeId} of the node to monitor.
     * @param attributeId               the attribute id of the attribute to monitor.
     * @param requestedSamplingInterval the requested sampling interval.
     * @param revisedSamplingInterval   the {@link CompletableFuture} to complete.
     */
    void onCreateMonitoredItem(NodeId nodeId,
                               UInteger attributeId,
                               double requestedSamplingInterval,
                               CompletableFuture<Double> revisedSamplingInterval);

    /**
     * The client is modifying a {@link MonitoredItem}.
     * <p>
     * Complete {@code revisedSamplingInterval} with any necessary revisions to the sampling interval.
     *
     * @param requestedSamplingInterval the sampling interval being requested.
     * @param revisedSamplingInterval   the {@link CompletableFuture} to complete.
     */
    void onModifyMonitoredItem(double requestedSamplingInterval,
                               CompletableFuture<Double> revisedSamplingInterval);

    /**
     * {@link DataItem}s have been created for nodes belonging to this {@link NodeManager}.
     * <p>
     * If sampling is enabled for this item, it is expected that a best-effort will be made to update the item's value
     * at the sampling rate.
     *
     * @param dataItems the {@link DataItem}s that were created.
     */
    void onDataItemsCreated(List<DataItem> dataItems);

    /**
     * {@link DataItem}s have been modified for nodes belonging to this {@link NodeManager}.
     * <p>
     * Check to see if the sampling rate has changed or if sampling has been enabled or disabled. Result have not been
     * returned to the client yet, so if the requested sampling rate cannot be supported now is the time to revise it.
     *
     * @param dataItems the {@link DataItem}s that were modified.
     */
    void onDataItemsModified(List<DataItem> dataItems);

    /**
     * {@link DataItem}s have been deleted for nodes belonging to this {@link NodeManager}.
     * <p>
     * Updates to this item should cease and any references to it should be removed.
     *
     * @param dataItems the {@link DataItem}s that were deleted.
     */
    void onDataItemsDeleted(List<DataItem> dataItems);

    /**
     * {@link EventItem}s have been created for nodes belonging to this {@link NodeManager}.
     *
     * @param eventItems the {@link EventItem}s that were created.
     */
    default void onEventItemsCreated(List<EventItem> eventItems) {
    }

    /**
     * {@link EventItem}s have been modified for nodes belonging to this {@link NodeManager}.
     *
     * @param eventItems the {@link EventItem}s that were modified.
     */
    default void onEventItemsModified(List<EventItem> eventItems) {
    }

    /**
     * {@link EventItem}s have been deleted for nodes belonging to this {@link NodeManager}.
     *
     * @param eventItems the {@link EventItem}s that were deleted.
     */
    default void onEventItemsDeleted(List<EventItem> eventItems) {
    }

    /**
     * {@link MonitoredItem}s have had their {@link MonitoringMode} modified by a client.
     * <p>
     * Check if sampling is still enabled and react accordingly.
     *
     * @param monitoredItems The {@link MonitoredItem}s whose {@link MonitoringMode} was modified.
     */
    void onMonitoringModeChanged(List<MonitoredItem> monitoredItems);

}
