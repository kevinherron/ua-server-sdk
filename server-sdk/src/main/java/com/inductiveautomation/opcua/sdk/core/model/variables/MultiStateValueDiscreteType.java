package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.sdk.core.model.UaMandatory;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.structured.EnumValueType;

public interface MultiStateValueDiscreteType extends DiscreteItemType {

    @UaMandatory("EnumValues")
    EnumValueType[] getEnumValues();

    @UaMandatory("ValueAsText")
    LocalizedText getValueAsText();

    void setEnumValues(EnumValueType[] enumValues);

    void setValueAsText(LocalizedText valueAsText);

    void atomicAction(Runnable runnable);

}
