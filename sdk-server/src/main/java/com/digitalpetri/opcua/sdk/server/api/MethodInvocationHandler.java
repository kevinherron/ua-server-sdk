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

    public static final class NodeIdUnknownHandler implements MethodInvocationHandler {
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

}
