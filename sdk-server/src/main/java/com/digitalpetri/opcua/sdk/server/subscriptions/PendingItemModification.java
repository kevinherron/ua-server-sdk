/*
 * Copyright 2015
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

package com.digitalpetri.opcua.sdk.server.subscriptions;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.core.types.structured.MonitoredItemModifyRequest;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoredItemModifyResult;

final class PendingItemModification {

    private final CompletableFuture<Double> createFuture = new CompletableFuture<>();
    private final CompletableFuture<MonitoredItemModifyResult> resultFuture = new CompletableFuture<>();

    private final MonitoredItemModifyRequest request;

    public PendingItemModification(MonitoredItemModifyRequest request) {
        this.request = request;
    }

    public MonitoredItemModifyRequest getRequest() {
        return request;
    }

    public CompletableFuture<Double> getCreateFuture() {
        return createFuture;
    }

    public CompletableFuture<MonitoredItemModifyResult> getResultFuture() {
        return resultFuture;
    }

}
