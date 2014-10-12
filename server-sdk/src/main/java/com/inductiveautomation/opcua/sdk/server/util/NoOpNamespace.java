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

package com.inductiveautomation.opcua.sdk.server.util;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.inductiveautomation.opcua.sdk.server.api.EventItem;
import com.inductiveautomation.opcua.sdk.server.api.MonitoredItem;
import com.inductiveautomation.opcua.sdk.server.api.Namespace;
import com.inductiveautomation.opcua.sdk.server.api.Reference;
import com.inductiveautomation.opcua.sdk.server.api.SampledItem;
import com.inductiveautomation.opcua.sdk.server.api.nodes.Node;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadValueId;
import com.inductiveautomation.opcua.stack.core.types.structured.WriteValue;

public class NoOpNamespace implements Namespace {

    @Override
    public int getNamespaceIndex() {
        return -1;
    }

    @Override
    public String getNamespaceUri() {
        return getClass().getSimpleName();
    }

    @Override
    public boolean containsNodeId(NodeId nodeId) {
        return false;
    }

    @Override
    public Optional<Node> getNode(NodeId nodeId) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Reference>> getReferences(NodeId nodeId) {
        return Optional.empty();
    }

    @Override
    public void read(List<ReadValueId> readValueIds,
                     Double maxAge,
                     TimestampsToReturn timestamps,
                     CompletableFuture<List<DataValue>> future) {

        List<DataValue> values = Collections.nCopies(
                readValueIds.size(), new DataValue(StatusCodes.Bad_NodeIdUnknown));

        future.complete(values);
    }

    @Override
    public void write(List<WriteValue> writeValues, CompletableFuture<List<StatusCode>> future) {
        List<StatusCode> results = Collections.nCopies(
                writeValues.size(), new StatusCode(StatusCodes.Bad_NodeIdUnknown));

        future.complete(results);
    }

    @Override
    public void onSampledItemsCreated(List<SampledItem> sampledItems) {

    }

    @Override
    public void onSampledItemsModified(List<SampledItem> sampledItems) {

    }

    @Override
    public void onSampledItemsDeleted(List<SampledItem> sampledItems) {

    }

    @Override
    public void onEventItemsCreated(List<EventItem> eventItems) {

    }

    @Override
    public void onEventItemsModified(List<EventItem> eventItems) {

    }

    @Override
    public void onEventItemsDeleted(List<EventItem> eventItems) {

    }

    @Override
    public void onMonitoringModeChanged(List<MonitoredItem> monitoredItems) {

    }

}
