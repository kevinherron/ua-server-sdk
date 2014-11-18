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

        server.getNamespaceManager().registerAndAdd(
                CttNamespace.NAMESPACE_URI,
                namespaceIndex -> new CttNamespace(server, namespaceIndex)
        );
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
