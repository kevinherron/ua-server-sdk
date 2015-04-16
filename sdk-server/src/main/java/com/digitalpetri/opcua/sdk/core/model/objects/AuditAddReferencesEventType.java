package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.structured.AddReferencesItem;

public interface AuditAddReferencesEventType extends AuditNodeManagementEventType {

    AddReferencesItem[] getReferencesToAdd();

    void setReferencesToAdd(AddReferencesItem[] referencesToAdd);

}
