package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface AuditWriteUpdateEventType extends AuditUpdateEventType {

    UInteger getAttributeId();

    String getIndexRange();

    Object getOldValue();

    Object getNewValue();

    void setAttributeId(UInteger attributeId);

    void setIndexRange(String indexRange);

    void setOldValue(Object oldValue);

    void setNewValue(Object newValue);

}
