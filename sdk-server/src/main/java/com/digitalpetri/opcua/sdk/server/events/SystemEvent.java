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

import com.digitalpetri.opcua.sdk.core.events.SystemEventType;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;
import com.digitalpetri.opcua.stack.core.types.structured.TimeZoneDataType;

/**
 * SystemEvents are Events of {@link SystemEventType} that are generated as a result of some Event that occurs within
 * the Server or by a system that the Server is representing.
 */
public class SystemEvent extends BaseEvent {

    public SystemEvent(ByteString eventId,
                       NodeId eventType,
                       NodeId sourceNode,
                       String sourceName,
                       DateTime time,
                       DateTime receiveTime,
                       Optional<TimeZoneDataType> localTime,
                       LocalizedText message,
                       UShort severity) {

        super(eventId, eventType, sourceNode, sourceName, time, receiveTime, localTime, message, severity);
    }

}
