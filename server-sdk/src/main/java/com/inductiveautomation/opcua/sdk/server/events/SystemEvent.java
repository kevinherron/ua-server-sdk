/*
 * Copyright 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.inductiveautomation.opcua.sdk.server.events;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.events.SystemEventType;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UShort;
import com.inductiveautomation.opcua.stack.core.types.structured.TimeZoneDataType;

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
