package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.structured.DeleteReferencesItem;

public interface AuditDeleteReferencesEventType extends AuditNodeManagementEventType {

    DeleteReferencesItem[] getReferencesToDelete();

    void setReferencesToDelete(DeleteReferencesItem[] referencesToDelete);

    void atomicSet(Runnable runnable);

}
