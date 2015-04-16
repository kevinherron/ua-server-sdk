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

package com.digitalpetri.opcua.sdk.server.services;

import com.digitalpetri.opcua.sdk.server.NamespaceManager;
import com.digitalpetri.opcua.sdk.server.OpcUaServer;
import com.digitalpetri.opcua.sdk.server.services.helpers.BrowseHelper;
import com.digitalpetri.opcua.sdk.server.services.helpers.TranslateBrowsePathsHelper;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.application.services.ServiceRequest;
import com.digitalpetri.opcua.stack.core.application.services.ViewServiceSet;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseNextRequest;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseNextResponse;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseRequest;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseResponse;
import com.digitalpetri.opcua.stack.core.types.structured.RegisterNodesRequest;
import com.digitalpetri.opcua.stack.core.types.structured.RegisterNodesResponse;
import com.digitalpetri.opcua.stack.core.types.structured.TranslateBrowsePathsToNodeIdsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.TranslateBrowsePathsToNodeIdsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.UnregisterNodesRequest;
import com.digitalpetri.opcua.stack.core.types.structured.UnregisterNodesResponse;

public class ViewServices implements ViewServiceSet {

    private final ServiceMetric browseCounter = new ServiceMetric();
    private final ServiceMetric browseNextCounter = new ServiceMetric();
    private final ServiceMetric translateBrowsePathsCounter = new ServiceMetric();

    private final BrowseHelper browseHelper = new BrowseHelper();

    @Override
    public void onBrowse(ServiceRequest<BrowseRequest, BrowseResponse> service) {
        browseCounter.record(service);

        browseHelper.browse(service);
    }

    @Override
    public void onBrowseNext(ServiceRequest<BrowseNextRequest, BrowseNextResponse> service) {
        browseNextCounter.record(service);

        browseHelper.browseNext(service);
    }

    @Override
    public void onTranslateBrowsePaths(ServiceRequest<TranslateBrowsePathsToNodeIdsRequest, TranslateBrowsePathsToNodeIdsResponse> service) {
        translateBrowsePathsCounter.record(service);

        NamespaceManager namespaceManager = service.attr(ServiceAttributes.ServerKey).get().getNamespaceManager();

        new TranslateBrowsePathsHelper(namespaceManager).onTranslateBrowsePaths(service);
    }

    @Override
    public void onRegisterNodes(ServiceRequest<RegisterNodesRequest, RegisterNodesResponse> service) throws UaException {
        OpcUaServer server = service.attr(ServiceAttributes.ServerKey).get();

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
        OpcUaServer server = service.attr(ServiceAttributes.ServerKey).get();

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
