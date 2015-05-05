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

import com.digitalpetri.opcua.sdk.core.model.UaMandatory;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.structured.ApplicationDescription;
import com.digitalpetri.opcua.stack.core.types.structured.ServiceCounterDataType;

public interface SessionDiagnosticsVariableType extends BaseDataVariableType {

    @UaMandatory("SessionId")
    NodeId getSessionId();

    @UaMandatory("SessionName")
    String getSessionName();

    @UaMandatory("ClientDescription")
    ApplicationDescription getClientDescription();

    @UaMandatory("ServerUri")
    String getServerUri();

    @UaMandatory("EndpointUrl")
    String getEndpointUrl();

    @UaMandatory("LocaleIds")
    String[] getLocaleIds();

    @UaMandatory("ActualSessionTimeout")
    Double getActualSessionTimeout();

    @UaMandatory("MaxResponseMessageSize")
    UInteger getMaxResponseMessageSize();

    @UaMandatory("ClientConnectionTime")
    DateTime getClientConnectionTime();

    @UaMandatory("ClientLastContactTime")
    DateTime getClientLastContactTime();

    @UaMandatory("CurrentSubscriptionsCount")
    UInteger getCurrentSubscriptionsCount();

    @UaMandatory("CurrentMonitoredItemsCount")
    UInteger getCurrentMonitoredItemsCount();

    @UaMandatory("CurrentPublishRequestsInQueue")
    UInteger getCurrentPublishRequestsInQueue();

    @UaMandatory("TotalRequestCount")
    ServiceCounterDataType getTotalRequestCount();

    @UaMandatory("UnauthorizedRequestCount")
    UInteger getUnauthorizedRequestCount();

    @UaMandatory("ReadCount")
    ServiceCounterDataType getReadCount();

    @UaMandatory("HistoryReadCount")
    ServiceCounterDataType getHistoryReadCount();

    @UaMandatory("WriteCount")
    ServiceCounterDataType getWriteCount();

    @UaMandatory("HistoryUpdateCount")
    ServiceCounterDataType getHistoryUpdateCount();

    @UaMandatory("CallCount")
    ServiceCounterDataType getCallCount();

    @UaMandatory("CreateMonitoredItemsCount")
    ServiceCounterDataType getCreateMonitoredItemsCount();

    @UaMandatory("ModifyMonitoredItemsCount")
    ServiceCounterDataType getModifyMonitoredItemsCount();

    @UaMandatory("SetMonitoringModeCount")
    ServiceCounterDataType getSetMonitoringModeCount();

    @UaMandatory("SetTriggeringCount")
    ServiceCounterDataType getSetTriggeringCount();

    @UaMandatory("DeleteMonitoredItemsCount")
    ServiceCounterDataType getDeleteMonitoredItemsCount();

    @UaMandatory("CreateSubscriptionCount")
    ServiceCounterDataType getCreateSubscriptionCount();

    @UaMandatory("ModifySubscriptionCount")
    ServiceCounterDataType getModifySubscriptionCount();

    @UaMandatory("SetPublishingModeCount")
    ServiceCounterDataType getSetPublishingModeCount();

    @UaMandatory("PublishCount")
    ServiceCounterDataType getPublishCount();

    @UaMandatory("RepublishCount")
    ServiceCounterDataType getRepublishCount();

    @UaMandatory("TransferSubscriptionsCount")
    ServiceCounterDataType getTransferSubscriptionsCount();

    @UaMandatory("DeleteSubscriptionsCount")
    ServiceCounterDataType getDeleteSubscriptionsCount();

    @UaMandatory("AddNodesCount")
    ServiceCounterDataType getAddNodesCount();

    @UaMandatory("AddReferencesCount")
    ServiceCounterDataType getAddReferencesCount();

    @UaMandatory("DeleteNodesCount")
    ServiceCounterDataType getDeleteNodesCount();

    @UaMandatory("DeleteReferencesCount")
    ServiceCounterDataType getDeleteReferencesCount();

    @UaMandatory("BrowseCount")
    ServiceCounterDataType getBrowseCount();

    @UaMandatory("BrowseNextCount")
    ServiceCounterDataType getBrowseNextCount();

    @UaMandatory("TranslateBrowsePathsToNodeIdsCount")
    ServiceCounterDataType getTranslateBrowsePathsToNodeIdsCount();

    @UaMandatory("QueryFirstCount")
    ServiceCounterDataType getQueryFirstCount();

    @UaMandatory("QueryNextCount")
    ServiceCounterDataType getQueryNextCount();

    @UaMandatory("RegisterNodesCount")
    ServiceCounterDataType getRegisterNodesCount();

    @UaMandatory("UnregisterNodesCount")
    ServiceCounterDataType getUnregisterNodesCount();

    void setSessionId(NodeId sessionId);

    void setSessionName(String sessionName);

    void setClientDescription(ApplicationDescription clientDescription);

    void setServerUri(String serverUri);

    void setEndpointUrl(String endpointUrl);

    void setLocaleIds(String[] localeIds);

    void setActualSessionTimeout(Double actualSessionTimeout);

    void setMaxResponseMessageSize(UInteger maxResponseMessageSize);

    void setClientConnectionTime(DateTime clientConnectionTime);

    void setClientLastContactTime(DateTime clientLastContactTime);

    void setCurrentSubscriptionsCount(UInteger currentSubscriptionsCount);

    void setCurrentMonitoredItemsCount(UInteger currentMonitoredItemsCount);

    void setCurrentPublishRequestsInQueue(UInteger currentPublishRequestsInQueue);

    void setTotalRequestCount(ServiceCounterDataType totalRequestCount);

    void setUnauthorizedRequestCount(UInteger unauthorizedRequestCount);

    void setReadCount(ServiceCounterDataType readCount);

    void setHistoryReadCount(ServiceCounterDataType historyReadCount);

    void setWriteCount(ServiceCounterDataType writeCount);

    void setHistoryUpdateCount(ServiceCounterDataType historyUpdateCount);

    void setCallCount(ServiceCounterDataType callCount);

    void setCreateMonitoredItemsCount(ServiceCounterDataType createMonitoredItemsCount);

    void setModifyMonitoredItemsCount(ServiceCounterDataType modifyMonitoredItemsCount);

    void setSetMonitoringModeCount(ServiceCounterDataType setMonitoringModeCount);

    void setSetTriggeringCount(ServiceCounterDataType setTriggeringCount);

    void setDeleteMonitoredItemsCount(ServiceCounterDataType deleteMonitoredItemsCount);

    void setCreateSubscriptionCount(ServiceCounterDataType createSubscriptionCount);

    void setModifySubscriptionCount(ServiceCounterDataType modifySubscriptionCount);

    void setSetPublishingModeCount(ServiceCounterDataType setPublishingModeCount);

    void setPublishCount(ServiceCounterDataType publishCount);

    void setRepublishCount(ServiceCounterDataType republishCount);

    void setTransferSubscriptionsCount(ServiceCounterDataType transferSubscriptionsCount);

    void setDeleteSubscriptionsCount(ServiceCounterDataType deleteSubscriptionsCount);

    void setAddNodesCount(ServiceCounterDataType addNodesCount);

    void setAddReferencesCount(ServiceCounterDataType addReferencesCount);

    void setDeleteNodesCount(ServiceCounterDataType deleteNodesCount);

    void setDeleteReferencesCount(ServiceCounterDataType deleteReferencesCount);

    void setBrowseCount(ServiceCounterDataType browseCount);

    void setBrowseNextCount(ServiceCounterDataType browseNextCount);

    void setTranslateBrowsePathsToNodeIdsCount(ServiceCounterDataType translateBrowsePathsToNodeIdsCount);

    void setQueryFirstCount(ServiceCounterDataType queryFirstCount);

    void setQueryNextCount(ServiceCounterDataType queryNextCount);

    void setRegisterNodesCount(ServiceCounterDataType registerNodesCount);

    void setUnregisterNodesCount(ServiceCounterDataType unregisterNodesCount);

}
