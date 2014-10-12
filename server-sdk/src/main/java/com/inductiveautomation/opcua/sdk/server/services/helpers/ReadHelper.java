/*
 * OPC-UA SDK
 *
 * Copyright (C) 2014 Kevin Herron
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
