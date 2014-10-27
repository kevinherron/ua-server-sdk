package com.inductiveautomation.opcua.sdk.core.model.objects;

public interface AuditConditionShelvingEventType extends AuditConditionEventType {

    Double getShelvingTime();

    void setShelvingTime(Double shelvingTime);

    void atomicSet(Runnable runnable);

}
