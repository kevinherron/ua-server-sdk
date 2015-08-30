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

package com.digitalpetri.opcua.sdk.core.nodes;

import java.util.Optional;
import javax.annotation.Nullable;

import com.digitalpetri.opcua.sdk.core.NumericRange;
import com.digitalpetri.opcua.sdk.server.NamespaceManager;
import com.digitalpetri.opcua.sdk.server.util.AttributeReader;
import com.digitalpetri.opcua.sdk.server.util.AttributeWriter;
import com.digitalpetri.opcua.stack.core.AttributeId;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.NodeClass;
import com.digitalpetri.opcua.stack.core.types.enumerated.TimestampsToReturn;

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
     */
    void setNodeId(NodeId nodeId);

    /**
     * Set the NodeClass attribute of this Node.
     *
     * @param nodeClass the NodeClass to set.
     */
    void setNodeClass(NodeClass nodeClass);

    /**
     * Set the BrowseName attribute of this Node.
     *
     * @param browseName the BrowseName to set.
     */
    void setBrowseName(QualifiedName browseName);

    /**
     * Set the DisplayName attribute of this Node.
     *
     * @param displayName the DisplayName to set.
     */
    void setDisplayName(LocalizedText displayName);

    /**
     * Set the Description attribute of this Node.
     *
     * @param description the Description to set.
     */
    void setDescription(Optional<LocalizedText> description);

    /**
     * Set the WriteMask attribute of this Node.
     *
     * @param writeMask the WriteMask to set.
     */
    void setWriteMask(Optional<UInteger> writeMask);

    /**
     * Set the UserWriteMask attribute of this Node.
     *
     * @param userWriteMask the UserWriteMask to set.
     */
    void setUserWriteMask(Optional<UInteger> userWriteMask);

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
     * @return the value of the specified attribute.
     */
    default DataValue readAttribute(UInteger attribute) {
        return readAttribute(attribute.intValue());
    }

    /**
     * Read the specified attribute.
     * <p>
     * If the attribute is not specified on this node, a value with status {@link StatusCodes#Bad_AttributeIdInvalid}
     * will be returned.
     *
     * @param attribute the id of the attribute to read.
     * @return the value of the specified attribute.
     */
    default DataValue readAttribute(int attribute) {
        return readAttribute(attribute, null, null);
    }

    /**
     * Read the specified attribute.
     * <p>
     * If the attribute is not specified on this node, a value with status {@link StatusCodes#Bad_AttributeIdInvalid}
     * will be returned.
     *
     * @param attribute  the id of the attribute to read.
     * @param timestamps the {@link TimestampsToReturn}.
     * @param indexRange the index range to read. Must be a parseable by {@link NumericRange}.
     * @return the value of the specified attribute.
     */
    default DataValue readAttribute(int attribute, @Nullable TimestampsToReturn timestamps, @Nullable String indexRange) {
        return AttributeId.from(attribute)
                .map(attributeId -> readAttribute(attributeId, timestamps, indexRange))
                .orElse(new DataValue(StatusCodes.Bad_AttributeIdInvalid));
    }

    /**
     * Read the specified attribute, applying {@code timestamps} and {@code indexRange} if specified.
     * <p>
     * If the attribute is not specified on this node, a value with status {@link StatusCodes#Bad_AttributeIdInvalid}
     * will be returned.
     *
     * @param attributeId the id of the attribute to read.
     * @param timestamps  the {@link TimestampsToReturn}.
     * @param indexRange  the index range to read. Must be a parseable by {@link NumericRange}.
     * @return the value of the specified attribute.
     */
    default DataValue readAttribute(AttributeId attributeId, @Nullable TimestampsToReturn timestamps, @Nullable String indexRange) {
        return AttributeReader.readAttribute(this, attributeId, timestamps, indexRange);
    }

    /**
     * Write to the specified attribute.
     *
     * @param ns         the {@link NamespaceManager}.
     * @param attribute  the id of the attribute to write.
     * @param value      the {@link DataValue} write.
     * @param indexRange the index range to write. Must be a parseable by {@link NumericRange}.
     * @throws UaException
     */
    default void writeAttribute(NamespaceManager ns, UInteger attribute, DataValue value, String indexRange) throws UaException {
        writeAttribute(ns, attribute.intValue(), value, indexRange);
    }

    /**
     * Write to the specified attribute.
     *
     * @param ns         the {@link NamespaceManager}.
     * @param attribute  the id of the attribute to write.
     * @param value      the {@link DataValue} write.
     * @param indexRange the index range to write. Must be a parseable by {@link NumericRange}.
     * @throws UaException
     */
    default void writeAttribute(NamespaceManager ns, int attribute, DataValue value, String indexRange) throws UaException {
        AttributeWriter.writeAttribute(ns, this, attribute, value, indexRange);
    }

}
