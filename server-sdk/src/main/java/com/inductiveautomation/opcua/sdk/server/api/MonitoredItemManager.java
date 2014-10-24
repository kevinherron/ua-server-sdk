/*
 * Copyright 2014 Inductive Automation
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

package com.inductiveautomation.opcua.sdk.server.api;

import java.util.List;

import com.inductiveautomation.opcua.stack.core.types.enumerated.MonitoringMode;

public interface MonitoredItemManager {

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
    default void onEventItemsCreated(List<EventItem> eventItems) {}

    /**
     * {@link EventItem}s have been modified for nodes belonging to this {@link NodeManager}.
     *
     * @param eventItems the {@link EventItem}s that were modified.
     */
    default void onEventItemsModified(List<EventItem> eventItems) {}

    /**
     * {@link EventItem}s have been deleted for nodes belonging to this {@link NodeManager}.
     *
     * @param eventItems the {@link EventItem}s that were deleted.
     */
    default void onEventItemsDeleted(List<EventItem> eventItems) {}

    /**
     * {@link MonitoredItem}s have had their {@link MonitoringMode} modified by a client.
     * <p>
     * Check if sampling is still enabled and react accordingly.
     *
     * @param monitoredItems The {@link MonitoredItem}s whose {@link MonitoringMode} was modified.
     */
    void onMonitoringModeChanged(List<MonitoredItem> monitoredItems);

}
