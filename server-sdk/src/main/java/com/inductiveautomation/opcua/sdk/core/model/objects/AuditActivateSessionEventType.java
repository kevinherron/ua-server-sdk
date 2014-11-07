package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.structured.SignedSoftwareCertificate;
import com.inductiveautomation.opcua.stack.core.types.structured.UserIdentityToken;

public interface AuditActivateSessionEventType extends AuditSessionEventType {

    SignedSoftwareCertificate[] getClientSoftwareCertificates();

    UserIdentityToken getUserIdentityToken();

    String getSecureChannelId();

    void setClientSoftwareCertificates(SignedSoftwareCertificate[] clientSoftwareCertificates);

    void setUserIdentityToken(UserIdentityToken userIdentityToken);

    void setSecureChannelId(String secureChannelId);

}
