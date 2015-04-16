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

package com.digitalpetri.opcua.sdk.server.subscriptions;

import java.util.Map;

import com.digitalpetri.opcua.sdk.server.items.BaseMonitoredItem;
import com.google.common.collect.Maps;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

public class TriggeringLinks {

    private final Map<UInteger, BaseMonitoredItem<?>> triggeredItems = Maps.newConcurrentMap();

    private final BaseMonitoredItem<?> triggeringItem;

    public TriggeringLinks(BaseMonitoredItem<?> triggeringItem) {
        this.triggeringItem = triggeringItem;
    }

    public BaseMonitoredItem<?> getTriggeringItem() {
        return triggeringItem;
    }

    public Map<UInteger, BaseMonitoredItem<?>> getTriggeredItems() {
        return triggeredItems;
    }

    public boolean isEmpty() {
        return triggeredItems.isEmpty();
    }

}
