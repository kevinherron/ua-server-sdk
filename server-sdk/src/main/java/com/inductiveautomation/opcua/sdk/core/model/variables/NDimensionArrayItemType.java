package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.sdk.core.model.UaMandatory;
import com.inductiveautomation.opcua.stack.core.types.structured.AxisInformation;

public interface NDimensionArrayItemType extends ArrayItemType {

    @UaMandatory("AxisDefinition")
    AxisInformation[] getAxisDefinition();

    void setAxisDefinition(AxisInformation[] axisDefinition);

    void atomicAction(Runnable runnable);

}
