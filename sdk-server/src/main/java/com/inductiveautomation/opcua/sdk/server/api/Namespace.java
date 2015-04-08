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
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UShort;

public interface Namespace extends ReadWriteManager, MethodManager, MonitoredItemManager, NodeManager {

    /**
     * @return the index of this {@link Namespace} in the server's namespace array.
     */
    UShort getNamespaceIndex();

    /**
     * @return the URI identifying this {@link Namespace}.
     */
    String getNamespaceUri();

    /**
     * Return <code>true</code> if this {@link NodeManager} contains a node for the requested {@link NodeId}.
     *
     * @param nodeId The requested {@link NodeId}.
     * @return <code>true</code> if this {@link NodeManager} contains a node for the requested {@link NodeId}.
     */
    boolean containsNodeId(NodeId nodeId);

    /**
     * Get all {@link Reference}s for the requested node, or {@link Optional#EMPTY} if the node does not exist.
     * <p>
     * If the node exists but does not have any references return an empty list.
     *
     * @param nodeId The {@link NodeId} of the node the {@link Reference}s belong to.
     * @return All {@link Reference}s belonging to the requested node.
     */
    Optional<List<Reference>> getReferences(NodeId nodeId);

    /**
     * For the Node identified by {@code nodeId} get the value of the attribute identified by {@code attributeId}
     * if it exists. Otherwise {@code null}.
     *
     * @param nodeId      the {@link NodeId} of the node.
     * @param attributeId the attribute id.
     * @return the value of the attribute, or {@code null} if that attribute does not exist.
     */
    <T> T getAttribute(NodeId nodeId, int attributeId);

    /**
     * For the Node identified by {@code nodeId} return whether or not the attribute identified by {@code attributeId}
     * exists.
     *
     * @param nodeId      the {@link NodeId} of the node.
     * @param attributeId the attribute id.
     * @return {@code true} if the attribute exists.
     */
    boolean attributeExists(NodeId nodeId, int attributeId);

}
