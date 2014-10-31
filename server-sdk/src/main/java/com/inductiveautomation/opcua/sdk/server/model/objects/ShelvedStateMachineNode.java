package com.inductiveautomation.opcua.sdk.server.model.objects;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.model.objects.ShelvedStateMachineType;
import com.inductiveautomation.opcua.sdk.core.model.objects.StateType;
import com.inductiveautomation.opcua.sdk.core.model.objects.TransitionType;
import com.inductiveautomation.opcua.sdk.server.api.UaNodeManager;
import com.inductiveautomation.opcua.sdk.server.util.UaObjectType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;


@UaObjectType(name = "ShelvedStateMachineType")
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

    public Double getUnshelveTime() {
        Optional<Double> unshelveTime = getProperty("UnshelveTime");

        return unshelveTime.orElse(null);
    }

    public StateType getUnshelved() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> unshelved = getObjectComponent("Unshelved");

        return unshelved.map(node -> (StateType) node).orElse(null);
    }

    public StateType getTimedShelved() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> timedShelved = getObjectComponent("TimedShelved");

        return timedShelved.map(node -> (StateType) node).orElse(null);
    }

    public StateType getOneShotShelved() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> oneShotShelved = getObjectComponent("OneShotShelved");

        return oneShotShelved.map(node -> (StateType) node).orElse(null);
    }

    public TransitionType getUnshelvedToTimedShelved() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> unshelvedToTimedShelved = getObjectComponent("UnshelvedToTimedShelved");

        return unshelvedToTimedShelved.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getUnshelvedToOneShotShelved() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> unshelvedToOneShotShelved = getObjectComponent("UnshelvedToOneShotShelved");

        return unshelvedToOneShotShelved.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getTimedShelvedToUnshelved() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> timedShelvedToUnshelved = getObjectComponent("TimedShelvedToUnshelved");

        return timedShelvedToUnshelved.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getTimedShelvedToOneShotShelved() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> timedShelvedToOneShotShelved = getObjectComponent("TimedShelvedToOneShotShelved");

        return timedShelvedToOneShotShelved.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getOneShotShelvedToUnshelved() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> oneShotShelvedToUnshelved = getObjectComponent("OneShotShelvedToUnshelved");

        return oneShotShelvedToUnshelved.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getOneShotShelvedToTimedShelved() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> oneShotShelvedToTimedShelved = getObjectComponent("OneShotShelvedToTimedShelved");

        return oneShotShelvedToTimedShelved.map(node -> (TransitionType) node).orElse(null);
    }

    public synchronized void setUnshelveTime(Double unshelveTime) {
        getPropertyNode("UnshelveTime").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(unshelveTime)));
        });
    }

    public synchronized void atomicSet(Runnable runnable) {
        runnable.run();
    }

}
