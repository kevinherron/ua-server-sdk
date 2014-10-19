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

package com.inductiveautomation.opcua.sdk.server.api;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.google.common.collect.Lists;
import com.inductiveautomation.opcua.sdk.server.api.MethodInvocationHandler.NodeIdUnknownHandler;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.structured.CallMethodRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CallMethodResult;

import static com.inductiveautomation.opcua.sdk.server.util.FutureUtils.sequence;

public interface MethodManager {

    /**
     * Invoke one or more methods belonging to this {@link MethodManager}.
     *
     * @param requests The {@link CallMethodRequest}s for the methods to invoke.
     * @param future   The future to complete with the {@link CallMethodResult}s.
     */
    default void call(List<CallMethodRequest> requests, CompletableFuture<List<CallMethodResult>> future) {
        List<CompletableFuture<CallMethodResult>> results = Lists.newArrayListWithCapacity(requests.size());

        for (CallMethodRequest request : requests) {
            MethodInvocationHandler handler = getInvocationHandler(request.getMethodId())
                    .orElse(new NodeIdUnknownHandler());

            CompletableFuture<CallMethodResult> result = new CompletableFuture<>();

            handler.invoke(request, result);

            results.add(result);
        }

        sequence(results).thenAccept(future::complete);
    }

    /**
     * Get the {@link MethodInvocationHandler} for the method identified by {@code methodId}, if it exists.
     *
     * @param methodId the {@link NodeId} identifying the method.
     * @return the {@link MethodInvocationHandler} for {@code methodId}, if it exists.
     */
    default Optional<MethodInvocationHandler> getInvocationHandler(NodeId methodId) {
        return Optional.empty();
    }

}
