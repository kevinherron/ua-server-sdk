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

package com.digitalpetri.opcua.sdk.server.api;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;

import com.digitalpetri.opcua.sdk.server.DiagnosticsContext;
import com.digitalpetri.opcua.sdk.server.OpcUaServer;
import com.digitalpetri.opcua.sdk.server.Session;

public class OperationContext<T, U> {

    private final CompletableFuture<List<U>> future = new CompletableFuture<>();

    private final OpcUaServer server;
    private final Session session;
    private final DiagnosticsContext<T> diagnostics;

    public OperationContext(OpcUaServer server,
                            @Nullable Session session,
                            DiagnosticsContext<T> diagnosticsContext) {

        this.server = server;
        this.session = session;
        this.diagnostics = diagnosticsContext;
    }

    public CompletableFuture<List<U>> getFuture() {
        return future;
    }

    public DiagnosticsContext<T> getDiagnostics() {
        return diagnostics;
    }

    public OpcUaServer getServer() {
        return server;
    }

    public Optional<Session> getSession() {
        return Optional.ofNullable(session);
    }

}
