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

public interface AuditUpdateMethodEventType extends AuditEventType {

    Property<NodeId> METHOD_ID = new Property.BasicProperty<>(
            QualifiedName.parse("0:MethodId"),
            NodeId.parse("ns=0;i=17"),
            -1,
            NodeId.class
    );

    Property<Object[]> INPUT_ARGUMENTS = new Property.BasicProperty<>(
            QualifiedName.parse("0:InputArguments"),
            NodeId.parse("ns=0;i=24"),
            1,
            Object[].class
    );

    NodeId getMethodId();

    PropertyType getMethodIdNode();

    void setMethodId(NodeId value);

    Object[] getInputArguments();

    PropertyType getInputArgumentsNode();

    void setInputArguments(Object[] value);
}
