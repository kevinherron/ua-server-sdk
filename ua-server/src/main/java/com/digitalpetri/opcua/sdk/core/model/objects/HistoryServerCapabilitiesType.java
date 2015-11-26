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

public interface HistoryServerCapabilitiesType extends BaseObjectType {

    Property<Boolean> ACCESS_HISTORY_DATA_CAPABILITY = new Property.BasicProperty<>(
            QualifiedName.parse("0:AccessHistoryDataCapability"),
            NodeId.parse("ns=0;i=1"),
            -1,
            Boolean.class
    );

    Property<Boolean> ACCESS_HISTORY_EVENTS_CAPABILITY = new Property.BasicProperty<>(
            QualifiedName.parse("0:AccessHistoryEventsCapability"),
            NodeId.parse("ns=0;i=1"),
            -1,
            Boolean.class
    );

    Property<UInteger> MAX_RETURN_DATA_VALUES = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxReturnDataValues"),
            NodeId.parse("ns=0;i=7"),
            -1,
            UInteger.class
    );

    Property<UInteger> MAX_RETURN_EVENT_VALUES = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxReturnEventValues"),
            NodeId.parse("ns=0;i=7"),
            -1,
            UInteger.class
    );

    Property<Boolean> INSERT_DATA_CAPABILITY = new Property.BasicProperty<>(
            QualifiedName.parse("0:InsertDataCapability"),
            NodeId.parse("ns=0;i=1"),
            -1,
            Boolean.class
    );

    Property<Boolean> REPLACE_DATA_CAPABILITY = new Property.BasicProperty<>(
            QualifiedName.parse("0:ReplaceDataCapability"),
            NodeId.parse("ns=0;i=1"),
            -1,
            Boolean.class
    );

    Property<Boolean> UPDATE_DATA_CAPABILITY = new Property.BasicProperty<>(
            QualifiedName.parse("0:UpdateDataCapability"),
            NodeId.parse("ns=0;i=1"),
            -1,
            Boolean.class
    );

    Property<Boolean> DELETE_RAW_CAPABILITY = new Property.BasicProperty<>(
            QualifiedName.parse("0:DeleteRawCapability"),
            NodeId.parse("ns=0;i=1"),
            -1,
            Boolean.class
    );

    Property<Boolean> DELETE_AT_TIME_CAPABILITY = new Property.BasicProperty<>(
            QualifiedName.parse("0:DeleteAtTimeCapability"),
            NodeId.parse("ns=0;i=1"),
            -1,
            Boolean.class
    );

    Property<Boolean> INSERT_EVENT_CAPABILITY = new Property.BasicProperty<>(
            QualifiedName.parse("0:InsertEventCapability"),
            NodeId.parse("ns=0;i=1"),
            -1,
            Boolean.class
    );

    Property<Boolean> REPLACE_EVENT_CAPABILITY = new Property.BasicProperty<>(
            QualifiedName.parse("0:ReplaceEventCapability"),
            NodeId.parse("ns=0;i=1"),
            -1,
            Boolean.class
    );

    Property<Boolean> UPDATE_EVENT_CAPABILITY = new Property.BasicProperty<>(
            QualifiedName.parse("0:UpdateEventCapability"),
            NodeId.parse("ns=0;i=1"),
            -1,
            Boolean.class
    );

    Property<Boolean> DELETE_EVENT_CAPABILITY = new Property.BasicProperty<>(
            QualifiedName.parse("0:DeleteEventCapability"),
            NodeId.parse("ns=0;i=1"),
            -1,
            Boolean.class
    );

    Property<Boolean> INSERT_ANNOTATION_CAPABILITY = new Property.BasicProperty<>(
            QualifiedName.parse("0:InsertAnnotationCapability"),
            NodeId.parse("ns=0;i=1"),
            -1,
            Boolean.class
    );

    Boolean getAccessHistoryDataCapability();

    PropertyType getAccessHistoryDataCapabilityNode();

    void setAccessHistoryDataCapability(Boolean value);

    Boolean getAccessHistoryEventsCapability();

    PropertyType getAccessHistoryEventsCapabilityNode();

    void setAccessHistoryEventsCapability(Boolean value);

    UInteger getMaxReturnDataValues();

    PropertyType getMaxReturnDataValuesNode();

    void setMaxReturnDataValues(UInteger value);

    UInteger getMaxReturnEventValues();

    PropertyType getMaxReturnEventValuesNode();

    void setMaxReturnEventValues(UInteger value);

    Boolean getInsertDataCapability();

    PropertyType getInsertDataCapabilityNode();

    void setInsertDataCapability(Boolean value);

    Boolean getReplaceDataCapability();

    PropertyType getReplaceDataCapabilityNode();

    void setReplaceDataCapability(Boolean value);

    Boolean getUpdateDataCapability();

    PropertyType getUpdateDataCapabilityNode();

    void setUpdateDataCapability(Boolean value);

    Boolean getDeleteRawCapability();

    PropertyType getDeleteRawCapabilityNode();

    void setDeleteRawCapability(Boolean value);

    Boolean getDeleteAtTimeCapability();

    PropertyType getDeleteAtTimeCapabilityNode();

    void setDeleteAtTimeCapability(Boolean value);

    Boolean getInsertEventCapability();

    PropertyType getInsertEventCapabilityNode();

    void setInsertEventCapability(Boolean value);

    Boolean getReplaceEventCapability();

    PropertyType getReplaceEventCapabilityNode();

    void setReplaceEventCapability(Boolean value);

    Boolean getUpdateEventCapability();

    PropertyType getUpdateEventCapabilityNode();

    void setUpdateEventCapability(Boolean value);

    Boolean getDeleteEventCapability();

    PropertyType getDeleteEventCapabilityNode();

    void setDeleteEventCapability(Boolean value);

    Boolean getInsertAnnotationCapability();

    PropertyType getInsertAnnotationCapabilityNode();

    void setInsertAnnotationCapability(Boolean value);

    FolderType getAggregateFunctionsNode();

}
