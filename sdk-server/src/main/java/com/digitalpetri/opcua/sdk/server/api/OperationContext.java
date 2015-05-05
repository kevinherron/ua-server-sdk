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

package com.digitalpetri.opcua.sdk.server.api;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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
