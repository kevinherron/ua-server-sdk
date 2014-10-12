package com.inductiveautomation.opcua.sdk.server.objects;

import com.inductiveautomation.opcua.sdk.server.api.nodes.ObjectNode;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public interface ObjectPrototype extends ObjectNode {

    NodeId getType();

}
