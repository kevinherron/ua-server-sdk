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

package com.digitalpetri.opcua.sdk.server.model.variables;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.model.variables.BuildInfoType;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.core.nodes.VariableTypeNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.structured.BuildInfo;

@com.digitalpetri.opcua.sdk.server.util.UaVariableNode(typeName = "0:BuildInfoType")
public class BuildInfoNode extends BaseDataVariableNode implements BuildInfoType {

    public BuildInfoNode(
            UaNodeManager nodeManager,
            NodeId nodeId,
            VariableTypeNode variableTypeNode) {

        super(nodeManager, nodeId, variableTypeNode);
    }

    public BuildInfoNode(
            UaNodeManager nodeManager,
            NodeId nodeId,
            QualifiedName browseName,
            LocalizedText displayName,
            Optional<LocalizedText> description,
            Optional<UInteger> writeMask,
            Optional<UInteger> userWriteMask,
            DataValue value,
            NodeId dataType,
            Integer valueRank,
            Optional<UInteger[]> arrayDimensions,
            UByte accessLevel,
            UByte userAccessLevel,
            Optional<Double> minimumSamplingInterval,
            boolean historizing) {

        super(nodeManager, nodeId, browseName, displayName, description, writeMask, userWriteMask,
                value, dataType, valueRank, arrayDimensions, accessLevel, userAccessLevel, minimumSamplingInterval, historizing);
    }

    @Override
    public DataValue getValue() {
        BuildInfo value = new BuildInfo(
                getProductUri(),
                getManufacturerName(),
                getProductName(),
                getSoftwareVersion(),
                getBuildNumber(),
                getBuildDate()
        );

        return new DataValue(new Variant(value));
    }

    @Override
    public String getProductUri() {
        Optional<VariableNode> component = getVariableComponent("ProductUri");

        return component.map(node -> (String) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getProductUriNode() {
        Optional<VariableNode> component = getVariableComponent("ProductUri");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setProductUri(String value) {
        getVariableComponent("ProductUri")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public String getManufacturerName() {
        Optional<VariableNode> component = getVariableComponent("ManufacturerName");

        return component.map(node -> (String) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getManufacturerNameNode() {
        Optional<VariableNode> component = getVariableComponent("ManufacturerName");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setManufacturerName(String value) {
        getVariableComponent("ManufacturerName")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public String getProductName() {
        Optional<VariableNode> component = getVariableComponent("ProductName");

        return component.map(node -> (String) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getProductNameNode() {
        Optional<VariableNode> component = getVariableComponent("ProductName");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setProductName(String value) {
        getVariableComponent("ProductName")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public String getSoftwareVersion() {
        Optional<VariableNode> component = getVariableComponent("SoftwareVersion");

        return component.map(node -> (String) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getSoftwareVersionNode() {
        Optional<VariableNode> component = getVariableComponent("SoftwareVersion");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setSoftwareVersion(String value) {
        getVariableComponent("SoftwareVersion")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public String getBuildNumber() {
        Optional<VariableNode> component = getVariableComponent("BuildNumber");

        return component.map(node -> (String) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getBuildNumberNode() {
        Optional<VariableNode> component = getVariableComponent("BuildNumber");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setBuildNumber(String value) {
        getVariableComponent("BuildNumber")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public DateTime getBuildDate() {
        Optional<VariableNode> component = getVariableComponent("BuildDate");

        return component.map(node -> (DateTime) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getBuildDateNode() {
        Optional<VariableNode> component = getVariableComponent("BuildDate");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setBuildDate(DateTime value) {
        getVariableComponent("BuildDate")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

}
