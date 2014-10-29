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

package com.inductiveautomation.opcua.sdk.server;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import com.google.common.collect.Maps;
import com.inductiveautomation.opcua.sdk.core.NamespaceTable;
import com.inductiveautomation.opcua.sdk.core.Reference;
import com.inductiveautomation.opcua.sdk.core.nodes.Node;
import com.inductiveautomation.opcua.sdk.server.api.Namespace;
import com.inductiveautomation.opcua.sdk.server.util.NoOpNamespace;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaRuntimeException;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UShort;
import com.inductiveautomation.opcua.stack.core.types.enumerated.IdType;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.ushort;

public class NamespaceManager {

    private static final Namespace NO_OP_NAMESPACE = new NoOpNamespace();

    private final NamespaceTable namespaceTable = new NamespaceTable();

    private final Map<UShort, Namespace> namespaces = Maps.newConcurrentMap();

    public NamespaceManager() {
        namespaceTable.putUri(NamespaceTable.OpcUaNamespace, ushort(0));
    }

    /**
     * Register a namespace URI.
     *
     * @param namespaceUri the namespace URI to register.
     * @return The index assigned to the given namespace URI.
     * @see #registerAndAdd(String, Function)
     */
    public UShort registerUri(String namespaceUri) {
        return namespaceTable.addUri(namespaceUri);
    }

    /**
     * Add a {@link Namespace}.
     * <p>
     * The URI must already be registered.
     *
     * @param namespace The {@link Namespace} to add.
     * @see #registerUri(String)
     * @see #registerAndAdd(String, Function)
     */
    public void addNamespace(Namespace namespace) {
        namespaces.put(namespace.getNamespaceIndex(), namespace);
    }

    /**
     * Register a namespace URI and add the corresponding {@link Namespace}.
     * <p>
     * This is a convenience method equivalent to calling {@link #registerUri(String)} followed by
     * {@link #addNamespace(Namespace)}.
     *
     * @param namespaceUri      The namespace URI to register.
     * @param namespaceFunction A function that returns a {@link Namespace} for the supplied namespace index.
     * @return The {@link Namespace} returned by {@code namespaceFunction}.
     */
    public <T extends Namespace> T registerAndAdd(String namespaceUri, Function<UShort, T> namespaceFunction) {
        UShort namespaceIndex = namespaceTable.addUri(namespaceUri);
        T namespace = namespaceFunction.apply(namespaceIndex);
        namespaces.put(namespaceIndex, namespace);

        return namespace;
    }

    public Namespace getNamespace(int index) {
        return getNamespace(ushort(index));
    }

    public Namespace getNamespace(UShort index) {
        Namespace namespace = namespaces.get(index);

        return namespace != null ? namespace : NO_OP_NAMESPACE;
    }

    public boolean containsNodeId(NodeId nodeId) {
        Namespace namespace = namespaces.get(nodeId.getNamespaceIndex());

        return namespace != null && namespace.containsNodeId(nodeId);
    }

    public boolean containsNodeId(ExpandedNodeId expandedNodeId) {
        return toNodeId(expandedNodeId).map(this::containsNodeId).orElse(false);
    }

    public Optional<Node> getNode(NodeId nodeId) {
        Namespace namespace = namespaces.get(nodeId.getNamespaceIndex());

        if (namespace == null) {
            return Optional.empty();
        } else {
            return namespace.getNode(nodeId);
        }
    }

    public Optional<Node> getNode(ExpandedNodeId expandedNodeId) {
        return toNodeId(expandedNodeId).flatMap(this::getNode);
    }

    public Optional<List<Reference>> getReferences(NodeId nodeId) {
        Namespace namespace = namespaces.get(nodeId.getNamespaceIndex());

        if (namespace == null) {
            return Optional.empty();
        } else {
            return namespace.getReferences(nodeId);
        }
    }

    public NamespaceTable getNamespaceTable() {
        return namespaceTable;
    }

    public Optional<NodeId> toNodeId(ExpandedNodeId expandedNodeId) {
        if (!expandedNodeId.isLocal()) return Optional.empty();

        String namespaceUri = expandedNodeId.getNamespaceUri();

        Object identifier = expandedNodeId.getIdentifier();
        IdType type = expandedNodeId.getType();

        if (namespaceUri == null || namespaceUri.isEmpty()) {
            return Optional.of(createNodeId(expandedNodeId.getNamespaceIndex(), identifier, type));
        } else {
            UShort index = namespaceTable.getIndex(namespaceUri);

            if (index == null) return Optional.empty();
            else return Optional.of(createNodeId(index, identifier, type));
        }
    }

    private NodeId createNodeId(UShort namespaceIndex, Object identifier, IdType type) {
        switch (type) {
            case Numeric:
                return new NodeId(namespaceIndex, (UInteger) identifier);
            case String:
                return new NodeId(namespaceIndex, (String) identifier);
            case Guid:
                return new NodeId(namespaceIndex, (UUID) identifier);
            case Opaque:
                return new NodeId(namespaceIndex, (ByteString) identifier);
            default:
                throw new UaRuntimeException(StatusCodes.Bad_InternalError, "unhandled type: " + type);
        }
    }

}
