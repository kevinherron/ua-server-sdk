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

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface UaVariableTypeNode extends UaNode {

    /**
     * Read the Value attribute {@link DataValue}.
     *
     * @return the Value attribute {@link DataValue}.
     */
    CompletableFuture<DataValue> readValue();

    /**
     * Read the Value attribute value.
     * <p>
     * If quality and timestamps are required, see {@link #readValue()}.
     *
     * @return the Value attribute value.
     */
    default CompletableFuture<Optional<Object>> readValueAttribute() {
        return readValue().thenApply(v -> {
            StatusCode statusCode = v.getStatusCode();

            if (statusCode.getValue() == StatusCodes.Bad_AttributeIdInvalid) {
                return Optional.empty();
            } else {
                return Optional.ofNullable(v.getValue().getValue());
            }
        });
    }

    /**
     * Read the DataType attribute {@link DataValue}.
     *
     * @return the DataType attribute {@link DataValue}.
     */
    CompletableFuture<DataValue> readDataType();

    /**
     * Read the DataType attribute value.
     *
     * @return the DataType attribute value.
     */
    default CompletableFuture<NodeId> readDataTypeAttribute() {
        return readDataType().thenApply(v -> (NodeId) v.getValue().getValue());
    }

    /**
     * Read the ValueRank attribute {@link DataValue}.
     *
     * @return the ValueRank attribute {@link DataValue}.
     */
    CompletableFuture<DataValue> readValueRank();

    /**
     * Read the ValueRank attribute value.
     *
     * @return the ValueRank attribute value.
     */
    default CompletableFuture<Integer> readValueRankAttribute() {
        return readValueRank().thenApply(v -> (Integer) v.getValue().getValue());
    }

    /**
     * Read the ArrayDimensions attribute {@link DataValue}.
     *
     * @return the ArrayDimensions attribute {@link DataValue}.
     */
    CompletableFuture<DataValue> readArrayDimensions();

    /**
     * Read the ArrayDimensions attribute value.
     *
     * @return the ArrayDimensions attribute value.
     */
    default CompletableFuture<Optional<UInteger[]>> readArrayDimensionsAttribute() {
        return readArrayDimensions().thenApply(v -> {
            StatusCode statusCode = v.getStatusCode();

            if (statusCode.getValue() == StatusCodes.Bad_AttributeIdInvalid) {
                return Optional.empty();
            } else {
                return Optional.ofNullable((UInteger[]) v.getValue().getValue());
            }
        });
    }

    /**
     * Read the IsAbstract attribute {@link DataValue}.
     *
     * @return the IsAbstract attribute {@link DataValue}.
     */
    CompletableFuture<DataValue> readIsAbstract();

    /**
     * Read the IsAbstract attribute value.
     *
     * @return the IsAbstract attribute value.
     */
    default CompletableFuture<Boolean> readIsAbstractAttribute() {
        return readIsAbstract().thenApply(v -> (Boolean) v.getValue().getValue());
    }

    /**
     * Write a {@link DataValue} to the Value attribute.
     *
     * @param value the {@link DataValue} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    CompletableFuture<StatusCode> writeValue(DataValue value);

    /**
     * Write an {@link Object} to the Value attribute.
     *
     * @param value the {@link Object} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    default CompletableFuture<StatusCode> writeValueAttribute(Object value) {
        return writeValue(new DataValue(new Variant(value)));
    }

    /**
     * Write a {@link DataValue} to the DataType attribute.
     *
     * @param value the {@link DataValue} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    CompletableFuture<StatusCode> writeDataType(DataValue value);

    /**
     * Write a {@link NodeId} to the DataType attribute.
     *
     * @param dataType the {@link NodeId} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    default CompletableFuture<StatusCode> writeDataTypeAttribute(NodeId dataType) {
        return writeDataType(new DataValue(new Variant(dataType)));
    }

    /**
     * Write a {@link DataValue} to the ValueRank attribute.
     *
     * @param value the {@link DataValue} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    CompletableFuture<StatusCode> writeValueRank(DataValue value);

    /**
     * Write an {@link Integer} to the ValueRank attribute.
     *
     * @param valueRank the {@link Integer} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    default CompletableFuture<StatusCode> writeValueRankAttribute(Integer valueRank) {
        return writeValueRank(new DataValue(new Variant(valueRank)));
    }

    /**
     * Write a {@link DataValue} to the ArrayDimensions attribute.
     *
     * @param value the {@link DataValue} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    CompletableFuture<StatusCode> writeArrayDimensions(DataValue value);

    /**
     * Write a {@link UInteger}[] to the ArrayDimensions attribute.
     *
     * @param arrayDimensions the {@link UInteger}[] to write.
     * @return the {@link StatusCode} of the write operation.
     */
    default CompletableFuture<StatusCode> writeArrayDimensionsAttribute(UInteger[] arrayDimensions) {
        return writeArrayDimensions(new DataValue(new Variant(arrayDimensions)));
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
    default CompletableFuture<StatusCode> writeIsAbstractAttribute(DataValue isAbstract) {
        return writeIsAbstract(new DataValue(new Variant(isAbstract)));
    }

}
