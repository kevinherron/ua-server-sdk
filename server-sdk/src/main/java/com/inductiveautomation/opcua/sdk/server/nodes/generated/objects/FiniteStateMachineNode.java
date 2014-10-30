package com.inductiveautomation.opcua.sdk.server.nodes.generated.objects;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.model.objects.FiniteStateMachineType;
import com.inductiveautomation.opcua.sdk.core.model.variables.FiniteStateVariableType;
import com.inductiveautomation.opcua.sdk.core.model.variables.FiniteTransitionVariableType;
import com.inductiveautomation.opcua.sdk.server.api.UaNodeManager;
import com.inductiveautomation.opcua.sdk.server.util.UaObjectType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;


@UaObjectType(name = "FiniteStateMachineType")
public class FiniteStateMachineNode extends StateMachineNode implements FiniteStateMachineType {

    public FiniteStateMachineNode(
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

    public FiniteStateVariableType getCurrentState() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.VariableNode> currentState = getVariableComponent("CurrentState");

        return currentState.map(node -> (FiniteStateVariableType) node).orElse(null);
    }

    public FiniteTransitionVariableType getLastTransition() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.VariableNode> lastTransition = getVariableComponent("LastTransition");

        return lastTransition.map(node -> (FiniteTransitionVariableType) node).orElse(null);
    }

    public synchronized void setCurrentState(FiniteStateVariableType currentState) {
        getVariableComponent("CurrentState").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(currentState)));
        });
    }

    public synchronized void setLastTransition(FiniteTransitionVariableType lastTransition) {
        getVariableComponent("LastTransition").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(lastTransition)));
        });
    }

    public synchronized void atomicSet(Runnable runnable) {
        runnable.run();
    }

}
