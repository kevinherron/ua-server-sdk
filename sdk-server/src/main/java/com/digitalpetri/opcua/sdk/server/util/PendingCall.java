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

package com.digitalpetri.opcua.sdk.server.util;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.core.types.structured.CallMethodRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CallMethodResult;

public class PendingCall implements Pending<CallMethodRequest, CallMethodResult> {

    private final CompletableFuture<CallMethodResult> future = new CompletableFuture<>();

    private final CallMethodRequest request;

    public PendingCall(CallMethodRequest request) {
        this.request = request;
    }

    @Override
    public CompletableFuture<CallMethodResult> getFuture() {
        return future;
    }

    @Override
    public CallMethodRequest getInput() {
        return request;
    }

}
