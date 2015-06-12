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

package com.digitalpetri.opcua.sdk.client.methods;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.sdk.client.api.UaClient;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.structured.CallMethodRequest;

public abstract class AbstractUaMethod {

    private final UaClient client;
    private final NodeId objectId;
    private final NodeId methodId;

    public AbstractUaMethod(UaClient client, NodeId objectId, NodeId methodId) {
        this.client = client;
        this.objectId = objectId;
        this.methodId = methodId;
    }

    public CompletableFuture<Variant[]> invoke(Variant[] inputArguments) {
        CallMethodRequest request = new CallMethodRequest(
                objectId, methodId, inputArguments);

        return client.call(request).thenCompose(result -> {
            StatusCode statusCode = result.getStatusCode();

            if (statusCode.isGood()) {
                Variant[] outputArguments = result.getOutputArguments();

                return CompletableFuture.completedFuture(outputArguments);
            } else {
                UaMethodException ex = new UaMethodException(
                        statusCode,
                        result.getInputArgumentResults(),
                        result.getInputArgumentDiagnosticInfos()
                );

                CompletableFuture<Variant[]> f = new CompletableFuture<>();
                f.completeExceptionally(ex);
                return f;
            }
        });
    }

}
