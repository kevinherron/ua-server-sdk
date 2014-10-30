package com.inductiveautomation.opcua.sdk.core.model.variables;

public interface TwoStateVariableType extends StateVariableType {

    Boolean getId();

    void setId(Boolean id);

    void atomicSet(Runnable runnable);

}
