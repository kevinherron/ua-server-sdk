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

import com.digitalpetri.opcua.sdk.core.model.objects.ServerType;
import com.digitalpetri.opcua.sdk.core.nodes.ObjectNode;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.sdk.server.model.variables.PropertyNode;
import com.digitalpetri.opcua.sdk.server.model.variables.ServerStatusNode;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.structured.ServerStatusDataType;

@com.digitalpetri.opcua.sdk.server.util.UaObjectNode(typeName = "0:ServerType")
public class ServerNode extends BaseObjectNode implements ServerType {

    public ServerNode(
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
    public String[] getServerArray() {
        Optional<String[]> property = getProperty(ServerType.SERVER_ARRAY);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getServerArrayNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ServerType.SERVER_ARRAY.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setServerArray(String[] value) {
        setProperty(ServerType.SERVER_ARRAY, value);
    }

    @Override
    public String[] getNamespaceArray() {
        Optional<String[]> property = getProperty(ServerType.NAMESPACE_ARRAY);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getNamespaceArrayNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ServerType.NAMESPACE_ARRAY.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setNamespaceArray(String[] value) {
        setProperty(ServerType.NAMESPACE_ARRAY, value);
    }

    @Override
    public UByte getServiceLevel() {
        Optional<UByte> property = getProperty(ServerType.SERVICE_LEVEL);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getServiceLevelNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ServerType.SERVICE_LEVEL.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setServiceLevel(UByte value) {
        setProperty(ServerType.SERVICE_LEVEL, value);
    }

    @Override
    public Boolean getAuditing() {
        Optional<Boolean> property = getProperty(ServerType.AUDITING);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getAuditingNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ServerType.AUDITING.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setAuditing(Boolean value) {
        setProperty(ServerType.AUDITING, value);
    }

    @Override
    public DateTime getEstimatedReturnTime() {
        Optional<DateTime> property = getProperty(ServerType.ESTIMATED_RETURN_TIME);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getEstimatedReturnTimeNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ServerType.ESTIMATED_RETURN_TIME.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setEstimatedReturnTime(DateTime value) {
        setProperty(ServerType.ESTIMATED_RETURN_TIME, value);
    }

    @Override
    public ServerCapabilitiesNode getServerCapabilitiesNode() {
        Optional<ObjectNode> component = getObjectComponent("ServerCapabilities");

        return component.map(node -> (ServerCapabilitiesNode) node).orElse(null);
    }

    @Override
    public ServerDiagnosticsNode getServerDiagnosticsNode() {
        Optional<ObjectNode> component = getObjectComponent("ServerDiagnostics");

        return component.map(node -> (ServerDiagnosticsNode) node).orElse(null);
    }

    @Override
    public VendorServerInfoNode getVendorServerInfoNode() {
        Optional<ObjectNode> component = getObjectComponent("VendorServerInfo");

        return component.map(node -> (VendorServerInfoNode) node).orElse(null);
    }

    @Override
    public ServerRedundancyNode getServerRedundancyNode() {
        Optional<ObjectNode> component = getObjectComponent("ServerRedundancy");

        return component.map(node -> (ServerRedundancyNode) node).orElse(null);
    }

    @Override
    public NamespacesNode getNamespacesNode() {
        Optional<ObjectNode> component = getObjectComponent("Namespaces");

        return component.map(node -> (NamespacesNode) node).orElse(null);
    }

    @Override
    public ServerStatusDataType getServerStatus() {
        Optional<VariableNode> component = getVariableComponent("ServerStatus");

        return component.map(node -> (ServerStatusDataType) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServerStatusNode getServerStatusNode() {
        Optional<VariableNode> component = getVariableComponent("ServerStatus");

        return component.map(node -> (ServerStatusNode) node).orElse(null);
    }

    @Override
    public void setServerStatus(ServerStatusDataType value) {
        getVariableComponent("ServerStatus")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

}
