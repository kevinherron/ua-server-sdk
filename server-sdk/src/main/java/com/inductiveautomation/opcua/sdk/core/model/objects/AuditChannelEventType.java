package com.inductiveautomation.opcua.sdk.core.model.objects;

public interface AuditChannelEventType extends AuditSecurityEventType {

    String getSecureChannelId();

    void setSecureChannelId(String secureChannelId);

    void atomicSet(Runnable runnable);

}
