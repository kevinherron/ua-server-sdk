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
import com.digitalpetri.opcua.stack.core.types.enumerated.PerformUpdateType;
import com.digitalpetri.opcua.stack.core.types.structured.EventFilter;
import com.digitalpetri.opcua.stack.core.types.structured.HistoryEventFieldList;

public interface AuditHistoryEventUpdateEventType extends AuditHistoryUpdateEventType {

    Property<NodeId> UPDATED_NODE = new Property.BasicProperty<>(
            QualifiedName.parse("0:UpdatedNode"),
            NodeId.parse("ns=0;i=17"),
            -1,
            NodeId.class
    );

    Property<PerformUpdateType> PERFORM_INSERT_REPLACE = new Property.BasicProperty<>(
            QualifiedName.parse("0:PerformInsertReplace"),
            NodeId.parse("ns=0;i=11293"),
            -1,
            PerformUpdateType.class
    );

    Property<EventFilter> FILTER = new Property.BasicProperty<>(
            QualifiedName.parse("0:Filter"),
            NodeId.parse("ns=0;i=725"),
            -1,
            EventFilter.class
    );

    Property<HistoryEventFieldList[]> NEW_VALUES = new Property.BasicProperty<>(
            QualifiedName.parse("0:NewValues"),
            NodeId.parse("ns=0;i=920"),
            1,
            HistoryEventFieldList[].class
    );

    Property<HistoryEventFieldList[]> OLD_VALUES = new Property.BasicProperty<>(
            QualifiedName.parse("0:OldValues"),
            NodeId.parse("ns=0;i=920"),
            1,
            HistoryEventFieldList[].class
    );

    NodeId getUpdatedNode();

    PropertyType getUpdatedNodeNode();

    void setUpdatedNode(NodeId value);

    PerformUpdateType getPerformInsertReplace();

    PropertyType getPerformInsertReplaceNode();

    void setPerformInsertReplace(PerformUpdateType value);

    EventFilter getFilter();

    PropertyType getFilterNode();

    void setFilter(EventFilter value);

    HistoryEventFieldList[] getNewValues();

    PropertyType getNewValuesNode();

    void setNewValues(HistoryEventFieldList[] value);

    HistoryEventFieldList[] getOldValues();

    PropertyType getOldValuesNode();

    void setOldValues(HistoryEventFieldList[] value);
}
