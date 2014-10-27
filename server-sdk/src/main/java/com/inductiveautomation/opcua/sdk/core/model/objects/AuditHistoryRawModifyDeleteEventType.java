package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;

public interface AuditHistoryRawModifyDeleteEventType extends AuditHistoryDeleteEventType {

    Boolean getIsDeleteModified();

    DateTime getStartTime();

    DateTime getEndTime();

    DataValue[] getOldValues();

    void setIsDeleteModified(Boolean isDeleteModified);

    void setStartTime(DateTime startTime);

    void setEndTime(DateTime endTime);

    void setOldValues(DataValue[] oldValues);

    void atomicSet(Runnable runnable);

}
