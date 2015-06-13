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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.digitalpetri.opcua.sdk.server.DiagnosticsContext;
import com.digitalpetri.opcua.sdk.server.OpcUaServer;
import com.digitalpetri.opcua.sdk.server.Session;
import com.digitalpetri.opcua.sdk.server.api.MethodServices.CallContext;
import com.digitalpetri.opcua.sdk.server.api.Namespace;
import com.digitalpetri.opcua.sdk.server.util.PendingCall;
import com.digitalpetri.opcua.stack.core.application.services.MethodServiceSet;
import com.digitalpetri.opcua.stack.core.application.services.ServiceRequest;
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;
import com.digitalpetri.opcua.stack.core.types.structured.CallMethodRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CallMethodResult;
import com.digitalpetri.opcua.stack.core.types.structured.CallRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CallResponse;
import com.digitalpetri.opcua.stack.core.types.structured.ResponseHeader;

import static com.digitalpetri.opcua.stack.core.util.ConversionUtil.a;
import static com.digitalpetri.opcua.sdk.server.util.FutureUtils.sequence;

public class MethodServices implements MethodServiceSet {

    private final ServiceMetric callCounter = new ServiceMetric();

    @Override
    public void onCall(ServiceRequest<CallRequest, CallResponse> service) {
        callCounter.record(service);

        DiagnosticsContext<CallMethodRequest> diagnosticsContext = new DiagnosticsContext<>();

        OpcUaServer server = service.attr(ServiceAttributes.SERVER_KEY).get();
        Session session = service.attr(ServiceAttributes.SESSION_KEY).get();

        CallRequest request = service.getRequest();

        List<PendingCall> pendingCalls = Arrays.stream(request.getMethodsToCall())
                .map(PendingCall::new)
                .collect(Collectors.toList());

        /*
         * Group by namespace and call asynchronously for each.
         */

        Map<UShort, List<PendingCall>> byNamespace = pendingCalls.stream()
                .collect(Collectors.groupingBy(pending -> pending.getInput().getMethodId().getNamespaceIndex()));

        byNamespace.keySet().forEach(index -> {
            List<PendingCall> pending = byNamespace.get(index);

            List<CallMethodRequest> requests = pending.stream()
                    .map(PendingCall::getInput)
                    .collect(Collectors.toList());

            Namespace namespace = server.getNamespaceManager().getNamespace(index);

            CallContext context = new CallContext(server, session, diagnosticsContext);

            server.getExecutorService().execute(() -> namespace.call(context, requests));

            context.getFuture().thenAccept(values -> {
                for (int i = 0; i < values.size(); i++) {
                    pending.get(i).getFuture().complete(values.get(i));
                }
            });
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
