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

package com.digitalpetri.opcua.sdk.server.services;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.sdk.server.DiagnosticsContext;
import com.digitalpetri.opcua.sdk.server.NamespaceManager;
import com.digitalpetri.opcua.sdk.server.OpcUaServer;
import com.digitalpetri.opcua.sdk.server.Session;
import com.digitalpetri.opcua.sdk.server.api.Namespace;
import com.digitalpetri.opcua.sdk.server.api.ViewManager.BrowseContext;
import com.digitalpetri.opcua.sdk.server.services.helpers.BrowseHelper;
import com.digitalpetri.opcua.sdk.server.services.helpers.BrowsePathsHelper;
import com.digitalpetri.opcua.sdk.server.util.PendingBrowse;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.application.services.ServiceRequest;
import com.digitalpetri.opcua.stack.core.application.services.ViewServiceSet;
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseDescription;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseNextRequest;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseNextResponse;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseRequest;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseResponse;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseResult;
import com.digitalpetri.opcua.stack.core.types.structured.RegisterNodesRequest;
import com.digitalpetri.opcua.stack.core.types.structured.RegisterNodesResponse;
import com.digitalpetri.opcua.stack.core.types.structured.ResponseHeader;
import com.digitalpetri.opcua.stack.core.types.structured.TranslateBrowsePathsToNodeIdsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.TranslateBrowsePathsToNodeIdsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.UnregisterNodesRequest;
import com.digitalpetri.opcua.stack.core.types.structured.UnregisterNodesResponse;

import static com.digitalpetri.opcua.sdk.server.util.FutureUtils.sequence;
import static com.digitalpetri.opcua.stack.core.util.ConversionUtil.a;
import static com.google.common.collect.Lists.newArrayListWithCapacity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class ViewServices implements ViewServiceSet {

    private final ServiceMetric browseCounter = new ServiceMetric();
    private final ServiceMetric browseNextCounter = new ServiceMetric();
    private final ServiceMetric translateBrowsePathsCounter = new ServiceMetric();

    private final BrowseHelper browseHelper = new BrowseHelper();

    @Override
    public void onBrowse(ServiceRequest<BrowseRequest, BrowseResponse> service) {
        browseCounter.record(service);

        BrowseRequest request = service.getRequest();

        DiagnosticsContext<BrowseDescription> diagnosticsContext = new DiagnosticsContext<>();

        OpcUaServer server = service.attr(ServiceAttributes.SERVER_KEY).get();
        Session session = service.attr(ServiceAttributes.SESSION_KEY).get();

        if (request.getNodesToBrowse().length > server.getConfig().getLimits().getMaxNodesPerBrowse().intValue()) {
            service.setServiceFault(StatusCodes.Bad_TooManyOperations);
            return;
        }

        BrowseDescription[] nodesToBrowse = request.getNodesToBrowse();
        List<PendingBrowse> pendingBrowses = newArrayListWithCapacity(nodesToBrowse.length);
        List<CompletableFuture<BrowseResult>> futures = newArrayListWithCapacity(nodesToBrowse.length);

        for (BrowseDescription browseDescription : nodesToBrowse) {
            PendingBrowse pending = new PendingBrowse(browseDescription);

            pendingBrowses.add(pending);
            futures.add(pending.getFuture());
        }

        Map<UShort, List<PendingBrowse>> byNamespace = pendingBrowses.stream()
                .collect(groupingBy(pending -> pending.getInput().getNodeId().getNamespaceIndex()));

        byNamespace.keySet().forEach(index -> {
            List<PendingBrowse> pending = byNamespace.get(index);

            CompletableFuture<List<BrowseResult>> future = new CompletableFuture<>();

            BrowseContext context = new BrowseContext(
                    server, session, future, diagnosticsContext);

            server.getExecutorService().execute(() -> {
                Namespace namespace = server.getNamespaceManager().getNamespace(index);

                List<BrowseDescription> browseDescriptions = pending.stream()
                        .map(PendingBrowse::getInput)
                        .collect(toList());

                namespace.browse(
                        context,
                        request.getView(),
                        request.getRequestedMaxReferencesPerNode(),
                        browseDescriptions);
            });

            future.thenAccept(results -> {
                for (int i = 0; i < results.size(); i++) {
                    pending.get(i).getFuture().complete(results.get(i));
                }
            });
        });

        sequence(futures).thenAcceptAsync(results -> {
            ResponseHeader header = service.createResponseHeader();

            DiagnosticInfo[] diagnosticInfos =
                    diagnosticsContext.getDiagnosticInfos(nodesToBrowse);

            BrowseResponse response = new BrowseResponse(
                    header, a(results, BrowseResult.class), diagnosticInfos);

            service.setResponse(response);
        }, server.getExecutorService());
    }

    @Override
    public void onBrowseNext(ServiceRequest<BrowseNextRequest, BrowseNextResponse> service) {
        browseNextCounter.record(service);

        browseHelper.browseNext(service);
    }

    @Override
    public void onTranslateBrowsePaths(
            ServiceRequest<TranslateBrowsePathsToNodeIdsRequest, TranslateBrowsePathsToNodeIdsResponse> service) {

        translateBrowsePathsCounter.record(service);

        OpcUaServer server = service.attr(ServiceAttributes.SERVER_KEY).get();
        NamespaceManager namespaceManager = server.getNamespaceManager();

        new BrowsePathsHelper(server, namespaceManager).onTranslateBrowsePaths(service);
    }

    @Override
    public void onRegisterNodes(ServiceRequest<RegisterNodesRequest, RegisterNodesResponse> service) throws UaException {
        OpcUaServer server = service.attr(ServiceAttributes.SERVER_KEY).get();

        RegisterNodesRequest request = service.getRequest();

        NodeId[] nodeIds = request.getNodesToRegister();

        if (nodeIds.length == 0) {
            throw new UaException(StatusCodes.Bad_NothingToDo);
        }

        if (nodeIds.length > server.getConfig().getLimits().getMaxNodesPerRegisterNodes().intValue()) {
            throw new UaException(StatusCodes.Bad_TooManyOperations);
        }

        service.setResponse(new RegisterNodesResponse(
                service.createResponseHeader(StatusCode.GOOD),
                nodeIds
        ));
    }

    @Override
    public void onUnregisterNodes(ServiceRequest<UnregisterNodesRequest, UnregisterNodesResponse> service) throws UaException {
        OpcUaServer server = service.attr(ServiceAttributes.SERVER_KEY).get();

        UnregisterNodesRequest request = service.getRequest();

        NodeId[] nodeIds = request.getNodesToUnregister();

        if (nodeIds.length == 0) {
            throw new UaException(StatusCodes.Bad_NothingToDo);
        }

        if (nodeIds.length > server.getConfig().getLimits().getMaxNodesPerRegisterNodes().intValue()) {
            throw new UaException(StatusCodes.Bad_TooManyOperations);
        }

        service.setResponse(new UnregisterNodesResponse(service.createResponseHeader(StatusCode.GOOD)));
    }

}
