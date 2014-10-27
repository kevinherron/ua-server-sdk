package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.structured.AxisInformation;

public interface XYArrayItemType extends ArrayItemType {

    AxisInformation getXAxisDefinition();

    void setXAxisDefinition(AxisInformation xAxisDefinition);

    void atomicSet(Runnable runnable);

}
