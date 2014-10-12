package com.inductiveautomation.opcua.sdk.server.objects;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.structured.ServerStatusDataType;
import com.inductiveautomation.opcua.stack.core.util.annotations.UByte;

public interface ServerObject extends ObjectPrototype {

    @Override
    default NodeId getType() { return Identifiers.Server; }

    String[] getServerArray();

    String[] getNamespaceArray();

    ServerStatusDataType getServerStatus();

    @UByte
    short getServiceLevel();

    boolean isAuditing();

    ServerCapabilities getServerCapabilities();

    ServerDiagnostics getServerDiagnostics();

}
