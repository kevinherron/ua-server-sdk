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
