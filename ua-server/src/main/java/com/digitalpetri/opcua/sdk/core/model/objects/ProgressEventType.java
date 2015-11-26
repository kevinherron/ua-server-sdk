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
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;

public interface ProgressEventType extends BaseEventType {

    Property<Object> CONTEXT = new Property.BasicProperty<>(
            QualifiedName.parse("0:Context"),
            NodeId.parse("ns=0;i=24"),
            -1,
            Object.class
    );

    Property<UShort> PROGRESS = new Property.BasicProperty<>(
            QualifiedName.parse("0:Progress"),
            NodeId.parse("ns=0;i=5"),
            -1,
            UShort.class
    );

    Object getContext();

    PropertyType getContextNode();

    void setContext(Object value);

    UShort getProgress();

    PropertyType getProgressNode();

    void setProgress(UShort value);
}
