package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.sdk.core.model.variables.SessionDiagnosticsArrayType;
import com.inductiveautomation.opcua.sdk.core.model.variables.SessionSecurityDiagnosticsArrayType;

public interface SessionsDiagnosticsSummaryType extends BaseObjectType {

    SessionDiagnosticsArrayType getSessionDiagnosticsArray();

    SessionSecurityDiagnosticsArrayType getSessionSecurityDiagnosticsArray();

    SessionDiagnosticsObjectType getSessionPlaceholder();


}
