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
import com.digitalpetri.opcua.stack.core.types.structured.EnumValueType;


public interface MultiStateValueDiscreteType extends DiscreteItemType {

    Property<EnumValueType[]> ENUM_VALUES = new Property.BasicProperty<>(
            QualifiedName.parse("0:EnumValues"),
            NodeId.parse("ns=0;i=7594"),
            1,
            EnumValueType[].class
    );

    Property<LocalizedText> VALUE_AS_TEXT = new Property.BasicProperty<>(
            QualifiedName.parse("0:ValueAsText"),
            NodeId.parse("ns=0;i=21"),
            -1,
            LocalizedText.class
    );


    EnumValueType[] getEnumValues();

    PropertyType getEnumValuesNode();

    void setEnumValues(EnumValueType[] value);

    LocalizedText getValueAsText();

    PropertyType getValueAsTextNode();

    void setValueAsText(LocalizedText value);

}
