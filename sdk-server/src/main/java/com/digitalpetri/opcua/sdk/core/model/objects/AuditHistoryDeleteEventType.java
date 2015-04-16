package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public interface AuditHistoryDeleteEventType extends AuditHistoryUpdateEventType {

    NodeId getUpdatedNode();

    void setUpdatedNode(NodeId updatedNode);

}
