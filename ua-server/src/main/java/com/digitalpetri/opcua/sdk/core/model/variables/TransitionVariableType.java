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
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;


public interface TransitionVariableType extends BaseDataVariableType {

    Property<Object> ID = new Property.BasicProperty<>(
            QualifiedName.parse("0:Id"),
            NodeId.parse("ns=0;i=24"),
            -1,
            Object.class
    );

    Property<QualifiedName> NAME = new Property.BasicProperty<>(
            QualifiedName.parse("0:Name"),
            NodeId.parse("ns=0;i=20"),
            -1,
            QualifiedName.class
    );

    Property<UInteger> NUMBER = new Property.BasicProperty<>(
            QualifiedName.parse("0:Number"),
            NodeId.parse("ns=0;i=7"),
            -1,
            UInteger.class
    );

    Property<DateTime> TRANSITION_TIME = new Property.BasicProperty<>(
            QualifiedName.parse("0:TransitionTime"),
            NodeId.parse("ns=0;i=294"),
            -1,
            DateTime.class
    );

    Property<DateTime> EFFECTIVE_TRANSITION_TIME = new Property.BasicProperty<>(
            QualifiedName.parse("0:EffectiveTransitionTime"),
            NodeId.parse("ns=0;i=294"),
            -1,
            DateTime.class
    );


    Object getId();

    PropertyType getIdNode();

    void setId(Object value);

    QualifiedName getName();

    PropertyType getNameNode();

    void setName(QualifiedName value);

    UInteger getNumber();

    PropertyType getNumberNode();

    void setNumber(UInteger value);

    DateTime getTransitionTime();

    PropertyType getTransitionTimeNode();

    void setTransitionTime(DateTime value);

    DateTime getEffectiveTransitionTime();

    PropertyType getEffectiveTransitionTimeNode();

    void setEffectiveTransitionTime(DateTime value);

}
