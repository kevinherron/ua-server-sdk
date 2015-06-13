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

import java.util.Map;

import com.digitalpetri.opcua.sdk.server.items.BaseMonitoredItem;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.google.common.collect.Maps;

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
