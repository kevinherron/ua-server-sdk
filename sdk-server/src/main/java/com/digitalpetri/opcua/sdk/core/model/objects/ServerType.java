package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.sdk.core.model.variables.ServerStatusType;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;

public interface ServerType extends BaseObjectType {

    String[] getServerArray();

    String[] getNamespaceArray();

    ServerStatusType getServerStatus();

    UByte getServiceLevel();

    Boolean getAuditing();

    ServerCapabilitiesType getServerCapabilities();

    ServerDiagnosticsType getServerDiagnostics();

    VendorServerInfoType getVendorServerInfo();

    ServerRedundancyType getServerRedundancy();

    NamespacesType getNamespaces();

    void setServerArray(String[] serverArray);

    void setNamespaceArray(String[] namespaceArray);

    void setServiceLevel(UByte serviceLevel);

    void setAuditing(Boolean auditing);

}
