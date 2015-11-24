package com.digitalpetri.opcua.sdk.server;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

import com.digitalpetri.opcua.sdk.server.api.Namespace;
import com.digitalpetri.opcua.sdk.server.model.UaNode;
import com.digitalpetri.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;
import com.google.common.collect.Maps;

public class UaNodeManager {

    private final ConcurrentMap<UShort, UaNodeSource> sources = Maps.newConcurrentMap();

    public void addNode(UaNode node) {
        UaNodeSource nodeSource = sources.get(node.getNodeId().getNamespaceIndex());
        if (nodeSource != null) nodeSource.addNode(node);
    }

    public Optional<UaNode> getNode(NodeId nodeId) {
        UaNodeSource nodeSource = sources.get(nodeId.getNamespaceIndex());

        return nodeSource != null ? nodeSource.getNode(nodeId) : Optional.empty();
    }

    public Optional<UaNode> getNode(ExpandedNodeId nodeId) {
        UaNodeSource nodeSource = sources.get(nodeId.getNamespaceIndex());

        return nodeSource != null ? nodeSource.getNode(nodeId) : Optional.empty();
    }

    public Optional<UaNode> removeNode(NodeId nodeId) {
        UaNodeSource nodeSource = sources.get(nodeId.getNamespaceIndex());

        return nodeSource != null ? nodeSource.removeNode(nodeId) : Optional.empty();
    }

    public void registerNodeSource(UaNodeSource nodeSource) {
        sources.put(nodeSource.getNamespaceIndex(), nodeSource);
    }

    public void unregisterNodeSource(UaNodeSource nodeSource) {
        sources.remove(nodeSource.getNamespaceIndex());
    }

    public interface UaNodeSource {

        /**
         * @return the index of this {@link Namespace} in the server's namespace array.
         */
        UShort getNamespaceIndex();

        void addNode(UaNode node);

        Optional<UaNode> getNode(NodeId nodeId);

        Optional<UaNode> getNode(ExpandedNodeId nodeId);

        Optional<UaNode> removeNode(NodeId nodeId);

    }
}
