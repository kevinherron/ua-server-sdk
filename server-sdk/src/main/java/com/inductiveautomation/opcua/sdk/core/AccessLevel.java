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

public enum AccessLevel {

    CurrentRead(0x01),
    CurrentWrite(0x02),
    HistoryRead(0x04),
    HistoryWrite(0x08),
    SemanticChange(0x10); // TODO What's this?

    public static final EnumSet<AccessLevel> None = EnumSet.noneOf(AccessLevel.class);
    public static final EnumSet<AccessLevel> ReadOnly = EnumSet.of(CurrentRead);
    public static final EnumSet<AccessLevel> ReadWrite = EnumSet.of(CurrentRead, CurrentWrite);
    public static final EnumSet<AccessLevel> HistoryReadOnly = EnumSet.of(HistoryRead);
    public static final EnumSet<AccessLevel> HistoryReadWrite = EnumSet.of(HistoryRead, HistoryWrite);

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

}
