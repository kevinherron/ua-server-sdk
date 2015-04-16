package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.sdk.core.model.variables.FiniteStateVariableType;
import com.digitalpetri.opcua.sdk.core.model.variables.FiniteTransitionVariableType;

public interface FiniteStateMachineType extends StateMachineType {

    FiniteStateVariableType getCurrentState();

    FiniteTransitionVariableType getLastTransition();


}
