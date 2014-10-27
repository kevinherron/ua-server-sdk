package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.structured.DeleteNodesItem;

public interface AuditDeleteNodesEventType extends AuditNodeManagementEventType {

    DeleteNodesItem[] getNodesToDelete();

    void setNodesToDelete(DeleteNodesItem[] nodesToDelete);

    void atomicSet(Runnable runnable);

}
