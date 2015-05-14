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

package com.digitalpetri.opcua.sdk.server.subscriptions;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoredItemCreateResult;

final class PendingItemCreation {

    private final CompletableFuture<MonitoredItemCreateResult> resultFuture = new CompletableFuture<>();

    private final MonitoredItemCreateRequest request;

    public PendingItemCreation(MonitoredItemCreateRequest request) {
        this.request = request;
    }

    public MonitoredItemCreateRequest getRequest() {
        return request;
    }

    public CompletableFuture<MonitoredItemCreateResult> getResultFuture() {
        return resultFuture;
    }

}
