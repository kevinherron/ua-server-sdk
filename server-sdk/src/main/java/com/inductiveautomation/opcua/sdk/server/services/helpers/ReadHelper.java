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
import com.inductiveautomation.opcua.sdk.server.util.PendingRead;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequest;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadValueId;
import com.inductiveautomation.opcua.stack.core.types.structured.ResponseHeader;

import static com.inductiveautomation.opcua.sdk.server.util.ConversionUtil.a;
import static com.inductiveautomation.opcua.sdk.server.util.FutureUtils.sequence;

public class ReadHelper {

    public static void read(ServiceRequest<ReadRequest, ReadResponse> service) {
        ReadRequest request = service.getRequest();

        OpcUaServer server = service.attr(ServiceAttributes.ServerKey).get();

        long maxNodesPerRead = server.getConfig().getServerCapabilities().getOperationLimits().getMaxNodesPerRead();

        if (request.getNodesToRead().length > maxNodesPerRead) {
            service.setServiceFault(StatusCodes.Bad_TooManyOperations);
            return;
        }

        List<PendingRead> pendingReads = Arrays.stream(request.getNodesToRead())
                .map(PendingRead::new)
                .collect(Collectors.toList());

		/*
         * Group PendingReads by namespace and call read asynchronously for each.
		 */

        Map<Integer, List<PendingRead>> byNamespace = pendingReads.stream()
                .collect(Collectors.groupingBy(pending -> pending.getInput().getNodeId().getNamespaceIndex()));

        byNamespace.keySet().forEach(index -> {
            List<PendingRead> pending = byNamespace.get(index);

            List<ReadValueId> readValueIds = pending.stream()
                    .map(PendingRead::getInput)
                    .collect(Collectors.toList());

            Namespace namespace = server.getNamespaceManager().getNamespace(index);

            CompletableFuture<List<DataValue>> future = Pending.callback(pending);

            server.getExecutorService().execute(
                    () -> namespace.read(readValueIds, request.getMaxAge(), request.getTimestampsToReturn(), future));
        });

		/*
		 * When all PendingReads have been completed send a ReadResponse with the values.
		 */

        List<CompletableFuture<DataValue>> futures = pendingReads.stream()
                .map(PendingRead::getFuture)
                .collect(Collectors.toList());

        sequence(futures).thenAcceptAsync(values -> {
            ResponseHeader header = service.createResponseHeader();

            ReadResponse response = new ReadResponse(
                    header, a(values, DataValue.class), new DiagnosticInfo[0]);

            service.setResponse(response);
        }, server.getExecutorService());
    }

}
