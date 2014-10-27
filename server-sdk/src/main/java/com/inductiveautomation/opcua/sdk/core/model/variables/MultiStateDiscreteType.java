package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;

public interface MultiStateDiscreteType extends DiscreteItemType {

    LocalizedText[] getEnumStrings();

    void setEnumStrings(LocalizedText[] enumStrings);

    void atomicSet(Runnable runnable);

}
