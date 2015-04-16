package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.structured.DeleteNodesItem;

public interface AuditDeleteNodesEventType extends AuditNodeManagementEventType {

    DeleteNodesItem[] getNodesToDelete();

    void setNodesToDelete(DeleteNodesItem[] nodesToDelete);

}
