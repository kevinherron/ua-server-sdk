/*
 * Copyright 2014 Inductive Automation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.inductiveautomation.opcua.sdk.server.api;

import java.util.List;
import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.Reference;
import com.inductiveautomation.opcua.sdk.core.nodes.Node;
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
