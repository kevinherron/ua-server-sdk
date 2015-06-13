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

package com.digitalpetri.opcua.sdk.server.events;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.events.BaseEventType;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;
import com.digitalpetri.opcua.stack.core.types.structured.TimeZoneDataType;

/**
 * The {@link BaseEventType} defines all general characteristics of an Event. All other EventTypes derive from it.
 * <p>
 * There is no other semantic associated with this type.
 */
public class BaseEvent implements BaseEventType {

    private final ByteString eventId;
    private final NodeId eventType;
    private final NodeId sourceNode;
    private final String sourceName;
    private final DateTime time;
    private final DateTime receiveTime;
    private final Optional<TimeZoneDataType> localTime;
    private final LocalizedText message;
    private final UShort severity;

    public BaseEvent(ByteString eventId,
                     NodeId eventType,
                     NodeId sourceNode,
                     String sourceName,
                     DateTime time,
                     DateTime receiveTime,
                     Optional<TimeZoneDataType> localTime,
                     LocalizedText message,
                     UShort severity) {

        this.eventId = eventId;
        this.eventType = eventType;
        this.sourceNode = sourceNode;
        this.sourceName = sourceName;
        this.time = time;
        this.receiveTime = receiveTime;
        this.localTime = localTime;
        this.message = message;
        this.severity = severity;
    }

    @Override
    public ByteString getEventId() {
        return eventId;
    }

    @Override
    public NodeId getEventType() {
        return eventType;
    }

    @Override
    public NodeId getSourceNode() {
        return sourceNode;
    }

    @Override
    public String getSourceName() {
        return sourceName;
    }

    @Override
    public DateTime getTime() {
        return time;
    }

    @Override
    public DateTime getReceiveTime() {
        return receiveTime;
    }

    @Override
    public Optional<TimeZoneDataType> getLocalTime() {
        return localTime;
    }

    @Override
    public LocalizedText getMessage() {
        return message;
    }

    @Override
    public UShort getSeverity() {
        return severity;
    }

    @Override
    public DataValue getProperty(QualifiedName propertyName) {
        return null; // TODO reflectively get property?
    }

    public static class BaseEventBuilder {

        protected ByteString eventId;
        protected NodeId eventType;
        protected NodeId sourceNode;
        protected String sourceName;
        protected DateTime time;
        protected DateTime receiveTime;
        protected Optional<TimeZoneDataType> localTime = Optional.empty();
        protected LocalizedText message;
        protected UShort severity;

        public BaseEventBuilder setEventId(ByteString eventId) {
            this.eventId = eventId;
            return this;
        }

        public BaseEventBuilder setEventType(NodeId eventType) {
            this.eventType = eventType;
            return this;
        }

        public BaseEventBuilder setSourceNode(NodeId sourceNode) {
            this.sourceNode = sourceNode;
            return this;
        }

        public BaseEventBuilder setSourceName(String sourceName) {
            this.sourceName = sourceName;
            return this;
        }

        public BaseEventBuilder setTime(DateTime time) {
            this.time = time;
            return this;
        }

        public BaseEventBuilder setReceiveTime(DateTime receiveTime) {
            this.receiveTime = receiveTime;
            return this;
        }

        public BaseEventBuilder setLocalTime(Optional<TimeZoneDataType> localTime) {
            this.localTime = localTime;
            return this;
        }

        public BaseEventBuilder setMessage(LocalizedText message) {
            this.message = message;
            return this;
        }

        public BaseEventBuilder setSeverity(UShort severity) {
            this.severity = severity;
            return this;
        }

        public BaseEvent build() {
            return new BaseEvent(
                    eventId,
                    eventType,
                    sourceNode,
                    sourceName,
                    time,
                    receiveTime,
                    localTime,
                    message,
                    severity
            );
        }

    }

}
