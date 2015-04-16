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

package com.digitalpetri.opcua.sdk.server.events;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.events.SystemStatusChangeEventType;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;
import com.digitalpetri.opcua.stack.core.types.enumerated.ServerState;
import com.digitalpetri.opcua.stack.core.types.structured.TimeZoneDataType;

/**
 * A {@link SystemStatusChangeEvent} is an Event of {@link SystemStatusChangeEventType} that indicates a status change
 * in a system.
 * <p>
 * For example, if the status indicates an underlying system is not running, then a Client cannot expect any Events
 * from the underlying system. A Server can identify its own status changes using this EventType.
 */
public class SystemStatusChangeEvent extends SystemEvent implements SystemStatusChangeEventType {

    private final ServerState systemState;

    public SystemStatusChangeEvent(ByteString eventId,
                                   NodeId eventType,
                                   NodeId sourceNode,
                                   String sourceName,
                                   DateTime time,
                                   DateTime receiveTime,
                                   Optional<TimeZoneDataType> localTime,
                                   LocalizedText message,
                                   UShort severity,
                                   ServerState systemState) {

        super(eventId, eventType, sourceNode, sourceName, time, receiveTime, localTime, message, severity);

        this.systemState = systemState;
    }

    @Override
    public ServerState getSystemState() {
        return systemState;
    }

    public static class SystemStatusChangeEventBuilder extends BaseEventBuilder {

        private ServerState serverState;

        public SystemStatusChangeEventBuilder setServerState(ServerState serverState) {
            this.serverState = serverState;
            return this;
        }

        public SystemStatusChangeEvent build() {
            return new SystemStatusChangeEvent(
                    eventId,
                    eventType,
                    sourceNode,
                    sourceName,
                    time,
                    receiveTime,
                    localTime,
                    message,
                    severity,
                    serverState
            );
        }

    }

}
