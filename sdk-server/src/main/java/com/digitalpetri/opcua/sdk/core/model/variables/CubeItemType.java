package com.digitalpetri.opcua.sdk.core.model.variables;

import com.digitalpetri.opcua.sdk.core.model.UaMandatory;
import com.digitalpetri.opcua.stack.core.types.structured.AxisInformation;

public interface CubeItemType extends ArrayItemType {

    @UaMandatory("XAxisDefinition")
    AxisInformation getXAxisDefinition();

    @UaMandatory("YAxisDefinition")
    AxisInformation getYAxisDefinition();

    @UaMandatory("ZAxisDefinition")
    AxisInformation getZAxisDefinition();

    void setXAxisDefinition(AxisInformation xAxisDefinition);

    void setYAxisDefinition(AxisInformation yAxisDefinition);

    void setZAxisDefinition(AxisInformation zAxisDefinition);

}
