package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.sdk.core.model.variables.SamplingIntervalDiagnosticsArrayType;
import com.digitalpetri.opcua.sdk.core.model.variables.SubscriptionDiagnosticsArrayType;
import com.digitalpetri.opcua.sdk.core.model.variables.ServerDiagnosticsSummaryType;

public interface ServerDiagnosticsType extends BaseObjectType {

    ServerDiagnosticsSummaryType getServerDiagnosticsSummary();

    SamplingIntervalDiagnosticsArrayType getSamplingIntervalDiagnosticsArray();

    SubscriptionDiagnosticsArrayType getSubscriptionDiagnosticsArray();

    SessionsDiagnosticsSummaryType getSessionsDiagnosticsSummary();

    Boolean getEnabledFlag();

    void setEnabledFlag(Boolean enabledFlag);

}
