package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.structured.SessionDiagnosticsDataType;
import com.inductiveautomation.opcua.stack.core.types.structured.SessionSecurityDiagnosticsDataType;

public interface SessionsDiagnosticsSummaryType extends BaseObjectType {

    SessionDiagnosticsDataType[] getSessionDiagnosticsArray();

    SessionSecurityDiagnosticsDataType[] getSessionSecurityDiagnosticsArray();

    SessionDiagnosticsObjectType getSessionPlaceholder();

    void setSessionDiagnosticsArray(SessionDiagnosticsDataType[] sessionDiagnosticsArray);

    void setSessionSecurityDiagnosticsArray(SessionSecurityDiagnosticsDataType[] sessionSecurityDiagnosticsArray);

    void setSessionPlaceholder(SessionDiagnosticsObjectType sessionPlaceholder);

    void atomicSet(Runnable runnable);

}
