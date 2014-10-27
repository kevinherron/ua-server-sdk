package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;

public interface AuditCertificateEventType extends AuditSecurityEventType {

    ByteString getCertificate();

    void setCertificate(ByteString certificate);

    void atomicSet(Runnable runnable);

}
