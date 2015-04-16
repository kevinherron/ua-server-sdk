package com.digitalpetri.opcua.sdk.core.model.variables;

import com.digitalpetri.opcua.sdk.core.model.UaMandatory;
import com.digitalpetri.opcua.stack.core.types.structured.AxisInformation;

public interface XYArrayItemType extends ArrayItemType {

    @UaMandatory("XAxisDefinition")
    AxisInformation getXAxisDefinition();

    void setXAxisDefinition(AxisInformation xAxisDefinition);

}
