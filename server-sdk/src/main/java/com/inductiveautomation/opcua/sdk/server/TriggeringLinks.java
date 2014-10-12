package com.inductiveautomation.opcua.sdk.server;

import java.util.Map;

import com.inductiveautomation.opcua.sdk.server.items.BaseMonitoredItem;
import com.google.common.collect.Maps;

public class TriggeringLinks {

    private final Map<Long, BaseMonitoredItem<?>> triggeredItems = Maps.newConcurrentMap();

    private final BaseMonitoredItem<?> triggeringItem;

    public TriggeringLinks(BaseMonitoredItem<?> triggeringItem) {
        this.triggeringItem = triggeringItem;
    }

    public BaseMonitoredItem<?> getTriggeringItem() {
        return triggeringItem;
    }

    public Map<Long, BaseMonitoredItem<?>> getTriggeredItems() {
        return triggeredItems;
    }

    public boolean isEmpty() {
        return triggeredItems.isEmpty();
    }

}
