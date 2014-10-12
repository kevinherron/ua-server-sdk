package com.inductiveautomation.opcua.sdk.server.services;

import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.application.services.QueryServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.QueryFirstRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.QueryFirstResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.QueryNextRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.QueryNextResponse;

import static com.inductiveautomation.opcua.stack.core.StatusCodes.Bad_ServiceUnsupported;

public class QueryServices implements QueryServiceSet {

    private final ServiceMetric queryFirstMetric = new ServiceMetric();
    private final ServiceMetric queryNextMetric = new ServiceMetric();

    @Override
    public void onQueryFirst(ServiceRequest<QueryFirstRequest, QueryFirstResponse> service) throws UaException {
        queryFirstMetric.record(service);

        service.setServiceFault(Bad_ServiceUnsupported);
    }

    @Override
    public void onQueryNext(ServiceRequest<QueryNextRequest, QueryNextResponse> service) throws UaException {
        queryNextMetric.record(service);

        service.setServiceFault(Bad_ServiceUnsupported);
    }

}
