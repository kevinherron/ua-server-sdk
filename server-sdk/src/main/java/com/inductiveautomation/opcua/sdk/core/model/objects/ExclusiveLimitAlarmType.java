package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;

public interface ExclusiveLimitAlarmType extends LimitAlarmType {

    LocalizedText getActiveState();

    ExclusiveLimitStateMachineType getLimitState();

    void setActiveState(LocalizedText activeState);

    void setLimitState(ExclusiveLimitStateMachineType limitState);

    void atomicSet(Runnable runnable);

}
