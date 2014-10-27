package com.inductiveautomation.opcua.sdk.core.model.variables;

public interface DataItemType extends BaseDataVariableType {

    String getDefinition();

    Double getValuePrecision();

    void setDefinition(String definition);

    void setValuePrecision(Double valuePrecision);

    void atomicSet(Runnable runnable);

}
