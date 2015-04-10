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

package com.inductiveautomation.opcua.sdk.client.api;

import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public interface UaSubscription {

    UInteger getSubscriptionId();
    double getRequestedPublishInterval();
    UInteger getRequestedLifetimeCount();
    UInteger getRequestedKeepAliveCount();
    UInteger getMaxNotificationsPerPublish();
    boolean isPublishingEnabled();
    UByte getPriority();

    CompletableFuture<List<StatusCode>> createItems(List<UaMonitoredItem> items);
    CompletableFuture<List<StatusCode>> createItems(Supplier<List<UaMonitoredItem>> items);

    CompletableFuture<List<StatusCode>> deleteItems(List<UaMonitoredItem> items);
    CompletableFuture<List<StatusCode>> deleteItems(Supplier<List<UaMonitoredItem>> items);

    interface CreateItemsContext {
        void addItem(UaMonitoredItem item);
    }

    interface ModifyItemsContext {
        void addItem(UaMonitoredItem item);
    }

    interface DeleteItemsContext {
        void addItem(UaMonitoredItem item);
    }

}
