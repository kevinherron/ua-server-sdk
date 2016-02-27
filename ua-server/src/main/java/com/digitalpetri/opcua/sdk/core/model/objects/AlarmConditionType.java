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
import com.digitalpetri.opcua.sdk.core.model.variables.TwoStateVariableType;
import com.digitalpetri.opcua.sdk.server.model.Property;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;

public interface AlarmConditionType extends AcknowledgeableConditionType {

    Property<NodeId> INPUT_NODE = new Property.BasicProperty<>(
            QualifiedName.parse("0:InputNode"),
            NodeId.parse("ns=0;i=17"),
            -1,
            NodeId.class
    );

    Property<Boolean> SUPPRESSED_OR_SHELVED = new Property.BasicProperty<>(
            QualifiedName.parse("0:SuppressedOrShelved"),
            NodeId.parse("ns=0;i=1"),
            -1,
            Boolean.class
    );

    Property<Double> MAX_TIME_SHELVED = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxTimeShelved"),
            NodeId.parse("ns=0;i=290"),
            -1,
            Double.class
    );

    NodeId getInputNode();

    PropertyType getInputNodeNode();

    void setInputNode(NodeId value);

    Boolean getSuppressedOrShelved();

    PropertyType getSuppressedOrShelvedNode();

    void setSuppressedOrShelved(Boolean value);

    Double getMaxTimeShelved();

    PropertyType getMaxTimeShelvedNode();

    void setMaxTimeShelved(Double value);

    ShelvedStateMachineType getShelvingStateNode();

    LocalizedText getEnabledState();

    TwoStateVariableType getEnabledStateNode();

    void setEnabledState(LocalizedText value);

    LocalizedText getActiveState();

    TwoStateVariableType getActiveStateNode();

    void setActiveState(LocalizedText value);

    LocalizedText getSuppressedState();

    TwoStateVariableType getSuppressedStateNode();

    void setSuppressedState(LocalizedText value);
}
