package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.sdk.core.model.variables.TwoStateVariableType;

public interface AcknowledgeableConditionType extends ConditionType {

    TwoStateVariableType getEnabledState();

    TwoStateVariableType getAckedState();

    TwoStateVariableType getConfirmedState();


}
