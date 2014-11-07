package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.enumerated.PerformUpdateType;
import com.inductiveautomation.opcua.stack.core.types.structured.EventFilter;
import com.inductiveautomation.opcua.stack.core.types.structured.HistoryEventFieldList;

public interface AuditHistoryEventUpdateEventType extends AuditHistoryUpdateEventType {

    NodeId getUpdatedNode();

    PerformUpdateType getPerformInsertReplace();

    EventFilter getFilter();

    HistoryEventFieldList getNewValues();

    HistoryEventFieldList getOldValues();

    void setUpdatedNode(NodeId updatedNode);

    void setPerformInsertReplace(PerformUpdateType performInsertReplace);

    void setFilter(EventFilter filter);

    void setNewValues(HistoryEventFieldList newValues);

    void setOldValues(HistoryEventFieldList oldValues);

}
