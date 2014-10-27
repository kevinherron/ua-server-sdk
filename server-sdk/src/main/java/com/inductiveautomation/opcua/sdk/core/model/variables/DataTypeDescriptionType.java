package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;

public interface DataTypeDescriptionType extends BaseDataVariableType {

    String getDataTypeVersion();

    ByteString getDictionaryFragment();

    void setDataTypeVersion(String dataTypeVersion);

    void setDictionaryFragment(ByteString dictionaryFragment);

    void atomicSet(Runnable runnable);

}
