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

public interface UaMethodNode extends UaNode {

    /**
     * Read the Executable attribute {@link DataValue}.
     *
     * @return the Executable attribute {@link DataValue}.
     */
    CompletableFuture<DataValue> readExecutable();

    /**
     * Read the Executable attribute.
     * <p>
     * If quality and timestamps are required, see {@link #readExecutable()}.
     *
     * @return the Executable attribute.
     */
    default CompletableFuture<Boolean> readExecutableAttribute() {
        return readExecutable().thenApply(v -> (Boolean) v.getValue().getValue());
    }

    /**
     * Read the UserExecutable attribute {@link DataValue}.
     *
     * @return the UserExecutable attribute {@link DataValue}.
     */
    CompletableFuture<DataValue> readUserExecutable();

    /**
     * Read the UserExecutable attribute.
     * <p>
     * If quality and timestamps are required, see {@link #readUserExecutable()}.
     *
     * @return the UserExecutable attribute.
     */
    default CompletableFuture<Boolean> readUserExecutableAttribute() {
        return readUserExecutable().thenApply(v -> (Boolean) v.getValue().getValue());
    }

    /**
     * Write a {@link DataValue} to the Executable attribute.
     *
     * @param value the {@link DataValue} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    CompletableFuture<StatusCode> writeExecutable(DataValue value);

    /**
     * Write a {@link Boolean} to the Executable attribute.
     *
     * @param executable the {@link Boolean} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    default CompletableFuture<StatusCode> writeExecutableAttribute(Boolean executable) {
        return writeExecutable(new DataValue(new Variant(executable)));
    }

    /**
     * Write a {@link DataValue} to the UserExecutable attribute.
     *
     * @param value the {@link DataValue} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    CompletableFuture<StatusCode> writeUserExecutable(DataValue value);

    /**
     * Write a {@link Boolean} to the UserExecutable attribute.
     *
     * @param userExecutable the {@link Boolean} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    default CompletableFuture<StatusCode> writeUserExecutableAttribute(Boolean userExecutable) {
        return writeExecutable(new DataValue(new Variant(userExecutable)));
    }

}
