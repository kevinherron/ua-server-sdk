package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;

public interface AcknowledgeableConditionType extends ConditionType {

    LocalizedText getEnabledState();

    LocalizedText getAckedState();

    LocalizedText getConfirmedState();

    void setEnabledState(LocalizedText enabledState);

    void setAckedState(LocalizedText ackedState);

    void setConfirmedState(LocalizedText confirmedState);

    void atomicSet(Runnable runnable);

}
