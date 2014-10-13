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

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.structured.CallMethodRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CallMethodResult;

public interface MethodManager {

    /**
     * Invoke one or more methods belonging to this {@link MethodManager}.
     *
     * @param requests The {@link CallMethodRequest}s for the methods to invoke.
     * @param future   The future to complete with the {@link CallMethodResult}s.
     */
    default void call(List<CallMethodRequest> requests, CompletableFuture<List<CallMethodResult>> future) {
        CallMethodResult notSupported = new CallMethodResult(
                new StatusCode(StatusCodes.Bad_NotSupported),
                new StatusCode[0],
                new DiagnosticInfo[0],
                new Variant[0]);

        future.complete(Collections.nCopies(requests.size(), notSupported));
    }

}
