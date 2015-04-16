package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;

public interface AuditConditionAcknowledgeEventType extends AuditConditionEventType {

    ByteString getEventId();

    LocalizedText getComment();

    void setEventId(ByteString eventId);

    void setComment(LocalizedText comment);

}
