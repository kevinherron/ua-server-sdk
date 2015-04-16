package com.digitalpetri.opcua.sdk.core.model.variables;

import com.digitalpetri.opcua.sdk.core.model.UaMandatory;
import com.digitalpetri.opcua.sdk.core.model.UaOptional;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;

public interface OptionSetType extends BaseDataVariableType {

    @UaMandatory("OptionSetValues")
    LocalizedText[] getOptionSetValues();

    @UaOptional("BitMask")
    Boolean[] getBitMask();

    void setOptionSetValues(LocalizedText[] optionSetValues);

    void setBitMask(Boolean[] bitMask);

}
