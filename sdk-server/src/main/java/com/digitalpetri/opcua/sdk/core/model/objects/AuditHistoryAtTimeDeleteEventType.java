package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;

public interface AuditHistoryAtTimeDeleteEventType extends AuditHistoryDeleteEventType {

    DateTime[] getReqTimes();

    DataValue[] getOldValues();

    void setReqTimes(DateTime[] reqTimes);

    void setOldValues(DataValue[] oldValues);

}
