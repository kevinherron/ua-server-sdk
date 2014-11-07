package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.sdk.core.model.UaMandatory;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface SubscriptionDiagnosticsType extends BaseDataVariableType {

    @UaMandatory("SessionId")
    NodeId getSessionId();

    @UaMandatory("SubscriptionId")
    UInteger getSubscriptionId();

    @UaMandatory("Priority")
    UByte getPriority();

    @UaMandatory("PublishingInterval")
    Double getPublishingInterval();

    @UaMandatory("MaxKeepAliveCount")
    UInteger getMaxKeepAliveCount();

    @UaMandatory("MaxLifetimeCount")
    UInteger getMaxLifetimeCount();

    @UaMandatory("MaxNotificationsPerPublish")
    UInteger getMaxNotificationsPerPublish();

    @UaMandatory("PublishingEnabled")
    Boolean getPublishingEnabled();

    @UaMandatory("ModifyCount")
    UInteger getModifyCount();

    @UaMandatory("EnableCount")
    UInteger getEnableCount();

    @UaMandatory("DisableCount")
    UInteger getDisableCount();

    @UaMandatory("RepublishRequestCount")
    UInteger getRepublishRequestCount();

    @UaMandatory("RepublishMessageRequestCount")
    UInteger getRepublishMessageRequestCount();

    @UaMandatory("RepublishMessageCount")
    UInteger getRepublishMessageCount();

    @UaMandatory("TransferRequestCount")
    UInteger getTransferRequestCount();

    @UaMandatory("TransferredToAltClientCount")
    UInteger getTransferredToAltClientCount();

    @UaMandatory("TransferredToSameClientCount")
    UInteger getTransferredToSameClientCount();

    @UaMandatory("PublishRequestCount")
    UInteger getPublishRequestCount();

    @UaMandatory("DataChangeNotificationsCount")
    UInteger getDataChangeNotificationsCount();

    @UaMandatory("EventNotificationsCount")
    UInteger getEventNotificationsCount();

    @UaMandatory("NotificationsCount")
    UInteger getNotificationsCount();

    @UaMandatory("LatePublishRequestCount")
    UInteger getLatePublishRequestCount();

    @UaMandatory("CurrentKeepAliveCount")
    UInteger getCurrentKeepAliveCount();

    @UaMandatory("CurrentLifetimeCount")
    UInteger getCurrentLifetimeCount();

    @UaMandatory("UnacknowledgedMessageCount")
    UInteger getUnacknowledgedMessageCount();

    @UaMandatory("DiscardedMessageCount")
    UInteger getDiscardedMessageCount();

    @UaMandatory("MonitoredItemCount")
    UInteger getMonitoredItemCount();

    @UaMandatory("DisabledMonitoredItemCount")
    UInteger getDisabledMonitoredItemCount();

    @UaMandatory("MonitoringQueueOverflowCount")
    UInteger getMonitoringQueueOverflowCount();

    @UaMandatory("NextSequenceNumber")
    UInteger getNextSequenceNumber();

    @UaMandatory("EventQueueOverFlowCount")
    UInteger getEventQueueOverFlowCount();

    void setSessionId(NodeId sessionId);

    void setSubscriptionId(UInteger subscriptionId);

    void setPriority(UByte priority);

    void setPublishingInterval(Double publishingInterval);

    void setMaxKeepAliveCount(UInteger maxKeepAliveCount);

    void setMaxLifetimeCount(UInteger maxLifetimeCount);

    void setMaxNotificationsPerPublish(UInteger maxNotificationsPerPublish);

    void setPublishingEnabled(Boolean publishingEnabled);

    void setModifyCount(UInteger modifyCount);

    void setEnableCount(UInteger enableCount);

    void setDisableCount(UInteger disableCount);

    void setRepublishRequestCount(UInteger republishRequestCount);

    void setRepublishMessageRequestCount(UInteger republishMessageRequestCount);

    void setRepublishMessageCount(UInteger republishMessageCount);

    void setTransferRequestCount(UInteger transferRequestCount);

    void setTransferredToAltClientCount(UInteger transferredToAltClientCount);

    void setTransferredToSameClientCount(UInteger transferredToSameClientCount);

    void setPublishRequestCount(UInteger publishRequestCount);

    void setDataChangeNotificationsCount(UInteger dataChangeNotificationsCount);

    void setEventNotificationsCount(UInteger eventNotificationsCount);

    void setNotificationsCount(UInteger notificationsCount);

    void setLatePublishRequestCount(UInteger latePublishRequestCount);

    void setCurrentKeepAliveCount(UInteger currentKeepAliveCount);

    void setCurrentLifetimeCount(UInteger currentLifetimeCount);

    void setUnacknowledgedMessageCount(UInteger unacknowledgedMessageCount);

    void setDiscardedMessageCount(UInteger discardedMessageCount);

    void setMonitoredItemCount(UInteger monitoredItemCount);

    void setDisabledMonitoredItemCount(UInteger disabledMonitoredItemCount);

    void setMonitoringQueueOverflowCount(UInteger monitoringQueueOverflowCount);

    void setNextSequenceNumber(UInteger nextSequenceNumber);

    void setEventQueueOverFlowCount(UInteger eventQueueOverFlowCount);

}
