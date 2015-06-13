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

import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.application.services.NodeManagementServiceSet;
import com.digitalpetri.opcua.stack.core.application.services.ServiceRequest;
import com.digitalpetri.opcua.stack.core.types.structured.AddNodesRequest;
import com.digitalpetri.opcua.stack.core.types.structured.AddNodesResponse;
import com.digitalpetri.opcua.stack.core.types.structured.AddReferencesRequest;
import com.digitalpetri.opcua.stack.core.types.structured.AddReferencesResponse;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteNodesRequest;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteNodesResponse;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteReferencesRequest;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteReferencesResponse;

import static com.digitalpetri.opcua.stack.core.StatusCodes.Bad_ServiceUnsupported;

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
