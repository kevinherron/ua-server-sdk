package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.structured.HistoryEventFieldList;

public interface AuditHistoryEventDeleteEventType extends AuditHistoryDeleteEventType {

    ByteString[] getEventIds();

    HistoryEventFieldList getOldValues();

    void setEventIds(ByteString[] eventIds);

    void setOldValues(HistoryEventFieldList oldValues);

}
