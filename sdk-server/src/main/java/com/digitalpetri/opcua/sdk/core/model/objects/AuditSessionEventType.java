package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public interface AuditSessionEventType extends AuditEventType {

    NodeId getSessionId();

    void setSessionId(NodeId sessionId);

}
