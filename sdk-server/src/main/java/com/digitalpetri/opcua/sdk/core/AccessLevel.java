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

import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;

public enum AccessLevel {

    CurrentRead(0x01),
    CurrentWrite(0x02),
    HistoryRead(0x04),
    HistoryWrite(0x08),
    SemanticChange(0x10);

    public static final EnumSet<AccessLevel> NONE = EnumSet.noneOf(AccessLevel.class);
    public static final EnumSet<AccessLevel> READ_ONLY = EnumSet.of(CurrentRead);
    public static final EnumSet<AccessLevel> READ_WRITE = EnumSet.of(CurrentRead, CurrentWrite);
    public static final EnumSet<AccessLevel> HISTORY_READ_ONLY = EnumSet.of(HistoryRead);
    public static final EnumSet<AccessLevel> HISTORY_READ_WRITE = EnumSet.of(HistoryRead, HistoryWrite);

    private final int value;

    AccessLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static short getMask(AccessLevel... levels) {
        short result = 0;
        for (AccessLevel level : levels) result |= level.value;
        return result;
    }

    public static short getMask(EnumSet<AccessLevel> levels) {
        short result = 0;
        for (AccessLevel level : levels) result |= level.value;
        return result;
    }

    public static EnumSet<AccessLevel> fromMask(int accessLevel) {
        EnumSet<AccessLevel> e = EnumSet.noneOf(AccessLevel.class);
        for (AccessLevel al : values()) {
            if ((al.value & accessLevel) != 0) {
                e.add(al);
            }
        }
        return e;
    }

    public static EnumSet<AccessLevel> fromMask(UByte accessLevel) {
        return fromMask(accessLevel.intValue());
    }

}
