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

package com.inductiveautomation.opcua.server.ctt;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import com.inductiveautomation.opcua.sdk.server.OpcUaServer;
import com.inductiveautomation.opcua.sdk.server.api.OpcUaServerConfig;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequest;
import com.inductiveautomation.opcua.stack.core.application.services.TestServiceSet;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.structured.RequestHeader;
import com.inductiveautomation.opcua.stack.core.types.structured.ResponseHeader;
import com.inductiveautomation.opcua.stack.core.types.structured.TestStackExRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.TestStackExResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.TestStackRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.TestStackResponse;

public class CttServer {

    public static void main(String[] args) throws Exception {
        CttServer server = new CttServer(new CttServerConfig());

        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));

        server.startup();
        server.shutdownFuture().get();
    }

    private final CompletableFuture<Void> shutdownFuture = new CompletableFuture<>();

    private final OpcUaServer server;

    public CttServer(OpcUaServerConfig config) {
        server = new OpcUaServer(config);

        server.getNamespaceManager().getNamespaceTable().putUri(
                CttNamespace.NamespaceUri,
                CttNamespace.NamespaceIndex
        );

        server.getNamespaceManager().addNamespace(new CttNamespace(server));

        server.getServer().addServiceSet(new TestServiceSet() {
            @Override
            public void onTestStack(ServiceRequest<TestStackRequest, TestStackResponse> serviceRequest) throws UaException {
                TestStackRequest request = serviceRequest.getRequest();

                serviceRequest.setResponse(new TestStackResponse(header(request.getRequestHeader()), request.getInput()));
            }

            @Override
            public void onTestStackEx(ServiceRequest<TestStackExRequest, TestStackExResponse> serviceRequest) throws UaException {
                TestStackExRequest request = serviceRequest.getRequest();

                serviceRequest.setResponse(new TestStackExResponse(header(request.getRequestHeader()), request.getInput()));
            }

            private ResponseHeader header(RequestHeader header) {
                return new ResponseHeader(
                        DateTime.now(),
                        header.getRequestHandle(),
                        StatusCode.Good,
                        null, new String[0], null);
            }
        });
    }

    public void startup() throws UaException {
        server.startup();
    }

    public void shutdown() {
        server.shutdown();

        shutdownFuture.complete(null);
    }

    public Future<Void> shutdownFuture() {
        return shutdownFuture;
    }

}
