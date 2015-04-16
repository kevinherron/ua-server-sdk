package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.sdk.core.model.variables.ServerVendorCapabilityType;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;
import com.digitalpetri.opcua.stack.core.types.structured.SignedSoftwareCertificate;

public interface ServerCapabilitiesType extends BaseObjectType {

    String[] getServerProfileArray();

    String[] getLocaleIdArray();

    Double getMinSupportedSampleRate();

    UShort getMaxBrowseContinuationPoints();

    UShort getMaxQueryContinuationPoints();

    UShort getMaxHistoryContinuationPoints();

    SignedSoftwareCertificate[] getSoftwareCertificates();

    UInteger getMaxArrayLength();

    UInteger getMaxStringLength();

    OperationLimitsType getOperationLimits();

    FolderType getModellingRules();

    FolderType getAggregateFunctions();

    ServerVendorCapabilityType getVendorCapability();

    void setServerProfileArray(String[] serverProfileArray);

    void setLocaleIdArray(String[] localeIdArray);

    void setMinSupportedSampleRate(Double minSupportedSampleRate);

    void setMaxBrowseContinuationPoints(UShort maxBrowseContinuationPoints);

    void setMaxQueryContinuationPoints(UShort maxQueryContinuationPoints);

    void setMaxHistoryContinuationPoints(UShort maxHistoryContinuationPoints);

    void setSoftwareCertificates(SignedSoftwareCertificate[] softwareCertificates);

    void setMaxArrayLength(UInteger maxArrayLength);

    void setMaxStringLength(UInteger maxStringLength);

}
