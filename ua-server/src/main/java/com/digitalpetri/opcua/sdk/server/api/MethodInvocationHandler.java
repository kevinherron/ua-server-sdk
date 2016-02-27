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

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.structured.CallMethodRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CallMethodResult;

public interface MethodInvocationHandler {

    /**
     * Invoke the given {@link CallMethodRequest} and complete {@code future} when finished.
     * <p>
     * Under no circumstances should the future be completed exceptionally.
     *
     * @param request the {@link CallMethodRequest}.
     * @param future  the {@link CompletableFuture} to complete.
     */
    void invoke(CallMethodRequest request, CompletableFuture<CallMethodResult> future);

    final class NodeIdUnknownHandler implements MethodInvocationHandler {
        @Override
        public void invoke(CallMethodRequest request, CompletableFuture<CallMethodResult> future) {
            CallMethodResult nodeIdUnknown = new CallMethodResult(
                    new StatusCode(StatusCodes.Bad_NodeIdUnknown),
                    new StatusCode[0],
                    new DiagnosticInfo[0],
                    new Variant[0]);

            future.complete(nodeIdUnknown);
        }
    }

    final class NotImplementedHandler implements MethodInvocationHandler {
        @Override
        public void invoke(CallMethodRequest request, CompletableFuture<CallMethodResult> future) {
            CallMethodResult nodeIdUnknown = new CallMethodResult(
                    new StatusCode(StatusCodes.Bad_NotImplemented),
                    new StatusCode[0],
                    new DiagnosticInfo[0],
                    new Variant[0]);

            future.complete(nodeIdUnknown);
        }
    }

}
