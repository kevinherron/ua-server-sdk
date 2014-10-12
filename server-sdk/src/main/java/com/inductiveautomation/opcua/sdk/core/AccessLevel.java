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
