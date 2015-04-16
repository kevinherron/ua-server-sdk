package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.sdk.core.model.variables.TwoStateVariableType;

public interface ExclusiveLimitAlarmType extends LimitAlarmType {

    TwoStateVariableType getActiveState();

    ExclusiveLimitStateMachineType getLimitState();


}
