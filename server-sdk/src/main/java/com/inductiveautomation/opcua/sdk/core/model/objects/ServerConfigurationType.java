package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface ServerConfigurationType extends BaseObjectType {

    String[] getSupportedCertificateFormats();

    String[] getSupportedPrivateKeyFormats();

    UInteger getMaxTrustListSize();

    Boolean getNoSupportForLocalCRLs();

    Boolean getMulticastDnsEnabled();

    TrustListType getTrustList();

    TrustListType getHttpsTrustList();

    void setSupportedCertificateFormats(String[] supportedCertificateFormats);

    void setSupportedPrivateKeyFormats(String[] supportedPrivateKeyFormats);

    void setMaxTrustListSize(UInteger maxTrustListSize);

    void setNoSupportForLocalCRLs(Boolean noSupportForLocalCRLs);

    void setMulticastDnsEnabled(Boolean multicastDnsEnabled);

    void setTrustList(TrustListType trustList);

    void setHttpsTrustList(TrustListType httpsTrustList);

    void atomicSet(Runnable runnable);

}
