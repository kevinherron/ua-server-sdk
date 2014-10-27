package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.sdk.core.model.variables.StateVariableType;
import com.inductiveautomation.opcua.sdk.core.model.variables.TransitionVariableType;

public interface TransitionEventType extends BaseEventType {

    TransitionVariableType getTransition();

    StateVariableType getFromState();

    StateVariableType getToState();


    void atomicSet(Runnable runnable);

}
