package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.sdk.core.model.variables.TwoStateVariableType;

public interface NonExclusiveLimitAlarmType extends LimitAlarmType {

    TwoStateVariableType getActiveState();

    TwoStateVariableType getHighHighState();

    TwoStateVariableType getHighState();

    TwoStateVariableType getLowState();

    TwoStateVariableType getLowLowState();


}
