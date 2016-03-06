package com.digitalpetri.opcua.sdk.server.api;

import java.util.concurrent.ConcurrentMap;

import com.digitalpetri.opcua.sdk.server.model.UaNode;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.google.common.collect.ForwardingConcurrentMap;
import com.google.common.collect.MapMaker;

public abstract class AbstractUaNodeManager extends ForwardingConcurrentMap<NodeId, UaNode> implements UaNodeManager {

    private final ConcurrentMap<NodeId, UaNode> nodeMap;

    public AbstractUaNodeManager() {
        MapMaker mapMaker = new MapMaker();

        nodeMap = makeNodeMap(mapMaker);
    }

    protected ConcurrentMap<NodeId, UaNode> makeNodeMap(MapMaker mapMaker) {
        return mapMaker.makeMap();
    }

    @Override
    protected final ConcurrentMap<NodeId, UaNode> delegate() {
        return nodeMap;
    }

}
