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

import com.digitalpetri.opcua.sdk.core.model.objects.ShelvedStateMachineType;
import com.digitalpetri.opcua.sdk.core.nodes.ObjectNode;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.sdk.server.model.variables.PropertyNode;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@com.digitalpetri.opcua.sdk.server.util.UaObjectNode(typeName = "0:ShelvedStateMachineType")
public class ShelvedStateMachineNode extends FiniteStateMachineNode implements ShelvedStateMachineType {

    public ShelvedStateMachineNode(
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
    public Double getUnshelveTime() {
        Optional<Double> property = getProperty(ShelvedStateMachineType.UNSHELVE_TIME);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getUnshelveTimeNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ShelvedStateMachineType.UNSHELVE_TIME.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setUnshelveTime(Double value) {
        setProperty(ShelvedStateMachineType.UNSHELVE_TIME, value);
    }

    @Override
    public StateNode getUnshelvedNode() {
        Optional<ObjectNode> component = getObjectComponent("Unshelved");

        return component.map(node -> (StateNode) node).orElse(null);
    }

    @Override
    public StateNode getTimedShelvedNode() {
        Optional<ObjectNode> component = getObjectComponent("TimedShelved");

        return component.map(node -> (StateNode) node).orElse(null);
    }

    @Override
    public StateNode getOneShotShelvedNode() {
        Optional<ObjectNode> component = getObjectComponent("OneShotShelved");

        return component.map(node -> (StateNode) node).orElse(null);
    }

    @Override
    public TransitionNode getUnshelvedToTimedShelvedNode() {
        Optional<ObjectNode> component = getObjectComponent("UnshelvedToTimedShelved");

        return component.map(node -> (TransitionNode) node).orElse(null);
    }

    @Override
    public TransitionNode getUnshelvedToOneShotShelvedNode() {
        Optional<ObjectNode> component = getObjectComponent("UnshelvedToOneShotShelved");

        return component.map(node -> (TransitionNode) node).orElse(null);
    }

    @Override
    public TransitionNode getTimedShelvedToUnshelvedNode() {
        Optional<ObjectNode> component = getObjectComponent("TimedShelvedToUnshelved");

        return component.map(node -> (TransitionNode) node).orElse(null);
    }

    @Override
    public TransitionNode getTimedShelvedToOneShotShelvedNode() {
        Optional<ObjectNode> component = getObjectComponent("TimedShelvedToOneShotShelved");

        return component.map(node -> (TransitionNode) node).orElse(null);
    }

    @Override
    public TransitionNode getOneShotShelvedToUnshelvedNode() {
        Optional<ObjectNode> component = getObjectComponent("OneShotShelvedToUnshelved");

        return component.map(node -> (TransitionNode) node).orElse(null);
    }

    @Override
    public TransitionNode getOneShotShelvedToTimedShelvedNode() {
        Optional<ObjectNode> component = getObjectComponent("OneShotShelvedToTimedShelved");

        return component.map(node -> (TransitionNode) node).orElse(null);
    }

}
