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
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;
import com.inductiveautomation.opcua.stack.core.util.annotations.UInt32;

public interface Node {

    /**
     * See OPC-UA Part 3, section 5.2.2.
     *
     * @return The NodeId ({@link NodeId}) of this node.
     */
    NodeId getNodeId();

    /**
     * See OPC-UA Part 3, section 5.2.3.
     *
     * @return The NodeClass ({@link NodeClass}) of this node.
     */
    NodeClass getNodeClass();

    /**
     * See OPC-UA Part 3, section 5.2.4.
     *
     * @return The BrowseName ({@link QualifiedName}) of this node.
     */
    QualifiedName getBrowseName();

    /**
     * See OPC-UA Part 3, section 5.2.5.
     *
     * @return The DisplayName ({@link QualifiedName}) of this node.
     */
    LocalizedText getDisplayName();

    /**
     * See OPC-UA Part 3, section 5.2.6.
     *
     * @return If this attribute is present, the Description ({@link LocalizedText}).
     */
    Optional<LocalizedText> getDescription();

    /**
     * See OPC-UA Part 3, section 5.2.7.
     *
     * @return If this attribute is present, the WriteMask ({@link Long}).
     */
    @UInt32 Optional<Long> getWriteMask();

    /**
     * See OPC-UA Part 3, section 5.2.8.
     *
     * @return If this attribute is present, the UserWriteMask ({@link Long}).
     */
    @UInt32 Optional<Long> getUserWriteMask();

    /**
     * @param attributeId The id of the attribute in question.
     * @return {@code true} if this {@link Node} has the attribute identified by {@code attributeId}.
     */
    default boolean hasAttribute(@UInt32 int attributeId) {
        return StatusCodes.Bad_AttributeIdInvalid != readAttribute(attributeId).getStatusCode().getValue();
    }

    /**
     * @param attributeId The id of the attribute in question.
     * @return {@code true} if this {@link Node} has the attribute identified by {@code attributeId}.
     */
    default boolean hasAttribute(@UInt32 long attributeId) {
        return hasAttribute((int) attributeId);
    }

    /**
     * Read the specified attribute.
     * <p>
     * If the attribute is not specified on this node, a value with status {@link StatusCodes#Bad_AttributeIdInvalid}
     * will be returned.
     *
     * @param attribute The id of the attribute to read.
     * @return The value of the specified attribute.
     */
    default DataValue readAttribute(@UInt32 int attribute) {
        return AttributeReader.readAttribute(attribute, this);
    }


    /**
     * Read the specified attribute.
     * <p>
     * If the attribute is not specified on this node, a value with status {@link StatusCodes#Bad_AttributeIdInvalid}
     * will be returned.
     *
     * @param attribute The id of the attribute to read.
     * @return The value of the specified attribute.
     */
    default DataValue readAttribute(@UInt32 long attribute) {
        return readAttribute((int) attribute);
    }

}
