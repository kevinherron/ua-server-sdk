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

package com.inductiveautomation.opcua.sdk.server.items;

import com.inductiveautomation.opcua.sdk.server.api.EventItem;
import com.inductiveautomation.opcua.sdk.server.api.events.Event;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExtensionObject;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.MonitoringMode;
import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.inductiveautomation.opcua.stack.core.types.structured.EventFieldList;
import com.inductiveautomation.opcua.stack.core.types.structured.EventFilter;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadValueId;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class EventMonitoredItem extends BaseMonitoredItem<Variant[]> implements EventItem {

    private volatile EventFilter filter;

    public EventMonitoredItem(UInteger id,
                              ReadValueId readValueId,
                              MonitoringMode monitoringMode,
                              TimestampsToReturn timestamps,
                              UInteger clientHandle,
                              double samplingInterval,
                              UInteger queueSize,
                              boolean discardOldest,
                              EventFilter filter) {

        super(id, readValueId, monitoringMode, timestamps, clientHandle, samplingInterval, queueSize, discardOldest);

        this.filter = filter;
    }

    @Override
    public void setEvent(Event event) {

    }

    @Override
    public ExtensionObject getFilterResult() {
        return null;
    }

    @Override
    protected void installFilter(ExtensionObject filterXo) throws UaException {

    }

    @Override
    protected EventFieldList wrapQueueValue(Variant[] value) {
        return new EventFieldList(uint(getClientHandle()), value);
    }

    public static BaseMonitoredItem<?> create() {
        return null; // TODO
    }
}
