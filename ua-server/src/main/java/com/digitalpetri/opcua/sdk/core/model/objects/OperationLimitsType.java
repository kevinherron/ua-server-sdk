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

import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface OperationLimitsType extends BaseObjectType {

    UInteger getMaxNodesPerRead();

    UInteger getMaxNodesPerHistoryReadData();

    UInteger getMaxNodesPerHistoryReadEvents();

    UInteger getMaxNodesPerWrite();

    UInteger getMaxNodesPerHistoryUpdateData();

    UInteger getMaxNodesPerHistoryUpdateEvents();

    UInteger getMaxNodesPerMethodCall();

    UInteger getMaxNodesPerBrowse();

    UInteger getMaxNodesPerRegisterNodes();

    UInteger getMaxNodesPerTranslateBrowsePathsToNodeIds();

    UInteger getMaxNodesPerNodeManagement();

    UInteger getMaxMonitoredItemsPerCall();

    void setMaxNodesPerRead(UInteger maxNodesPerRead);

    void setMaxNodesPerHistoryReadData(UInteger maxNodesPerHistoryReadData);

    void setMaxNodesPerHistoryReadEvents(UInteger maxNodesPerHistoryReadEvents);

    void setMaxNodesPerWrite(UInteger maxNodesPerWrite);

    void setMaxNodesPerHistoryUpdateData(UInteger maxNodesPerHistoryUpdateData);

    void setMaxNodesPerHistoryUpdateEvents(UInteger maxNodesPerHistoryUpdateEvents);

    void setMaxNodesPerMethodCall(UInteger maxNodesPerMethodCall);

    void setMaxNodesPerBrowse(UInteger maxNodesPerBrowse);

    void setMaxNodesPerRegisterNodes(UInteger maxNodesPerRegisterNodes);

    void setMaxNodesPerTranslateBrowsePathsToNodeIds(UInteger maxNodesPerTranslateBrowsePathsToNodeIds);

    void setMaxNodesPerNodeManagement(UInteger maxNodesPerNodeManagement);

    void setMaxMonitoredItemsPerCall(UInteger maxMonitoredItemsPerCall);

}
