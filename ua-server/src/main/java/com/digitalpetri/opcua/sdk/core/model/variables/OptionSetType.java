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

package com.digitalpetri.opcua.sdk.core.model.variables;

import com.digitalpetri.opcua.sdk.server.model.Property;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;


public interface OptionSetType extends BaseDataVariableType {

    Property<LocalizedText[]> OPTION_SET_VALUES = new Property.BasicProperty<>(
            QualifiedName.parse("0:OptionSetValues"),
            NodeId.parse("ns=0;i=21"),
            1,
            LocalizedText[].class
    );

    Property<Boolean[]> BIT_MASK = new Property.BasicProperty<>(
            QualifiedName.parse("0:BitMask"),
            NodeId.parse("ns=0;i=1"),
            1,
            Boolean[].class
    );


    LocalizedText[] getOptionSetValues();

    PropertyType getOptionSetValuesNode();

    void setOptionSetValues(LocalizedText[] value);

    Boolean[] getBitMask();

    PropertyType getBitMaskNode();

    void setBitMask(Boolean[] value);

}
