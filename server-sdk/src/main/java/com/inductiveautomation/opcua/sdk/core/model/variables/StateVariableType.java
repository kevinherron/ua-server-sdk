package com.inductiveautomation.opcua.sdk.core.model.variables;

public interface StateVariableType extends BaseDataVariableType {

    Object getId();

    void setId(Object id);

    void atomicSet(Runnable runnable);

}
