package com.inductiveautomation.opcua.sdk.server.services;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.inductiveautomation.opcua.sdk.server.OpcUaServer;
import com.inductiveautomation.opcua.sdk.server.api.Namespace;
import com.inductiveautomation.opcua.sdk.server.util.Pending;
import com.inductiveautomation.opcua.sdk.server.util.PendingCall;
import com.inductiveautomation.opcua.stack.core.application.services.MethodServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequest;
import com.inductiveautomation.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.inductiveautomation.opcua.stack.core.types.structured.CallMethodRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CallMethodResult;
import com.inductiveautomation.opcua.stack.core.types.structured.CallRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CallResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.ResponseHeader;

import static com.inductiveautomation.opcua.sdk.server.util.ConversionUtil.a;
import static com.inductiveautomation.opcua.sdk.server.util.FutureUtils.sequence;

public class MethodServices implements MethodServiceSet {

    private final ServiceMetric callCounter = new ServiceMetric();

    @Override
    public void onCall(ServiceRequest<CallRequest, CallResponse> service) {
        callCounter.record(service);

        OpcUaServer server = service.attr(ServiceAttributes.ServerKey).get();

        CallRequest request = service.getRequest();

        List<PendingCall> pendingCalls = Arrays.stream(request.getMethodsToCall())
                .map(PendingCall::new)
                .collect(Collectors.toList());

        /*
         * Group by namespace and call asynchronously for each.
         */

        Map<Integer, List<PendingCall>> byNamespace = pendingCalls.stream()
                .collect(Collectors.groupingBy(pending -> pending.getInput().getMethodId().getNamespaceIndex()));

        byNamespace.keySet().forEach(index -> {
            List<PendingCall> pending = byNamespace.get(index);

            List<CallMethodRequest> requests = pending.stream()
                    .map(PendingCall::getInput)
                    .collect(Collectors.toList());

            Namespace namespace = server.getNamespaceManager().getNamespace(index);

            CompletableFuture<List<CallMethodResult>> future = Pending.callback(pending);

            server.getExecutorService().execute(() -> namespace.call(requests, future));
        });

        /*
         * When all PendingCalls have been completed send a CallResponse with the values.
		 */

        List<CompletableFuture<CallMethodResult>> futures = pendingCalls.stream()
                .map(PendingCall::getFuture)
                .collect(Collectors.toList());

        sequence(futures).thenAcceptAsync(values -> {
            ResponseHeader header = service.createResponseHeader();
            CallResponse response = new CallResponse(
                    header, a(values, CallMethodResult.class), new DiagnosticInfo[0]);

            service.setResponse(response);
        }, server.getExecutorService());
    }

}
