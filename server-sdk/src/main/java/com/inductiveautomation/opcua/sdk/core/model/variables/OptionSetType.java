package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.sdk.core.model.UaMandatory;
import com.inductiveautomation.opcua.sdk.core.model.UaOptional;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;

public interface OptionSetType extends BaseDataVariableType {

    @UaMandatory("OptionSetValues")
    LocalizedText[] getOptionSetValues();

    @UaOptional("BitMask")
    Boolean[] getBitMask();

    void setOptionSetValues(LocalizedText[] optionSetValues);

    void setBitMask(Boolean[] bitMask);

    void atomicAction(Runnable runnable);

}
