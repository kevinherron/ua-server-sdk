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

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.codepoetics.protonpack.StreamUtils;
import com.google.common.collect.Lists;
import com.inductiveautomation.opcua.sdk.client.OpcUaClientConfig;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.MonitoringMode;
import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseResult;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateMonitoredItemsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSubscriptionResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteMonitoredItemsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteSubscriptionsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.EndpointDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.ModifyMonitoredItemsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.ModifySubscriptionResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoredItemModifyRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadValueId;
import com.inductiveautomation.opcua.stack.core.types.structured.SetMonitoringModeResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.SetPublishingModeResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.SetTriggeringResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.SignatureData;
import com.inductiveautomation.opcua.stack.core.types.structured.UserIdentityToken;
import com.inductiveautomation.opcua.stack.core.types.structured.ViewDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.WriteValue;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public interface UaClient {

    OpcUaClientConfig getConfig();

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
    CompletableFuture<ReadResponse> read(List<ReadValueId> readValueIds,
                                         double maxAge,
                                         TimestampsToReturn timestampsToReturn);

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
    default CompletableFuture<List<DataValue>> read(List<NodeId> nodeIds,
                                                    List<UInteger> attributeIds,
                                                    double maxAge,
                                                    TimestampsToReturn timestampsToReturn) {

        if (nodeIds.size() != attributeIds.size()) {
            CompletableFuture<List<DataValue>> failed = new CompletableFuture<>();
            failed.completeExceptionally(new IllegalArgumentException("nodeIds.size() != attributeIds.size()"));
            return failed;
        } else {
            Stream<ReadValueId> stream = StreamUtils.zip(
                    nodeIds.stream(), attributeIds.stream(),
                    (nId, aId) -> new ReadValueId(nId, aId, null, QualifiedName.NULL_VALUE));

            return read(stream.collect(Collectors.toList()), maxAge, timestampsToReturn)
                    .thenApply(r -> Lists.newArrayList(r.getResults()));
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
                                                          double maxAge,
                                                          TimestampsToReturn timestampsToReturn) {

        List<ReadValueId> readValueIds = nodeIds.stream()
                .map(nodeId -> new ReadValueId(nodeId, uint(13), null, QualifiedName.NULL_VALUE))
                .collect(Collectors.toList());

        return read(readValueIds, maxAge, timestampsToReturn)
                .thenApply(r -> Lists.newArrayList(r.getResults()));
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

    default CompletableFuture<BrowseResult> browse(ViewDescription view,
                                                   UInteger maxReferencesPerNode,
                                                   BrowseDescription browseDescription) {

        return browse(view, maxReferencesPerNode, Lists.newArrayList(browseDescription))
                .thenApply(results -> results.get(0));
    }


    CompletableFuture<List<BrowseResult>> browseNext(boolean releaseContinuationPoints,
                                                     List<ByteString> continuationPoints);

    default CompletableFuture<BrowseResult> browseNext(boolean releaseContinuationPoint,
                                                       ByteString continuationPoint) {

        return browseNext(releaseContinuationPoint, Lists.newArrayList(continuationPoint))
                .thenApply(results -> results.get(0));
    }


    CompletableFuture<CreateSubscriptionResponse> createSubscription(double requestedPublishingInterval,
                                                                     UInteger requestedLifetimeCount,
                                                                     UInteger requestedMaxKeepAliveCount,
                                                                     UInteger maxNotificationsPerPublish,
                                                                     boolean publishingEnabled,
                                                                     UByte priority);

    CompletableFuture<ModifySubscriptionResponse> modifySubscription(UInteger subscriptionId,
                                                                     double requestedPublishingInterval,
                                                                     UInteger requestedLifetimeCount,
                                                                     UInteger requestedMaxKeepAliveCount,
                                                                     UInteger maxNotificationsPerPublish,
                                                                     UByte priority);


    CompletableFuture<DeleteSubscriptionsResponse> deleteSubscriptions(List<UInteger> subscriptionIds);

    CompletableFuture<SetPublishingModeResponse> setPublishingMode(boolean publishingEnabled,
                                                                   List<UInteger> subscriptionIds);

    CompletableFuture<CreateMonitoredItemsResponse> createMonitoredItems(UInteger subscriptionId,
                                                                         TimestampsToReturn timestampsToReturn,
                                                                         List<MonitoredItemCreateRequest> itemsToCreate);

    CompletableFuture<ModifyMonitoredItemsResponse> modifyMonitoredItems(UInteger subscriptionId,
                                                                         TimestampsToReturn timestampsToReturn,
                                                                         List<MonitoredItemModifyRequest> itemsToModify);

    CompletableFuture<DeleteMonitoredItemsResponse> deleteMonitoredItems(UInteger subscriptionId,
                                                                         List<UInteger> monitoredItemIds);

    CompletableFuture<SetMonitoringModeResponse> setMonitoringMode(UInteger subscriptionId,
                                                                   MonitoringMode monitoringMode,
                                                                   List<UInteger> monitoredItemIds);

    CompletableFuture<SetTriggeringResponse> setTriggering(UInteger subscriptionId,
                                                           UInteger triggeringItemId,
                                                           List<UInteger> linksToAdd,
                                                           List<UInteger> linksToRemove);


    interface IdentityTokenProvider {

        /**
         * Return the {@link UserIdentityToken} and {@link SignatureData} (if applicable for the token) to use when
         * activating a session.
         *
         * @param endpoint the {@link EndpointDescription} being connected to.
         * @return an Object[] containing the {@link UserIdentityToken} at index 0 and the {@link SignatureData} at index 1.
         */
        Object[] getIdentityToken(EndpointDescription endpoint) throws Exception;

    }

}
