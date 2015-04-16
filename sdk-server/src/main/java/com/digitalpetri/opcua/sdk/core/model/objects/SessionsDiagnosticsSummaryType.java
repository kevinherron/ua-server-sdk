package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.sdk.core.model.variables.SessionSecurityDiagnosticsArrayType;
import com.digitalpetri.opcua.sdk.core.model.variables.SessionDiagnosticsArrayType;

public interface SessionsDiagnosticsSummaryType extends BaseObjectType {

    SessionDiagnosticsArrayType getSessionDiagnosticsArray();

    SessionSecurityDiagnosticsArrayType getSessionSecurityDiagnosticsArray();

    SessionDiagnosticsObjectType getSessionPlaceholder();


}
