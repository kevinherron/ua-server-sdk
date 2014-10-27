package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.sdk.core.model.variables.SessionDiagnosticsVariableType;
import com.inductiveautomation.opcua.sdk.core.model.variables.SessionSecurityDiagnosticsType;
import com.inductiveautomation.opcua.sdk.core.model.variables.SubscriptionDiagnosticsArrayType;

public interface SessionDiagnosticsObjectType extends BaseObjectType {

    SessionDiagnosticsVariableType getSessionDiagnostics();

    SessionSecurityDiagnosticsType getSessionSecurityDiagnostics();

    SubscriptionDiagnosticsArrayType getSubscriptionDiagnosticsArray();


    void atomicSet(Runnable runnable);

}
