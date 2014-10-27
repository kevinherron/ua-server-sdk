package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.structured.ProgramDiagnosticDataType;

public interface ProgramStateMachineType extends FiniteStateMachineType {

    Boolean getCreatable();

    Boolean getDeletable();

    Boolean getAutoDelete();

    Integer getRecycleCount();

    UInteger getInstanceCount();

    UInteger getMaxInstanceCount();

    UInteger getMaxRecycleCount();

    LocalizedText getCurrentState();

    LocalizedText getLastTransition();

    ProgramDiagnosticDataType getProgramDiagnostics();

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

    void setCurrentState(LocalizedText currentState);

    void setLastTransition(LocalizedText lastTransition);

    void setProgramDiagnostics(ProgramDiagnosticDataType programDiagnostics);

    void setFinalResultData(BaseObjectType finalResultData);

    void setReady(StateType ready);

    void setRunning(StateType running);

    void setSuspended(StateType suspended);

    void setHalted(StateType halted);

    void setHaltedToReady(TransitionType haltedToReady);

    void setReadyToRunning(TransitionType readyToRunning);

    void setRunningToHalted(TransitionType runningToHalted);

    void setRunningToReady(TransitionType runningToReady);

    void setRunningToSuspended(TransitionType runningToSuspended);

    void setSuspendedToRunning(TransitionType suspendedToRunning);

    void setSuspendedToHalted(TransitionType suspendedToHalted);

    void setSuspendedToReady(TransitionType suspendedToReady);

    void setReadyToHalted(TransitionType readyToHalted);

    void atomicSet(Runnable runnable);

}
