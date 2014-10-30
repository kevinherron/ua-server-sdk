package com.inductiveautomation.opcua.sdk.core.model.variables;

public interface TransitionVariableType extends BaseDataVariableType {

    Object getId();

    void setId(Object id);

    void atomicSet(Runnable runnable);

}
