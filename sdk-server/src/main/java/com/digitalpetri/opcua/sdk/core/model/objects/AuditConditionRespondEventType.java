package com.digitalpetri.opcua.sdk.core.model.objects;

public interface AuditConditionRespondEventType extends AuditConditionEventType {

    Integer getSelectedResponse();

    void setSelectedResponse(Integer selectedResponse);

}
