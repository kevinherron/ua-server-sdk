package com.inductiveautomation.opcua.sdk.core.model.objects;

public interface CertificateRequestedAuditEventType extends AuditUpdateMethodEventType {

    String getApplicationUri();

    void setApplicationUri(String applicationUri);

    void atomicSet(Runnable runnable);

}
