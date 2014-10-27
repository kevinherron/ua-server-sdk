package com.inductiveautomation.opcua.sdk.core.model.variables;

public interface DataTypeDictionaryType extends BaseDataVariableType {

    String getDataTypeVersion();

    String getNamespaceUri();

    void setDataTypeVersion(String dataTypeVersion);

    void setNamespaceUri(String namespaceUri);

    void atomicSet(Runnable runnable);

}
