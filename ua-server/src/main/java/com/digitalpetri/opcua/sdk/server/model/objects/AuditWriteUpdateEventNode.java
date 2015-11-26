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

package com.digitalpetri.opcua.sdk.server.model.objects;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.model.objects.AuditWriteUpdateEventType;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.sdk.server.model.variables.PropertyNode;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@com.digitalpetri.opcua.sdk.server.util.UaObjectNode(typeName = "0:AuditWriteUpdateEventType")
public class AuditWriteUpdateEventNode extends AuditUpdateEventNode implements AuditWriteUpdateEventType {

    public AuditWriteUpdateEventNode(
            UaNodeManager nodeManager,
            NodeId nodeId,
            QualifiedName browseName,
            LocalizedText displayName,
            Optional<LocalizedText> description,
            Optional<UInteger> writeMask,
            Optional<UInteger> userWriteMask,
            UByte eventNotifier) {

        super(nodeManager, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
    }

    @Override
    public UInteger getAttributeId() {
        Optional<UInteger> property = getProperty(AuditWriteUpdateEventType.ATTRIBUTE_ID);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getAttributeIdNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(AuditWriteUpdateEventType.ATTRIBUTE_ID.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setAttributeId(UInteger value) {
        setProperty(AuditWriteUpdateEventType.ATTRIBUTE_ID, value);
    }

    @Override
    public String getIndexRange() {
        Optional<String> property = getProperty(AuditWriteUpdateEventType.INDEX_RANGE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getIndexRangeNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(AuditWriteUpdateEventType.INDEX_RANGE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setIndexRange(String value) {
        setProperty(AuditWriteUpdateEventType.INDEX_RANGE, value);
    }

    @Override
    public Object getOldValue() {
        Optional<Object> property = getProperty(AuditWriteUpdateEventType.OLD_VALUE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getOldValueNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(AuditWriteUpdateEventType.OLD_VALUE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setOldValue(Object value) {
        setProperty(AuditWriteUpdateEventType.OLD_VALUE, value);
    }

    @Override
    public Object getNewValue() {
        Optional<Object> property = getProperty(AuditWriteUpdateEventType.NEW_VALUE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getNewValueNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(AuditWriteUpdateEventType.NEW_VALUE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setNewValue(Object value) {
        setProperty(AuditWriteUpdateEventType.NEW_VALUE, value);
    }

}
