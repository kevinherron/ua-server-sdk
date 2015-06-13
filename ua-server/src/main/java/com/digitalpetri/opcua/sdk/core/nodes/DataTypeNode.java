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

public interface DataTypeNode extends Node {

    /**
     * The IsAbstract attribute specifies if the DataType is abstract or not.
     * <p>
     * Abstract DataTypes can be used in the AddressSpace, i.e. Variables and VariableTypes can point with their
     * DataType Attribute to an abstract DataType. However, concrete values can never be of an abstract DataType and
     * shall always be of a concrete subtype of the abstract DataType.
     *
     * @return {@code true} if the DataType is abstract.
     */
    Boolean getIsAbstract();

    /**
     * Set the IsAbstract attribute of this DataType.
     *
     * @param isAbstract {@code true} if this
     */
    void setIsAbstract(boolean isAbstract);

}
