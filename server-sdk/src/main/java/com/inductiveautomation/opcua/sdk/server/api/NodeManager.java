/*
 * OPC-UA SDK
 *
 * Copyright (C) 2014 Kevin Herron
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

package com.inductiveautomation.opcua.sdk.server.api;

import java.util.List;
import java.util.Optional;

import com.inductiveautomation.opcua.sdk.server.api.nodes.Node;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExtensionObject;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;

public interface NodeManager {

    /**
     * Return <code>true</code> if this {@link NodeManager} contains a node for the requested {@link NodeId}.
     *
     * @param nodeId The requested {@link NodeId}.
     * @return <code>true</code> if this {@link NodeManager} contains a node for the requested {@link NodeId}.
     */
    boolean containsNodeId(NodeId nodeId);

    /**
     * Get the {@link Node} for the given {@link NodeId}, or {@link Optional#EMPTY} if none exists.
     *
     * @param nodeId The {@link NodeId} of the requested {@link Node}.
     * @return The {@link Node} for the given {@link NodeId}, or <code>null</code> if none exists.
     */
    Optional<Node> getNode(NodeId nodeId);

    /**
     * Get all {@link Reference}s for the requested node, or {@link Optional#EMPTY} if the node does not exist.
     * <p>
     * If the node exists but does not have any references return an empty list.
     *
     * @param nodeId The {@link NodeId} of the node the {@link Reference}s belong to.
     * @return All {@link Reference}s belonging to the requested node.
     */
    Optional<List<Reference>> getReferences(NodeId nodeId);


    default NodeId addNode(Optional<ExpandedNodeId> requestedNodeId,
                           QualifiedName browseName,
                           NodeClass nodeClass,
                           ExtensionObject nodeAttributes,
                           ExpandedNodeId typeDefintiion) throws UaException {

        throw new UaException(StatusCodes.Bad_NotSupported);
    }


    default void deleteNode(NodeId nodeId, boolean deleteTargetReferences) throws UaException {
        throw new UaException(StatusCodes.Bad_NotSupported);
    }

    default void addReference(NodeId sourceNodeId,
                              NodeId referenceTypeId,
                              boolean forward,
                              String targetServerUri,
                              ExpandedNodeId targetNodeId,
                              NodeClass targetNodeClass) throws UaException {

        throw new UaException(StatusCodes.Bad_NotSupported);
    }

    default void deleteReference(NodeId sourceNodeId,
                                 NodeId referenceTypeId,
                                 boolean forward,
                                 ExpandedNodeId targetNodeId,
                                 boolean bidirectional) throws UaException {

        throw new UaException(StatusCodes.Bad_NotSupported);
    }

}
