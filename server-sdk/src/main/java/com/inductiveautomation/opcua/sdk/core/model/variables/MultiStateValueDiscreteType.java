package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.structured.EnumValueType;

public interface MultiStateValueDiscreteType extends DiscreteItemType {

    EnumValueType[] getEnumValues();

    LocalizedText getValueAsText();

    void setEnumValues(EnumValueType[] enumValues);

    void setValueAsText(LocalizedText valueAsText);

    void atomicSet(Runnable runnable);

}
