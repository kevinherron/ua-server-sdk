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
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;


public interface DataTypeDescriptionType extends BaseDataVariableType {

    Property<String> DATA_TYPE_VERSION = new Property.BasicProperty<>(
            QualifiedName.parse("0:DataTypeVersion"),
            NodeId.parse("ns=0;i=12"),
            -1,
            String.class
    );

    Property<ByteString> DICTIONARY_FRAGMENT = new Property.BasicProperty<>(
            QualifiedName.parse("0:DictionaryFragment"),
            NodeId.parse("ns=0;i=15"),
            -1,
            ByteString.class
    );


    String getDataTypeVersion();

    PropertyType getDataTypeVersionNode();

    void setDataTypeVersion(String value);

    ByteString getDictionaryFragment();

    PropertyType getDictionaryFragmentNode();

    void setDictionaryFragment(ByteString value);

}
