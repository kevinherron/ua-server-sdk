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
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.NodeClass;

public interface UaNode {

    /**
     * Read the NodeId attribute {@link DataValue}.
     *
     * @return the NodeId attribute {@link DataValue}.
     */
    CompletableFuture<DataValue> readNodeId();

    /**
     * Read the NodeId attribute.
     * <p>
     * If quality and timestamps are required, see {@link #readNodeId()}.
     *
     * @return the NodeId attribute.
     * @see #readNodeId()
     */
    default CompletableFuture<NodeId> readNodeIdAttribute() {
        return readNodeId().thenApply(v -> (NodeId) v.getValue().getValue());
    }

    /**
     * Read the NodeClass attribute {@link DataValue}.
     *
     * @return the NodeClass attribute {@link DataValue}.
     */
    CompletableFuture<DataValue> readNodeClass();

    /**
     * Read the NodeClass attribute.
     * <p>
     * If quality and timestamps are required, see {@link #readNodeClass()}.
     *
     * @return the NodeClass attribute.
     * @see #readNodeClass()
     */
    default CompletableFuture<NodeClass> readNodeClassAttribute() {
        return readNodeClass().thenApply(v -> (NodeClass) v.getValue().getValue());
    }

    /**
     * Read the BrowseName attribute {@link DataValue}.
     *
     * @return the BrowseName attribute {@link DataValue}.
     */
    CompletableFuture<DataValue> readBrowseName();

    /**
     * Read the BrowseName attribute.
     * <p>
     * If quality and timestamps are required, see {@link #readBrowseName()}.
     *
     * @return the BrowseName attribute.
     */
    default CompletableFuture<QualifiedName> readBrowseNameAttribute() {
        return readBrowseName().thenApply(v -> (QualifiedName) v.getValue().getValue());
    }

    /**
     * Read the DisplayName attribute {@link DataValue}.
     *
     * @return the DisplayName attribute {@link DataValue}.
     */
    CompletableFuture<DataValue> readDisplayName();

    /**
     * Read the DisplayName attribute.
     * <p>
     * If quality and timestamps are required, see {@link #readDisplayName()}.
     *
     * @return the DisplayName attribute.
     */
    default CompletableFuture<LocalizedText> readDisplayNameAttribute() {
        return readDisplayName().thenApply(v -> (LocalizedText) v.getValue().getValue());
    }

    /**
     * Read the Description attribute {@link DataValue}.
     *
     * @return the Description attribute {@link DataValue}.
     */
    CompletableFuture<DataValue> readDescription();

    /**
     * Read the Description attribute.
     * <p>
     * If quality and timestamp are required, see {@link #readDescription()}.
     *
     * @return the Description attribute, if present.
     */
    default CompletableFuture<Optional<LocalizedText>> readDescriptionAttribute() {
        return readDescription().thenApply(v -> {
            StatusCode statusCode = v.getStatusCode();

            if (statusCode.getValue() == StatusCodes.Bad_AttributeIdInvalid) {
                return Optional.empty();
            } else {
                return Optional.ofNullable((LocalizedText) v.getValue().getValue());
            }
        });
    }

    /**
     * Read the WriteMask attribute {@link DataValue}.
     *
     * @return the WriteMask attribute {@link DataValue}.
     */
    CompletableFuture<DataValue> readWriteMask();

    /**
     * Read the WriteMask attribute.
     * <p>
     * If quality and timestamp are required, see {@link #readWriteMask()}.
     *
     * @return the WriteMask attribute, if present.
     */
    default CompletableFuture<Optional<UInteger>> readWriteMaskAttribute() {
        return readWriteMask().thenApply(v -> {
            StatusCode statusCode = v.getStatusCode();

            if (statusCode.getValue() == StatusCodes.Bad_AttributeIdInvalid) {
                return Optional.empty();
            } else {
                return Optional.ofNullable((UInteger) v.getValue().getValue());
            }
        });
    }

    /**
     * Read the UserWriteMask attribute {@link DataValue}.
     *
     * @return the UserWriteMask attribute {@link DataValue}.
     */
    CompletableFuture<DataValue> readUserWriteMask();

    /**
     * Read the UserWriteMask attribute.
     * <p>
     * If quality and timestamp are required, see {@link #readUserWriteMask()}.
     *
     * @return the UserWriteMask attribute, if present.
     */
    default CompletableFuture<Optional<UInteger>> readUserWriteMaskAttribute() {
        return readUserWriteMask().thenApply(v -> {
            StatusCode statusCode = v.getStatusCode();

            if (statusCode.getValue() == StatusCodes.Bad_AttributeIdInvalid) {
                return Optional.empty();
            } else {
                return Optional.ofNullable((UInteger) v.getValue().getValue());
            }
        });
    }

    /**
     * Write a {@link DataValue} to the NodeId attribute.
     *
     * @param value the {@link DataValue} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    CompletableFuture<StatusCode> writeNodeId(DataValue value);

    /**
     * Write a {@link NodeId} to the NodeId attribute.
     *
     * @param nodeId the {@link NodeId} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    default CompletableFuture<StatusCode> writeNodeIdAttribute(NodeId nodeId) {
        return writeNodeId(new DataValue(new Variant(nodeId)));
    }

    /**
     * Write a {@link DataValue} to the NodeClass attribute.
     *
     * @param value the {@link DataValue} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    CompletableFuture<StatusCode> writeNodeClass(DataValue value);

    /**
     * Write a {@link NodeClass} to the NodeClass attribute.
     *
     * @param nodeClass the {@link NodeClass} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    default CompletableFuture<StatusCode> writeNodeClassAttribute(NodeClass nodeClass) {
        return writeNodeClass(new DataValue(new Variant(nodeClass)));
    }

    /**
     * Write a {@link DataValue} to the BrowseName attribute.
     *
     * @param value the {@link DataValue} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    CompletableFuture<StatusCode> writeBrowseName(DataValue value);

    /**
     * Write a {@link QualifiedName} to the BrowseName attribute.
     *
     * @param browseName the {@link QualifiedName} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    default CompletableFuture<StatusCode> writeBrowseNameAttribute(QualifiedName browseName) {
        return writeBrowseName(new DataValue(new Variant(browseName)));
    }

    /**
     * Write a {@link DataValue} to the DisplayName attribute.
     *
     * @param value the {@link DataValue} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    CompletableFuture<StatusCode> writeDisplayName(DataValue value);

    /**
     * Write a {@link LocalizedText} to the DisplayName attribute.
     *
     * @param displayName the {@link LocalizedText} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    default CompletableFuture<StatusCode> writeDisplayNameAttribute(LocalizedText displayName) {
        return writeDisplayName(new DataValue(new Variant(displayName)));
    }

    /**
     * Write a {@link DataValue} to the Description attribute.
     *
     * @param value the {@link DataValue} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    CompletableFuture<StatusCode> writeDescription(DataValue value);

    /**
     * Write a {@link LocalizedText} to the Description attribute.
     *
     * @param description the {@link LocalizedText} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    default CompletableFuture<StatusCode> writeDescriptionAttribute(LocalizedText description) {
        return writeDescription(new DataValue(new Variant(description)));
    }

    /**
     * Write a {@link DataValue} to the WriteMask attribute.
     *
     * @param value the {@link DataValue} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    CompletableFuture<StatusCode> writeWriteMask(DataValue value);

    /**
     * Write a {@link UInteger} to the WriteMask attribute.
     *
     * @param writeMask the {@link UInteger} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    default CompletableFuture<StatusCode> writeWriteMaskAttribute(UInteger writeMask) {
        return writeWriteMask(new DataValue(new Variant(writeMask)));
    }

    /**
     * Write a {@link DataValue} to the UserWriteMask attribute.
     *
     * @param value the {@link DataValue} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    CompletableFuture<StatusCode> writeUserWriteMask(DataValue value);

    /**
     * Write a {@link UInteger} to the UserWriteMask attribute.
     *
     * @param userWriteMask the {@link UInteger} to write.
     * @return the {@link StatusCode} of the write operation.
     */
    default CompletableFuture<StatusCode> writeUserWriteMaskAttribute(UInteger userWriteMask) {
        return writeUserWriteMask(new DataValue(new Variant(userWriteMask)));
    }

}
