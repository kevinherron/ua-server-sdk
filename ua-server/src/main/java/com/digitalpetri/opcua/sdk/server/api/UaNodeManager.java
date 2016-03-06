/*
 * digitalpetri OPC-UA SDK
 *
 * Copyright (C) 2015 Kevin Herron
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.digitalpetri.opcua.sdk.server.api;

import java.util.Optional;
import java.util.concurrent.ConcurrentMap;

import com.digitalpetri.opcua.sdk.core.Reference;
import com.digitalpetri.opcua.sdk.server.model.UaNode;
import com.digitalpetri.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public interface UaNodeManager extends ConcurrentMap<NodeId, UaNode> {

    /**
     * Add a {@link UaNode} to this {@link UaNodeManager}.
     * <p>
     * This method is shorthand for:
     * <pre>
     *     {@code nodeManager.put(node.getNodeId(), node);}
     * </pre>
     *
     * @param node the {@link UaNode} to add.
     */
    default void addNode(UaNode node) {
        put(node.getNodeId(), node);
    }

    /**
     * Add a {@link Reference} to the {@link UaNode} indicated by {@link Reference#getSourceNodeId()}.
     *
     * @param reference the {@link Reference} to add.
     * @return {@code true} if the {@link UaNode} exists and the reference was added.
     */
    default boolean addReference(Reference reference) {
        return getNode(reference.getSourceNodeId()).map(node -> {
            node.addReference(reference);

            return true;
        }).orElse(false);
    }

    /**
     * Check if a {@link UaNode} exists in this {@link UaNodeManager}.
     *
     * @param node the {@link UaNode} in question.
     * @return {@code true} if this {@link UaNodeManager} contains the {@link UaNode}.
     */
    default boolean containsNode(UaNode node) {
        return containsNodeId(node.getNodeId());
    }

    /**
     * Check if a {@link UaNode} identified by {@link NodeId} exists in this {@link UaNodeManager}.
     *
     * @param nodeId the {@link NodeId} of the {@link UaNode} in question.
     * @return {@code true} if this {@link UaNodeManager} contains the {@link UaNode} identified by {@code nodeId}.
     */
    default boolean containsNodeId(NodeId nodeId) {
        return containsKey(nodeId);
    }

    /**
     * Get the {@link UaNode} identified by the provided {@link NodeId}, if it exists.
     *
     * @param nodeId the {@link NodeId} of the {@link UaNode}.
     * @return an {@link Optional} containing the {@link UaNode}, if present.
     */
    default Optional<UaNode> getNode(NodeId nodeId) {
        return Optional.ofNullable(get(nodeId));
    }

    /**
     * Get the {@link UaNode} identified by the provided {@link ExpandedNodeId}, if it exists.
     *
     * @param nodeId the {@link ExpandedNodeId} of the {@link UaNode}.
     * @return an {@link Optional} containing the {@link UaNode}, if present.
     */
    default Optional<UaNode> getNode(ExpandedNodeId nodeId) {
        return nodeId.local().flatMap(this::getNode);
    }

    /**
     * Remove the {@link UaNode} identified by the provided {@link NodeId}, if it exists.
     *
     * @param nodeId the {@link NodeId} of the {@link UaNode}.
     * @return an {@link Optional} containing the {@link UaNode}, if removed.
     */
    default Optional<UaNode> removeNode(NodeId nodeId) {
        return Optional.ofNullable(remove(nodeId));
    }

}
