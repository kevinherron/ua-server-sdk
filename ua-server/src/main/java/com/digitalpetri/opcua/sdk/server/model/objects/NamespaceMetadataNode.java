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

import com.digitalpetri.opcua.sdk.core.model.objects.NamespaceMetadataType;
import com.digitalpetri.opcua.sdk.core.nodes.ObjectNode;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.sdk.server.model.variables.PropertyNode;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.IdType;

@com.digitalpetri.opcua.sdk.server.util.UaObjectNode(typeName = "0:NamespaceMetadataType")
public class NamespaceMetadataNode extends BaseObjectNode implements NamespaceMetadataType {

    public NamespaceMetadataNode(
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
    public String getNamespaceUri() {
        Optional<String> property = getProperty(NamespaceMetadataType.NAMESPACE_URI);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getNamespaceUriNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(NamespaceMetadataType.NAMESPACE_URI.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setNamespaceUri(String value) {
        setProperty(NamespaceMetadataType.NAMESPACE_URI, value);
    }

    @Override
    public String getNamespaceVersion() {
        Optional<String> property = getProperty(NamespaceMetadataType.NAMESPACE_VERSION);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getNamespaceVersionNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(NamespaceMetadataType.NAMESPACE_VERSION.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setNamespaceVersion(String value) {
        setProperty(NamespaceMetadataType.NAMESPACE_VERSION, value);
    }

    @Override
    public DateTime getNamespacePublicationDate() {
        Optional<DateTime> property = getProperty(NamespaceMetadataType.NAMESPACE_PUBLICATION_DATE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getNamespacePublicationDateNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(NamespaceMetadataType.NAMESPACE_PUBLICATION_DATE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setNamespacePublicationDate(DateTime value) {
        setProperty(NamespaceMetadataType.NAMESPACE_PUBLICATION_DATE, value);
    }

    @Override
    public Boolean getIsNamespaceSubset() {
        Optional<Boolean> property = getProperty(NamespaceMetadataType.IS_NAMESPACE_SUBSET);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getIsNamespaceSubsetNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(NamespaceMetadataType.IS_NAMESPACE_SUBSET.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setIsNamespaceSubset(Boolean value) {
        setProperty(NamespaceMetadataType.IS_NAMESPACE_SUBSET, value);
    }

    @Override
    public IdType[] getStaticNodeIdIdentifierTypes() {
        Optional<IdType[]> property = getProperty(NamespaceMetadataType.STATIC_NODE_ID_IDENTIFIER_TYPES);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getStaticNodeIdIdentifierTypesNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(NamespaceMetadataType.STATIC_NODE_ID_IDENTIFIER_TYPES.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setStaticNodeIdIdentifierTypes(IdType[] value) {
        setProperty(NamespaceMetadataType.STATIC_NODE_ID_IDENTIFIER_TYPES, value);
    }

    @Override
    public String[] getStaticNumericNodeIdRange() {
        Optional<String[]> property = getProperty(NamespaceMetadataType.STATIC_NUMERIC_NODE_ID_RANGE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getStaticNumericNodeIdRangeNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(NamespaceMetadataType.STATIC_NUMERIC_NODE_ID_RANGE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setStaticNumericNodeIdRange(String[] value) {
        setProperty(NamespaceMetadataType.STATIC_NUMERIC_NODE_ID_RANGE, value);
    }

    @Override
    public String[] getStaticStringNodeIdPattern() {
        Optional<String[]> property = getProperty(NamespaceMetadataType.STATIC_STRING_NODE_ID_PATTERN);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getStaticStringNodeIdPatternNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(NamespaceMetadataType.STATIC_STRING_NODE_ID_PATTERN.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setStaticStringNodeIdPattern(String[] value) {
        setProperty(NamespaceMetadataType.STATIC_STRING_NODE_ID_PATTERN, value);
    }

    @Override
    public AddressSpaceFileNode getNamespaceFileNode() {
        Optional<ObjectNode> component = getObjectComponent("NamespaceFile");

        return component.map(node -> (AddressSpaceFileNode) node).orElse(null);
    }

}
