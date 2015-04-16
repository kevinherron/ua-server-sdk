/*
 * Copyright 2015
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

package com.digitalpetri.opcua.sdk.client.subscriptions;

import java.util.function.Consumer;
import java.util.function.Function;

import com.digitalpetri.opcua.sdk.client.subscriptions.OpcUaSubscription.ModifyItemsContext;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.ExtensionObject;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.MonitoringMode;
import com.digitalpetri.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoringFilterResult;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoringParameters;
import com.digitalpetri.opcua.stack.core.types.structured.ReadValueId;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class OpcUaMonitoredItem {

    private volatile Consumer<DataValue> valueConsumer;
    private volatile Consumer<Variant[]> eventConsumer;
    private volatile UInteger revisedQueueSize = uint(0);
    private volatile double revisedSamplingInterval = 0.0;
    private volatile MonitoringFilterResult filterResult = null;
    private volatile TimestampsToReturn timestampsToReturn = TimestampsToReturn.Both;

    private volatile StatusCode statusCode =
            new StatusCode(StatusCodes.Bad_MonitoredItemIdInvalid);

    private volatile MonitoringMode monitoringMode = MonitoringMode.Disabled;

    private volatile UInteger subscriptionId = uint(0);
    private volatile UInteger monitoredItemId = uint(0);

    private final UInteger clientHandle;
    private final ReadValueId readValueId;

    public OpcUaMonitoredItem(UInteger clientHandle, ReadValueId readValueId) {
        this.clientHandle = clientHandle;
        this.readValueId = readValueId;
    }

    public UInteger getSubscriptionId() {
        return subscriptionId;
    }

    public UInteger getClientHandle() {
        return clientHandle;
    }

    public UInteger getMonitoredItemId() {
        return monitoredItemId;
    }

    public ReadValueId getReadValueId() {
        return readValueId;
    }

    public MonitoringMode getMonitoringMode() {
        return monitoringMode;
    }

    public TimestampsToReturn getTimestampsToReturn() {
        return timestampsToReturn;
    }

    public void modify(ModifyItemsContext context, Function<OpcUaMonitoredItem, MonitoringParameters> modifier) {
        MonitoringParameters parameters = modifier.apply(this);

        context.addItem(this, parameters);
    }

    public void setValueConsumer(Consumer<DataValue> valueConsumer) {
        this.valueConsumer = valueConsumer;
    }

    public void setEventConsumer(Consumer<Variant[]> eventConsumer) {
        this.eventConsumer = eventConsumer;
    }

    void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    void setSubscriptionId(UInteger subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    void setMonitoredItemId(UInteger monitoredItemId) {
        this.monitoredItemId = monitoredItemId;
    }

    void setMonitoringMode(MonitoringMode monitoringMode) {
        this.monitoringMode = monitoringMode;
    }

    void setFilterResult(ExtensionObject filterResult) {
        if (filterResult != null && filterResult.getObject() instanceof MonitoringFilterResult) {
            this.filterResult = (MonitoringFilterResult) filterResult.getObject();
        }
    }

    void setRevisedSamplingInterval(double revisedSamplingInterval) {
        this.revisedSamplingInterval = revisedSamplingInterval;
    }

    void setRevisedQueueSize(UInteger revisedQueueSize) {
        this.revisedQueueSize = revisedQueueSize;
    }

    void setTimestampsToReturn(TimestampsToReturn timestampsToReturn) {
        this.timestampsToReturn = timestampsToReturn;
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
