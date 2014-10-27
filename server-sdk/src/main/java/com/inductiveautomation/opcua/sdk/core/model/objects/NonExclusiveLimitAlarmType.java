package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;

public interface NonExclusiveLimitAlarmType extends LimitAlarmType {

    LocalizedText getActiveState();

    LocalizedText getHighHighState();

    LocalizedText getHighState();

    LocalizedText getLowState();

    LocalizedText getLowLowState();

    void setActiveState(LocalizedText activeState);

    void setHighHighState(LocalizedText highHighState);

    void setHighState(LocalizedText highState);

    void setLowState(LocalizedText lowState);

    void setLowLowState(LocalizedText lowLowState);

    void atomicSet(Runnable runnable);

}
