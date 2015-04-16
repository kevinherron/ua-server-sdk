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

package com.digitalpetri.opcua.sdk.server.items;

import com.digitalpetri.opcua.sdk.core.events.BaseEventType;
import com.digitalpetri.opcua.sdk.server.api.EventItem;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.builtin.ExtensionObject;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.MonitoringMode;
import com.digitalpetri.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.digitalpetri.opcua.stack.core.types.structured.EventFieldList;
import com.digitalpetri.opcua.stack.core.types.structured.EventFilter;
import com.digitalpetri.opcua.stack.core.types.structured.ReadValueId;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class MonitoredEventItem extends BaseMonitoredItem<Variant[]> implements EventItem {

    private volatile EventFilter filter;

    public MonitoredEventItem(UInteger id,
                              ReadValueId readValueId,
                              MonitoringMode monitoringMode,
                              TimestampsToReturn timestamps,
                              UInteger clientHandle,
                              double samplingInterval,
                              UInteger queueSize,
                              boolean discardOldest,
                              ExtensionObject filter) throws UaException {

        super(id, readValueId, monitoringMode, timestamps, clientHandle, samplingInterval, queueSize, discardOldest);

        installFilter(filter);
    }

    @Override
    public void setEvent(BaseEventType event) {

    }

    @Override
    protected void enqueue(Variant[] value) {

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

    @Override
    public boolean isSamplingEnabled() {
        return getMonitoringMode() != MonitoringMode.Disabled;
    }

    public static BaseMonitoredItem<?> create() {
        return null; // TODO
    }
}
