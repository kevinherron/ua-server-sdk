package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface AuditCancelEventType extends AuditSessionEventType {

    UInteger getRequestHandle();

    void setRequestHandle(UInteger requestHandle);

}
