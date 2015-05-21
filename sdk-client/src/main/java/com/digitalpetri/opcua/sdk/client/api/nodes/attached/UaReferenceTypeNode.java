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
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;

public interface UaReferenceTypeNode {

    /**
     * Read the IsAbstract {@link DataValue}.
     *
     * @return the IsAbstract {@link DataValue}.
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
     * Read the Symmetric attribute {@link DataValue}.
     *
     * @return the Symmetric attribute {@link DataValue}.
     */
    CompletableFuture<DataValue> readSymmetric();

    /**
     * Read the Symmetric attribute.
     * <p>
     * If quality and timestamps are required, see {@link #readSymmetric()}.
     *
     * @return the Symmetric attribute.
     */
    default CompletableFuture<Boolean> readSymmetricAttribute() {
        return readSymmetric().thenApply(v -> (Boolean) v.getValue().getValue());
    }

    /**
     * Read the InverseName attribute {@link DataValue}.
     *
     * @return the InverseName attribute {@link DataValue}.
     */
    CompletableFuture<DataValue> readInverseName();

    /**
     * Read the InverseName attribute.
     * <p>
     * If quality and timestamps are required, see {@link #readInverseName()}.
     *
     * @return the InverseName attribute.
     */
    default CompletableFuture<Optional<LocalizedText>> readInverseNameAttribute() {
        return readInverseName().thenApply(v -> {
            StatusCode statusCode = v.getStatusCode();

            if (statusCode.getValue() == StatusCodes.Bad_AttributeIdInvalid) {
                return Optional.empty();
            } else {
                return Optional.ofNullable((LocalizedText) v.getValue().getValue());
            }
        });
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

    /**
     * Write a {@link DataValue} to the Symmetric attribute.
     *
     * @param value the {@link DataValue} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    CompletableFuture<StatusCode> writeSymmetric(DataValue value);

    /**
     * Write a {@link Boolean} to the Symmetric attribute.
     *
     * @param symmetric the {@link Boolean} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    default CompletableFuture<StatusCode> writeSymmetricAttribute(boolean symmetric) {
        return writeSymmetric(new DataValue(new Variant(symmetric)));
    }

    /**
     * Write a {@link DataValue} to the InverseName attribute.
     *
     * @param value the {@link DataValue} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    CompletableFuture<StatusCode> writeInverseName(DataValue value);

    /**
     * Write a {@link LocalizedText} to the InverseName attribute.
     *
     * @param inverseName the {@link LocalizedText} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    default CompletableFuture<StatusCode> writeInverseNameAttribute(LocalizedText inverseName) {
        return writeInverseName(new DataValue(new Variant(inverseName)));
    }

}
