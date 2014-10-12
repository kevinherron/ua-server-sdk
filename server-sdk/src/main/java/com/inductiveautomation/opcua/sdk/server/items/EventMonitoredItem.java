package com.inductiveautomation.opcua.sdk.server.items;

import com.inductiveautomation.opcua.sdk.server.api.EventItem;
import com.inductiveautomation.opcua.sdk.server.api.events.Event;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExtensionObject;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.enumerated.MonitoringMode;
import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.inductiveautomation.opcua.stack.core.types.structured.EventFieldList;
import com.inductiveautomation.opcua.stack.core.types.structured.EventFilter;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoringParameters;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadValueId;

public class EventMonitoredItem extends BaseMonitoredItem<Variant[]> implements EventItem {

    private volatile EventFilter filter;

    public EventMonitoredItem(long id,
                              ReadValueId readValueId,
                              MonitoringMode monitoringMode,
                              TimestampsToReturn timestamps,
                              MonitoringParameters parameters,
                              EventFilter filter) {

        super(id, readValueId, monitoringMode, timestamps, parameters);

        this.filter = filter;
    }

    @Override
    public void setEvent(Event event) {

    }

    @Override
    public ExtensionObject getFilterResult() {
        return null;
    }

    @Override
    protected void installFilter(ExtensionObject filterXo) throws UaException {

    }

    @Override
    protected EventFieldList wrapQueueValue(Variant[] value) {
        return new EventFieldList(getClientHandle(), value);
    }

    public static BaseMonitoredItem<?> create() {
        return null; // TODO
    }
}
