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

import com.digitalpetri.opcua.sdk.core.model.objects.AuditUpdateMethodEventType;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.sdk.server.model.variables.PropertyNode;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@com.digitalpetri.opcua.sdk.server.util.UaObjectNode(typeName = "0:AuditUpdateMethodEventType")
public class AuditUpdateMethodEventNode extends AuditEventNode implements AuditUpdateMethodEventType {

    public AuditUpdateMethodEventNode(
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
    public NodeId getMethodId() {
        Optional<NodeId> property = getProperty(AuditUpdateMethodEventType.METHOD_ID);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getMethodIdNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(AuditUpdateMethodEventType.METHOD_ID.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setMethodId(NodeId value) {
        setProperty(AuditUpdateMethodEventType.METHOD_ID, value);
    }

    @Override
    public Object[] getInputArguments() {
        Optional<Object[]> property = getProperty(AuditUpdateMethodEventType.INPUT_ARGUMENTS);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getInputArgumentsNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(AuditUpdateMethodEventType.INPUT_ARGUMENTS.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setInputArguments(Object[] value) {
        setProperty(AuditUpdateMethodEventType.INPUT_ARGUMENTS, value);
    }

}
