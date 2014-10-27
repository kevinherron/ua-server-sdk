package com.inductiveautomation.opcua.sdk.core.model.objects;

public interface AuditUpdateEventType extends AuditEventType {


    void atomicSet(Runnable runnable);

}
