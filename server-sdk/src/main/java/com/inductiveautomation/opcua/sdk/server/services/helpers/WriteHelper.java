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

package com.inductiveautomation.opcua.sdk.server.services.helpers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.inductiveautomation.opcua.sdk.server.OpcUaServer;
import com.inductiveautomation.opcua.sdk.server.api.Namespace;
import com.inductiveautomation.opcua.sdk.server.services.ServiceAttributes;
import com.inductiveautomation.opcua.sdk.server.util.Pending;
import com.inductiveautomation.opcua.sdk.server.util.PendingWrite;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequest;
import com.inductiveautomation.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.structured.ResponseHeader;
import com.inductiveautomation.opcua.stack.core.types.structured.WriteRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.WriteResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.WriteValue;

import static com.inductiveautomation.opcua.sdk.server.util.ConversionUtil.a;
import static com.inductiveautomation.opcua.sdk.server.util.FutureUtils.sequence;

public class WriteHelper {

    public static void write(ServiceRequest<WriteRequest, WriteResponse> service) {
        WriteRequest request = service.getRequest();

        OpcUaServer server = service.attr(ServiceAttributes.ServerKey).get();

        long maxNodesPerWrite = server.getConfig().getServerCapabilities().getOperationLimits().getMaxNodesPerWrite();

        if (request.getNodesToWrite().length > maxNodesPerWrite) {
            service.setServiceFault(StatusCodes.Bad_TooManyOperations);
            return;
        }

        List<PendingWrite> pendingWrites = Arrays.stream(request.getNodesToWrite())
                .map(PendingWrite::new)
                .collect(Collectors.toList());

        Map<Integer, List<PendingWrite>> byNamespace = pendingWrites.stream()
                .collect(Collectors.groupingBy(pending -> pending.getInput().getNodeId().getNamespaceIndex()));

        byNamespace.keySet().forEach(index -> {
            List<PendingWrite> pending = byNamespace.get(index);

            List<WriteValue> writeValues = pending.stream()
                    .map(PendingWrite::getInput)
                    .collect(Collectors.toList());

            Namespace namespace = server.getNamespaceManager().getNamespace(index);

            CompletableFuture<List<StatusCode>> future = Pending.callback(pending);

            server.getExecutorService().execute(() -> namespace.write(writeValues, future));
        });

        List<CompletableFuture<StatusCode>> futures = pendingWrites.stream()
                .map(PendingWrite::getFuture)
                .collect(Collectors.toList());

        sequence(futures).thenAcceptAsync(values -> {
            ResponseHeader header = service.createResponseHeader();
            WriteResponse response = new WriteResponse(
                    header, a(values, StatusCode.class), new DiagnosticInfo[0]);

            service.setResponse(response);
        }, server.getExecutorService());
    }

}
