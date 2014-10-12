package com.inductiveautomation.opcua.sdk.server.objects;

import com.inductiveautomation.opcua.stack.core.types.structured.SamplingIntervalDiagnosticsDataType;
import com.inductiveautomation.opcua.stack.core.types.structured.ServerDiagnosticsSummaryDataType;
import com.inductiveautomation.opcua.stack.core.types.structured.SubscriptionDiagnosticsDataType;

public interface ServerDiagnostics extends ObjectPrototype {

    ServerDiagnosticsSummaryDataType getServerDiagnosticsSummary();

    SamplingIntervalDiagnosticsDataType[] getSamplingIntervalDiagnosticsArray();

    SubscriptionDiagnosticsDataType[] getSubscriptionDiagnosticsArray();

    SessionDiagnosticsSummary getSessionDiagnostics();

}
