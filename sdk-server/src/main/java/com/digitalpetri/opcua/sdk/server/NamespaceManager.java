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

package com.digitalpetri.opcua.sdk.server;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import com.digitalpetri.opcua.sdk.core.NamespaceTable;
import com.digitalpetri.opcua.sdk.server.api.Namespace;
import com.digitalpetri.opcua.sdk.server.util.NoOpNamespace;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaRuntimeException;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;
import com.digitalpetri.opcua.stack.core.types.enumerated.IdType;
import com.google.common.collect.Maps;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.ushort;

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

//    public boolean containsNodeId(NodeId nodeId) {
//        Namespace namespace = namespaces.get(nodeId.getNamespaceIndex());
//
//        return namespace != null && namespace.containsNodeId(nodeId);
//    }
//
//    public boolean containsNodeId(ExpandedNodeId expandedNodeId) {
//        return toNodeId(expandedNodeId).map(this::containsNodeId).orElse(false);
//    }
//
//    public <T> Optional<T> getAttribute(NodeId nodeId, int attributeId) {
//        Namespace namespace = namespaces.get(nodeId.getNamespaceIndex());
//
//        if (namespace == null) namespace = NO_OP_NAMESPACE;
//
//        return Optional.ofNullable(namespace.getAttribute(nodeId, attributeId));
//    }
//
//    public <T> Optional<T> getAttribute(ExpandedNodeId nodeId, int attributeId) {
//        return nodeId.local().flatMap(id -> getAttribute(id, attributeId));
//    }
//
//    public boolean attributeExists(NodeId nodeId, int attribute) {
//        Namespace namespace = namespaces.get(nodeId.getNamespaceIndex());
//
//        if (namespace == null) namespace = NO_OP_NAMESPACE;
//
//        return namespace.attributeExists(nodeId, attribute);
//    }
//
//    public boolean attributeExists(NodeId nodeId, UInteger attributeId) {
//        return attributeExists(nodeId, attributeId.intValue());
//    }
//
//    public Optional<List<Reference>> getReferences(NodeId nodeId) {
//        Namespace namespace = namespaces.get(nodeId.getNamespaceIndex());
//
//        if (namespace == null) {
//            return Optional.empty();
//        } else {
//            return namespace.getReferences(nodeId);
//        }
//    }
//
//    public Optional<List<Reference>> getReferences(ExpandedNodeId nodeId) {
//        return nodeId.local().flatMap(this::getReferences);
//    }

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
