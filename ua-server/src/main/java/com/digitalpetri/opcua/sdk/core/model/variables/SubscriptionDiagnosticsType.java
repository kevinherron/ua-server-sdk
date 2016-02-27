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

package com.digitalpetri.opcua.sdk.core.model.variables;

import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;


public interface SubscriptionDiagnosticsType extends BaseDataVariableType {


    NodeId getSessionId();

    BaseDataVariableType getSessionIdNode();

    void setSessionId(NodeId value);

    UInteger getSubscriptionId();

    BaseDataVariableType getSubscriptionIdNode();

    void setSubscriptionId(UInteger value);

    UByte getPriority();

    BaseDataVariableType getPriorityNode();

    void setPriority(UByte value);

    Double getPublishingInterval();

    BaseDataVariableType getPublishingIntervalNode();

    void setPublishingInterval(Double value);

    UInteger getMaxKeepAliveCount();

    BaseDataVariableType getMaxKeepAliveCountNode();

    void setMaxKeepAliveCount(UInteger value);

    UInteger getMaxLifetimeCount();

    BaseDataVariableType getMaxLifetimeCountNode();

    void setMaxLifetimeCount(UInteger value);

    UInteger getMaxNotificationsPerPublish();

    BaseDataVariableType getMaxNotificationsPerPublishNode();

    void setMaxNotificationsPerPublish(UInteger value);

    Boolean getPublishingEnabled();

    BaseDataVariableType getPublishingEnabledNode();

    void setPublishingEnabled(Boolean value);

    UInteger getModifyCount();

    BaseDataVariableType getModifyCountNode();

    void setModifyCount(UInteger value);

    UInteger getEnableCount();

    BaseDataVariableType getEnableCountNode();

    void setEnableCount(UInteger value);

    UInteger getDisableCount();

    BaseDataVariableType getDisableCountNode();

    void setDisableCount(UInteger value);

    UInteger getRepublishRequestCount();

    BaseDataVariableType getRepublishRequestCountNode();

    void setRepublishRequestCount(UInteger value);

    UInteger getRepublishMessageRequestCount();

    BaseDataVariableType getRepublishMessageRequestCountNode();

    void setRepublishMessageRequestCount(UInteger value);

    UInteger getRepublishMessageCount();

    BaseDataVariableType getRepublishMessageCountNode();

    void setRepublishMessageCount(UInteger value);

    UInteger getTransferRequestCount();

    BaseDataVariableType getTransferRequestCountNode();

    void setTransferRequestCount(UInteger value);

    UInteger getTransferredToAltClientCount();

    BaseDataVariableType getTransferredToAltClientCountNode();

    void setTransferredToAltClientCount(UInteger value);

    UInteger getTransferredToSameClientCount();

    BaseDataVariableType getTransferredToSameClientCountNode();

    void setTransferredToSameClientCount(UInteger value);

    UInteger getPublishRequestCount();

    BaseDataVariableType getPublishRequestCountNode();

    void setPublishRequestCount(UInteger value);

    UInteger getDataChangeNotificationsCount();

    BaseDataVariableType getDataChangeNotificationsCountNode();

    void setDataChangeNotificationsCount(UInteger value);

    UInteger getEventNotificationsCount();

    BaseDataVariableType getEventNotificationsCountNode();

    void setEventNotificationsCount(UInteger value);

    UInteger getNotificationsCount();

    BaseDataVariableType getNotificationsCountNode();

    void setNotificationsCount(UInteger value);

    UInteger getLatePublishRequestCount();

    BaseDataVariableType getLatePublishRequestCountNode();

    void setLatePublishRequestCount(UInteger value);

    UInteger getCurrentKeepAliveCount();

    BaseDataVariableType getCurrentKeepAliveCountNode();

    void setCurrentKeepAliveCount(UInteger value);

    UInteger getCurrentLifetimeCount();

    BaseDataVariableType getCurrentLifetimeCountNode();

    void setCurrentLifetimeCount(UInteger value);

    UInteger getUnacknowledgedMessageCount();

    BaseDataVariableType getUnacknowledgedMessageCountNode();

    void setUnacknowledgedMessageCount(UInteger value);

    UInteger getDiscardedMessageCount();

    BaseDataVariableType getDiscardedMessageCountNode();

    void setDiscardedMessageCount(UInteger value);

    UInteger getMonitoredItemCount();

    BaseDataVariableType getMonitoredItemCountNode();

    void setMonitoredItemCount(UInteger value);

    UInteger getDisabledMonitoredItemCount();

    BaseDataVariableType getDisabledMonitoredItemCountNode();

    void setDisabledMonitoredItemCount(UInteger value);

    UInteger getMonitoringQueueOverflowCount();

    BaseDataVariableType getMonitoringQueueOverflowCountNode();

    void setMonitoringQueueOverflowCount(UInteger value);

    UInteger getNextSequenceNumber();

    BaseDataVariableType getNextSequenceNumberNode();

    void setNextSequenceNumber(UInteger value);

    UInteger getEventQueueOverFlowCount();

    BaseDataVariableType getEventQueueOverFlowCountNode();

    void setEventQueueOverFlowCount(UInteger value);
}
