package com.inductiveautomation.opcua.sdk.server.objects;

import com.inductiveautomation.opcua.stack.core.util.annotations.UInt32;

public interface OperationLimits {

    public static final int DEFAULT_MAX = 0xFFFF; // 65535

    /**
     * @return the maximum size of the nodesToRead array when a Client calls the Read Service.
     */
    @UInt32
    default long getMaxNodesPerRead() { return DEFAULT_MAX; }

    /**
     * @return the maximum size of the nodesToRead array when a Client calls the HistoryRead Service using the
     * historyReadDetails Raw, PROCESSED, MODIFIED or ATTIME.
     */
    @UInt32
    default long getMaxNodesPerHistoryReadData() { return DEFAULT_MAX; }

    /**
     * @return the maximum size of the nodesToRead array when a Client calls the HistoryRead Service using the
     * historyReadDetails EVENTS.
     */
    @UInt32
    default long getMaxNodesPerHistoryReadEvents() { return DEFAULT_MAX; }

    /**
     * @return the maximum size of the nodesToWrite array when a Client calls the Write Service.
     */
    @UInt32
    default long getMaxNodesPerWrite() { return DEFAULT_MAX; }

    /**
     * @return the maximum size of the historyUpdateDetails array supported by the Server when a Client calls the
     * HistoryUpdate Service using historyReadDetails RAW, PROCESSED, MODIFIED or ATTIME.
     */
    @UInt32
    default long getMaxNodesPerHistoryUpdateData() { return DEFAULT_MAX; }

    /**
     * @return the maximum size of the historyUpdateDetails array when a Client calls the HistoryUpdate Service using
     * historyReadDetails EVENTS.
     */
    @UInt32
    default long getMaxNodesPerHistoryUpdateEvents() { return DEFAULT_MAX; }

    /**
     * @return the maximum size of the methodsToCall array when a Client calls the Call Service.
     */
    @UInt32
    default long getMaxNodesPerMethodCall() { return DEFAULT_MAX; }

    /**
     * @return the maximum size of the nodesToBrowse array when calling the Browse Service or the continuationPoints
     * array when a Client calls the BrowseNext Service.
     */
    @UInt32
    default long getMaxNodesPerBrowse() { return DEFAULT_MAX; }

    /**
     * @return the maximum size of the nodesToRegister array when a Client calls the RegisterNodes Service and the
     * maximum size of the nodesToUnregister when calling the UnregisterNodes Service.
     */
    @UInt32
    default long getMaxNodesPerRegisterNodes() { return DEFAULT_MAX; }

    /**
     * @return the maximum size of the browsePaths array when a Client calls the TranslateBrowsePathsToNodeIds Service.
     */
    @UInt32
    default long getMaxNodesPerTranslateBrowsePaths() { return DEFAULT_MAX; }

    /**
     * @return the maximum size of the nodesToAdd array when a Client calls the AddNodes Service, the maximum size of
     * the referencesToAdd array when a Client calls the AddReferences Service, the maximum size of the nodesToDelete
     * array when a Client calls the DeleteNodes Service, and the maximum size of the referencesToDelete array when a
     * Client calls the DeleteReferences Service.
     */
    @UInt32
    default long getMaxNodesPerNodeManagement() { return DEFAULT_MAX; }

    /**
     * @return the maximum size of the itemsToCreate array when a Client calls the CreateMonitoredItems Service, the
     * maximum size of the itemsToModify array when a Client calls the ModifyMonitoredItems Service, the maximum size
     * of the monitoredItemIds array when a Client calls the SetMonitoringMode Service, and the maximum size of the
     * linksToAdd and the linksToRemove arrays when a Client calls the SetTriggering Service.
     */
    @UInt32
    default long getMaxMonitoredItemsPerCall() { return DEFAULT_MAX; }

}
