package com.inductiveautomation.opcua.sdk.server.api;

import com.inductiveautomation.opcua.sdk.server.api.events.Event;

public interface EventItem extends MonitoredItem {

    void setEvent(Event event);

}
