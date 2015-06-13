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

import java.util.Optional;

import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface VariableNode extends Node {

    DataValue getValue();

    NodeId getDataType();

    Integer getValueRank();

    Optional<UInteger[]> getArrayDimensions();

    UByte getAccessLevel();

    UByte getUserAccessLevel();

    Optional<Double> getMinimumSamplingInterval();

    Boolean getHistorizing();

    /**
     * Set the Value attribute of this Node.
     *
     * @param value the Value to set.
     */
    void setValue(DataValue value);

    /**
     * Set the DataType attribute of this Node.
     *
     * @param dataType the DataType to set.
     */
    void setDataType(NodeId dataType);

    void setValueRank(Integer valueRank);

    void setArrayDimensions(Optional<UInteger[]> arrayDimensions);

    void setAccessLevel(UByte accessLevel);

    void setUserAccessLevel(UByte userAccessLevel);

    void setMinimumSamplingInterval(Optional<Double> minimumSamplingInterval);

    void setHistorizing(boolean historizing);

}
