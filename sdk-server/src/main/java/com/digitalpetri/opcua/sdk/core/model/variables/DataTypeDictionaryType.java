package com.digitalpetri.opcua.sdk.core.model.variables;

import com.digitalpetri.opcua.sdk.core.model.UaOptional;

public interface DataTypeDictionaryType extends BaseDataVariableType {

    @UaOptional("DataTypeVersion")
    String getDataTypeVersion();

    @UaOptional("NamespaceUri")
    String getNamespaceUri();

    void setDataTypeVersion(String dataTypeVersion);

    void setNamespaceUri(String namespaceUri);

}
