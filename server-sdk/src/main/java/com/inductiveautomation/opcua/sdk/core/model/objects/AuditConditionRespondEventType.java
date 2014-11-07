package com.inductiveautomation.opcua.sdk.core.model.objects;

public interface AuditConditionRespondEventType extends AuditConditionEventType {

    Integer getSelectedResponse();

    void setSelectedResponse(Integer selectedResponse);

}
