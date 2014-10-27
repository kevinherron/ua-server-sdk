package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface SubscriptionDiagnosticsType extends BaseDataVariableType {

    NodeId getSessionId();

    UInteger getSubscriptionId();

    UByte getPriority();

    Double getPublishingInterval();

    UInteger getMaxKeepAliveCount();

    UInteger getMaxLifetimeCount();

    UInteger getMaxNotificationsPerPublish();

    Boolean getPublishingEnabled();

    UInteger getModifyCount();

    UInteger getEnableCount();

    UInteger getDisableCount();

    UInteger getRepublishRequestCount();

    UInteger getRepublishMessageRequestCount();

    UInteger getRepublishMessageCount();

    UInteger getTransferRequestCount();

    UInteger getTransferredToAltClientCount();

    UInteger getTransferredToSameClientCount();

    UInteger getPublishRequestCount();

    UInteger getDataChangeNotificationsCount();

    UInteger getEventNotificationsCount();

    UInteger getNotificationsCount();

    UInteger getLatePublishRequestCount();

    UInteger getCurrentKeepAliveCount();

    UInteger getCurrentLifetimeCount();

    UInteger getUnacknowledgedMessageCount();

    UInteger getDiscardedMessageCount();

    UInteger getMonitoredItemCount();

    UInteger getDisabledMonitoredItemCount();

    UInteger getMonitoringQueueOverflowCount();

    UInteger getNextSequenceNumber();

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

    void atomicSet(Runnable runnable);

}
