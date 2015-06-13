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

package com.digitalpetri.opcua.sdk.client.api.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.codepoetics.protonpack.StreamUtils;
import com.digitalpetri.opcua.stack.core.AttributeId;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.digitalpetri.opcua.stack.core.types.structured.HistoryReadDetails;
import com.digitalpetri.opcua.stack.core.types.structured.HistoryReadResponse;
import com.digitalpetri.opcua.stack.core.types.structured.HistoryReadValueId;
import com.digitalpetri.opcua.stack.core.types.structured.HistoryUpdateDetails;
import com.digitalpetri.opcua.stack.core.types.structured.HistoryUpdateResponse;
import com.digitalpetri.opcua.stack.core.types.structured.ReadResponse;
import com.digitalpetri.opcua.stack.core.types.structured.ReadValueId;
import com.digitalpetri.opcua.stack.core.types.structured.WriteResponse;
import com.digitalpetri.opcua.stack.core.types.structured.WriteValue;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;
import static com.google.common.collect.Lists.newArrayList;

public interface AttributeServices {

    /**
     * This service is used to read one or more attributes of one or more nodes.
     *
     * @param maxAge             the requested max age of the value, in milliseconds. If maxAge is set to 0, the Server
     *                           shall attempt to read a new value from the data source. If maxAge is set to the max
     *                           Int32 value or greater, the Server shall attempt to get a cached value. Negative values
     *                           are invalid for maxAge.
     * @param timestampsToReturn the requested {@link TimestampsToReturn}.
     * @param readValueIds       the {@link ReadValueId}s identifying the nodes and attributes to read.
     * @return {@link CompletableFuture} containing the {@link ReadResponse}.
     */
    CompletableFuture<ReadResponse> read(double maxAge,
                                         TimestampsToReturn timestampsToReturn,
                                         List<ReadValueId> readValueIds);

    /**
     * This service is used to read one or more attributes of one or more nodes.
     *
     * @param maxAge             the requested max age of the value, in milliseconds. If maxAge is set to 0, the Server
     *                           shall attempt to read a new value from the data source. If maxAge is set to the max
     *                           Int32 value or greater, the Server shall attempt to get a cached value. Negative values
     *                           are invalid for maxAge.
     * @param timestampsToReturn the requested {@link TimestampsToReturn}.
     * @param nodeIds            the {@link NodeId}s identifying the nodes to read.
     * @param attributeIds       the attribute ids to read, the size and order matching the provided {@link NodeId}s.
     * @return a {@link CompletableFuture} containing a list of {@link DataValue}s, the size and order matching the
     * provided {@link NodeId}s.
     */
    default CompletableFuture<List<DataValue>> read(double maxAge,
                                                    TimestampsToReturn timestampsToReturn,
                                                    List<NodeId> nodeIds,
                                                    List<UInteger> attributeIds) {

        if (nodeIds.size() != attributeIds.size()) {
            CompletableFuture<List<DataValue>> failed = new CompletableFuture<>();
            failed.completeExceptionally(new IllegalArgumentException("nodeIds.size() != attributeIds.size()"));
            return failed;
        } else {
            Stream<ReadValueId> stream = StreamUtils.zip(
                    nodeIds.stream(), attributeIds.stream(),
                    (nId, aId) -> new ReadValueId(nId, aId, null, QualifiedName.NULL_VALUE));

            return read(maxAge, timestampsToReturn, stream.collect(Collectors.toList()))
                    .thenApply(r -> newArrayList(r.getResults()));
        }
    }

    /**
     * This service is used to read the value attribute of one or more Nodes.
     *
     * @param maxAge             the requested max age of the value, in milliseconds. If maxAge is set to 0, the Server
     *                           shall attempt to read a new value from the data source. If maxAge is set to the max
     *                           Int32 value or greater, the Server shall attempt to get a cached value. Negative values
     *                           are invalid for maxAge.
     * @param timestampsToReturn the requested {@link TimestampsToReturn}.
     * @param nodeIds            the {@link NodeId}s identifying the nodes to read.
     * @return a {@link CompletableFuture} containing a list of {@link DataValue}s, the size and order matching the
     * provided {@link NodeId}s.
     */
    default CompletableFuture<List<DataValue>> readValues(double maxAge,
                                                          TimestampsToReturn timestampsToReturn,
                                                          List<NodeId> nodeIds) {

        List<ReadValueId> readValueIds = nodeIds.stream()
                .map(nodeId -> new ReadValueId(nodeId, AttributeId.VALUE.uid(), null, QualifiedName.NULL_VALUE))
                .collect(Collectors.toList());

        return read(maxAge, timestampsToReturn, readValueIds)
                .thenApply(r -> newArrayList(r.getResults()));
    }

    /**
     * This service is used to write values to one or more attributes of one or more nodes.
     *
     * @param writeValues the list of nodes and their attributes to write.
     * @return a {@link CompletableFuture} containing the {@link WriteResponse}.
     */
    CompletableFuture<WriteResponse> write(List<WriteValue> writeValues);

    /**
     * This service is used to write to the value attribute of one or more nodes.
     *
     * @param nodeIds the {@link NodeId}s identifying the nodes to write to.
     * @param values  the {@link DataValue}s to write.
     * @return a {@link CompletableFuture} containing a list of results for the writes.
     */
    default CompletableFuture<List<StatusCode>> writeValues(List<NodeId> nodeIds, List<DataValue> values) {
        if (nodeIds.size() != values.size()) {
            CompletableFuture<List<StatusCode>> failed = new CompletableFuture<>();
            failed.completeExceptionally(new IllegalArgumentException("nodeIds.size() != values.size()"));
            return failed;
        } else {
            Stream<WriteValue> stream = StreamUtils.zip(
                    nodeIds.stream(), values.stream(),
                    (nodeId, value) -> new WriteValue(nodeId, uint(13), null, value));

            return write(stream.collect(Collectors.toList()))
                    .thenApply(response -> newArrayList(response.getResults()));
        }
    }

    /**
     * This Service is used to read historical values or Events of one or more Nodes.
     *
     * @param historyReadDetails        defines the types of history read to be performed.
     * @param timestampsToReturn        specifies the timestamps to be returned for each requested value attribute.
     * @param releaseContinuationPoints if {@code true}, passed continuationPoints shall be reset to free resources in
     *                                  the Server. if {@code false}, passed continuationPoints shall be used to get the
     *                                  next set of historical information.
     * @param nodesToRead               the list of items upon which the historical retrieval is to be performed.
     * @return a {@link CompletableFuture} containing the {@link HistoryReadResponse}.
     */
    CompletableFuture<HistoryReadResponse> historyRead(HistoryReadDetails historyReadDetails,
                                                       TimestampsToReturn timestampsToReturn,
                                                       boolean releaseContinuationPoints,
                                                       List<HistoryReadValueId> nodesToRead);


    /**
     * This Service is used to update historical values or Events of one or more Nodes.
     *
     * @param historyUpdateDetails the details defined for this update.
     * @return a {@link CompletableFuture} containing the {@link HistoryUpdateResponse}.
     */
    CompletableFuture<HistoryUpdateResponse> historyUpdate(List<HistoryUpdateDetails> historyUpdateDetails);

}
