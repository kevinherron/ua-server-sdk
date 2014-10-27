package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;

public interface TransitionEventType extends BaseEventType {

    LocalizedText getTransition();

    LocalizedText getFromState();

    LocalizedText getToState();

    void setTransition(LocalizedText transition);

    void setFromState(LocalizedText fromState);

    void setToState(LocalizedText toState);

    void atomicSet(Runnable runnable);

}
