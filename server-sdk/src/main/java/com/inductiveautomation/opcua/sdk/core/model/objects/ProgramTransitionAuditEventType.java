package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;

public interface ProgramTransitionAuditEventType extends AuditUpdateStateEventType {

    LocalizedText getTransition();

    void setTransition(LocalizedText transition);

    void atomicSet(Runnable runnable);

}
