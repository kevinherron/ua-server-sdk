package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.sdk.core.model.variables.SamplingIntervalDiagnosticsArrayType;
import com.inductiveautomation.opcua.sdk.core.model.variables.ServerDiagnosticsSummaryType;
import com.inductiveautomation.opcua.sdk.core.model.variables.SubscriptionDiagnosticsArrayType;

public interface ServerDiagnosticsType extends BaseObjectType {

    Boolean getEnabledFlag();

    ServerDiagnosticsSummaryType getServerDiagnosticsSummary();

    SamplingIntervalDiagnosticsArrayType getSamplingIntervalDiagnosticsArray();

    SubscriptionDiagnosticsArrayType getSubscriptionDiagnosticsArray();

    SessionsDiagnosticsSummaryType getSessionsDiagnosticsSummary();

    void setEnabledFlag(Boolean enabledFlag);

    void atomicSet(Runnable runnable);

}
