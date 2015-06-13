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

package com.digitalpetri.opcua.sdk.client.nodes.attached;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.sdk.client.OpcUaClient;
import com.digitalpetri.opcua.sdk.client.api.nodes.attached.UaReferenceTypeNode;
import com.digitalpetri.opcua.stack.core.AttributeId;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;

public class AttachedReferenceTypeNode extends AttachedNode implements UaReferenceTypeNode {

    public AttachedReferenceTypeNode(OpcUaClient client, NodeId nodeId) {
        super(client, nodeId);
    }

    @Override
    public CompletableFuture<DataValue> readIsAbstract() {
        return readAttribute(AttributeId.IS_ABSTRACT);
    }

    @Override
    public CompletableFuture<DataValue> readSymmetric() {
        return readAttribute(AttributeId.SYMMETRIC);
    }

    @Override
    public CompletableFuture<DataValue> readInverseName() {
        return readAttribute(AttributeId.INVERSE_NAME);
    }

    @Override
    public CompletableFuture<StatusCode> writeIsAbstract(DataValue value) {
        return writeAttribute(AttributeId.IS_ABSTRACT, value);
    }

    @Override
    public CompletableFuture<StatusCode> writeSymmetric(DataValue value) {
        return writeAttribute(AttributeId.SYMMETRIC, value);
    }

    @Override
    public CompletableFuture<StatusCode> writeInverseName(DataValue value) {
        return writeAttribute(AttributeId.INVERSE_NAME, value);
    }

}
