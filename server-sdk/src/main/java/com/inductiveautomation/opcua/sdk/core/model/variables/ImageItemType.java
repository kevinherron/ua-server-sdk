package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.structured.AxisInformation;

public interface ImageItemType extends ArrayItemType {

    AxisInformation getXAxisDefinition();

    AxisInformation getYAxisDefinition();

    void setXAxisDefinition(AxisInformation xAxisDefinition);

    void setYAxisDefinition(AxisInformation yAxisDefinition);

    void atomicSet(Runnable runnable);

}
