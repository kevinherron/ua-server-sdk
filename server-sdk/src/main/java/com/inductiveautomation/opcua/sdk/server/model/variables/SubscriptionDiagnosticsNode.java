package com.inductiveautomation.opcua.sdk.server.model.variables;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.core.model.UaMandatory;
import com.inductiveautomation.opcua.sdk.core.model.variables.SubscriptionDiagnosticsType;
import com.inductiveautomation.opcua.sdk.core.nodes.VariableNode;
import com.inductiveautomation.opcua.sdk.server.api.UaNodeManager;
import com.inductiveautomation.opcua.sdk.server.util.UaVariableType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.structured.SubscriptionDiagnosticsDataType;

@UaVariableType(name = "SubscriptionDiagnosticsType")
public class SubscriptionDiagnosticsNode extends BaseDataVariableNode implements SubscriptionDiagnosticsType {

    public SubscriptionDiagnosticsNode(UaNodeManager nodeManager,
                                       NodeId nodeId,
                                       QualifiedName browseName,
                                       LocalizedText displayName,
                                       Optional<LocalizedText> description,
                                       Optional<UInteger> writeMask,
                                       Optional<UInteger> userWriteMask,
                                       DataValue value,
                                       NodeId dataType,
                                       Integer valueRank,
                                       Optional<UInteger[]> arrayDimensions,
                                       UByte accessLevel,
                                       UByte userAccessLevel,
                                       Optional<Double> minimumSamplingInterval,
                                       boolean historizing) {

        super(nodeManager, nodeId, browseName, displayName, description, writeMask, userWriteMask,
                value, dataType, valueRank, arrayDimensions, accessLevel, userAccessLevel, minimumSamplingInterval, historizing);

    }

    @Override
    public DataValue getValue() {
        SubscriptionDiagnosticsDataType value = new SubscriptionDiagnosticsDataType(
                getSessionId(),
                getSubscriptionId(),
                getPriority(),
                getPublishingInterval(),
                getMaxKeepAliveCount(),
                getMaxLifetimeCount(),
                getMaxNotificationsPerPublish(),
                getPublishingEnabled(),
                getModifyCount(),
                getEnableCount(),
                getDisableCount(),
                getRepublishRequestCount(),
                getRepublishMessageRequestCount(),
                getRepublishMessageCount(),
                getTransferRequestCount(),
                getTransferredToAltClientCount(),
                getTransferredToSameClientCount(),
                getPublishRequestCount(),
                getDataChangeNotificationsCount(),
                getEventNotificationsCount(),
                getNotificationsCount(),
                getLatePublishRequestCount(),
                getCurrentKeepAliveCount(),
                getCurrentLifetimeCount(),
                getUnacknowledgedMessageCount(),
                getDiscardedMessageCount(),
                getMonitoredItemCount(),
                getDisabledMonitoredItemCount(),
                getMonitoringQueueOverflowCount(),
                getNextSequenceNumber(),
                getEventQueueOverFlowCount()
        );

        return new DataValue(new Variant(value));
    }

    @Override
    public synchronized void setValue(DataValue value) {
        SubscriptionDiagnosticsDataType v = (SubscriptionDiagnosticsDataType) value.getValue().getValue();

        setSessionId(v.getSessionId());
        setSubscriptionId(v.getSubscriptionId());
        setPriority(v.getPriority());
        setPublishingInterval(v.getPublishingInterval());
        setMaxKeepAliveCount(v.getMaxKeepAliveCount());
        setMaxLifetimeCount(v.getMaxLifetimeCount());
        setMaxNotificationsPerPublish(v.getMaxNotificationsPerPublish());
        setPublishingEnabled(v.getPublishingEnabled());
        setModifyCount(v.getModifyCount());
        setEnableCount(v.getEnableCount());
        setDisableCount(v.getDisableCount());
        setRepublishRequestCount(v.getRepublishRequestCount());
        setRepublishMessageRequestCount(v.getRepublishMessageRequestCount());
        setRepublishMessageCount(v.getRepublishMessageCount());
        setTransferRequestCount(v.getTransferRequestCount());
        setTransferredToAltClientCount(v.getTransferredToAltClientCount());
        setTransferredToSameClientCount(v.getTransferredToSameClientCount());
        setPublishRequestCount(v.getPublishRequestCount());
        setDataChangeNotificationsCount(v.getDataChangeNotificationsCount());
        setEventNotificationsCount(v.getEventNotificationsCount());
        setNotificationsCount(v.getNotificationsCount());
        setLatePublishRequestCount(v.getLatePublishRequestCount());
        setCurrentKeepAliveCount(v.getCurrentKeepAliveCount());
        setCurrentLifetimeCount(v.getCurrentLifetimeCount());
        setUnacknowledgedMessageCount(v.getUnacknowledgedMessageCount());
        setDiscardedMessageCount(v.getDiscardedMessageCount());
        setMonitoredItemCount(v.getMonitoredItemCount());
        setDisabledMonitoredItemCount(v.getDisabledMonitoredItemCount());
        setMonitoringQueueOverflowCount(v.getMonitoringQueueOverflowCount());
        setNextSequenceNumber(v.getNextSequenceNumber());
        setEventQueueOverFlowCount(v.getEventQueueOverFlowCount());

        fireAttributeChanged(AttributeIds.Value, value);
    }

    @Override
    @UaMandatory("SessionId")
    public NodeId getSessionId() {
        Optional<VariableNode> node = getVariableComponent("SessionId");

        return node.map(n -> (NodeId) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("SubscriptionId")
    public UInteger getSubscriptionId() {
        Optional<VariableNode> node = getVariableComponent("SubscriptionId");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("Priority")
    public UByte getPriority() {
        Optional<VariableNode> node = getVariableComponent("Priority");

        return node.map(n -> (UByte) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("PublishingInterval")
    public Double getPublishingInterval() {
        Optional<VariableNode> node = getVariableComponent("PublishingInterval");

        return node.map(n -> (Double) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("MaxKeepAliveCount")
    public UInteger getMaxKeepAliveCount() {
        Optional<VariableNode> node = getVariableComponent("MaxKeepAliveCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("MaxLifetimeCount")
    public UInteger getMaxLifetimeCount() {
        Optional<VariableNode> node = getVariableComponent("MaxLifetimeCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("MaxNotificationsPerPublish")
    public UInteger getMaxNotificationsPerPublish() {
        Optional<VariableNode> node = getVariableComponent("MaxNotificationsPerPublish");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("PublishingEnabled")
    public Boolean getPublishingEnabled() {
        Optional<VariableNode> node = getVariableComponent("PublishingEnabled");

        return node.map(n -> (Boolean) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("ModifyCount")
    public UInteger getModifyCount() {
        Optional<VariableNode> node = getVariableComponent("ModifyCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("EnableCount")
    public UInteger getEnableCount() {
        Optional<VariableNode> node = getVariableComponent("EnableCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("DisableCount")
    public UInteger getDisableCount() {
        Optional<VariableNode> node = getVariableComponent("DisableCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("RepublishRequestCount")
    public UInteger getRepublishRequestCount() {
        Optional<VariableNode> node = getVariableComponent("RepublishRequestCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("RepublishMessageRequestCount")
    public UInteger getRepublishMessageRequestCount() {
        Optional<VariableNode> node = getVariableComponent("RepublishMessageRequestCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("RepublishMessageCount")
    public UInteger getRepublishMessageCount() {
        Optional<VariableNode> node = getVariableComponent("RepublishMessageCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("TransferRequestCount")
    public UInteger getTransferRequestCount() {
        Optional<VariableNode> node = getVariableComponent("TransferRequestCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("TransferredToAltClientCount")
    public UInteger getTransferredToAltClientCount() {
        Optional<VariableNode> node = getVariableComponent("TransferredToAltClientCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("TransferredToSameClientCount")
    public UInteger getTransferredToSameClientCount() {
        Optional<VariableNode> node = getVariableComponent("TransferredToSameClientCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("PublishRequestCount")
    public UInteger getPublishRequestCount() {
        Optional<VariableNode> node = getVariableComponent("PublishRequestCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("DataChangeNotificationsCount")
    public UInteger getDataChangeNotificationsCount() {
        Optional<VariableNode> node = getVariableComponent("DataChangeNotificationsCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("EventNotificationsCount")
    public UInteger getEventNotificationsCount() {
        Optional<VariableNode> node = getVariableComponent("EventNotificationsCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("NotificationsCount")
    public UInteger getNotificationsCount() {
        Optional<VariableNode> node = getVariableComponent("NotificationsCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("LatePublishRequestCount")
    public UInteger getLatePublishRequestCount() {
        Optional<VariableNode> node = getVariableComponent("LatePublishRequestCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("CurrentKeepAliveCount")
    public UInteger getCurrentKeepAliveCount() {
        Optional<VariableNode> node = getVariableComponent("CurrentKeepAliveCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("CurrentLifetimeCount")
    public UInteger getCurrentLifetimeCount() {
        Optional<VariableNode> node = getVariableComponent("CurrentLifetimeCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("UnacknowledgedMessageCount")
    public UInteger getUnacknowledgedMessageCount() {
        Optional<VariableNode> node = getVariableComponent("UnacknowledgedMessageCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("DiscardedMessageCount")
    public UInteger getDiscardedMessageCount() {
        Optional<VariableNode> node = getVariableComponent("DiscardedMessageCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("MonitoredItemCount")
    public UInteger getMonitoredItemCount() {
        Optional<VariableNode> node = getVariableComponent("MonitoredItemCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("DisabledMonitoredItemCount")
    public UInteger getDisabledMonitoredItemCount() {
        Optional<VariableNode> node = getVariableComponent("DisabledMonitoredItemCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("MonitoringQueueOverflowCount")
    public UInteger getMonitoringQueueOverflowCount() {
        Optional<VariableNode> node = getVariableComponent("MonitoringQueueOverflowCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("NextSequenceNumber")
    public UInteger getNextSequenceNumber() {
        Optional<VariableNode> node = getVariableComponent("NextSequenceNumber");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("EventQueueOverFlowCount")
    public UInteger getEventQueueOverFlowCount() {
        Optional<VariableNode> node = getVariableComponent("EventQueueOverFlowCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public synchronized void setSessionId(NodeId sessionId) {
        getVariableComponent("SessionId").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(sessionId)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setSubscriptionId(UInteger subscriptionId) {
        getVariableComponent("SubscriptionId").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(subscriptionId)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setPriority(UByte priority) {
        getVariableComponent("Priority").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(priority)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setPublishingInterval(Double publishingInterval) {
        getVariableComponent("PublishingInterval").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(publishingInterval)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setMaxKeepAliveCount(UInteger maxKeepAliveCount) {
        getVariableComponent("MaxKeepAliveCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxKeepAliveCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setMaxLifetimeCount(UInteger maxLifetimeCount) {
        getVariableComponent("MaxLifetimeCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxLifetimeCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setMaxNotificationsPerPublish(UInteger maxNotificationsPerPublish) {
        getVariableComponent("MaxNotificationsPerPublish").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxNotificationsPerPublish)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setPublishingEnabled(Boolean publishingEnabled) {
        getVariableComponent("PublishingEnabled").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(publishingEnabled)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setModifyCount(UInteger modifyCount) {
        getVariableComponent("ModifyCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(modifyCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setEnableCount(UInteger enableCount) {
        getVariableComponent("EnableCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(enableCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setDisableCount(UInteger disableCount) {
        getVariableComponent("DisableCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(disableCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setRepublishRequestCount(UInteger republishRequestCount) {
        getVariableComponent("RepublishRequestCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(republishRequestCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setRepublishMessageRequestCount(UInteger republishMessageRequestCount) {
        getVariableComponent("RepublishMessageRequestCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(republishMessageRequestCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setRepublishMessageCount(UInteger republishMessageCount) {
        getVariableComponent("RepublishMessageCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(republishMessageCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setTransferRequestCount(UInteger transferRequestCount) {
        getVariableComponent("TransferRequestCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(transferRequestCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setTransferredToAltClientCount(UInteger transferredToAltClientCount) {
        getVariableComponent("TransferredToAltClientCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(transferredToAltClientCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setTransferredToSameClientCount(UInteger transferredToSameClientCount) {
        getVariableComponent("TransferredToSameClientCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(transferredToSameClientCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setPublishRequestCount(UInteger publishRequestCount) {
        getVariableComponent("PublishRequestCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(publishRequestCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setDataChangeNotificationsCount(UInteger dataChangeNotificationsCount) {
        getVariableComponent("DataChangeNotificationsCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(dataChangeNotificationsCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setEventNotificationsCount(UInteger eventNotificationsCount) {
        getVariableComponent("EventNotificationsCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(eventNotificationsCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setNotificationsCount(UInteger notificationsCount) {
        getVariableComponent("NotificationsCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(notificationsCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setLatePublishRequestCount(UInteger latePublishRequestCount) {
        getVariableComponent("LatePublishRequestCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(latePublishRequestCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setCurrentKeepAliveCount(UInteger currentKeepAliveCount) {
        getVariableComponent("CurrentKeepAliveCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(currentKeepAliveCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setCurrentLifetimeCount(UInteger currentLifetimeCount) {
        getVariableComponent("CurrentLifetimeCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(currentLifetimeCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setUnacknowledgedMessageCount(UInteger unacknowledgedMessageCount) {
        getVariableComponent("UnacknowledgedMessageCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(unacknowledgedMessageCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setDiscardedMessageCount(UInteger discardedMessageCount) {
        getVariableComponent("DiscardedMessageCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(discardedMessageCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setMonitoredItemCount(UInteger monitoredItemCount) {
        getVariableComponent("MonitoredItemCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(monitoredItemCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setDisabledMonitoredItemCount(UInteger disabledMonitoredItemCount) {
        getVariableComponent("DisabledMonitoredItemCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(disabledMonitoredItemCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setMonitoringQueueOverflowCount(UInteger monitoringQueueOverflowCount) {
        getVariableComponent("MonitoringQueueOverflowCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(monitoringQueueOverflowCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setNextSequenceNumber(UInteger nextSequenceNumber) {
        getVariableComponent("NextSequenceNumber").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(nextSequenceNumber)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setEventQueueOverFlowCount(UInteger eventQueueOverFlowCount) {
        getVariableComponent("EventQueueOverFlowCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(eventQueueOverFlowCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void atomicAction(Runnable runnable) {
        runnable.run();
    }

}
