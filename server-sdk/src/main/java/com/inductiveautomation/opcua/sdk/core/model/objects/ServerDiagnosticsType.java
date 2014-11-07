package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.sdk.core.model.variables.SamplingIntervalDiagnosticsArrayType;
import com.inductiveautomation.opcua.sdk.core.model.variables.ServerDiagnosticsSummaryType;
import com.inductiveautomation.opcua.sdk.core.model.variables.SubscriptionDiagnosticsArrayType;

public interface ServerDiagnosticsType extends BaseObjectType {

    ServerDiagnosticsSummaryType getServerDiagnosticsSummary();

    SamplingIntervalDiagnosticsArrayType getSamplingIntervalDiagnosticsArray();

    SubscriptionDiagnosticsArrayType getSubscriptionDiagnosticsArray();

    SessionsDiagnosticsSummaryType getSessionsDiagnosticsSummary();

    Boolean getEnabledFlag();

    void setEnabledFlag(Boolean enabledFlag);

}
