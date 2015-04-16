package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface AuditProgramTransitionEventType extends AuditUpdateStateEventType {

    UInteger getTransitionNumber();

    void setTransitionNumber(UInteger transitionNumber);

}
