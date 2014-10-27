package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.structured.AxisInformation;

public interface NDimensionArrayItemType extends ArrayItemType {

    AxisInformation[] getAxisDefinition();

    void setAxisDefinition(AxisInformation[] axisDefinition);

    void atomicSet(Runnable runnable);

}
