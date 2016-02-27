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

public interface OperationLimitsType extends BaseObjectType {

    Property<UInteger> MAX_NODES_PER_READ = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxNodesPerRead"),
            NodeId.parse("ns=0;i=7"),
            -1,
            UInteger.class
    );

    Property<UInteger> MAX_NODES_PER_HISTORY_READ_DATA = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxNodesPerHistoryReadData"),
            NodeId.parse("ns=0;i=7"),
            -1,
            UInteger.class
    );

    Property<UInteger> MAX_NODES_PER_HISTORY_READ_EVENTS = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxNodesPerHistoryReadEvents"),
            NodeId.parse("ns=0;i=7"),
            -1,
            UInteger.class
    );

    Property<UInteger> MAX_NODES_PER_WRITE = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxNodesPerWrite"),
            NodeId.parse("ns=0;i=7"),
            -1,
            UInteger.class
    );

    Property<UInteger> MAX_NODES_PER_HISTORY_UPDATE_DATA = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxNodesPerHistoryUpdateData"),
            NodeId.parse("ns=0;i=7"),
            -1,
            UInteger.class
    );

    Property<UInteger> MAX_NODES_PER_HISTORY_UPDATE_EVENTS = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxNodesPerHistoryUpdateEvents"),
            NodeId.parse("ns=0;i=7"),
            -1,
            UInteger.class
    );

    Property<UInteger> MAX_NODES_PER_METHOD_CALL = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxNodesPerMethodCall"),
            NodeId.parse("ns=0;i=7"),
            -1,
            UInteger.class
    );

    Property<UInteger> MAX_NODES_PER_BROWSE = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxNodesPerBrowse"),
            NodeId.parse("ns=0;i=7"),
            -1,
            UInteger.class
    );

    Property<UInteger> MAX_NODES_PER_REGISTER_NODES = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxNodesPerRegisterNodes"),
            NodeId.parse("ns=0;i=7"),
            -1,
            UInteger.class
    );

    Property<UInteger> MAX_NODES_PER_TRANSLATE_BROWSE_PATHS_TO_NODE_IDS = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxNodesPerTranslateBrowsePathsToNodeIds"),
            NodeId.parse("ns=0;i=7"),
            -1,
            UInteger.class
    );

    Property<UInteger> MAX_NODES_PER_NODE_MANAGEMENT = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxNodesPerNodeManagement"),
            NodeId.parse("ns=0;i=7"),
            -1,
            UInteger.class
    );

    Property<UInteger> MAX_MONITORED_ITEMS_PER_CALL = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxMonitoredItemsPerCall"),
            NodeId.parse("ns=0;i=7"),
            -1,
            UInteger.class
    );

    UInteger getMaxNodesPerRead();

    PropertyType getMaxNodesPerReadNode();

    void setMaxNodesPerRead(UInteger value);

    UInteger getMaxNodesPerHistoryReadData();

    PropertyType getMaxNodesPerHistoryReadDataNode();

    void setMaxNodesPerHistoryReadData(UInteger value);

    UInteger getMaxNodesPerHistoryReadEvents();

    PropertyType getMaxNodesPerHistoryReadEventsNode();

    void setMaxNodesPerHistoryReadEvents(UInteger value);

    UInteger getMaxNodesPerWrite();

    PropertyType getMaxNodesPerWriteNode();

    void setMaxNodesPerWrite(UInteger value);

    UInteger getMaxNodesPerHistoryUpdateData();

    PropertyType getMaxNodesPerHistoryUpdateDataNode();

    void setMaxNodesPerHistoryUpdateData(UInteger value);

    UInteger getMaxNodesPerHistoryUpdateEvents();

    PropertyType getMaxNodesPerHistoryUpdateEventsNode();

    void setMaxNodesPerHistoryUpdateEvents(UInteger value);

    UInteger getMaxNodesPerMethodCall();

    PropertyType getMaxNodesPerMethodCallNode();

    void setMaxNodesPerMethodCall(UInteger value);

    UInteger getMaxNodesPerBrowse();

    PropertyType getMaxNodesPerBrowseNode();

    void setMaxNodesPerBrowse(UInteger value);

    UInteger getMaxNodesPerRegisterNodes();

    PropertyType getMaxNodesPerRegisterNodesNode();

    void setMaxNodesPerRegisterNodes(UInteger value);

    UInteger getMaxNodesPerTranslateBrowsePathsToNodeIds();

    PropertyType getMaxNodesPerTranslateBrowsePathsToNodeIdsNode();

    void setMaxNodesPerTranslateBrowsePathsToNodeIds(UInteger value);

    UInteger getMaxNodesPerNodeManagement();

    PropertyType getMaxNodesPerNodeManagementNode();

    void setMaxNodesPerNodeManagement(UInteger value);

    UInteger getMaxMonitoredItemsPerCall();

    PropertyType getMaxMonitoredItemsPerCallNode();

    void setMaxMonitoredItemsPerCall(UInteger value);
}
