package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public interface AuditHistoryUpdateEventType extends AuditUpdateEventType {

    NodeId getParameterDataTypeId();

    void setParameterDataTypeId(NodeId parameterDataTypeId);

}
