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

package com.digitalpetri.opcua.sdk.core;

import java.util.EnumSet;

import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

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
