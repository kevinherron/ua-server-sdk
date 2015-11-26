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

import com.digitalpetri.opcua.sdk.core.model.objects.ServerCapabilitiesType;
import com.digitalpetri.opcua.sdk.core.nodes.ObjectNode;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.sdk.server.model.variables.PropertyNode;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;
import com.digitalpetri.opcua.stack.core.types.structured.SignedSoftwareCertificate;

@com.digitalpetri.opcua.sdk.server.util.UaObjectNode(typeName = "0:ServerCapabilitiesType")
public class ServerCapabilitiesNode extends BaseObjectNode implements ServerCapabilitiesType {

    public ServerCapabilitiesNode(
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
    public String[] getServerProfileArray() {
        Optional<String[]> property = getProperty(ServerCapabilitiesType.SERVER_PROFILE_ARRAY);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getServerProfileArrayNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ServerCapabilitiesType.SERVER_PROFILE_ARRAY.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setServerProfileArray(String[] value) {
        setProperty(ServerCapabilitiesType.SERVER_PROFILE_ARRAY, value);
    }

    @Override
    public String[] getLocaleIdArray() {
        Optional<String[]> property = getProperty(ServerCapabilitiesType.LOCALE_ID_ARRAY);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getLocaleIdArrayNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ServerCapabilitiesType.LOCALE_ID_ARRAY.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setLocaleIdArray(String[] value) {
        setProperty(ServerCapabilitiesType.LOCALE_ID_ARRAY, value);
    }

    @Override
    public Double getMinSupportedSampleRate() {
        Optional<Double> property = getProperty(ServerCapabilitiesType.MIN_SUPPORTED_SAMPLE_RATE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getMinSupportedSampleRateNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ServerCapabilitiesType.MIN_SUPPORTED_SAMPLE_RATE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setMinSupportedSampleRate(Double value) {
        setProperty(ServerCapabilitiesType.MIN_SUPPORTED_SAMPLE_RATE, value);
    }

    @Override
    public UShort getMaxBrowseContinuationPoints() {
        Optional<UShort> property = getProperty(ServerCapabilitiesType.MAX_BROWSE_CONTINUATION_POINTS);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getMaxBrowseContinuationPointsNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ServerCapabilitiesType.MAX_BROWSE_CONTINUATION_POINTS.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setMaxBrowseContinuationPoints(UShort value) {
        setProperty(ServerCapabilitiesType.MAX_BROWSE_CONTINUATION_POINTS, value);
    }

    @Override
    public UShort getMaxQueryContinuationPoints() {
        Optional<UShort> property = getProperty(ServerCapabilitiesType.MAX_QUERY_CONTINUATION_POINTS);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getMaxQueryContinuationPointsNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ServerCapabilitiesType.MAX_QUERY_CONTINUATION_POINTS.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setMaxQueryContinuationPoints(UShort value) {
        setProperty(ServerCapabilitiesType.MAX_QUERY_CONTINUATION_POINTS, value);
    }

    @Override
    public UShort getMaxHistoryContinuationPoints() {
        Optional<UShort> property = getProperty(ServerCapabilitiesType.MAX_HISTORY_CONTINUATION_POINTS);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getMaxHistoryContinuationPointsNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ServerCapabilitiesType.MAX_HISTORY_CONTINUATION_POINTS.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setMaxHistoryContinuationPoints(UShort value) {
        setProperty(ServerCapabilitiesType.MAX_HISTORY_CONTINUATION_POINTS, value);
    }

    @Override
    public SignedSoftwareCertificate[] getSoftwareCertificates() {
        Optional<SignedSoftwareCertificate[]> property = getProperty(ServerCapabilitiesType.SOFTWARE_CERTIFICATES);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getSoftwareCertificatesNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ServerCapabilitiesType.SOFTWARE_CERTIFICATES.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setSoftwareCertificates(SignedSoftwareCertificate[] value) {
        setProperty(ServerCapabilitiesType.SOFTWARE_CERTIFICATES, value);
    }

    @Override
    public UInteger getMaxArrayLength() {
        Optional<UInteger> property = getProperty(ServerCapabilitiesType.MAX_ARRAY_LENGTH);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getMaxArrayLengthNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ServerCapabilitiesType.MAX_ARRAY_LENGTH.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setMaxArrayLength(UInteger value) {
        setProperty(ServerCapabilitiesType.MAX_ARRAY_LENGTH, value);
    }

    @Override
    public UInteger getMaxStringLength() {
        Optional<UInteger> property = getProperty(ServerCapabilitiesType.MAX_STRING_LENGTH);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getMaxStringLengthNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ServerCapabilitiesType.MAX_STRING_LENGTH.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setMaxStringLength(UInteger value) {
        setProperty(ServerCapabilitiesType.MAX_STRING_LENGTH, value);
    }

    @Override
    public UInteger getMaxByteStringLength() {
        Optional<UInteger> property = getProperty(ServerCapabilitiesType.MAX_BYTE_STRING_LENGTH);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getMaxByteStringLengthNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ServerCapabilitiesType.MAX_BYTE_STRING_LENGTH.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setMaxByteStringLength(UInteger value) {
        setProperty(ServerCapabilitiesType.MAX_BYTE_STRING_LENGTH, value);
    }

    @Override
    public OperationLimitsNode getOperationLimitsNode() {
        Optional<ObjectNode> component = getObjectComponent("OperationLimits");

        return component.map(node -> (OperationLimitsNode) node).orElse(null);
    }

    @Override
    public FolderNode getModellingRulesNode() {
        Optional<ObjectNode> component = getObjectComponent("ModellingRules");

        return component.map(node -> (FolderNode) node).orElse(null);
    }

    @Override
    public FolderNode getAggregateFunctionsNode() {
        Optional<ObjectNode> component = getObjectComponent("AggregateFunctions");

        return component.map(node -> (FolderNode) node).orElse(null);
    }

}
