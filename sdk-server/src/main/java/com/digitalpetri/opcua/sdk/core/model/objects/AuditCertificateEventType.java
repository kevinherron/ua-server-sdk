package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;

public interface AuditCertificateEventType extends AuditSecurityEventType {

    ByteString getCertificate();

    void setCertificate(ByteString certificate);

}
