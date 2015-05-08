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

import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public enum AttributeId {

    NODE_ID(1),
    NODE_CLASS(2),
    BROWSE_NAME(3),
    DISPLAY_NAME(4),
    DESCRIPTION(5),
    WRITE_MASK(6),
    USER_WRITE_MASK(7),
    IS_ABSTRACT(8),
    SYMMETRIC(9),
    INVERSE_NAME(10),
    CONTAINS_NO_LOOPS(11),
    EVENT_NOTIFIER(12),
    VALUE(13),
    DATA_TYPE(14),
    VALUE_RANK(15),
    ARRAY_DIMENSIONS(16),
    ACCESS_LEVEL(17),
    USER_ACCESS_LEVEL(18),
    MINIMUM_SAMPLING_INTERVAL(19),
    HISTORIZING(20),
    EXECUTABLE(21),
    USER_EXECUTABLE(22);

    private final int id;

    AttributeId(int id) {
        this.id = id;
    }

    public final int id() {
        return id;
    }

    public final UInteger uid() {
        return uint(id);
    }

}
