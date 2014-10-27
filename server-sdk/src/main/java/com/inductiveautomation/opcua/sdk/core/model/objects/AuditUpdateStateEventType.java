package com.inductiveautomation.opcua.sdk.core.model.objects;

public interface AuditUpdateStateEventType extends AuditUpdateMethodEventType {

    Object getOldStateId();

    Object getNewStateId();

    void setOldStateId(Object oldStateId);

    void setNewStateId(Object newStateId);

    void atomicSet(Runnable runnable);

}
