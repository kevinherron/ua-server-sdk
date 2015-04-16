package com.digitalpetri.opcua.sdk.core.model.variables;

import com.digitalpetri.opcua.sdk.core.model.UaMandatory;
import com.digitalpetri.opcua.stack.core.types.structured.AxisInformation;

public interface ImageItemType extends ArrayItemType {

    @UaMandatory("XAxisDefinition")
    AxisInformation getXAxisDefinition();

    @UaMandatory("YAxisDefinition")
    AxisInformation getYAxisDefinition();

    void setXAxisDefinition(AxisInformation xAxisDefinition);

    void setYAxisDefinition(AxisInformation yAxisDefinition);

}
