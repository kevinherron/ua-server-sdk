package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.structured.SessionDiagnosticsDataType;
import com.inductiveautomation.opcua.stack.core.types.structured.SessionSecurityDiagnosticsDataType;
import com.inductiveautomation.opcua.stack.core.types.structured.SubscriptionDiagnosticsDataType;

public interface SessionDiagnosticsObjectType extends BaseObjectType {

    SessionDiagnosticsDataType getSessionDiagnostics();

    SessionSecurityDiagnosticsDataType getSessionSecurityDiagnostics();

    SubscriptionDiagnosticsDataType[] getSubscriptionDiagnosticsArray();

    void setSessionDiagnostics(SessionDiagnosticsDataType sessionDiagnostics);

    void setSessionSecurityDiagnostics(SessionSecurityDiagnosticsDataType sessionSecurityDiagnostics);

    void setSubscriptionDiagnosticsArray(SubscriptionDiagnosticsDataType[] subscriptionDiagnosticsArray);

    void atomicSet(Runnable runnable);

}
