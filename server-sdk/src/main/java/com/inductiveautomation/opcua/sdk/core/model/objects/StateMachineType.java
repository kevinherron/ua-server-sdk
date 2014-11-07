package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.sdk.core.model.variables.StateVariableType;
import com.inductiveautomation.opcua.sdk.core.model.variables.TransitionVariableType;

public interface StateMachineType extends BaseObjectType {

    StateVariableType getCurrentState();

    TransitionVariableType getLastTransition();


}
