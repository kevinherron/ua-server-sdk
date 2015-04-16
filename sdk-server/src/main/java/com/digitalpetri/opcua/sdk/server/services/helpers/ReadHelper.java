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

package com.digitalpetri.opcua.sdk.server.services.helpers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.digitalpetri.opcua.sdk.server.api.Namespace;
import com.digitalpetri.opcua.sdk.server.services.ServiceAttributes;
import com.digitalpetri.opcua.sdk.server.util.Pending;
import com.digitalpetri.opcua.sdk.server.OpcUaServer;
import com.digitalpetri.opcua.sdk.server.util.PendingRead;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.application.services.ServiceRequest;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;
import com.digitalpetri.opcua.stack.core.types.structured.ReadRequest;
import com.digitalpetri.opcua.stack.core.types.structured.ReadResponse;
import com.digitalpetri.opcua.stack.core.types.structured.ReadValueId;
import com.digitalpetri.opcua.stack.core.types.structured.ResponseHeader;

import static com.digitalpetri.opcua.sdk.core.util.ConversionUtil.a;
import static com.digitalpetri.opcua.sdk.server.util.FutureUtils.sequence;

public class ReadHelper {

    public static void read(ServiceRequest<ReadRequest, ReadResponse> service) {
        ReadRequest request = service.getRequest();

        OpcUaServer server = service.attr(ServiceAttributes.ServerKey).get();

        if (request.getNodesToRead().length == 0) {
            service.setServiceFault(StatusCodes.Bad_NothingToDo);
            return;
        }

        if (request.getNodesToRead().length > server.getConfig().getLimits().getMaxNodesPerRead().longValue()) {
            service.setServiceFault(StatusCodes.Bad_TooManyOperations);
            return;
        }

        if (request.getMaxAge() < 0d) {
            service.setServiceFault(StatusCodes.Bad_MaxAgeInvalid);
            return;
        }

        if (request.getTimestampsToReturn() == null) {
            service.setServiceFault(StatusCodes.Bad_TimestampsToReturnInvalid);
            return;
        }

        List<PendingRead> pendingReads = Arrays.stream(request.getNodesToRead())
                .map(PendingRead::new)
                .collect(Collectors.toList());

		/*
         * Group PendingReads by namespace and call read asynchronously for each.
		 */

        Map<UShort, List<PendingRead>> byNamespace = pendingReads.stream()
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
