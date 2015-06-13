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

package com.digitalpetri.opcua.sdk.core.nodes;

import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;

public interface ObjectNode extends Node {

    /**
     * The EventNotifier attribute identifies whether the Object can be used to subscribe to Events or to read and
     * write the history of the Events.
     *
     * @return the EventNotifier attribute of this Object.
     */
    UByte getEventNotifier();

    /**
     * Set the EventNotifier attribute of this Object.
     *
     * @param eventNotifier the EventNotifier attribute to set.
     */
    void setEventNotifier(UByte eventNotifier);

}
