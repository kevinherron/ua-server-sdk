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
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface VariableTypeNode extends Node {

    Optional<DataValue> getValue();

    NodeId getDataType();

    Integer getValueRank();

    Optional<UInteger[]> getArrayDimensions();

    Boolean getIsAbstract();

    void setValue(Optional<DataValue> value);

    void setDataType(NodeId dataType);

    void setValueRank(int valueRank);

    void setArrayDimensions(Optional<UInteger[]> arrayDimensions);

    void setIsAbstract(boolean isAbstract);

}
