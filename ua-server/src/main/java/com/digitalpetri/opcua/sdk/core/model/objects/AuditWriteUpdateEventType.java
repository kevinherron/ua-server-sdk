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
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface AuditWriteUpdateEventType extends AuditUpdateEventType {

    Property<UInteger> ATTRIBUTE_ID = new Property.BasicProperty<>(
            QualifiedName.parse("0:AttributeId"),
            NodeId.parse("ns=0;i=7"),
            -1,
            UInteger.class
    );

    Property<String> INDEX_RANGE = new Property.BasicProperty<>(
            QualifiedName.parse("0:IndexRange"),
            NodeId.parse("ns=0;i=291"),
            -1,
            String.class
    );

    Property<Object> OLD_VALUE = new Property.BasicProperty<>(
            QualifiedName.parse("0:OldValue"),
            NodeId.parse("ns=0;i=24"),
            -1,
            Object.class
    );

    Property<Object> NEW_VALUE = new Property.BasicProperty<>(
            QualifiedName.parse("0:NewValue"),
            NodeId.parse("ns=0;i=24"),
            -1,
            Object.class
    );

    UInteger getAttributeId();

    PropertyType getAttributeIdNode();

    void setAttributeId(UInteger value);

    String getIndexRange();

    PropertyType getIndexRangeNode();

    void setIndexRange(String value);

    Object getOldValue();

    PropertyType getOldValueNode();

    void setOldValue(Object value);

    Object getNewValue();

    PropertyType getNewValueNode();

    void setNewValue(Object value);
}
