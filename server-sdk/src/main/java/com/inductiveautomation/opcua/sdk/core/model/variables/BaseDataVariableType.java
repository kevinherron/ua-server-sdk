package com.inductiveautomation.opcua.sdk.core.model.variables;

public interface BaseDataVariableType extends BaseVariableType {


    void atomicAction(Runnable runnable);

}
