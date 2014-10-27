package com.inductiveautomation.opcua.sdk.core.model.objects;

public interface RefreshRequiredEventType extends SystemEventType {


    void atomicSet(Runnable runnable);

}
