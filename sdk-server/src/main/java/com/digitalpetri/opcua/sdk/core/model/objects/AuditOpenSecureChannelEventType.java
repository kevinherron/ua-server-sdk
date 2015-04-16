package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.enumerated.MessageSecurityMode;
import com.digitalpetri.opcua.stack.core.types.enumerated.SecurityTokenRequestType;

public interface AuditOpenSecureChannelEventType extends AuditChannelEventType {

    ByteString getClientCertificate();

    String getClientCertificateThumbprint();

    SecurityTokenRequestType getRequestType();

    String getSecurityPolicyUri();

    MessageSecurityMode getSecurityMode();

    Double getRequestedLifetime();

    void setClientCertificate(ByteString clientCertificate);

    void setClientCertificateThumbprint(String clientCertificateThumbprint);

    void setRequestType(SecurityTokenRequestType requestType);

    void setSecurityPolicyUri(String securityPolicyUri);

    void setSecurityMode(MessageSecurityMode securityMode);

    void setRequestedLifetime(Double requestedLifetime);

}
