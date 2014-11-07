package com.inductiveautomation.opcua.sdk.server.model.objects;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.model.objects.BaseObjectType;
import com.inductiveautomation.opcua.sdk.core.model.objects.ProgramStateMachineType;
import com.inductiveautomation.opcua.sdk.core.model.objects.StateType;
import com.inductiveautomation.opcua.sdk.core.model.objects.TransitionType;
import com.inductiveautomation.opcua.sdk.core.model.variables.FiniteStateVariableType;
import com.inductiveautomation.opcua.sdk.core.model.variables.FiniteTransitionVariableType;
import com.inductiveautomation.opcua.sdk.core.model.variables.ProgramDiagnosticType;
import com.inductiveautomation.opcua.sdk.server.api.UaNamespace;
import com.inductiveautomation.opcua.sdk.server.util.UaObjectType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;


@UaObjectType(name = "ProgramStateMachineType")
public class ProgramStateMachineNode extends FiniteStateMachineNode implements ProgramStateMachineType {

    public ProgramStateMachineNode(
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

    public FiniteStateVariableType getCurrentState() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.VariableNode> currentState = getVariableComponent("CurrentState");

        return currentState.map(node -> (FiniteStateVariableType) node).orElse(null);
    }

    public FiniteTransitionVariableType getLastTransition() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.VariableNode> lastTransition = getVariableComponent("LastTransition");

        return lastTransition.map(node -> (FiniteTransitionVariableType) node).orElse(null);
    }

    public Boolean getCreatable() {
        Optional<Boolean> creatable = getProperty("Creatable");

        return creatable.orElse(null);
    }

    public Boolean getDeletable() {
        Optional<Boolean> deletable = getProperty("Deletable");

        return deletable.orElse(null);
    }

    public Boolean getAutoDelete() {
        Optional<Boolean> autoDelete = getProperty("AutoDelete");

        return autoDelete.orElse(null);
    }

    public Integer getRecycleCount() {
        Optional<Integer> recycleCount = getProperty("RecycleCount");

        return recycleCount.orElse(null);
    }

    public UInteger getInstanceCount() {
        Optional<UInteger> instanceCount = getProperty("InstanceCount");

        return instanceCount.orElse(null);
    }

    public UInteger getMaxInstanceCount() {
        Optional<UInteger> maxInstanceCount = getProperty("MaxInstanceCount");

        return maxInstanceCount.orElse(null);
    }

    public UInteger getMaxRecycleCount() {
        Optional<UInteger> maxRecycleCount = getProperty("MaxRecycleCount");

        return maxRecycleCount.orElse(null);
    }

    public ProgramDiagnosticType getProgramDiagnostics() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.VariableNode> programDiagnostics = getVariableComponent("ProgramDiagnostics");

        return programDiagnostics.map(node -> (ProgramDiagnosticType) node).orElse(null);
    }

    public BaseObjectType getFinalResultData() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> finalResultData = getObjectComponent("FinalResultData");

        return finalResultData.map(node -> (BaseObjectType) node).orElse(null);
    }

    public StateType getReady() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> ready = getObjectComponent("Ready");

        return ready.map(node -> (StateType) node).orElse(null);
    }

    public StateType getRunning() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> running = getObjectComponent("Running");

        return running.map(node -> (StateType) node).orElse(null);
    }

    public StateType getSuspended() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> suspended = getObjectComponent("Suspended");

        return suspended.map(node -> (StateType) node).orElse(null);
    }

    public StateType getHalted() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> halted = getObjectComponent("Halted");

        return halted.map(node -> (StateType) node).orElse(null);
    }

    public TransitionType getHaltedToReady() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> haltedToReady = getObjectComponent("HaltedToReady");

        return haltedToReady.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getReadyToRunning() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> readyToRunning = getObjectComponent("ReadyToRunning");

        return readyToRunning.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getRunningToHalted() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> runningToHalted = getObjectComponent("RunningToHalted");

        return runningToHalted.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getRunningToReady() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> runningToReady = getObjectComponent("RunningToReady");

        return runningToReady.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getRunningToSuspended() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> runningToSuspended = getObjectComponent("RunningToSuspended");

        return runningToSuspended.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getSuspendedToRunning() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> suspendedToRunning = getObjectComponent("SuspendedToRunning");

        return suspendedToRunning.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getSuspendedToHalted() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> suspendedToHalted = getObjectComponent("SuspendedToHalted");

        return suspendedToHalted.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getSuspendedToReady() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> suspendedToReady = getObjectComponent("SuspendedToReady");

        return suspendedToReady.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getReadyToHalted() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> readyToHalted = getObjectComponent("ReadyToHalted");

        return readyToHalted.map(node -> (TransitionType) node).orElse(null);
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

    public synchronized void setCreatable(Boolean creatable) {
        getPropertyNode("Creatable").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(creatable)));
        });
    }

    public synchronized void setDeletable(Boolean deletable) {
        getPropertyNode("Deletable").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(deletable)));
        });
    }

    public synchronized void setAutoDelete(Boolean autoDelete) {
        getPropertyNode("AutoDelete").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(autoDelete)));
        });
    }

    public synchronized void setRecycleCount(Integer recycleCount) {
        getPropertyNode("RecycleCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(recycleCount)));
        });
    }

    public synchronized void setInstanceCount(UInteger instanceCount) {
        getPropertyNode("InstanceCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(instanceCount)));
        });
    }

    public synchronized void setMaxInstanceCount(UInteger maxInstanceCount) {
        getPropertyNode("MaxInstanceCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxInstanceCount)));
        });
    }

    public synchronized void setMaxRecycleCount(UInteger maxRecycleCount) {
        getPropertyNode("MaxRecycleCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxRecycleCount)));
        });
    }

    public synchronized void setProgramDiagnostics(ProgramDiagnosticType programDiagnostics) {
        getVariableComponent("ProgramDiagnostics").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(programDiagnostics)));
        });
    }
}
