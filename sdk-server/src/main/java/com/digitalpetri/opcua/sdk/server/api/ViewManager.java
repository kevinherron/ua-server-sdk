/*
 * Copyright 2014
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

package com.digitalpetri.opcua.sdk.server.api;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.sdk.core.Reference;
import com.digitalpetri.opcua.sdk.server.DiagnosticsContext;
import com.digitalpetri.opcua.sdk.server.OpcUaServer;
import com.digitalpetri.opcua.sdk.server.Session;
import com.digitalpetri.opcua.sdk.server.services.helpers.BrowseHelper;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseDescription;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseResult;
import com.digitalpetri.opcua.stack.core.types.structured.ViewDescription;

import static com.digitalpetri.opcua.sdk.server.util.FutureUtils.sequence;
import static java.util.stream.Collectors.toList;

public interface ViewManager {

    default void browse(ViewDescription view,
                        UInteger maxReferencesPerNode,
                        List<BrowseDescription> nodesToBrowse,
                        BrowseContext context) {

        OpcUaServer server = context.getServer();

        List<CompletableFuture<BrowseResult>> futures = nodesToBrowse.stream()
                .map(browseDescription -> BrowseHelper.browse(server, view, maxReferencesPerNode, browseDescription))
                .collect(toList());

        sequence(futures).thenApply(results -> context.getFuture().complete(results));
    }

    /**
     * If the node identified by {@code nodeId} exists return all {@link Reference}s.
     *
     * @param nodeId the {@link NodeId} identifying the node.
     * @return a {@link CompletableFuture} containing the {@link Reference}s. If the node is unknown, complete the
     * future exceptionally.
     */
    CompletableFuture<List<Reference>> getReferences(NodeId nodeId);


    final class BrowseContext extends OperationContext<BrowseDescription, BrowseResult> {
        public BrowseContext(OpcUaServer server, Session session,
                             DiagnosticsContext<BrowseDescription> diagnosticsContext) {

            super(server, session, diagnosticsContext);
        }
    }

}
