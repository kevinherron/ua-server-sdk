package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.sdk.core.model.variables.SessionDiagnosticsVariableType;
import com.digitalpetri.opcua.sdk.core.model.variables.SubscriptionDiagnosticsArrayType;
import com.digitalpetri.opcua.sdk.core.model.variables.SessionSecurityDiagnosticsType;

public interface SessionDiagnosticsObjectType extends BaseObjectType {

    SessionDiagnosticsVariableType getSessionDiagnostics();

    SessionSecurityDiagnosticsType getSessionSecurityDiagnostics();

    SubscriptionDiagnosticsArrayType getSubscriptionDiagnosticsArray();


}
