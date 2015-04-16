package com.digitalpetri.opcua.sdk.core.model.variables;

import com.digitalpetri.opcua.sdk.core.model.UaMandatory;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;

public interface ConditionVariableType extends BaseDataVariableType {

    @UaMandatory("SourceTimestamp")
    DateTime getSourceTimestamp();

    void setSourceTimestamp(DateTime sourceTimestamp);

}
