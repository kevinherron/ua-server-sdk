package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.structured.ServerStatusDataType;

public interface ServerType extends BaseObjectType {

    String[] getServerArray();

    String[] getNamespaceArray();

    UByte getServiceLevel();

    Boolean getAuditing();

    ServerStatusDataType getServerStatus();

    ServerCapabilitiesType getServerCapabilities();

    ServerDiagnosticsType getServerDiagnostics();

    VendorServerInfoType getVendorServerInfo();

    ServerRedundancyType getServerRedundancy();

    NamespacesType getNamespaces();

    void setServerArray(String[] serverArray);

    void setNamespaceArray(String[] namespaceArray);

    void setServiceLevel(UByte serviceLevel);

    void setAuditing(Boolean auditing);

    void setServerStatus(ServerStatusDataType serverStatus);

    void setServerCapabilities(ServerCapabilitiesType serverCapabilities);

    void setServerDiagnostics(ServerDiagnosticsType serverDiagnostics);

    void setVendorServerInfo(VendorServerInfoType vendorServerInfo);

    void setServerRedundancy(ServerRedundancyType serverRedundancy);

    void setNamespaces(NamespacesType namespaces);

    void atomicSet(Runnable runnable);

}
