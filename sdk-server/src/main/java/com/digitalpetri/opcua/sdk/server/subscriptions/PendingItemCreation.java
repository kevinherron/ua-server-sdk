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

import com.digitalpetri.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoredItemCreateResult;

final class PendingItemCreation {

    private final CompletableFuture<Double> createFuture = new CompletableFuture<>();
    private final CompletableFuture<MonitoredItemCreateResult> resultFuture = new CompletableFuture<>();

    private final MonitoredItemCreateRequest request;

    public PendingItemCreation(MonitoredItemCreateRequest request) {
        this.request = request;
    }

    public MonitoredItemCreateRequest getRequest() {
        return request;
    }

    public CompletableFuture<Double> getCreateFuture() {
        return createFuture;
    }

    public CompletableFuture<MonitoredItemCreateResult> getResultFuture() {
        return resultFuture;
    }

}
