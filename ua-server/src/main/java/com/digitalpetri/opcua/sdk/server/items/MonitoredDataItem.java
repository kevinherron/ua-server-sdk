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

import com.digitalpetri.opcua.sdk.core.AttributeIds;
import com.digitalpetri.opcua.sdk.server.api.DataItem;
import com.digitalpetri.opcua.sdk.server.util.DataChangeMonitoringFilter;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.ExtensionObject;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.DataChangeTrigger;
import com.digitalpetri.opcua.stack.core.types.enumerated.DeadbandType;
import com.digitalpetri.opcua.stack.core.types.enumerated.MonitoringMode;
import com.digitalpetri.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.digitalpetri.opcua.stack.core.types.structured.AggregateFilter;
import com.digitalpetri.opcua.stack.core.types.structured.DataChangeFilter;
import com.digitalpetri.opcua.stack.core.types.structured.EventFilter;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoredItemNotification;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoringFilter;
import com.digitalpetri.opcua.stack.core.types.structured.ReadValueId;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class MonitoredDataItem extends BaseMonitoredItem<DataValue> implements DataItem {

    private static final DataChangeFilter DefaultFilter = new DataChangeFilter(
            DataChangeTrigger.StatusValue,
            uint(DeadbandType.None.getValue()),
            0.0
    );

    private volatile DataValue lastValue = null;
    private volatile DataChangeFilter filter = null;
    private volatile ExtensionObject filterResult = null;

    public MonitoredDataItem(UInteger id,
                             UInteger subscriptionId,
                             ReadValueId readValueId,
                             MonitoringMode monitoringMode,
                             TimestampsToReturn timestamps,
                             UInteger clientHandle,
                             double samplingInterval,
                             ExtensionObject filter,
                             UInteger queueSize,
                             boolean discardOldest) throws UaException {

        super(id, subscriptionId, readValueId, monitoringMode, timestamps, clientHandle, samplingInterval, queueSize, discardOldest);

        installFilter(filter);
    }

    @Override
    public synchronized void setValue(DataValue value) {
        boolean valuePassesFilter = DataChangeMonitoringFilter.filter(lastValue, value, filter);

        if (valuePassesFilter) {
            lastValue = value;

            enqueue(value);

            if (triggeredItems != null) {
                triggeredItems.values().forEach(item -> item.triggered = true);
            }
        }
    }

    @Override
    protected void enqueue(DataValue value) {
        if (queue.size() < queue.maxSize()) {
            queue.add(value);
        } else {
            if (getQueueSize() > 1) {
                /* Set overflow if queueSize > 1... */
                value = value.withStatus(value.getStatusCode().withOverflow());
            } else if (value.getStatusCode().isOverflowSet()) {
                /* But make sure it's clear otherwise. */
                value = value.withStatus(value.getStatusCode().withoutOverflow());
            }

            if (discardOldest) {
                queue.add(value);
            } else {
                queue.set(queue.maxSize() - 1, value);
            }
        }
    }

    @Override
    public synchronized void setQuality(StatusCode quality) {
        if (lastValue == null) {
            setValue(new DataValue(Variant.NULL_VALUE, quality, DateTime.now(), DateTime.now()));
        } else {
            DataValue value = new DataValue(
                    lastValue.getValue(),
                    quality,
                    DateTime.now(),
                    DateTime.now());

            setValue(value);
        }
    }

    @Override
    public boolean isSamplingEnabled() {
        return getMonitoringMode() != MonitoringMode.Disabled;
    }

    @Override
    public synchronized void setMonitoringMode(MonitoringMode monitoringMode) {
        if (monitoringMode == MonitoringMode.Disabled) {
            lastValue = null;
        }

        super.setMonitoringMode(monitoringMode);
    }

    public synchronized void clearLastValue() {
        lastValue = null;
    }

    @Override
    protected void installFilter(ExtensionObject filterXo) throws UaException {
        if (filterXo == null || filterXo.decode() == null) {
            this.filter = DefaultFilter;
        } else {
            Object filterObject = filterXo.decode();

            if (filterObject instanceof MonitoringFilter) {
                if (filterObject instanceof DataChangeFilter) {
                    this.filter = ((DataChangeFilter) filterObject);

                    DeadbandType deadbandType = DeadbandType.from(filter.getDeadbandType().intValue());

                    if (deadbandType == null) {
                        throw new UaException(StatusCodes.Bad_DeadbandFilterInvalid);
                    }

                    if (deadbandType != DeadbandType.None &&
                            getReadValueId().getAttributeId().intValue() != AttributeIds.Value) {
                        throw new UaException(StatusCodes.Bad_FilterNotAllowed);
                    }
                } else if (filterObject instanceof AggregateFilter) {
                    throw new UaException(StatusCodes.Bad_MonitoredItemFilterUnsupported);
                } else if (filterObject instanceof EventFilter) {
                    throw new UaException(StatusCodes.Bad_FilterNotAllowed);
                }
            } else {
                throw new UaException(StatusCodes.Bad_MonitoredItemFilterInvalid);
            }
        }
    }

    @Override
    public ExtensionObject getFilterResult() {
        return filterResult;
    }

    @Override
    protected MonitoredItemNotification wrapQueueValue(DataValue value) {
        value = DataValue.derivedValue(value, timestamps);

        return new MonitoredItemNotification(uint(getClientHandle()), value);
    }

}
