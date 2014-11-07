package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.structured.AddReferencesItem;

public interface AuditAddReferencesEventType extends AuditNodeManagementEventType {

    AddReferencesItem[] getReferencesToAdd();

    void setReferencesToAdd(AddReferencesItem[] referencesToAdd);

}
