package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.sdk.core.model.variables.FiniteTransitionVariableType;

public interface ProgramTransitionAuditEventType extends AuditUpdateStateEventType {

    FiniteTransitionVariableType getTransition();


}
