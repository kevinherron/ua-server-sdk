package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;

public interface ConditionVariableType extends BaseDataVariableType {

    DateTime getSourceTimestamp();

    void setSourceTimestamp(DateTime sourceTimestamp);

    void atomicSet(Runnable runnable);

}
