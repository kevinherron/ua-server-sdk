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

package com.digitalpetri.opcua.sdk.client.api.subscriptions;

import java.util.function.Consumer;

import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.ExtensionObject;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.MonitoringMode;
import com.digitalpetri.opcua.stack.core.types.structured.ReadValueId;

public interface UaMonitoredItem {

    /**
     * Get the client-assigned id.
     * <p>
     * This handle is used in the subscription to match incoming values to the corresponding monitored item.
     *
     * @return the client-assigned id.
     */
    UInteger getClientHandle();

    /**
     * Get the server-assigned id.
     *
     * @return the server-assigned id.
     */
    UInteger getMonitoredItemId();

    /**
     * Get the {@link ReadValueId}.
     *
     * @return the {@link ReadValueId}.
     */
    ReadValueId getReadValueId();

    /**
     * Get the {@link StatusCode} of the last operation.
     *
     * @return the {@link StatusCode} of the last operation.
     */
    StatusCode getStatusCode();

    /**
     * Get the revised sampling interval.
     *
     * @return the revised sampling interval.
     */
    double getRevisedSamplingInterval();

    /**
     * Get the revised queue size.
     *
     * @return the revised queue size.
     */
    UInteger getRevisedQueueSize();

    /**
     * Get the filter result {@link ExtensionObject}.
     *
     * @return the filter result {@link ExtensionObject}.
     */
    ExtensionObject getFilterResult();

    /**
     * Get the {@link MonitoringMode}.
     *
     * @return the {@link MonitoringMode}.
     */
    MonitoringMode getMonitoringMode();

    /**
     * Set the {@link Consumer} that will receive values as they arrive from the server.
     *
     * @param valueConsumer the {@link Consumer} that will receive values as they arrive from the server.
     */
    void setValueConsumer(Consumer<DataValue> valueConsumer);

    /**
     * Set the {@link Consumer} that will receive events as they arrive from the server.
     *
     * @param eventConsumer the {@link Consumer} that will receive events as they arrive from the server.
     */
    void setEventConsumer(Consumer<Variant[]> eventConsumer);

}
