package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.structured.AddNodesItem;

public interface AuditAddNodesEventType extends AuditNodeManagementEventType {

    AddNodesItem[] getNodesToAdd();

    void setNodesToAdd(AddNodesItem[] nodesToAdd);

}
