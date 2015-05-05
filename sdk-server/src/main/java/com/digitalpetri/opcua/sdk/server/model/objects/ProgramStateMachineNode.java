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

import com.digitalpetri.opcua.sdk.core.model.objects.BaseObjectType;
import com.digitalpetri.opcua.sdk.core.model.objects.ProgramStateMachineType;
import com.digitalpetri.opcua.sdk.core.model.objects.StateType;
import com.digitalpetri.opcua.sdk.core.model.objects.TransitionType;
import com.digitalpetri.opcua.sdk.core.model.variables.FiniteStateVariableType;
import com.digitalpetri.opcua.sdk.core.model.variables.FiniteTransitionVariableType;
import com.digitalpetri.opcua.sdk.core.model.variables.ProgramDiagnosticType;
import com.digitalpetri.opcua.sdk.core.nodes.ObjectNode;
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
        Optional<VariableNode> currentState = getVariableComponent("CurrentState");

        return currentState.map(node -> (FiniteStateVariableType) node).orElse(null);
    }

    public FiniteTransitionVariableType getLastTransition() {
        Optional<VariableNode> lastTransition = getVariableComponent("LastTransition");

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
        Optional<VariableNode> programDiagnostics = getVariableComponent("ProgramDiagnostics");

        return programDiagnostics.map(node -> (ProgramDiagnosticType) node).orElse(null);
    }

    public BaseObjectType getFinalResultData() {
        Optional<ObjectNode> finalResultData = getObjectComponent("FinalResultData");

        return finalResultData.map(node -> (BaseObjectType) node).orElse(null);
    }

    public StateType getReady() {
        Optional<ObjectNode> ready = getObjectComponent("Ready");

        return ready.map(node -> (StateType) node).orElse(null);
    }

    public StateType getRunning() {
        Optional<ObjectNode> running = getObjectComponent("Running");

        return running.map(node -> (StateType) node).orElse(null);
    }

    public StateType getSuspended() {
        Optional<ObjectNode> suspended = getObjectComponent("Suspended");

        return suspended.map(node -> (StateType) node).orElse(null);
    }

    public StateType getHalted() {
        Optional<ObjectNode> halted = getObjectComponent("Halted");

        return halted.map(node -> (StateType) node).orElse(null);
    }

    public TransitionType getHaltedToReady() {
        Optional<ObjectNode> haltedToReady = getObjectComponent("HaltedToReady");

        return haltedToReady.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getReadyToRunning() {
        Optional<ObjectNode> readyToRunning = getObjectComponent("ReadyToRunning");

        return readyToRunning.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getRunningToHalted() {
        Optional<ObjectNode> runningToHalted = getObjectComponent("RunningToHalted");

        return runningToHalted.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getRunningToReady() {
        Optional<ObjectNode> runningToReady = getObjectComponent("RunningToReady");

        return runningToReady.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getRunningToSuspended() {
        Optional<ObjectNode> runningToSuspended = getObjectComponent("RunningToSuspended");

        return runningToSuspended.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getSuspendedToRunning() {
        Optional<ObjectNode> suspendedToRunning = getObjectComponent("SuspendedToRunning");

        return suspendedToRunning.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getSuspendedToHalted() {
        Optional<ObjectNode> suspendedToHalted = getObjectComponent("SuspendedToHalted");

        return suspendedToHalted.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getSuspendedToReady() {
        Optional<ObjectNode> suspendedToReady = getObjectComponent("SuspendedToReady");

        return suspendedToReady.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getReadyToHalted() {
        Optional<ObjectNode> readyToHalted = getObjectComponent("ReadyToHalted");

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
