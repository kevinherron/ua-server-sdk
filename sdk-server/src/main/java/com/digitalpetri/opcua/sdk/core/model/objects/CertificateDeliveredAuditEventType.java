package com.digitalpetri.opcua.sdk.core.model.objects;

public interface CertificateDeliveredAuditEventType extends AuditUpdateMethodEventType {

    String getApplicationUri();

    void setApplicationUri(String applicationUri);

}
