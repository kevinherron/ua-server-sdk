package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface AuditWriteUpdateEventType extends AuditUpdateEventType {

    UInteger getAttributeId();

    String getIndexRange();

    Object getOldValue();

    Object getNewValue();

    void setAttributeId(UInteger attributeId);

    void setIndexRange(String indexRange);

    void setOldValue(Object oldValue);

    void setNewValue(Object newValue);

    void atomicSet(Runnable runnable);

}
