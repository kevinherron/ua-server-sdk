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

import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.structured.SubscriptionAcknowledgement;

public class PendingAck implements Pending<SubscriptionAcknowledgement, StatusCode> {

    private final CompletableFuture<StatusCode> future = new CompletableFuture<>();

    private final SubscriptionAcknowledgement input;

    public PendingAck(SubscriptionAcknowledgement input) {
        this.input = input;
    }

    @Override
    public CompletableFuture<StatusCode> getFuture() {
        return future;
    }

    @Override
    public SubscriptionAcknowledgement getInput() {
        return input;
    }

}
