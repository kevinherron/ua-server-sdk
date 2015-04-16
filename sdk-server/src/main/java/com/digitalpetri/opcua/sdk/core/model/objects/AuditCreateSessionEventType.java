package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;

public interface AuditCreateSessionEventType extends AuditSessionEventType {

    String getSecureChannelId();

    ByteString getClientCertificate();

    String getClientCertificateThumbprint();

    Double getRevisedSessionTimeout();

    void setSecureChannelId(String secureChannelId);

    void setClientCertificate(ByteString clientCertificate);

    void setClientCertificateThumbprint(String clientCertificateThumbprint);

    void setRevisedSessionTimeout(Double revisedSessionTimeout);

}
