/*
 * Copyright 2014 Inductive Automation
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

package com.inductiveautomation.opcua.sdk.core;

import java.util.EnumSet;

import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

public enum WriteMask {

    AccessLevel(1),
    ArrayDimensions(2),
    BrowseName(4),
    ContainsNoLoops(8),
    DataType(16),
    Description(32),
    DisplayName(64),
    EventNotifier(128),
    Executable(256),
    Historizing(512),
    InverseName(1024),
    IsAbstract(2048),
    MinimumSamplingInterval(4096),
    NodeClass(8192),
    NodeId(16384),
    Symmetric(32768),
    UserAccessLevel(65536),
    UserExecutable(131072),
    UserWriteMask(262144),
    ValueRank(524288),
    WriteMask(1048576),
    ValueForVariableType(2097152);

    public static final EnumSet<AccessLevel> None = EnumSet.noneOf(AccessLevel.class);

    private final int value;

    private WriteMask(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static EnumSet<WriteMask> fromMask(int accessLevel) {
        EnumSet<WriteMask> e = EnumSet.noneOf(WriteMask.class);
        for (WriteMask wm : values()) {
            if ((wm.value & accessLevel) != 0) {
                e.add(wm);
            }
        }
        return e;
    }

    public static EnumSet<WriteMask> fromMask(UInteger accessLevel) {
        return fromMask(accessLevel.intValue());
    }

}
