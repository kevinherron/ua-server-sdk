package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public interface AuditUpdateMethodEventType extends AuditEventType {

    NodeId getMethodId();

    Object[] getInputArguments();

    void setMethodId(NodeId methodId);

    void setInputArguments(Object[] inputArguments);

    void atomicSet(Runnable runnable);

}
