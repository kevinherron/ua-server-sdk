package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;

public interface AuditConditionConfirmEventType extends AuditConditionEventType {

    ByteString getEventId();

    LocalizedText getComment();

    void setEventId(ByteString eventId);

    void setComment(LocalizedText comment);

}
