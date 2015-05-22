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

import java.util.function.Consumer;

import com.digitalpetri.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.ExtensionObject;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.MonitoringMode;
import com.digitalpetri.opcua.stack.core.types.structured.ReadValueId;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class OpcUaMonitoredItem implements UaMonitoredItem {

    private volatile Consumer<DataValue> valueConsumer;
    private volatile Consumer<Variant[]> eventConsumer;

    private volatile StatusCode statusCode;
    private volatile double revisedSamplingInterval = 0.0;
    private volatile UInteger revisedQueueSize = uint(0);
    private volatile ExtensionObject filterResult;
    private volatile MonitoringMode monitoringMode = MonitoringMode.Disabled;

    private final UInteger clientHandle;
    private final ReadValueId readValueId;
    private final UInteger monitoredItemId;

    public OpcUaMonitoredItem(UInteger clientHandle,
                       ReadValueId readValueId,
                       UInteger monitoredItemId,
                       StatusCode statusCode,
                       double revisedSamplingInterval,
                       UInteger revisedQueueSize,
                       ExtensionObject filterResult,
                       MonitoringMode monitoringMode) {

        this.clientHandle = clientHandle;
        this.readValueId = readValueId;
        this.monitoredItemId = monitoredItemId;
        this.statusCode = statusCode;
        this.revisedSamplingInterval = revisedSamplingInterval;
        this.revisedQueueSize = revisedQueueSize;
        this.filterResult = filterResult;
        this.monitoringMode = monitoringMode;
    }

    @Override
    public UInteger getClientHandle() {
        return clientHandle;
    }

    @Override
    public ReadValueId getReadValueId() {
        return readValueId;
    }

    @Override
    public UInteger getMonitoredItemId() {
        return monitoredItemId;
    }

    @Override
    public StatusCode getStatusCode() {
        return statusCode;
    }

    @Override
    public double getRevisedSamplingInterval() {
        return revisedSamplingInterval;
    }

    @Override
    public UInteger getRevisedQueueSize() {
        return revisedQueueSize;
    }

    @Override
    public ExtensionObject getFilterResult() {
        return filterResult;
    }

    @Override
    public MonitoringMode getMonitoringMode() {
        return monitoringMode;
    }

    @Override
    public void setValueConsumer(Consumer<DataValue> valueConsumer) {
        this.valueConsumer = valueConsumer;
    }

    @Override
    public void setEventConsumer(Consumer<Variant[]> eventConsumer) {
        this.eventConsumer = eventConsumer;
    }

    void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    void setFilterResult(ExtensionObject filterResult) {
        this.filterResult = filterResult;
    }

    void setRevisedSamplingInterval(double revisedSamplingInterval) {
        this.revisedSamplingInterval = revisedSamplingInterval;
    }

    void setRevisedQueueSize(UInteger revisedQueueSize) {
        this.revisedQueueSize = revisedQueueSize;
    }

    void setMonitoringMode(MonitoringMode monitoringMode) {
        this.monitoringMode = monitoringMode;
    }

    void onValueArrived(DataValue value) {
        Consumer<DataValue> c = valueConsumer;
        if (c != null) c.accept(value);
    }

    void onEventArrived(Variant[] values) {
        Consumer<Variant[]> c = eventConsumer;
        if (c != null) c.accept(values);
    }

}
