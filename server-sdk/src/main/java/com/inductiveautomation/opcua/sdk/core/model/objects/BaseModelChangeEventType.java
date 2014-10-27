package com.inductiveautomation.opcua.sdk.core.model.objects;

public interface BaseModelChangeEventType extends BaseEventType {


    void atomicSet(Runnable runnable);

}
