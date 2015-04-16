package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;
import com.digitalpetri.opcua.stack.core.types.structured.TimeZoneDataType;

public interface BaseEventType extends BaseObjectType {

    ByteString getEventId();

    NodeId getEventType();

    NodeId getSourceNode();

    String getSourceName();

    DateTime getTime();

    DateTime getReceiveTime();

    TimeZoneDataType getLocalTime();

    LocalizedText getMessage();

    UShort getSeverity();

    void setEventId(ByteString eventId);

    void setEventType(NodeId eventType);

    void setSourceNode(NodeId sourceNode);

    void setSourceName(String sourceName);

    void setTime(DateTime time);

    void setReceiveTime(DateTime receiveTime);

    void setLocalTime(TimeZoneDataType localTime);

    void setMessage(LocalizedText message);

    void setSeverity(UShort severity);

}
