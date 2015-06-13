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
import com.digitalpetri.opcua.sdk.core.model.objects.StateType;
import com.digitalpetri.opcua.sdk.core.model.objects.TransitionType;
import com.digitalpetri.opcua.sdk.core.nodes.ObjectNode;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.util.UaObjectType;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;


@UaObjectType(name = "ShelvedStateMachineType")
public class ShelvedStateMachineNode extends FiniteStateMachineNode implements ShelvedStateMachineType {

    public ShelvedStateMachineNode(
            UaNamespace namespace,
            NodeId nodeId,
            QualifiedName browseName,
            LocalizedText displayName,
            Optional<LocalizedText> description,
            Optional<UInteger> writeMask,
            Optional<UInteger> userWriteMask,
            UByte eventNotifier) {

        super(namespace, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
    }

    public Double getUnshelveTime() {
        Optional<Double> unshelveTime = getProperty("UnshelveTime");

        return unshelveTime.orElse(null);
    }

    public StateType getUnshelved() {
        Optional<ObjectNode> unshelved = getObjectComponent("Unshelved");

        return unshelved.map(node -> (StateType) node).orElse(null);
    }

    public StateType getTimedShelved() {
        Optional<ObjectNode> timedShelved = getObjectComponent("TimedShelved");

        return timedShelved.map(node -> (StateType) node).orElse(null);
    }

    public StateType getOneShotShelved() {
        Optional<ObjectNode> oneShotShelved = getObjectComponent("OneShotShelved");

        return oneShotShelved.map(node -> (StateType) node).orElse(null);
    }

    public TransitionType getUnshelvedToTimedShelved() {
        Optional<ObjectNode> unshelvedToTimedShelved = getObjectComponent("UnshelvedToTimedShelved");

        return unshelvedToTimedShelved.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getUnshelvedToOneShotShelved() {
        Optional<ObjectNode> unshelvedToOneShotShelved = getObjectComponent("UnshelvedToOneShotShelved");

        return unshelvedToOneShotShelved.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getTimedShelvedToUnshelved() {
        Optional<ObjectNode> timedShelvedToUnshelved = getObjectComponent("TimedShelvedToUnshelved");

        return timedShelvedToUnshelved.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getTimedShelvedToOneShotShelved() {
        Optional<ObjectNode> timedShelvedToOneShotShelved = getObjectComponent("TimedShelvedToOneShotShelved");

        return timedShelvedToOneShotShelved.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getOneShotShelvedToUnshelved() {
        Optional<ObjectNode> oneShotShelvedToUnshelved = getObjectComponent("OneShotShelvedToUnshelved");

        return oneShotShelvedToUnshelved.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getOneShotShelvedToTimedShelved() {
        Optional<ObjectNode> oneShotShelvedToTimedShelved = getObjectComponent("OneShotShelvedToTimedShelved");

        return oneShotShelvedToTimedShelved.map(node -> (TransitionType) node).orElse(null);
    }

    public synchronized void setUnshelveTime(Double unshelveTime) {
        getPropertyNode("UnshelveTime").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(unshelveTime)));
        });
    }
}
