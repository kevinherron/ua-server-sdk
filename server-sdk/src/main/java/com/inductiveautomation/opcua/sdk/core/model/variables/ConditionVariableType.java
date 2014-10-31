package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.sdk.core.model.UaMandatory;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;

public interface ConditionVariableType extends BaseDataVariableType {

    @UaMandatory("SourceTimestamp")
    DateTime getSourceTimestamp();

    void setSourceTimestamp(DateTime sourceTimestamp);

    void atomicAction(Runnable runnable);

}
