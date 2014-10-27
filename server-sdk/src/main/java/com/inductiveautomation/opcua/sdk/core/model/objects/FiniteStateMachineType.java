package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.sdk.core.model.variables.FiniteStateVariableType;
import com.inductiveautomation.opcua.sdk.core.model.variables.FiniteTransitionVariableType;

public interface FiniteStateMachineType extends StateMachineType {

    FiniteStateVariableType getCurrentState();

    FiniteTransitionVariableType getLastTransition();


    void atomicSet(Runnable runnable);

}
