package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UShort;
import com.inductiveautomation.opcua.stack.core.types.structured.SignedSoftwareCertificate;

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

    Object getVendorCapability();

    void setServerProfileArray(String[] serverProfileArray);

    void setLocaleIdArray(String[] localeIdArray);

    void setMinSupportedSampleRate(Double minSupportedSampleRate);

    void setMaxBrowseContinuationPoints(UShort maxBrowseContinuationPoints);

    void setMaxQueryContinuationPoints(UShort maxQueryContinuationPoints);

    void setMaxHistoryContinuationPoints(UShort maxHistoryContinuationPoints);

    void setSoftwareCertificates(SignedSoftwareCertificate[] softwareCertificates);

    void setMaxArrayLength(UInteger maxArrayLength);

    void setMaxStringLength(UInteger maxStringLength);

    void setOperationLimits(OperationLimitsType operationLimits);

    void setModellingRules(FolderType modellingRules);

    void setAggregateFunctions(FolderType aggregateFunctions);

    void setVendorCapability(Object vendorCapability);

    void atomicSet(Runnable runnable);

}
