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
import com.digitalpetri.opcua.sdk.client.api.nodes.attached.UaMethodNode;
import com.digitalpetri.opcua.stack.core.AttributeId;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;

public class AttachedMethodNode extends AttachedNode implements UaMethodNode {

    public AttachedMethodNode(OpcUaClient client, NodeId nodeId) {
        super(client, nodeId);
    }

    @Override
    public CompletableFuture<DataValue> readExecutable() {
        return readAttribute(AttributeId.EXECUTABLE);
    }

    @Override
    public CompletableFuture<DataValue> readUserExecutable() {
        return readAttribute(AttributeId.USER_EXECUTABLE);
    }

    @Override
    public CompletableFuture<StatusCode> writeExecutable(DataValue value) {
        return writeAttribute(AttributeId.EXECUTABLE, value);
    }

    @Override
    public CompletableFuture<StatusCode> writeUserExecutable(DataValue value) {
        return writeAttribute(AttributeId.USER_EXECUTABLE, value);
    }

}
