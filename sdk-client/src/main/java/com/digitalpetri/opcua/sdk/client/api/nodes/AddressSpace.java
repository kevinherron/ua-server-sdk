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

package com.digitalpetri.opcua.sdk.client.api.nodes;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.sdk.client.api.nodes.attached.UaDataTypeNode;
import com.digitalpetri.opcua.sdk.client.api.nodes.attached.UaMethodNode;
import com.digitalpetri.opcua.sdk.client.api.nodes.attached.UaNode;
import com.digitalpetri.opcua.sdk.client.api.nodes.attached.UaObjectNode;
import com.digitalpetri.opcua.sdk.client.api.nodes.attached.UaObjectTypeNode;
import com.digitalpetri.opcua.sdk.client.api.nodes.attached.UaReferenceTypeNode;
import com.digitalpetri.opcua.sdk.client.api.nodes.attached.UaVariableNode;
import com.digitalpetri.opcua.sdk.client.api.nodes.attached.UaVariableTypeNode;
import com.digitalpetri.opcua.sdk.client.api.nodes.attached.UaViewNode;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public interface AddressSpace {

    CompletableFuture<UaNode> getNode(NodeId nodeId);

    UaDataTypeNode getDataTypeNode(NodeId nodeId);

    UaMethodNode getMethodNode(NodeId nodeId);

    UaObjectNode getObjectNode(NodeId nodeId);

    UaObjectTypeNode getObjectTypeNode(NodeId nodeId);

    UaReferenceTypeNode getReferenceTypeNode(NodeId nodeId);

    UaVariableNode getVariableNode(NodeId nodeId);

    UaVariableTypeNode getVariableTypeNode(NodeId nodeId);

    UaViewNode getViewNode(NodeId nodeId);

}
