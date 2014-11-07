package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;

public interface AuditEventType extends BaseEventType {

    DateTime getActionTimeStamp();

    Boolean getStatus();

    String getServerId();

    String getClientAuditEntryId();

    String getClientUserId();

    void setActionTimeStamp(DateTime actionTimeStamp);

    void setStatus(Boolean status);

    void setServerId(String serverId);

    void setClientAuditEntryId(String clientAuditEntryId);

    void setClientUserId(String clientUserId);

}
