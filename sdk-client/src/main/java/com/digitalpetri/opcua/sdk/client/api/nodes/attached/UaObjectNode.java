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

package com.digitalpetri.opcua.sdk.client.api.nodes.attached;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;

public interface UaObjectNode extends UaNode {

    /**
     * Read the EventNotifier attribute {@link DataValue}.
     *
     * @return the EventNotifier attribute {@link DataValue}.
     */
    CompletableFuture<DataValue> readEventNotifier();

    /**
     * Read the EventNotifier attribute.
     * <p>
     * If quality and timestamps are required, see {@link #readEventNotifier()}.
     *
     * @return the EventNotifier attribute.
     */
    default CompletableFuture<UByte> readEventNotifierAttribute() {
        return readEventNotifier().thenApply(v -> (UByte) v.getValue().getValue());
    }

    /**
     * Write a {@link DataValue} to the EventNotifier attribute.
     *
     * @param value the {@link DataValue} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    CompletableFuture<StatusCode> writeEventNotifier(DataValue value);

    /**
     * Write a {@link UByte} to the EventNotifier attribute.
     *
     * @param eventNotifier the {@link UByte} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    default CompletableFuture<StatusCode> writeEventNotifierAttribute(UByte eventNotifier) {
        return writeEventNotifier(new DataValue(new Variant(eventNotifier)));
    }

}
