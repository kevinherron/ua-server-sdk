package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.sdk.core.model.UaMandatory;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;

public interface MultiStateDiscreteType extends DiscreteItemType {

    @UaMandatory("EnumStrings")
    LocalizedText[] getEnumStrings();

    void setEnumStrings(LocalizedText[] enumStrings);

    void atomicAction(Runnable runnable);

}
