package com.digitalpetri.opcua.sdk.core.model.objects;

public interface AuditChannelEventType extends AuditSecurityEventType {

    String getSecureChannelId();

    void setSecureChannelId(String secureChannelId);

}
