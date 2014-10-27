package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public interface AuditSessionEventType extends AuditEventType {

    NodeId getSessionId();

    void setSessionId(NodeId sessionId);

    void atomicSet(Runnable runnable);

}
