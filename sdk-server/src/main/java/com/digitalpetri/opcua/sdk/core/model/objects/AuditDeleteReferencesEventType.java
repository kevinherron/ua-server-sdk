package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.structured.DeleteReferencesItem;

public interface AuditDeleteReferencesEventType extends AuditNodeManagementEventType {

    DeleteReferencesItem[] getReferencesToDelete();

    void setReferencesToDelete(DeleteReferencesItem[] referencesToDelete);

}
