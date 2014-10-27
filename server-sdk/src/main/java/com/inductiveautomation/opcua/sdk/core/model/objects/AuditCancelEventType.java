package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface AuditCancelEventType extends AuditSessionEventType {

    UInteger getRequestHandle();

    void setRequestHandle(UInteger requestHandle);

    void atomicSet(Runnable runnable);

}
