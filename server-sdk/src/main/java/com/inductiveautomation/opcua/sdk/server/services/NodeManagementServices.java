/*
 * Copyright 2014 Inductive Automation
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

package com.inductiveautomation.opcua.sdk.server.services;

import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.application.services.NodeManagementServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.AddNodesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.AddNodesResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.AddReferencesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.AddReferencesResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteNodesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteNodesResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteReferencesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteReferencesResponse;

import static com.inductiveautomation.opcua.stack.core.StatusCodes.Bad_ServiceUnsupported;

public class NodeManagementServices implements NodeManagementServiceSet {

    private final ServiceMetric addNodesMetric = new ServiceMetric();
    private final ServiceMetric deleteNodesMetric = new ServiceMetric();
    private final ServiceMetric addReferencesMetric = new ServiceMetric();
    private final ServiceMetric deleteReferencesMetric = new ServiceMetric();

    @Override
    public void onAddNodes(ServiceRequest<AddNodesRequest, AddNodesResponse> service) throws UaException {
        addNodesMetric.record(service);

        service.setServiceFault(Bad_ServiceUnsupported);
    }

    @Override
    public void onDeleteNodes(ServiceRequest<DeleteNodesRequest, DeleteNodesResponse> service) throws UaException {
        deleteNodesMetric.record(service);

        service.setServiceFault(Bad_ServiceUnsupported);
    }

    @Override
    public void onAddReferences(ServiceRequest<AddReferencesRequest, AddReferencesResponse> service) throws UaException {
        addReferencesMetric.record(service);

        service.setServiceFault(Bad_ServiceUnsupported);
    }

    @Override
    public void onDeleteReferences(ServiceRequest<DeleteReferencesRequest, DeleteReferencesResponse> service) throws UaException {
        deleteReferencesMetric.record(service);

        service.setServiceFault(Bad_ServiceUnsupported);
    }

}
