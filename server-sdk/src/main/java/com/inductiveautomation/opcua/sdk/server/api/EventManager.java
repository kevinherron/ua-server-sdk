package com.inductiveautomation.opcua.sdk.server.api;

import java.util.List;

public interface EventManager {

    /**
     * {@link MonitoredEventItem} have been created for nodes belonging to this {#link EventManager}.
     *
     * @param eventItems the {@link MonitoredEventItem}s that were created.
     */
    void onEventItemsCreated(List<MonitoredEventItem> eventItems);

    void onEventItemsModified(List<MonitoredEventItem> eventItems);

    void onEventItemsDeleted(List<MonitoredEventItem> eventItems);

    void onMonitoringModeChanged(List<MonitoredEventItem> eventItems);

}
