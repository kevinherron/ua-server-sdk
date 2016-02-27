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

import com.digitalpetri.opcua.sdk.core.model.objects.ServerConfigurationType;
import com.digitalpetri.opcua.sdk.core.nodes.ObjectNode;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.sdk.server.model.variables.PropertyNode;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@com.digitalpetri.opcua.sdk.server.util.UaObjectNode(typeName = "0:ServerConfigurationType")
public class ServerConfigurationNode extends BaseObjectNode implements ServerConfigurationType {

    public ServerConfigurationNode(
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
    public String[] getServerCapabilities() {
        Optional<String[]> property = getProperty(ServerConfigurationType.SERVER_CAPABILITIES);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getServerCapabilitiesNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ServerConfigurationType.SERVER_CAPABILITIES.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setServerCapabilities(String[] value) {
        setProperty(ServerConfigurationType.SERVER_CAPABILITIES, value);
    }

    @Override
    public String[] getSupportedPrivateKeyFormats() {
        Optional<String[]> property = getProperty(ServerConfigurationType.SUPPORTED_PRIVATE_KEY_FORMATS);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getSupportedPrivateKeyFormatsNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ServerConfigurationType.SUPPORTED_PRIVATE_KEY_FORMATS.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setSupportedPrivateKeyFormats(String[] value) {
        setProperty(ServerConfigurationType.SUPPORTED_PRIVATE_KEY_FORMATS, value);
    }

    @Override
    public UInteger getMaxTrustListSize() {
        Optional<UInteger> property = getProperty(ServerConfigurationType.MAX_TRUST_LIST_SIZE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getMaxTrustListSizeNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ServerConfigurationType.MAX_TRUST_LIST_SIZE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setMaxTrustListSize(UInteger value) {
        setProperty(ServerConfigurationType.MAX_TRUST_LIST_SIZE, value);
    }

    @Override
    public Boolean getMulticastDnsEnabled() {
        Optional<Boolean> property = getProperty(ServerConfigurationType.MULTICAST_DNS_ENABLED);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getMulticastDnsEnabledNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ServerConfigurationType.MULTICAST_DNS_ENABLED.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setMulticastDnsEnabled(Boolean value) {
        setProperty(ServerConfigurationType.MULTICAST_DNS_ENABLED, value);
    }

    @Override
    public CertificateGroupFolderNode getCertificateGroupsNode() {
        Optional<ObjectNode> component = getObjectComponent("CertificateGroups");

        return component.map(node -> (CertificateGroupFolderNode) node).orElse(null);
    }

}
