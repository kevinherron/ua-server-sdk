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
