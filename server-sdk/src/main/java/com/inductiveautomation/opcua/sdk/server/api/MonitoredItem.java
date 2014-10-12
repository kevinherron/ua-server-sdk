package com.inductiveautomation.opcua.sdk.server.api;

import com.inductiveautomation.opcua.stack.core.types.structured.ReadValueId;

public interface MonitoredItem {

    /**
     * @return the server-side id of this {@link MonitoredDataItem}.
     */
    Long getId();

    /**
     * @return the {@link ReadValueId} being monitored.
     */
    ReadValueId getReadValueId();

}
