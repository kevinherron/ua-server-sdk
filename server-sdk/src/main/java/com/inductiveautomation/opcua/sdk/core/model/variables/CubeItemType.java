package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.structured.AxisInformation;

public interface CubeItemType extends ArrayItemType {

    AxisInformation getXAxisDefinition();

    AxisInformation getYAxisDefinition();

    AxisInformation getZAxisDefinition();

    void setXAxisDefinition(AxisInformation xAxisDefinition);

    void setYAxisDefinition(AxisInformation yAxisDefinition);

    void setZAxisDefinition(AxisInformation zAxisDefinition);

    void atomicSet(Runnable runnable);

}
