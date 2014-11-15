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

import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.server.api.DataItem;
import com.inductiveautomation.opcua.sdk.server.util.DataChangeMonitoringFilter;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExtensionObject;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.DataChangeTrigger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.DeadbandType;
import com.inductiveautomation.opcua.stack.core.types.enumerated.MonitoringMode;
import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.inductiveautomation.opcua.stack.core.types.structured.AggregateFilter;
import com.inductiveautomation.opcua.stack.core.types.structured.DataChangeFilter;
import com.inductiveautomation.opcua.stack.core.types.structured.EventFilter;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoredItemNotification;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoringFilter;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadValueId;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

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
                             ReadValueId readValueId,
                             MonitoringMode monitoringMode,
                             TimestampsToReturn timestamps,
                             UInteger clientHandle,
                             double samplingInterval,
                             ExtensionObject filter,
                             UInteger queueSize,
                             boolean discardOldest) throws UaException {

        super(id, readValueId, monitoringMode, timestamps, clientHandle, samplingInterval, queueSize, discardOldest);

        installFilter(filter);
    }

    @Override
    public synchronized void setValue(DataValue value) {
        boolean valuePassesFilter = DataChangeMonitoringFilter.filter(lastValue, value, filter);

        if (valuePassesFilter) {
            lastValue = value;

            enqueue(value);
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
            setValue(new DataValue(Variant.NullValue, quality, DateTime.now(), DateTime.now()));
        } else {
            DataValue value = new DataValue(
                    lastValue.getValue(),
                    quality,
                    DateTime.now(),
                    DateTime.now()
            );

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
        if (filterXo == null || filterXo.getObject() == null) {
            this.filter = DefaultFilter;
        } else {
            Object filterObject = filterXo.getObject();

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
