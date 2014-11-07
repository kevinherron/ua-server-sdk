package com.inductiveautomation.opcua.sdk.server.model.objects;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.model.objects.OperationLimitsType;
import com.inductiveautomation.opcua.sdk.server.api.UaNamespace;
import com.inductiveautomation.opcua.sdk.server.util.UaObjectType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;


@UaObjectType(name = "OperationLimitsType")
public class OperationLimitsNode extends BaseObjectNode implements OperationLimitsType {

    public OperationLimitsNode(
            UaNamespace namespace,
            NodeId nodeId,
            QualifiedName browseName,
            LocalizedText displayName,
            Optional<LocalizedText> description,
            Optional<UInteger> writeMask,
            Optional<UInteger> userWriteMask,
            UByte eventNotifier) {

        super(namespace, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
    }

    public UInteger getMaxNodesPerRead() {
        Optional<UInteger> maxNodesPerRead = getProperty("MaxNodesPerRead");

        return maxNodesPerRead.orElse(null);
    }

    public UInteger getMaxNodesPerHistoryReadData() {
        Optional<UInteger> maxNodesPerHistoryReadData = getProperty("MaxNodesPerHistoryReadData");

        return maxNodesPerHistoryReadData.orElse(null);
    }

    public UInteger getMaxNodesPerHistoryReadEvents() {
        Optional<UInteger> maxNodesPerHistoryReadEvents = getProperty("MaxNodesPerHistoryReadEvents");

        return maxNodesPerHistoryReadEvents.orElse(null);
    }

    public UInteger getMaxNodesPerWrite() {
        Optional<UInteger> maxNodesPerWrite = getProperty("MaxNodesPerWrite");

        return maxNodesPerWrite.orElse(null);
    }

    public UInteger getMaxNodesPerHistoryUpdateData() {
        Optional<UInteger> maxNodesPerHistoryUpdateData = getProperty("MaxNodesPerHistoryUpdateData");

        return maxNodesPerHistoryUpdateData.orElse(null);
    }

    public UInteger getMaxNodesPerHistoryUpdateEvents() {
        Optional<UInteger> maxNodesPerHistoryUpdateEvents = getProperty("MaxNodesPerHistoryUpdateEvents");

        return maxNodesPerHistoryUpdateEvents.orElse(null);
    }

    public UInteger getMaxNodesPerMethodCall() {
        Optional<UInteger> maxNodesPerMethodCall = getProperty("MaxNodesPerMethodCall");

        return maxNodesPerMethodCall.orElse(null);
    }

    public UInteger getMaxNodesPerBrowse() {
        Optional<UInteger> maxNodesPerBrowse = getProperty("MaxNodesPerBrowse");

        return maxNodesPerBrowse.orElse(null);
    }

    public UInteger getMaxNodesPerRegisterNodes() {
        Optional<UInteger> maxNodesPerRegisterNodes = getProperty("MaxNodesPerRegisterNodes");

        return maxNodesPerRegisterNodes.orElse(null);
    }

    public UInteger getMaxNodesPerTranslateBrowsePathsToNodeIds() {
        Optional<UInteger> maxNodesPerTranslateBrowsePathsToNodeIds = getProperty("MaxNodesPerTranslateBrowsePathsToNodeIds");

        return maxNodesPerTranslateBrowsePathsToNodeIds.orElse(null);
    }

    public UInteger getMaxNodesPerNodeManagement() {
        Optional<UInteger> maxNodesPerNodeManagement = getProperty("MaxNodesPerNodeManagement");

        return maxNodesPerNodeManagement.orElse(null);
    }

    public UInteger getMaxMonitoredItemsPerCall() {
        Optional<UInteger> maxMonitoredItemsPerCall = getProperty("MaxMonitoredItemsPerCall");

        return maxMonitoredItemsPerCall.orElse(null);
    }

    public synchronized void setMaxNodesPerRead(UInteger maxNodesPerRead) {
        getPropertyNode("MaxNodesPerRead").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxNodesPerRead)));
        });
    }

    public synchronized void setMaxNodesPerHistoryReadData(UInteger maxNodesPerHistoryReadData) {
        getPropertyNode("MaxNodesPerHistoryReadData").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxNodesPerHistoryReadData)));
        });
    }

    public synchronized void setMaxNodesPerHistoryReadEvents(UInteger maxNodesPerHistoryReadEvents) {
        getPropertyNode("MaxNodesPerHistoryReadEvents").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxNodesPerHistoryReadEvents)));
        });
    }

    public synchronized void setMaxNodesPerWrite(UInteger maxNodesPerWrite) {
        getPropertyNode("MaxNodesPerWrite").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxNodesPerWrite)));
        });
    }

    public synchronized void setMaxNodesPerHistoryUpdateData(UInteger maxNodesPerHistoryUpdateData) {
        getPropertyNode("MaxNodesPerHistoryUpdateData").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxNodesPerHistoryUpdateData)));
        });
    }

    public synchronized void setMaxNodesPerHistoryUpdateEvents(UInteger maxNodesPerHistoryUpdateEvents) {
        getPropertyNode("MaxNodesPerHistoryUpdateEvents").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxNodesPerHistoryUpdateEvents)));
        });
    }

    public synchronized void setMaxNodesPerMethodCall(UInteger maxNodesPerMethodCall) {
        getPropertyNode("MaxNodesPerMethodCall").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxNodesPerMethodCall)));
        });
    }

    public synchronized void setMaxNodesPerBrowse(UInteger maxNodesPerBrowse) {
        getPropertyNode("MaxNodesPerBrowse").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxNodesPerBrowse)));
        });
    }

    public synchronized void setMaxNodesPerRegisterNodes(UInteger maxNodesPerRegisterNodes) {
        getPropertyNode("MaxNodesPerRegisterNodes").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxNodesPerRegisterNodes)));
        });
    }

    public synchronized void setMaxNodesPerTranslateBrowsePathsToNodeIds(UInteger maxNodesPerTranslateBrowsePathsToNodeIds) {
        getPropertyNode("MaxNodesPerTranslateBrowsePathsToNodeIds").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxNodesPerTranslateBrowsePathsToNodeIds)));
        });
    }

    public synchronized void setMaxNodesPerNodeManagement(UInteger maxNodesPerNodeManagement) {
        getPropertyNode("MaxNodesPerNodeManagement").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxNodesPerNodeManagement)));
        });
    }

    public synchronized void setMaxMonitoredItemsPerCall(UInteger maxMonitoredItemsPerCall) {
        getPropertyNode("MaxMonitoredItemsPerCall").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxMonitoredItemsPerCall)));
        });
    }
}
