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

import com.digitalpetri.opcua.sdk.core.model.variables.SamplingIntervalDiagnosticsType;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.core.nodes.VariableTypeNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.structured.SamplingIntervalDiagnosticsDataType;

@com.digitalpetri.opcua.sdk.server.util.UaVariableNode(typeName = "0:SamplingIntervalDiagnosticsType")
public class SamplingIntervalDiagnosticsNode extends BaseDataVariableNode implements SamplingIntervalDiagnosticsType {

    public SamplingIntervalDiagnosticsNode(
            UaNodeManager nodeManager,
            NodeId nodeId,
            VariableTypeNode variableTypeNode) {

        super(nodeManager, nodeId, variableTypeNode);
    }

    public SamplingIntervalDiagnosticsNode(
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
        SamplingIntervalDiagnosticsDataType value = new SamplingIntervalDiagnosticsDataType(
                getSamplingInterval(),
                getSampledMonitoredItemsCount(),
                getMaxSampledMonitoredItemsCount(),
                getDisabledMonitoredItemsSamplingCount()
        );

        return new DataValue(new Variant(value));
    }

    @Override
    public Double getSamplingInterval() {
        Optional<VariableNode> component = getVariableComponent("SamplingInterval");

        return component.map(node -> (Double) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getSamplingIntervalNode() {
        Optional<VariableNode> component = getVariableComponent("SamplingInterval");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setSamplingInterval(Double value) {
        getVariableComponent("SamplingInterval")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public UInteger getSampledMonitoredItemsCount() {
        Optional<VariableNode> component = getVariableComponent("SampledMonitoredItemsCount");

        return component.map(node -> (UInteger) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getSampledMonitoredItemsCountNode() {
        Optional<VariableNode> component = getVariableComponent("SampledMonitoredItemsCount");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setSampledMonitoredItemsCount(UInteger value) {
        getVariableComponent("SampledMonitoredItemsCount")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public UInteger getMaxSampledMonitoredItemsCount() {
        Optional<VariableNode> component = getVariableComponent("MaxSampledMonitoredItemsCount");

        return component.map(node -> (UInteger) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getMaxSampledMonitoredItemsCountNode() {
        Optional<VariableNode> component = getVariableComponent("MaxSampledMonitoredItemsCount");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setMaxSampledMonitoredItemsCount(UInteger value) {
        getVariableComponent("MaxSampledMonitoredItemsCount")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public UInteger getDisabledMonitoredItemsSamplingCount() {
        Optional<VariableNode> component = getVariableComponent("DisabledMonitoredItemsSamplingCount");

        return component.map(node -> (UInteger) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getDisabledMonitoredItemsSamplingCountNode() {
        Optional<VariableNode> component = getVariableComponent("DisabledMonitoredItemsSamplingCount");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setDisabledMonitoredItemsSamplingCount(UInteger value) {
        getVariableComponent("DisabledMonitoredItemsSamplingCount")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

}
