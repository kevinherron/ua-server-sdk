package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.sdk.core.model.variables.TwoStateVariableType;

public interface AcknowledgeableConditionType extends ConditionType {

    TwoStateVariableType getEnabledState();

    TwoStateVariableType getAckedState();

    TwoStateVariableType getConfirmedState();


}
