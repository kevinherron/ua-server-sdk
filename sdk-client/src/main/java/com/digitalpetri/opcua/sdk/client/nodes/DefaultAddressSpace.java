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

package com.digitalpetri.opcua.sdk.client.nodes;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.sdk.client.OpcUaClient;
import com.digitalpetri.opcua.sdk.client.api.nodes.AddressSpace;
import com.digitalpetri.opcua.sdk.client.api.nodes.attached.UaDataTypeNode;
import com.digitalpetri.opcua.sdk.client.api.nodes.attached.UaMethodNode;
import com.digitalpetri.opcua.sdk.client.api.nodes.attached.UaNode;
import com.digitalpetri.opcua.sdk.client.api.nodes.attached.UaObjectNode;
import com.digitalpetri.opcua.sdk.client.api.nodes.attached.UaObjectTypeNode;
import com.digitalpetri.opcua.sdk.client.api.nodes.attached.UaReferenceTypeNode;
import com.digitalpetri.opcua.sdk.client.api.nodes.attached.UaVariableNode;
import com.digitalpetri.opcua.sdk.client.api.nodes.attached.UaVariableTypeNode;
import com.digitalpetri.opcua.sdk.client.api.nodes.attached.UaViewNode;
import com.digitalpetri.opcua.sdk.client.nodes.attached.AttachedDataTypeNode;
import com.digitalpetri.opcua.sdk.client.nodes.attached.AttachedMethodNode;
import com.digitalpetri.opcua.sdk.client.nodes.attached.AttachedObjectNode;
import com.digitalpetri.opcua.sdk.client.nodes.attached.AttachedObjectTypeNode;
import com.digitalpetri.opcua.sdk.client.nodes.attached.AttachedReferenceTypeNode;
import com.digitalpetri.opcua.sdk.client.nodes.attached.AttachedVariableNode;
import com.digitalpetri.opcua.sdk.client.nodes.attached.AttachedVariableTypeNode;
import com.digitalpetri.opcua.sdk.client.nodes.attached.AttachedViewNode;
import com.digitalpetri.opcua.stack.core.AttributeId;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.enumerated.NodeClass;
import com.digitalpetri.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.digitalpetri.opcua.stack.core.types.structured.ReadResponse;
import com.digitalpetri.opcua.stack.core.types.structured.ReadValueId;

import static com.google.common.collect.Lists.newArrayList;

public class DefaultAddressSpace implements AddressSpace {

    private final OpcUaClient client;

    public DefaultAddressSpace(OpcUaClient client) {
        this.client = client;
    }

    @Override
    public CompletableFuture<UaNode> getNode(NodeId nodeId) {
        ReadValueId readValueId = new ReadValueId(
                nodeId, AttributeId.NODE_CLASS.uid(), null, QualifiedName.NULL_VALUE);

        CompletableFuture<ReadResponse> future =
                client.read(0.0, TimestampsToReturn.Neither, newArrayList(readValueId));

        return future.thenCompose(response -> {
            DataValue value = response.getResults()[0];
            NodeClass nodeClass = (NodeClass) value.getValue().getValue();

            if (nodeClass != null) {
                client.getNodeCache().putAttribute(nodeId, AttributeId.NODE_CLASS, value);

                return CompletableFuture.completedFuture(createNode(nodeId, nodeClass));
            } else {
                return failedFuture(new UaException(value.getStatusCode(), "NodeClass was null"));
            }
        });
    }

    @Override
    public UaDataTypeNode getDataTypeNode(NodeId nodeId) {
        return new AttachedDataTypeNode(client, nodeId);
    }

    @Override
    public UaMethodNode getMethodNode(NodeId nodeId) {
        return new AttachedMethodNode(client, nodeId);
    }

    @Override
    public UaObjectNode getObjectNode(NodeId nodeId) {
        return new AttachedObjectNode(client, nodeId);
    }

    @Override
    public UaObjectTypeNode getObjectTypeNode(NodeId nodeId) {
        return new AttachedObjectTypeNode(client, nodeId);
    }

    @Override
    public UaReferenceTypeNode getReferenceTypeNode(NodeId nodeId) {
        return new AttachedReferenceTypeNode(client, nodeId);
    }

    @Override
    public UaVariableNode getVariableNode(NodeId nodeId) {
        return new AttachedVariableNode(client, nodeId);
    }

    @Override
    public UaVariableTypeNode getVariableTypeNode(NodeId nodeId) {
        return new AttachedVariableTypeNode(client, nodeId);
    }

    @Override
    public UaViewNode getViewNode(NodeId nodeId) {
        return new AttachedViewNode(client, nodeId);
    }

    private UaNode createNode(NodeId nodeId, NodeClass nodeClass) {
        switch (nodeClass) {
            case DataType:
                return new AttachedDataTypeNode(client, nodeId);
            case Method:
                return new AttachedMethodNode(client, nodeId);
            case Object:
                return new AttachedObjectNode(client, nodeId);
            case ObjectType:
                return new AttachedObjectTypeNode(client, nodeId);
            case ReferenceType:
                return new AttachedReferenceTypeNode(client, nodeId);
            case Variable:
                return new AttachedVariableNode(client, nodeId);
            case VariableType:
                return new AttachedVariableTypeNode(client, nodeId);
            case View:
                return new AttachedViewNode(client, nodeId);
            default:
                throw new IllegalStateException("unhandled NodeClass: " + nodeClass);
        }
    }


    private static <T> CompletableFuture<T> failedFuture(StatusCode statusCode) {
        return failedFuture(new UaException(statusCode));
    }

    private static <T> CompletableFuture<T> failedFuture(UaException exception) {
        CompletableFuture<T> f = new CompletableFuture<>();
        f.completeExceptionally(exception);
        return f;
    }

}
