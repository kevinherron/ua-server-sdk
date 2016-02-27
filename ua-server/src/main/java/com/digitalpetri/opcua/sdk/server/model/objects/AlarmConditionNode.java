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

import com.digitalpetri.opcua.sdk.core.model.objects.AlarmConditionType;
import com.digitalpetri.opcua.sdk.core.nodes.ObjectNode;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.sdk.server.model.variables.PropertyNode;
import com.digitalpetri.opcua.sdk.server.model.variables.TwoStateVariableNode;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@com.digitalpetri.opcua.sdk.server.util.UaObjectNode(typeName = "0:AlarmConditionType")
public class AlarmConditionNode extends AcknowledgeableConditionNode implements AlarmConditionType {

    public AlarmConditionNode(
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
    public NodeId getInputNode() {
        Optional<NodeId> property = getProperty(AlarmConditionType.INPUT_NODE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getInputNodeNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(AlarmConditionType.INPUT_NODE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setInputNode(NodeId value) {
        setProperty(AlarmConditionType.INPUT_NODE, value);
    }

    @Override
    public Boolean getSuppressedOrShelved() {
        Optional<Boolean> property = getProperty(AlarmConditionType.SUPPRESSED_OR_SHELVED);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getSuppressedOrShelvedNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(AlarmConditionType.SUPPRESSED_OR_SHELVED.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setSuppressedOrShelved(Boolean value) {
        setProperty(AlarmConditionType.SUPPRESSED_OR_SHELVED, value);
    }

    @Override
    public Double getMaxTimeShelved() {
        Optional<Double> property = getProperty(AlarmConditionType.MAX_TIME_SHELVED);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getMaxTimeShelvedNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(AlarmConditionType.MAX_TIME_SHELVED.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setMaxTimeShelved(Double value) {
        setProperty(AlarmConditionType.MAX_TIME_SHELVED, value);
    }

    @Override
    public ShelvedStateMachineNode getShelvingStateNode() {
        Optional<ObjectNode> component = getObjectComponent("ShelvingState");

        return component.map(node -> (ShelvedStateMachineNode) node).orElse(null);
    }

    @Override
    public LocalizedText getEnabledState() {
        Optional<VariableNode> component = getVariableComponent("EnabledState");

        return component.map(node -> (LocalizedText) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public TwoStateVariableNode getEnabledStateNode() {
        Optional<VariableNode> component = getVariableComponent("EnabledState");

        return component.map(node -> (TwoStateVariableNode) node).orElse(null);
    }

    @Override
    public void setEnabledState(LocalizedText value) {
        getVariableComponent("EnabledState")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public LocalizedText getActiveState() {
        Optional<VariableNode> component = getVariableComponent("ActiveState");

        return component.map(node -> (LocalizedText) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public TwoStateVariableNode getActiveStateNode() {
        Optional<VariableNode> component = getVariableComponent("ActiveState");

        return component.map(node -> (TwoStateVariableNode) node).orElse(null);
    }

    @Override
    public void setActiveState(LocalizedText value) {
        getVariableComponent("ActiveState")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public LocalizedText getSuppressedState() {
        Optional<VariableNode> component = getVariableComponent("SuppressedState");

        return component.map(node -> (LocalizedText) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public TwoStateVariableNode getSuppressedStateNode() {
        Optional<VariableNode> component = getVariableComponent("SuppressedState");

        return component.map(node -> (TwoStateVariableNode) node).orElse(null);
    }

    @Override
    public void setSuppressedState(LocalizedText value) {
        getVariableComponent("SuppressedState")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

}
