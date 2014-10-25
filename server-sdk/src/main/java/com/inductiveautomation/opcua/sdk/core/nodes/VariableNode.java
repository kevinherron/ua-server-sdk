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

import java.util.Optional;

import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface VariableNode extends Node {

    DataValue getValue();

    NodeId getDataType();

    Integer getValueRank();

    Optional<UInteger[]> getArrayDimensions();

    UByte getAccessLevel();

    UByte getUserAccessLevel();

    Optional<Double> getMinimumSamplingInterval();

    Boolean isHistorizing();

    /**
     * Set the Value attribute of this Node.
     *
     * @param value the Value to set.
     * @throws UaException if the attribute cannot be set.
     */
    default void setValue(DataValue value) throws UaException {
        throw new UaException(StatusCodes.Bad_NotWritable);
    }

    /**
     * Set the DataType attribute of this Node.
     *
     * @param dataType the DataType to set.
     * @throws UaException if the attribute cannot be set.
     */
    default void setDataType(NodeId dataType) throws UaException {
        throw new UaException(StatusCodes.Bad_NotWritable);
    }

    default void setValueRank(Integer valueRank) throws UaException {
        throw new UaException(StatusCodes.Bad_NotWritable);
    }

    default void setArrayDimensions(Optional<UInteger[]> arrayDimensions) throws UaException {
        throw new UaException(StatusCodes.Bad_NotWritable);
    }

    default void setAccessLevel(UByte accessLevel) throws UaException {
        throw new UaException(StatusCodes.Bad_NotWritable);
    }

    default void setUserAccessLevel(UByte userAccessLevel) throws UaException {
        throw new UaException(StatusCodes.Bad_NotWritable);
    }

    default void setMinimumSamplingInterval(Optional<Double> minimumSamplingInterval) throws UaException {
        throw new UaException(StatusCodes.Bad_NotWritable);
    }

    default void setHistorizing(boolean historizing) throws UaException {
        throw new UaException(StatusCodes.Bad_NotWritable);
    }

}
