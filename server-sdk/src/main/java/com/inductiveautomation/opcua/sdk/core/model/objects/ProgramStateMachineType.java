package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.sdk.core.model.variables.FiniteStateVariableType;
import com.inductiveautomation.opcua.sdk.core.model.variables.FiniteTransitionVariableType;
import com.inductiveautomation.opcua.sdk.core.model.variables.ProgramDiagnosticType;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface ProgramStateMachineType extends FiniteStateMachineType {

    FiniteStateVariableType getCurrentState();

    FiniteTransitionVariableType getLastTransition();

    Boolean getCreatable();

    Boolean getDeletable();

    Boolean getAutoDelete();

    Integer getRecycleCount();

    UInteger getInstanceCount();

    UInteger getMaxInstanceCount();

    UInteger getMaxRecycleCount();

    ProgramDiagnosticType getProgramDiagnostics();

    BaseObjectType getFinalResultData();

    StateType getReady();

    StateType getRunning();

    StateType getSuspended();

    StateType getHalted();

    TransitionType getHaltedToReady();

    TransitionType getReadyToRunning();

    TransitionType getRunningToHalted();

    TransitionType getRunningToReady();

    TransitionType getRunningToSuspended();

    TransitionType getSuspendedToRunning();

    TransitionType getSuspendedToHalted();

    TransitionType getSuspendedToReady();

    TransitionType getReadyToHalted();

    void setCreatable(Boolean creatable);

    void setDeletable(Boolean deletable);

    void setAutoDelete(Boolean autoDelete);

    void setRecycleCount(Integer recycleCount);

    void setInstanceCount(UInteger instanceCount);

    void setMaxInstanceCount(UInteger maxInstanceCount);

    void setMaxRecycleCount(UInteger maxRecycleCount);

}
