package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.enumerated.ServerState;

public interface SystemStatusChangeEventType extends SystemEventType {

    ServerState getSystemState();

    void setSystemState(ServerState systemState);

}
