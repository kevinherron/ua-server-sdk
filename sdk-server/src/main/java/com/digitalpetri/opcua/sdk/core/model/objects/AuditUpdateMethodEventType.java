package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public interface AuditUpdateMethodEventType extends AuditEventType {

    NodeId getMethodId();

    Object[] getInputArguments();

    void setMethodId(NodeId methodId);

    void setInputArguments(Object[] inputArguments);

}
