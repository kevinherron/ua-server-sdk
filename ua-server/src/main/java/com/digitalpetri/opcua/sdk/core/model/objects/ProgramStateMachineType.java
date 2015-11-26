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

import com.digitalpetri.opcua.sdk.core.model.variables.FiniteStateVariableType;
import com.digitalpetri.opcua.sdk.core.model.variables.FiniteTransitionVariableType;
import com.digitalpetri.opcua.sdk.core.model.variables.ProgramDiagnosticType;
import com.digitalpetri.opcua.sdk.core.model.variables.PropertyType;
import com.digitalpetri.opcua.sdk.server.model.Property;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.structured.ProgramDiagnosticDataType;

public interface ProgramStateMachineType extends FiniteStateMachineType {

    Property<Boolean> CREATABLE = new Property.BasicProperty<>(
            QualifiedName.parse("0:Creatable"),
            NodeId.parse("ns=0;i=1"),
            -1,
            Boolean.class
    );

    Property<Boolean> DELETABLE = new Property.BasicProperty<>(
            QualifiedName.parse("0:Deletable"),
            NodeId.parse("ns=0;i=1"),
            -1,
            Boolean.class
    );

    Property<Boolean> AUTO_DELETE = new Property.BasicProperty<>(
            QualifiedName.parse("0:AutoDelete"),
            NodeId.parse("ns=0;i=1"),
            -1,
            Boolean.class
    );

    Property<Integer> RECYCLE_COUNT = new Property.BasicProperty<>(
            QualifiedName.parse("0:RecycleCount"),
            NodeId.parse("ns=0;i=6"),
            -1,
            Integer.class
    );

    Property<UInteger> INSTANCE_COUNT = new Property.BasicProperty<>(
            QualifiedName.parse("0:InstanceCount"),
            NodeId.parse("ns=0;i=7"),
            -1,
            UInteger.class
    );

    Property<UInteger> MAX_INSTANCE_COUNT = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxInstanceCount"),
            NodeId.parse("ns=0;i=7"),
            -1,
            UInteger.class
    );

    Property<UInteger> MAX_RECYCLE_COUNT = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxRecycleCount"),
            NodeId.parse("ns=0;i=7"),
            -1,
            UInteger.class
    );

    Boolean getCreatable();

    PropertyType getCreatableNode();

    void setCreatable(Boolean value);

    Boolean getDeletable();

    PropertyType getDeletableNode();

    void setDeletable(Boolean value);

    Boolean getAutoDelete();

    PropertyType getAutoDeleteNode();

    void setAutoDelete(Boolean value);

    Integer getRecycleCount();

    PropertyType getRecycleCountNode();

    void setRecycleCount(Integer value);

    UInteger getInstanceCount();

    PropertyType getInstanceCountNode();

    void setInstanceCount(UInteger value);

    UInteger getMaxInstanceCount();

    PropertyType getMaxInstanceCountNode();

    void setMaxInstanceCount(UInteger value);

    UInteger getMaxRecycleCount();

    PropertyType getMaxRecycleCountNode();

    void setMaxRecycleCount(UInteger value);

    BaseObjectType getFinalResultDataNode();

    StateType getReadyNode();

    StateType getRunningNode();

    StateType getSuspendedNode();

    StateType getHaltedNode();

    TransitionType getHaltedToReadyNode();

    TransitionType getReadyToRunningNode();

    TransitionType getRunningToHaltedNode();

    TransitionType getRunningToReadyNode();

    TransitionType getRunningToSuspendedNode();

    TransitionType getSuspendedToRunningNode();

    TransitionType getSuspendedToHaltedNode();

    TransitionType getSuspendedToReadyNode();

    TransitionType getReadyToHaltedNode();

    LocalizedText getCurrentState();

    FiniteStateVariableType getCurrentStateNode();

    void setCurrentState(LocalizedText value);

    LocalizedText getLastTransition();

    FiniteTransitionVariableType getLastTransitionNode();

    void setLastTransition(LocalizedText value);

    ProgramDiagnosticDataType getProgramDiagnostics();

    ProgramDiagnosticType getProgramDiagnosticsNode();

    void setProgramDiagnostics(ProgramDiagnosticDataType value);
}
