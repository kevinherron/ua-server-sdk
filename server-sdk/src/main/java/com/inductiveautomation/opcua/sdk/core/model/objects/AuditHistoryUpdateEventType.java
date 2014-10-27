package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public interface AuditHistoryUpdateEventType extends AuditUpdateEventType {

    NodeId getParameterDataTypeId();

    void setParameterDataTypeId(NodeId parameterDataTypeId);

    void atomicSet(Runnable runnable);

}
