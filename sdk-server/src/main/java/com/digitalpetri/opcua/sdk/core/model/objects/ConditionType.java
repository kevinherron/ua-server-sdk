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

import com.digitalpetri.opcua.sdk.core.model.variables.ConditionVariableType;
import com.digitalpetri.opcua.sdk.core.model.variables.TwoStateVariableType;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public interface ConditionType extends BaseEventType {

    NodeId getConditionClassId();

    LocalizedText getConditionClassName();

    String getConditionName();

    NodeId getBranchId();

    Boolean getRetain();

    TwoStateVariableType getEnabledState();

    ConditionVariableType getQuality();

    ConditionVariableType getLastSeverity();

    ConditionVariableType getComment();

    String getClientUserId();

    void setConditionClassId(NodeId conditionClassId);

    void setConditionClassName(LocalizedText conditionClassName);

    void setConditionName(String conditionName);

    void setBranchId(NodeId branchId);

    void setRetain(Boolean retain);

    void setClientUserId(String clientUserId);

}
