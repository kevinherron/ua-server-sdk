/*
 * digitalpetri OPC-UA SDK
 *
 * Copyright (C) 2015 Kevin Herron
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
