package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.sdk.core.model.variables.FiniteTransitionVariableType;

public interface ProgramTransitionAuditEventType extends AuditUpdateStateEventType {

    FiniteTransitionVariableType getTransition();


}
