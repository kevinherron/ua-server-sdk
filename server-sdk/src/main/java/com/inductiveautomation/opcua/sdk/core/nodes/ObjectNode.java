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

package com.inductiveautomation.opcua.sdk.core.nodes;

import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;

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
     * @throws UaException if the attribute cannot be set.
     */
    default void setEventNotifier(UByte eventNotifier) throws UaException {
        throw new UaException(StatusCodes.Bad_NotWritable);
    }

}
