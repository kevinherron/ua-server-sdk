package com.digitalpetri.opcua.sdk.core.model.variables;

import com.digitalpetri.opcua.sdk.core.model.UaMandatory;
import com.digitalpetri.opcua.stack.core.types.structured.AxisInformation;

public interface NDimensionArrayItemType extends ArrayItemType {

    @UaMandatory("AxisDefinition")
    AxisInformation[] getAxisDefinition();

    void setAxisDefinition(AxisInformation[] axisDefinition);

}
