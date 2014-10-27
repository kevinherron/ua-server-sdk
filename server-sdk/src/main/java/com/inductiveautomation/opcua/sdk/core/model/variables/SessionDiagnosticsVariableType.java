package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.structured.ApplicationDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.ServiceCounterDataType;

public interface SessionDiagnosticsVariableType extends BaseDataVariableType {

    NodeId getSessionId();

    String getSessionName();

    ApplicationDescription getClientDescription();

    String getServerUri();

    String getEndpointUrl();

    String[] getLocaleIds();

    Double getActualSessionTimeout();

    UInteger getMaxResponseMessageSize();

    DateTime getClientConnectionTime();

    DateTime getClientLastContactTime();

    UInteger getCurrentSubscriptionsCount();

    UInteger getCurrentMonitoredItemsCount();

    UInteger getCurrentPublishRequestsInQueue();

    ServiceCounterDataType getTotalRequestCount();

    UInteger getUnauthorizedRequestCount();

    ServiceCounterDataType getReadCount();

    ServiceCounterDataType getHistoryReadCount();

    ServiceCounterDataType getWriteCount();

    ServiceCounterDataType getHistoryUpdateCount();

    ServiceCounterDataType getCallCount();

    ServiceCounterDataType getCreateMonitoredItemsCount();

    ServiceCounterDataType getModifyMonitoredItemsCount();

    ServiceCounterDataType getSetMonitoringModeCount();

    ServiceCounterDataType getSetTriggeringCount();

    ServiceCounterDataType getDeleteMonitoredItemsCount();

    ServiceCounterDataType getCreateSubscriptionCount();

    ServiceCounterDataType getModifySubscriptionCount();

    ServiceCounterDataType getSetPublishingModeCount();

    ServiceCounterDataType getPublishCount();

    ServiceCounterDataType getRepublishCount();

    ServiceCounterDataType getTransferSubscriptionsCount();

    ServiceCounterDataType getDeleteSubscriptionsCount();

    ServiceCounterDataType getAddNodesCount();

    ServiceCounterDataType getAddReferencesCount();

    ServiceCounterDataType getDeleteNodesCount();

    ServiceCounterDataType getDeleteReferencesCount();

    ServiceCounterDataType getBrowseCount();

    ServiceCounterDataType getBrowseNextCount();

    ServiceCounterDataType getTranslateBrowsePathsToNodeIdsCount();

    ServiceCounterDataType getQueryFirstCount();

    ServiceCounterDataType getQueryNextCount();

    ServiceCounterDataType getRegisterNodesCount();

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

    void atomicSet(Runnable runnable);

}
