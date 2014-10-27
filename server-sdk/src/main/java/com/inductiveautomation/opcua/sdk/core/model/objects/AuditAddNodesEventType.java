package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.structured.AddNodesItem;

public interface AuditAddNodesEventType extends AuditNodeManagementEventType {

    AddNodesItem[] getNodesToAdd();

    void setNodesToAdd(AddNodesItem[] nodesToAdd);

    void atomicSet(Runnable runnable);

}
