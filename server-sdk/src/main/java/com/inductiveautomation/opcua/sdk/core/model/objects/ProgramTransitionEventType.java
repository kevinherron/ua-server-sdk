package com.inductiveautomation.opcua.sdk.core.model.objects;

public interface ProgramTransitionEventType extends TransitionEventType {

    Object getIntermediateResult();

    void setIntermediateResult(Object intermediateResult);

    void atomicSet(Runnable runnable);

}
