package com.inductiveautomation.opcua.sdk.core.model.objects;

public interface ApplicationRegistrationChangedAuditEventType extends AuditUpdateMethodEventType {

    String getApplicationUri();

    void setApplicationUri(String applicationUri);

}
