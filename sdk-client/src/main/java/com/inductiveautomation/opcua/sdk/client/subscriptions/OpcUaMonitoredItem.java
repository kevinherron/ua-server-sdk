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

package com.inductiveautomation.opcua.sdk.client.subscriptions;

import java.util.function.Function;

import com.inductiveautomation.opcua.sdk.client.subscriptions.OpcUaSubscription.ModifyItemsContext;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExtensionObject;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.MonitoringMode;
import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoringFilterResult;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoringParameters;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadValueId;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class OpcUaMonitoredItem {

    private volatile UInteger revisedQueueSize = uint(0);
    private volatile double revisedSamplingInterval = 0.0;
    private volatile MonitoringFilterResult filterResult = null;
    private volatile TimestampsToReturn timestampsToReturn = TimestampsToReturn.Both;

    private volatile StatusCode statusCode =
            new StatusCode(StatusCodes.Bad_MonitoredItemIdInvalid);

    private volatile MonitoringMode monitoringMode = MonitoringMode.Disabled;

    private volatile UInteger subscriptionId = uint(0);
    private volatile UInteger monitoredItemId = uint(0);

    private final ReadValueId readValueId;

    public OpcUaMonitoredItem(ReadValueId readValueId) {
        this.readValueId = readValueId;
    }

    public UInteger getSubscriptionId() {
        return subscriptionId;
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

}
