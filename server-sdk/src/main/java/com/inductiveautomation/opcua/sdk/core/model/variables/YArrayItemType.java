package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.sdk.core.model.UaMandatory;
import com.inductiveautomation.opcua.stack.core.types.structured.AxisInformation;

public interface YArrayItemType extends ArrayItemType {

    @UaMandatory("XAxisDefinition")
    AxisInformation getXAxisDefinition();

    void setXAxisDefinition(AxisInformation xAxisDefinition);

    void atomicAction(Runnable runnable);

}
