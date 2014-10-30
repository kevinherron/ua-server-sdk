package com.inductiveautomation.opcua.sdk.server.nodes.generated.variables;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.core.model.variables.SessionDiagnosticsVariableType;
import com.inductiveautomation.opcua.sdk.core.nodes.VariableNode;
import com.inductiveautomation.opcua.sdk.server.api.UaNodeManager;
import com.inductiveautomation.opcua.sdk.server.util.UaVariableType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.structured.ApplicationDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.ServiceCounterDataType;
import com.inductiveautomation.opcua.stack.core.types.structured.SessionDiagnosticsDataType;

@UaVariableType(name = "SessionDiagnosticsVariableType")
public class SessionDiagnosticsVariableNode extends BaseDataVariableNode implements SessionDiagnosticsVariableType {

    public SessionDiagnosticsVariableNode(UaNodeManager nodeManager,
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
        SessionDiagnosticsDataType value = new SessionDiagnosticsDataType(
                getSessionId(),
                getSessionName(),
                getClientDescription(),
                getServerUri(),
                getEndpointUrl(),
                getLocaleIds(),
                getActualSessionTimeout(),
                getMaxResponseMessageSize(),
                getClientConnectionTime(),
                getClientLastContactTime(),
                getCurrentSubscriptionsCount(),
                getCurrentMonitoredItemsCount(),
                getCurrentPublishRequestsInQueue(),
                getTotalRequestCount(),
                getUnauthorizedRequestCount(),
                getReadCount(),
                getHistoryReadCount(),
                getWriteCount(),
                getHistoryUpdateCount(),
                getCallCount(),
                getCreateMonitoredItemsCount(),
                getModifyMonitoredItemsCount(),
                getSetMonitoringModeCount(),
                getSetTriggeringCount(),
                getDeleteMonitoredItemsCount(),
                getCreateSubscriptionCount(),
                getModifySubscriptionCount(),
                getSetPublishingModeCount(),
                getPublishCount(),
                getRepublishCount(),
                getTransferSubscriptionsCount(),
                getDeleteSubscriptionsCount(),
                getAddNodesCount(),
                getAddReferencesCount(),
                getDeleteNodesCount(),
                getDeleteReferencesCount(),
                getBrowseCount(),
                getBrowseNextCount(),
                getTranslateBrowsePathsToNodeIdsCount(),
                getQueryFirstCount(),
                getQueryNextCount(),
                getRegisterNodesCount(),
                getUnregisterNodesCount()
        );

        return new DataValue(new Variant(value));
    }

    @Override
    public synchronized void setValue(DataValue value) {
        SessionDiagnosticsDataType v = (SessionDiagnosticsDataType) value.getValue().getValue();

        setSessionId(v.getSessionId());
        setSessionName(v.getSessionName());
        setClientDescription(v.getClientDescription());
        setServerUri(v.getServerUri());
        setEndpointUrl(v.getEndpointUrl());
        setLocaleIds(v.getLocaleIds());
        setActualSessionTimeout(v.getActualSessionTimeout());
        setMaxResponseMessageSize(v.getMaxResponseMessageSize());
        setClientConnectionTime(v.getClientConnectionTime());
        setClientLastContactTime(v.getClientLastContactTime());
        setCurrentSubscriptionsCount(v.getCurrentSubscriptionsCount());
        setCurrentMonitoredItemsCount(v.getCurrentMonitoredItemsCount());
        setCurrentPublishRequestsInQueue(v.getCurrentPublishRequestsInQueue());
        setTotalRequestCount(v.getTotalRequestCount());
        setUnauthorizedRequestCount(v.getUnauthorizedRequestCount());
        setReadCount(v.getReadCount());
        setHistoryReadCount(v.getHistoryReadCount());
        setWriteCount(v.getWriteCount());
        setHistoryUpdateCount(v.getHistoryUpdateCount());
        setCallCount(v.getCallCount());
        setCreateMonitoredItemsCount(v.getCreateMonitoredItemsCount());
        setModifyMonitoredItemsCount(v.getModifyMonitoredItemsCount());
        setSetMonitoringModeCount(v.getSetMonitoringModeCount());
        setSetTriggeringCount(v.getSetTriggeringCount());
        setDeleteMonitoredItemsCount(v.getDeleteMonitoredItemsCount());
        setCreateSubscriptionCount(v.getCreateSubscriptionCount());
        setModifySubscriptionCount(v.getModifySubscriptionCount());
        setSetPublishingModeCount(v.getSetPublishingModeCount());
        setPublishCount(v.getPublishCount());
        setRepublishCount(v.getRepublishCount());
        setTransferSubscriptionsCount(v.getTransferSubscriptionsCount());
        setDeleteSubscriptionsCount(v.getDeleteSubscriptionsCount());
        setAddNodesCount(v.getAddNodesCount());
        setAddReferencesCount(v.getAddReferencesCount());
        setDeleteNodesCount(v.getDeleteNodesCount());
        setDeleteReferencesCount(v.getDeleteReferencesCount());
        setBrowseCount(v.getBrowseCount());
        setBrowseNextCount(v.getBrowseNextCount());
        setTranslateBrowsePathsToNodeIdsCount(v.getTranslateBrowsePathsToNodeIdsCount());
        setQueryFirstCount(v.getQueryFirstCount());
        setQueryNextCount(v.getQueryNextCount());
        setRegisterNodesCount(v.getRegisterNodesCount());
        setUnregisterNodesCount(v.getUnregisterNodesCount());

        fireAttributeChanged(AttributeIds.Value, value);
    }

    @Override
    public NodeId getSessionId() {
        Optional<VariableNode> node = getVariableComponent("SessionId");

        return node.map(n -> (NodeId) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public String getSessionName() {
        Optional<VariableNode> node = getVariableComponent("SessionName");

        return node.map(n -> (String) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ApplicationDescription getClientDescription() {
        Optional<VariableNode> node = getVariableComponent("ClientDescription");

        return node.map(n -> (ApplicationDescription) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public String getServerUri() {
        Optional<VariableNode> node = getVariableComponent("ServerUri");

        return node.map(n -> (String) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public String getEndpointUrl() {
        Optional<VariableNode> node = getVariableComponent("EndpointUrl");

        return node.map(n -> (String) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public String[] getLocaleIds() {
        Optional<VariableNode> node = getVariableComponent("LocaleIds");

        return node.map(n -> (String[]) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public Double getActualSessionTimeout() {
        Optional<VariableNode> node = getVariableComponent("ActualSessionTimeout");

        return node.map(n -> (Double) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public UInteger getMaxResponseMessageSize() {
        Optional<VariableNode> node = getVariableComponent("MaxResponseMessageSize");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public DateTime getClientConnectionTime() {
        Optional<VariableNode> node = getVariableComponent("ClientConnectionTime");

        return node.map(n -> (DateTime) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public DateTime getClientLastContactTime() {
        Optional<VariableNode> node = getVariableComponent("ClientLastContactTime");

        return node.map(n -> (DateTime) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public UInteger getCurrentSubscriptionsCount() {
        Optional<VariableNode> node = getVariableComponent("CurrentSubscriptionsCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public UInteger getCurrentMonitoredItemsCount() {
        Optional<VariableNode> node = getVariableComponent("CurrentMonitoredItemsCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public UInteger getCurrentPublishRequestsInQueue() {
        Optional<VariableNode> node = getVariableComponent("CurrentPublishRequestsInQueue");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getTotalRequestCount() {
        Optional<VariableNode> node = getVariableComponent("TotalRequestCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public UInteger getUnauthorizedRequestCount() {
        Optional<VariableNode> node = getVariableComponent("UnauthorizedRequestCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getReadCount() {
        Optional<VariableNode> node = getVariableComponent("ReadCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getHistoryReadCount() {
        Optional<VariableNode> node = getVariableComponent("HistoryReadCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getWriteCount() {
        Optional<VariableNode> node = getVariableComponent("WriteCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getHistoryUpdateCount() {
        Optional<VariableNode> node = getVariableComponent("HistoryUpdateCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getCallCount() {
        Optional<VariableNode> node = getVariableComponent("CallCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getCreateMonitoredItemsCount() {
        Optional<VariableNode> node = getVariableComponent("CreateMonitoredItemsCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getModifyMonitoredItemsCount() {
        Optional<VariableNode> node = getVariableComponent("ModifyMonitoredItemsCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getSetMonitoringModeCount() {
        Optional<VariableNode> node = getVariableComponent("SetMonitoringModeCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getSetTriggeringCount() {
        Optional<VariableNode> node = getVariableComponent("SetTriggeringCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getDeleteMonitoredItemsCount() {
        Optional<VariableNode> node = getVariableComponent("DeleteMonitoredItemsCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getCreateSubscriptionCount() {
        Optional<VariableNode> node = getVariableComponent("CreateSubscriptionCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getModifySubscriptionCount() {
        Optional<VariableNode> node = getVariableComponent("ModifySubscriptionCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getSetPublishingModeCount() {
        Optional<VariableNode> node = getVariableComponent("SetPublishingModeCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getPublishCount() {
        Optional<VariableNode> node = getVariableComponent("PublishCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getRepublishCount() {
        Optional<VariableNode> node = getVariableComponent("RepublishCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getTransferSubscriptionsCount() {
        Optional<VariableNode> node = getVariableComponent("TransferSubscriptionsCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getDeleteSubscriptionsCount() {
        Optional<VariableNode> node = getVariableComponent("DeleteSubscriptionsCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getAddNodesCount() {
        Optional<VariableNode> node = getVariableComponent("AddNodesCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getAddReferencesCount() {
        Optional<VariableNode> node = getVariableComponent("AddReferencesCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getDeleteNodesCount() {
        Optional<VariableNode> node = getVariableComponent("DeleteNodesCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getDeleteReferencesCount() {
        Optional<VariableNode> node = getVariableComponent("DeleteReferencesCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getBrowseCount() {
        Optional<VariableNode> node = getVariableComponent("BrowseCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getBrowseNextCount() {
        Optional<VariableNode> node = getVariableComponent("BrowseNextCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getTranslateBrowsePathsToNodeIdsCount() {
        Optional<VariableNode> node = getVariableComponent("TranslateBrowsePathsToNodeIdsCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getQueryFirstCount() {
        Optional<VariableNode> node = getVariableComponent("QueryFirstCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getQueryNextCount() {
        Optional<VariableNode> node = getVariableComponent("QueryNextCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getRegisterNodesCount() {
        Optional<VariableNode> node = getVariableComponent("RegisterNodesCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServiceCounterDataType getUnregisterNodesCount() {
        Optional<VariableNode> node = getVariableComponent("UnregisterNodesCount");

        return node.map(n -> (ServiceCounterDataType) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public void setSessionId(NodeId sessionId) {
        getVariableComponent("SessionId").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(sessionId)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setSessionName(String sessionName) {
        getVariableComponent("SessionName").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(sessionName)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setClientDescription(ApplicationDescription clientDescription) {
        getVariableComponent("ClientDescription").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(clientDescription)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setServerUri(String serverUri) {
        getVariableComponent("ServerUri").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(serverUri)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setEndpointUrl(String endpointUrl) {
        getVariableComponent("EndpointUrl").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(endpointUrl)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setLocaleIds(String[] localeIds) {
        getVariableComponent("LocaleIds").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(localeIds)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setActualSessionTimeout(Double actualSessionTimeout) {
        getVariableComponent("ActualSessionTimeout").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(actualSessionTimeout)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setMaxResponseMessageSize(UInteger maxResponseMessageSize) {
        getVariableComponent("MaxResponseMessageSize").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxResponseMessageSize)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setClientConnectionTime(DateTime clientConnectionTime) {
        getVariableComponent("ClientConnectionTime").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(clientConnectionTime)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setClientLastContactTime(DateTime clientLastContactTime) {
        getVariableComponent("ClientLastContactTime").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(clientLastContactTime)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setCurrentSubscriptionsCount(UInteger currentSubscriptionsCount) {
        getVariableComponent("CurrentSubscriptionsCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(currentSubscriptionsCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setCurrentMonitoredItemsCount(UInteger currentMonitoredItemsCount) {
        getVariableComponent("CurrentMonitoredItemsCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(currentMonitoredItemsCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setCurrentPublishRequestsInQueue(UInteger currentPublishRequestsInQueue) {
        getVariableComponent("CurrentPublishRequestsInQueue").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(currentPublishRequestsInQueue)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setTotalRequestCount(ServiceCounterDataType totalRequestCount) {
        getVariableComponent("TotalRequestCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(totalRequestCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setUnauthorizedRequestCount(UInteger unauthorizedRequestCount) {
        getVariableComponent("UnauthorizedRequestCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(unauthorizedRequestCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setReadCount(ServiceCounterDataType readCount) {
        getVariableComponent("ReadCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(readCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setHistoryReadCount(ServiceCounterDataType historyReadCount) {
        getVariableComponent("HistoryReadCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(historyReadCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setWriteCount(ServiceCounterDataType writeCount) {
        getVariableComponent("WriteCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(writeCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setHistoryUpdateCount(ServiceCounterDataType historyUpdateCount) {
        getVariableComponent("HistoryUpdateCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(historyUpdateCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setCallCount(ServiceCounterDataType callCount) {
        getVariableComponent("CallCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(callCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setCreateMonitoredItemsCount(ServiceCounterDataType createMonitoredItemsCount) {
        getVariableComponent("CreateMonitoredItemsCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(createMonitoredItemsCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setModifyMonitoredItemsCount(ServiceCounterDataType modifyMonitoredItemsCount) {
        getVariableComponent("ModifyMonitoredItemsCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(modifyMonitoredItemsCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setSetMonitoringModeCount(ServiceCounterDataType setMonitoringModeCount) {
        getVariableComponent("SetMonitoringModeCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(setMonitoringModeCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setSetTriggeringCount(ServiceCounterDataType setTriggeringCount) {
        getVariableComponent("SetTriggeringCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(setTriggeringCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setDeleteMonitoredItemsCount(ServiceCounterDataType deleteMonitoredItemsCount) {
        getVariableComponent("DeleteMonitoredItemsCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(deleteMonitoredItemsCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setCreateSubscriptionCount(ServiceCounterDataType createSubscriptionCount) {
        getVariableComponent("CreateSubscriptionCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(createSubscriptionCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setModifySubscriptionCount(ServiceCounterDataType modifySubscriptionCount) {
        getVariableComponent("ModifySubscriptionCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(modifySubscriptionCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setSetPublishingModeCount(ServiceCounterDataType setPublishingModeCount) {
        getVariableComponent("SetPublishingModeCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(setPublishingModeCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setPublishCount(ServiceCounterDataType publishCount) {
        getVariableComponent("PublishCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(publishCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setRepublishCount(ServiceCounterDataType republishCount) {
        getVariableComponent("RepublishCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(republishCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setTransferSubscriptionsCount(ServiceCounterDataType transferSubscriptionsCount) {
        getVariableComponent("TransferSubscriptionsCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(transferSubscriptionsCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setDeleteSubscriptionsCount(ServiceCounterDataType deleteSubscriptionsCount) {
        getVariableComponent("DeleteSubscriptionsCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(deleteSubscriptionsCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setAddNodesCount(ServiceCounterDataType addNodesCount) {
        getVariableComponent("AddNodesCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(addNodesCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setAddReferencesCount(ServiceCounterDataType addReferencesCount) {
        getVariableComponent("AddReferencesCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(addReferencesCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setDeleteNodesCount(ServiceCounterDataType deleteNodesCount) {
        getVariableComponent("DeleteNodesCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(deleteNodesCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setDeleteReferencesCount(ServiceCounterDataType deleteReferencesCount) {
        getVariableComponent("DeleteReferencesCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(deleteReferencesCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setBrowseCount(ServiceCounterDataType browseCount) {
        getVariableComponent("BrowseCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(browseCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setBrowseNextCount(ServiceCounterDataType browseNextCount) {
        getVariableComponent("BrowseNextCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(browseNextCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setTranslateBrowsePathsToNodeIdsCount(ServiceCounterDataType translateBrowsePathsToNodeIdsCount) {
        getVariableComponent("TranslateBrowsePathsToNodeIdsCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(translateBrowsePathsToNodeIdsCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setQueryFirstCount(ServiceCounterDataType queryFirstCount) {
        getVariableComponent("QueryFirstCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(queryFirstCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setQueryNextCount(ServiceCounterDataType queryNextCount) {
        getVariableComponent("QueryNextCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(queryNextCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setRegisterNodesCount(ServiceCounterDataType registerNodesCount) {
        getVariableComponent("RegisterNodesCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(registerNodesCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setUnregisterNodesCount(ServiceCounterDataType unregisterNodesCount) {
        getVariableComponent("UnregisterNodesCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(unregisterNodesCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void atomicSet(Runnable runnable) {
        runnable.run();
    }

}
