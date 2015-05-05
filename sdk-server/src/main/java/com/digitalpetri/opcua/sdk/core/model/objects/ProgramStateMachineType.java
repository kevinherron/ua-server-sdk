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
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface ProgramStateMachineType extends FiniteStateMachineType {

    FiniteStateVariableType getCurrentState();

    FiniteTransitionVariableType getLastTransition();

    Boolean getCreatable();

    Boolean getDeletable();

    Boolean getAutoDelete();

    Integer getRecycleCount();

    UInteger getInstanceCount();

    UInteger getMaxInstanceCount();

    UInteger getMaxRecycleCount();

    ProgramDiagnosticType getProgramDiagnostics();

    BaseObjectType getFinalResultData();

    StateType getReady();

    StateType getRunning();

    StateType getSuspended();

    StateType getHalted();

    TransitionType getHaltedToReady();

    TransitionType getReadyToRunning();

    TransitionType getRunningToHalted();

    TransitionType getRunningToReady();

    TransitionType getRunningToSuspended();

    TransitionType getSuspendedToRunning();

    TransitionType getSuspendedToHalted();

    TransitionType getSuspendedToReady();

    TransitionType getReadyToHalted();

    void setCreatable(Boolean creatable);

    void setDeletable(Boolean deletable);

    void setAutoDelete(Boolean autoDelete);

    void setRecycleCount(Integer recycleCount);

    void setInstanceCount(UInteger instanceCount);

    void setMaxInstanceCount(UInteger maxInstanceCount);

    void setMaxRecycleCount(UInteger maxRecycleCount);

}
