/*
 * Copyright 2014 Inductive Automation
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

package com.inductiveautomation.opcua.sdk.server.api.nodes;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.server.util.AttributeReader;
import com.inductiveautomation.opcua.sdk.server.util.AttributeWriter;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;

public interface Node {

    /**
     * See OPC-UA Part 3, section 5.2.2.
     *
     * @return the NodeId ({@link NodeId}) of this node.
     */
    NodeId getNodeId();

    /**
     * See OPC-UA Part 3, section 5.2.3.
     *
     * @return the NodeClass ({@link NodeClass}) of this node.
     */
    NodeClass getNodeClass();

    /**
     * See OPC-UA Part 3, section 5.2.4.
     *
     * @return the BrowseName ({@link QualifiedName}) of this node.
     */
    QualifiedName getBrowseName();

    /**
     * See OPC-UA Part 3, section 5.2.5.
     *
     * @return the DisplayName ({@link QualifiedName}) of this node.
     */
    LocalizedText getDisplayName();

    /**
     * See OPC-UA Part 3, section 5.2.6.
     *
     * @return if this attribute is present, the Description ({@link LocalizedText}).
     */
    Optional<LocalizedText> getDescription();

    /**
     * See OPC-UA Part 3, section 5.2.7.
     *
     * @return if this attribute is present, the WriteMask ({@link UInteger}).
     */
    Optional<UInteger> getWriteMask();

    /**
     * See OPC-UA Part 3, section 5.2.8.
     *
     * @return if this attribute is present, the UserWriteMask ({@link UInteger}).
     */
    Optional<UInteger> getUserWriteMask();

    /**
     * Set the NodeId attribute of this Node.
     *
     * @param nodeId the NodeId to set.
     * @throws UaException if the attribute cannot be set.
     */
    default void setNodeId(NodeId nodeId) throws UaException {
        throw new UaException(StatusCodes.Bad_NotWritable);
    }

    /**
     * Set the NodeClass attribute of this Node.
     *
     * @param nodeClass the NodeClass to set.
     * @throws UaException if the attribute cannot be set.
     */
    default void setNodeClass(NodeClass nodeClass) throws UaException {
        throw new UaException(StatusCodes.Bad_NotWritable);
    }

    /**
     * Set the BrowseName attribute of this Node.
     *
     * @param browseName the BrowseName to set.
     * @throws UaException if the attribute cannot be set.
     */
    default void setBrowseName(QualifiedName browseName) throws UaException {
        throw new UaException(StatusCodes.Bad_NotWritable);
    }

    /**
     * Set the DisplayName attribute of this Node.
     *
     * @param displayName the DisplayName to set.
     * @throws UaException if the attribute cannot be set.
     */
    default void setDisplayName(LocalizedText displayName) throws UaException {
        throw new UaException(StatusCodes.Bad_NotWritable);
    }

    /**
     * Set the Description attribute of this Node.
     *
     * @param description the Description to set.
     * @throws UaException if the attribute cannot be set.
     */
    default void setDescription(Optional<LocalizedText> description) throws UaException {
        throw new UaException(StatusCodes.Bad_NotWritable);
    }

    /**
     * Set the WriteMask attribute of this Node.
     *
     * @param writeMask the WriteMask to set.
     * @throws UaException if the attribute cannot be set.
     */
    default void setWriteMask(Optional<UInteger> writeMask) throws UaException {
        throw new UaException(StatusCodes.Bad_NotWritable);
    }

    /**
     * Set the UserWriteMask attribute of this Node.
     *
     * @param userWriteMask the UserWriteMask to set.
     * @throws UaException if the attribute cannot be set.
     */
    default void setUserWriteMask(Optional<UInteger> userWriteMask) throws UaException {
        throw new UaException(StatusCodes.Bad_NotWritable);
    }

    /**
     * @param attributeId the id of the attribute in question.
     * @return {@code true} if this {@link Node} has the attribute identified by {@code attributeId}.
     */
    default boolean hasAttribute(int attributeId) {
        return StatusCodes.Bad_AttributeIdInvalid != readAttribute(attributeId).getStatusCode().getValue();
    }

    /**
     * @param attributeId the id of the attribute in question.
     * @return {@code true} if this {@link Node} has the attribute identified by {@code attributeId}.
     */
    default boolean hasAttribute(UInteger attributeId) {
        return hasAttribute(attributeId.intValue());
    }

    /**
     * Read the specified attribute.
     * <p>
     * If the attribute is not specified on this node, a value with status {@link StatusCodes#Bad_AttributeIdInvalid}
     * will be returned.
     *
     * @param attribute the id of the attribute to read.
     * @return The value of the specified attribute.
     */
    default DataValue readAttribute(int attribute) {
        return AttributeReader.readAttribute(attribute, this);
    }


    /**
     * Read the specified attribute.
     * <p>
     * If the attribute is not specified on this node, a value with status {@link StatusCodes#Bad_AttributeIdInvalid}
     * will be returned.
     *
     * @param attribute the id of the attribute to read.
     * @return The value of the specified attribute.
     */
    default DataValue readAttribute(UInteger attribute) {
        return readAttribute(attribute.intValue());
    }

    /**
     * Write to the specified attribute.
     *
     * @param attribute the attribute to write to.
     * @param value     the {@link DataValue} write.
     * @throws UaException if the attribute is not present or not writable.
     */
    default void writeAttribute(UInteger attribute, DataValue value) throws UaException {
        writeAttribute(attribute.intValue(), value);
    }

    /**
     * Write to the specified attribute.
     *
     * @param attribute the attribute to write to.
     * @param value     the {@link DataValue} write.
     * @throws UaException if the attribute is not present or not writable.
     */
    default void writeAttribute(int attribute, DataValue value) throws UaException {
        AttributeWriter.writeAttribute(attribute, value, this);
    }

}
