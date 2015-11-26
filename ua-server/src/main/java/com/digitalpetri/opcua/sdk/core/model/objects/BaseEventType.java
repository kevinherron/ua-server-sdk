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

import com.digitalpetri.opcua.sdk.core.model.variables.PropertyType;
import com.digitalpetri.opcua.sdk.server.model.Property;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;
import com.digitalpetri.opcua.stack.core.types.structured.TimeZoneDataType;

public interface BaseEventType extends BaseObjectType {

    Property<ByteString> EVENT_ID = new Property.BasicProperty<>(
            QualifiedName.parse("0:EventId"),
            NodeId.parse("ns=0;i=15"),
            -1,
            ByteString.class
    );

    Property<NodeId> EVENT_TYPE = new Property.BasicProperty<>(
            QualifiedName.parse("0:EventType"),
            NodeId.parse("ns=0;i=17"),
            -1,
            NodeId.class
    );

    Property<NodeId> SOURCE_NODE = new Property.BasicProperty<>(
            QualifiedName.parse("0:SourceNode"),
            NodeId.parse("ns=0;i=17"),
            -1,
            NodeId.class
    );

    Property<String> SOURCE_NAME = new Property.BasicProperty<>(
            QualifiedName.parse("0:SourceName"),
            NodeId.parse("ns=0;i=12"),
            -1,
            String.class
    );

    Property<DateTime> TIME = new Property.BasicProperty<>(
            QualifiedName.parse("0:Time"),
            NodeId.parse("ns=0;i=294"),
            -1,
            DateTime.class
    );

    Property<DateTime> RECEIVE_TIME = new Property.BasicProperty<>(
            QualifiedName.parse("0:ReceiveTime"),
            NodeId.parse("ns=0;i=294"),
            -1,
            DateTime.class
    );

    Property<TimeZoneDataType> LOCAL_TIME = new Property.BasicProperty<>(
            QualifiedName.parse("0:LocalTime"),
            NodeId.parse("ns=0;i=8912"),
            -1,
            TimeZoneDataType.class
    );

    Property<LocalizedText> MESSAGE = new Property.BasicProperty<>(
            QualifiedName.parse("0:Message"),
            NodeId.parse("ns=0;i=21"),
            -1,
            LocalizedText.class
    );

    Property<UShort> SEVERITY = new Property.BasicProperty<>(
            QualifiedName.parse("0:Severity"),
            NodeId.parse("ns=0;i=5"),
            -1,
            UShort.class
    );

    ByteString getEventId();

    PropertyType getEventIdNode();

    void setEventId(ByteString value);

    NodeId getEventType();

    PropertyType getEventTypeNode();

    void setEventType(NodeId value);

    NodeId getSourceNode();

    PropertyType getSourceNodeNode();

    void setSourceNode(NodeId value);

    String getSourceName();

    PropertyType getSourceNameNode();

    void setSourceName(String value);

    DateTime getTime();

    PropertyType getTimeNode();

    void setTime(DateTime value);

    DateTime getReceiveTime();

    PropertyType getReceiveTimeNode();

    void setReceiveTime(DateTime value);

    TimeZoneDataType getLocalTime();

    PropertyType getLocalTimeNode();

    void setLocalTime(TimeZoneDataType value);

    LocalizedText getMessage();

    PropertyType getMessageNode();

    void setMessage(LocalizedText value);

    UShort getSeverity();

    PropertyType getSeverityNode();

    void setSeverity(UShort value);
}
