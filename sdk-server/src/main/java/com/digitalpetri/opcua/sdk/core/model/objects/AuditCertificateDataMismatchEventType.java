package com.digitalpetri.opcua.sdk.core.model.objects;

public interface AuditCertificateDataMismatchEventType extends AuditCertificateEventType {

    String getInvalidHostname();

    String getInvalidUri();

    void setInvalidHostname(String invalidHostname);

    void setInvalidUri(String invalidUri);

}
