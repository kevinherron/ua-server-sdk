package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.sdk.core.model.variables.TwoStateVariableType;

public interface NonExclusiveLimitAlarmType extends LimitAlarmType {

    TwoStateVariableType getActiveState();

    TwoStateVariableType getHighHighState();

    TwoStateVariableType getHighState();

    TwoStateVariableType getLowState();

    TwoStateVariableType getLowLowState();


    void atomicSet(Runnable runnable);

}
