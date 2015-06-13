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
        // TODO Apply EventFilter...

        Variant[] variants = new Variant[]{
                new Variant(event.getEventId()),
                new Variant(event.getEventType()),
                new Variant(event.getSourceNode()),
                new Variant(event.getSourceNode()),
                new Variant(event.getTime())
        };

        enqueue(variants);
    }

    @Override
    protected void enqueue(Variant[] value) {
        if (queueSize < queue.maxSize()) {
            queue.add(value);
        } else {
            if (getQueueSize() > 1) {
                // TODO Send an EventQueueOverflowEventType...
            }
            if (discardOldest) {
                queue.add(value);
            } else {
                queue.set(queue.maxSize() - 1, value);
            }
        }
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

}
