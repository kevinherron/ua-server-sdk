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

public interface UaObjectTypeNode extends UaNode {

    /**
     * Read the IsAbstract attribute {@link DataValue}.
     *
     * @return the IsAbstract attribute {@link DataValue}.
     */
    CompletableFuture<DataValue> readIsAbstract();

    /**
     * Read the IsAbstract attribute.
     * <p>
     * If quality and timestamps are required, see {@link #readIsAbstract()}.
     *
     * @return the IsAbstract attribute.
     */
    default CompletableFuture<Boolean> readIsAbstractAttribute() {
        return readIsAbstract().thenApply(v -> (Boolean) v.getValue().getValue());
    }

    /**
     * Write a {@link DataValue} to the IsAbstract attribute.
     *
     * @param value the {@link DataValue} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    CompletableFuture<StatusCode> writeIsAbstract(DataValue value);

    /**
     * Write a {@link Boolean} to the IsAbstract attribute.
     *
     * @param isAbstract the {@link Boolean} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    default CompletableFuture<StatusCode> writeIsAbstractAttribute(boolean isAbstract) {
        return writeIsAbstract(new DataValue(new Variant(isAbstract)));
    }

}
