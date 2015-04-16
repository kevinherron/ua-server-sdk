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
