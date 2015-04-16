package com.digitalpetri.opcua.sdk.core.model.variables;

import com.digitalpetri.opcua.sdk.core.model.UaMandatory;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;

public interface MultiStateDiscreteType extends DiscreteItemType {

    @UaMandatory("EnumStrings")
    LocalizedText[] getEnumStrings();

    void setEnumStrings(LocalizedText[] enumStrings);

}
