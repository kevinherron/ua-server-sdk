package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.structured.SamplingIntervalDiagnosticsDataType;
import com.inductiveautomation.opcua.stack.core.types.structured.ServerDiagnosticsSummaryDataType;
import com.inductiveautomation.opcua.stack.core.types.structured.SubscriptionDiagnosticsDataType;

public interface ServerDiagnosticsType extends BaseObjectType {

    Boolean getEnabledFlag();

    ServerDiagnosticsSummaryDataType getServerDiagnosticsSummary();

    SamplingIntervalDiagnosticsDataType[] getSamplingIntervalDiagnosticsArray();

    SubscriptionDiagnosticsDataType[] getSubscriptionDiagnosticsArray();

    SessionsDiagnosticsSummaryType getSessionsDiagnosticsSummary();

    void setEnabledFlag(Boolean enabledFlag);

    void setServerDiagnosticsSummary(ServerDiagnosticsSummaryDataType serverDiagnosticsSummary);

    void setSamplingIntervalDiagnosticsArray(SamplingIntervalDiagnosticsDataType[] samplingIntervalDiagnosticsArray);

    void setSubscriptionDiagnosticsArray(SubscriptionDiagnosticsDataType[] subscriptionDiagnosticsArray);

    void setSessionsDiagnosticsSummary(SessionsDiagnosticsSummaryType sessionsDiagnosticsSummary);

    void atomicSet(Runnable runnable);

}
