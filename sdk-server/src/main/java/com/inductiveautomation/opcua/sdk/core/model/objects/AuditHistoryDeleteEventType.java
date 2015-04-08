package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public interface AuditHistoryDeleteEventType extends AuditHistoryUpdateEventType {

    NodeId getUpdatedNode();

    void setUpdatedNode(NodeId updatedNode);

}
