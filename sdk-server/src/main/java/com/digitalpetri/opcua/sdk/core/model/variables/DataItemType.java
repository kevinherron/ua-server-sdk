package com.digitalpetri.opcua.sdk.core.model.variables;

import com.digitalpetri.opcua.sdk.core.model.UaOptional;

public interface DataItemType extends BaseDataVariableType {

    @UaOptional("Definition")
    String getDefinition();

    @UaOptional("ValuePrecision")
    Double getValuePrecision();

    void setDefinition(String definition);

    void setValuePrecision(Double valuePrecision);

}
