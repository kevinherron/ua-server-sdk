package com.inductiveautomation.opcua.sdk.server.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public interface ObjectInstance {

    NodeId getNodeId();
    NodeId getTypeId();

}
