/*
 * Copyright 2015
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

package com.inductiveautomation.opcua.sdk.client.api;

import com.codepoetics.protonpack.StreamUtils;
import com.inductiveautomation.opcua.stack.core.types.builtin.*;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.inductiveautomation.opcua.stack.core.types.structured.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public interface UaClient {

    CompletableFuture<UaClient> connect();

    CompletableFuture<UaClient> disconnect();

    /**
     * Read the nodes and attributes identified by the provided {@link ReadValueId}s.
     *
     * @param readValueIds       the {@link ReadValueId}s identifying the nodes and attributes to read.
     * @param maxAge             the requested max age of the value, in milliseconds. If maxAge is set to 0, the Server
     *                           shall attempt to read a new value from the data source. If maxAge is set to the max
     *                           Int32 value or greater, the Server shall attempt to get a cached value. Negative values
     *                           are invalid for maxAge.
     * @param timestampsToReturn the requested {@link TimestampsToReturn}.
     * @return {@link CompletableFuture} containing a list of {@link DataValue}s, the size and order matching the
     * provided {@link ReadValueId}s.
     */
    CompletableFuture<List<DataValue>> read(List<ReadValueId> readValueIds,
                                            double maxAge, TimestampsToReturn timestampsToReturn);

    /**
     * For each of the nodes identified by the provided {@link NodeId}s, read the attribute identified by the
     * corresponding attribute id.
     *
     * @param nodeIds            the {@link NodeId}s identifying the nodes to read.
     * @param attributeIds       the attribute ids to read, the size and order matching the provided {@link NodeId}s.
     * @param maxAge             the requested max age of the value, in milliseconds. If maxAge is set to 0, the Server
     *                           shall attempt to read a new value from the data source. If maxAge is set to the max
     *                           Int32 value or greater, the Server shall attempt to get a cached value. Negative values
     *                           are invalid for maxAge.
     * @param timestampsToReturn the requested {@link TimestampsToReturn}.
     * @return a {@link CompletableFuture} containing a list of {@link DataValue}s, the size and order matching the
     * provided {@link NodeId}s.
     */
    default CompletableFuture<List<DataValue>> read(List<NodeId> nodeIds, List<UInteger> attributeIds,
                                                    double maxAge, TimestampsToReturn timestampsToReturn) {

        if (nodeIds.size() != attributeIds.size()) {
            CompletableFuture<List<DataValue>> failed = new CompletableFuture<>();
            failed.completeExceptionally(new IllegalArgumentException("nodeIds.size() != attributeIds.size()"));
            return failed;
        } else {
            Stream<ReadValueId> stream = StreamUtils.zip(
                    nodeIds.stream(), attributeIds.stream(),
                    (nId, aId) -> new ReadValueId(nId, aId, null, QualifiedName.NULL_VALUE));

            return read(stream.collect(Collectors.toList()), maxAge, timestampsToReturn);
        }
    }

    /**
     * Read the value attribute of the node identified by each of the provided {@link NodeId}s.
     *
     * @param nodeIds            the {@link NodeId}s identifying the nodes to read.
     * @param maxAge             the requested max age of the value, in milliseconds. If maxAge is set to 0, the Server
     *                           shall attempt to read a new value from the data source. If maxAge is set to the max
     *                           Int32 value or greater, the Server shall attempt to get a cached value. Negative values
     *                           are invalid for maxAge.
     * @param timestampsToReturn the requested {@link TimestampsToReturn}.
     * @return a {@link CompletableFuture} containing a list of {@link DataValue}s, the size and order matching the
     * provided {@link NodeId}s.
     */
    default CompletableFuture<List<DataValue>> readValues(List<NodeId> nodeIds,
                                                          double maxAge, TimestampsToReturn timestampsToReturn) {

        List<ReadValueId> readValueIds = nodeIds.stream()
                .map(nodeId -> new ReadValueId(nodeId, uint(13), null, QualifiedName.NULL_VALUE))
                .collect(Collectors.toList());

        return read(readValueIds, maxAge, timestampsToReturn);
    }

    CompletableFuture<List<StatusCode>> write(List<WriteValue> writeValues);

    default CompletableFuture<List<StatusCode>> writeValues(List<NodeId> nodeIds, List<DataValue> values) {
        if (nodeIds.size() != values.size()) {
            CompletableFuture<List<StatusCode>> failed = new CompletableFuture<>();
            failed.completeExceptionally(new IllegalArgumentException("nodeIds.size() != values.size()"));
            return failed;
        } else {
            Stream<WriteValue> stream = StreamUtils.zip(
                    nodeIds.stream(), values.stream(),
                    (nodeId, value) -> new WriteValue(nodeId, uint(13), null, value));

            return write(stream.collect(Collectors.toList()));
        }
    }

    CompletableFuture<List<BrowseResult>> browse(ViewDescription view,
                                                 UInteger maxReferencesPerNode,
                                                 List<BrowseDescription> nodesToBrowse);

    default CompletableFuture<List<BrowseResult>> browse(List<BrowseDescription> nodesToBrowse) {
        return browse(new ViewDescription(NodeId.NULL_VALUE, DateTime.MIN_VALUE, uint(0)), uint(0), nodesToBrowse);
    }

    CompletableFuture<List<BrowseResult>> browseNext(boolean releaseContinuationPoints,
                                                     List<ByteString> continuationPoints);


}
