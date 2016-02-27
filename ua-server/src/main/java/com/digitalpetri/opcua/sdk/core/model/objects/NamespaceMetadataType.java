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

package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.sdk.core.model.variables.PropertyType;
import com.digitalpetri.opcua.sdk.server.model.Property;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.enumerated.IdType;

public interface NamespaceMetadataType extends BaseObjectType {

    Property<String> NAMESPACE_URI = new Property.BasicProperty<>(
            QualifiedName.parse("0:NamespaceUri"),
            NodeId.parse("ns=0;i=12"),
            -1,
            String.class
    );

    Property<String> NAMESPACE_VERSION = new Property.BasicProperty<>(
            QualifiedName.parse("0:NamespaceVersion"),
            NodeId.parse("ns=0;i=12"),
            -1,
            String.class
    );

    Property<DateTime> NAMESPACE_PUBLICATION_DATE = new Property.BasicProperty<>(
            QualifiedName.parse("0:NamespacePublicationDate"),
            NodeId.parse("ns=0;i=13"),
            -1,
            DateTime.class
    );

    Property<Boolean> IS_NAMESPACE_SUBSET = new Property.BasicProperty<>(
            QualifiedName.parse("0:IsNamespaceSubset"),
            NodeId.parse("ns=0;i=1"),
            -1,
            Boolean.class
    );

    Property<IdType[]> STATIC_NODE_ID_IDENTIFIER_TYPES = new Property.BasicProperty<>(
            QualifiedName.parse("0:StaticNodeIdIdentifierTypes"),
            NodeId.parse("ns=0;i=256"),
            1,
            IdType[].class
    );

    Property<String[]> STATIC_NUMERIC_NODE_ID_RANGE = new Property.BasicProperty<>(
            QualifiedName.parse("0:StaticNumericNodeIdRange"),
            NodeId.parse("ns=0;i=291"),
            1,
            String[].class
    );

    Property<String[]> STATIC_STRING_NODE_ID_PATTERN = new Property.BasicProperty<>(
            QualifiedName.parse("0:StaticStringNodeIdPattern"),
            NodeId.parse("ns=0;i=12"),
            1,
            String[].class
    );

    String getNamespaceUri();

    PropertyType getNamespaceUriNode();

    void setNamespaceUri(String value);

    String getNamespaceVersion();

    PropertyType getNamespaceVersionNode();

    void setNamespaceVersion(String value);

    DateTime getNamespacePublicationDate();

    PropertyType getNamespacePublicationDateNode();

    void setNamespacePublicationDate(DateTime value);

    Boolean getIsNamespaceSubset();

    PropertyType getIsNamespaceSubsetNode();

    void setIsNamespaceSubset(Boolean value);

    IdType[] getStaticNodeIdIdentifierTypes();

    PropertyType getStaticNodeIdIdentifierTypesNode();

    void setStaticNodeIdIdentifierTypes(IdType[] value);

    String[] getStaticNumericNodeIdRange();

    PropertyType getStaticNumericNodeIdRangeNode();

    void setStaticNumericNodeIdRange(String[] value);

    String[] getStaticStringNodeIdPattern();

    PropertyType getStaticStringNodeIdPatternNode();

    void setStaticStringNodeIdPattern(String[] value);

    AddressSpaceFileType getNamespaceFileNode();

}
