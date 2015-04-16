package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.sdk.core.model.variables.StateVariableType;
import com.digitalpetri.opcua.sdk.core.model.variables.TransitionVariableType;

public interface TransitionEventType extends BaseEventType {

    TransitionVariableType getTransition();

    StateVariableType getFromState();

    StateVariableType getToState();


}
