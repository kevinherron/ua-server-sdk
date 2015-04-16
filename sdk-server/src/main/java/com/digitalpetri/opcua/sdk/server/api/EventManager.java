/*
 * Copyright 2014
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

package com.digitalpetri.opcua.sdk.server.api;

import java.util.List;

import com.digitalpetri.opcua.sdk.server.items.MonitoredEventItem;

public interface EventManager {

    /**
     * {@link MonitoredEventItem} have been created for nodes belonging to this {#link EventManager}.
     *
     * @param eventItems the {@link MonitoredEventItem}s that were created.
     */
    void onEventItemsCreated(List<MonitoredEventItem> eventItems);

    void onEventItemsModified(List<MonitoredEventItem> eventItems);

    void onEventItemsDeleted(List<MonitoredEventItem> eventItems);

    void onMonitoringModeChanged(List<MonitoredEventItem> eventItems);

}
