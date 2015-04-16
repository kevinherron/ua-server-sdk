package com.digitalpetri.opcua.sdk.server.model.objects;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.model.objects.StateMachineType;
import com.digitalpetri.opcua.sdk.core.model.variables.StateVariableType;
import com.digitalpetri.opcua.sdk.core.model.variables.TransitionVariableType;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.util.UaObjectType;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;


@UaObjectType(name = "StateMachineType")
public class StateMachineNode extends BaseObjectNode implements StateMachineType {

    public StateMachineNode(
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

    public StateVariableType getCurrentState() {
        Optional<VariableNode> currentState = getVariableComponent("CurrentState");

        return currentState.map(node -> (StateVariableType) node).orElse(null);
    }

    public TransitionVariableType getLastTransition() {
        Optional<VariableNode> lastTransition = getVariableComponent("LastTransition");

        return lastTransition.map(node -> (TransitionVariableType) node).orElse(null);
    }

    public synchronized void setCurrentState(StateVariableType currentState) {
        getVariableComponent("CurrentState").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(currentState)));
        });
    }

    public synchronized void setLastTransition(TransitionVariableType lastTransition) {
        getVariableComponent("LastTransition").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(lastTransition)));
        });
    }
}
