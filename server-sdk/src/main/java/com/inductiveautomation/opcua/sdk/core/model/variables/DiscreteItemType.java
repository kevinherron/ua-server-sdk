package com.inductiveautomation.opcua.sdk.core.model.variables;

public interface DiscreteItemType extends DataItemType {


    void atomicAction(Runnable runnable);

}
