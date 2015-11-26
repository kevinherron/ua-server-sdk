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
import com.digitalpetri.opcua.sdk.core.model.variables.PropertyType;
import com.digitalpetri.opcua.sdk.core.model.variables.TwoStateVariableType;
import com.digitalpetri.opcua.sdk.server.model.Property;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;

public interface ConditionType extends BaseEventType {

    Property<NodeId> CONDITION_CLASS_ID = new Property.BasicProperty<>(
            QualifiedName.parse("0:ConditionClassId"),
            NodeId.parse("ns=0;i=17"),
            -1,
            NodeId.class
    );

    Property<LocalizedText> CONDITION_CLASS_NAME = new Property.BasicProperty<>(
            QualifiedName.parse("0:ConditionClassName"),
            NodeId.parse("ns=0;i=21"),
            -1,
            LocalizedText.class
    );

    Property<String> CONDITION_NAME = new Property.BasicProperty<>(
            QualifiedName.parse("0:ConditionName"),
            NodeId.parse("ns=0;i=12"),
            -1,
            String.class
    );

    Property<NodeId> BRANCH_ID = new Property.BasicProperty<>(
            QualifiedName.parse("0:BranchId"),
            NodeId.parse("ns=0;i=17"),
            -1,
            NodeId.class
    );

    Property<Boolean> RETAIN = new Property.BasicProperty<>(
            QualifiedName.parse("0:Retain"),
            NodeId.parse("ns=0;i=1"),
            -1,
            Boolean.class
    );

    Property<String> CLIENT_USER_ID = new Property.BasicProperty<>(
            QualifiedName.parse("0:ClientUserId"),
            NodeId.parse("ns=0;i=12"),
            -1,
            String.class
    );

    NodeId getConditionClassId();

    PropertyType getConditionClassIdNode();

    void setConditionClassId(NodeId value);

    LocalizedText getConditionClassName();

    PropertyType getConditionClassNameNode();

    void setConditionClassName(LocalizedText value);

    String getConditionName();

    PropertyType getConditionNameNode();

    void setConditionName(String value);

    NodeId getBranchId();

    PropertyType getBranchIdNode();

    void setBranchId(NodeId value);

    Boolean getRetain();

    PropertyType getRetainNode();

    void setRetain(Boolean value);

    String getClientUserId();

    PropertyType getClientUserIdNode();

    void setClientUserId(String value);

    LocalizedText getEnabledState();

    TwoStateVariableType getEnabledStateNode();

    void setEnabledState(LocalizedText value);

    StatusCode getQuality();

    ConditionVariableType getQualityNode();

    void setQuality(StatusCode value);

    UShort getLastSeverity();

    ConditionVariableType getLastSeverityNode();

    void setLastSeverity(UShort value);

    LocalizedText getComment();

    ConditionVariableType getCommentNode();

    void setComment(LocalizedText value);
}
