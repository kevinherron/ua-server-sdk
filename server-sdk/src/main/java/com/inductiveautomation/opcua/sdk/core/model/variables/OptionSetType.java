package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;

public interface OptionSetType extends BaseDataVariableType {

    LocalizedText[] getOptionSetValues();

    void setOptionSetValues(LocalizedText[] optionSetValues);

    void atomicSet(Runnable runnable);

}
