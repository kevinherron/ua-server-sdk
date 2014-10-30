package com.inductiveautomation.opcua.sdk.server.nodes.generated.variables;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.core.model.variables.ServerDiagnosticsSummaryType;
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
import com.inductiveautomation.opcua.stack.core.types.structured.ServerDiagnosticsSummaryDataType;

@UaVariableType(name = "ServerDiagnosticsSummaryType")
public class ServerDiagnosticsSummaryNode extends BaseDataVariableNode implements ServerDiagnosticsSummaryType {

    public ServerDiagnosticsSummaryNode(UaNodeManager nodeManager,
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
        ServerDiagnosticsSummaryDataType value = new ServerDiagnosticsSummaryDataType(
                getServerViewCount(),
                getCurrentSessionCount(),
                getCumulatedSessionCount(),
                getSecurityRejectedSessionCount(),
                getRejectedSessionCount(),
                getSessionTimeoutCount(),
                getSessionAbortCount(),
                getPublishingIntervalCount(),
                getCurrentSubscriptionCount(),
                getCumulatedSubscriptionCount(),
                getSecurityRejectedRequestsCount(),
                getRejectedRequestsCount()
        );

        return new DataValue(new Variant(value));
    }

    @Override
    public synchronized void setValue(DataValue value) {
        ServerDiagnosticsSummaryDataType v = (ServerDiagnosticsSummaryDataType) value.getValue().getValue();

        setServerViewCount(v.getServerViewCount());
        setCurrentSessionCount(v.getCurrentSessionCount());
        setCumulatedSessionCount(v.getCumulatedSessionCount());
        setSecurityRejectedSessionCount(v.getSecurityRejectedSessionCount());
        setRejectedSessionCount(v.getRejectedSessionCount());
        setSessionTimeoutCount(v.getSessionTimeoutCount());
        setSessionAbortCount(v.getSessionAbortCount());
        setPublishingIntervalCount(v.getPublishingIntervalCount());
        setCurrentSubscriptionCount(v.getCurrentSubscriptionCount());
        setCumulatedSubscriptionCount(v.getCumulatedSubscriptionCount());
        setSecurityRejectedRequestsCount(v.getSecurityRejectedRequestsCount());
        setRejectedRequestsCount(v.getRejectedRequestsCount());

        fireAttributeChanged(AttributeIds.Value, value);
    }

    @Override
    public UInteger getServerViewCount() {
        Optional<VariableNode> node = getVariableComponent("ServerViewCount");
        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public UInteger getCurrentSessionCount() {
        Optional<VariableNode> node = getVariableComponent("CurrentSessionCount");
        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public UInteger getCumulatedSessionCount() {
        Optional<VariableNode> node = getVariableComponent("CumulatedSessionCount");
        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public UInteger getSecurityRejectedSessionCount() {
        Optional<VariableNode> node = getVariableComponent("SecurityRejectedSessionCount");
        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public UInteger getRejectedSessionCount() {
        Optional<VariableNode> node = getVariableComponent("RejectedSessionCount");
        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public UInteger getSessionTimeoutCount() {
        Optional<VariableNode> node = getVariableComponent("SessionTimeoutCount");
        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public UInteger getSessionAbortCount() {
        Optional<VariableNode> node = getVariableComponent("SessionAbortCount");
        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public UInteger getPublishingIntervalCount() {
        Optional<VariableNode> node = getVariableComponent("PublishingIntervalCount");
        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public UInteger getCurrentSubscriptionCount() {
        Optional<VariableNode> node = getVariableComponent("CurrentSubscriptionCount");
        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public UInteger getCumulatedSubscriptionCount() {
        Optional<VariableNode> node = getVariableComponent("CumulatedSubscriptionCount");
        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public UInteger getSecurityRejectedRequestsCount() {
        Optional<VariableNode> node = getVariableComponent("SecurityRejectedRequestsCount");
        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public UInteger getRejectedRequestsCount() {
        Optional<VariableNode> node = getVariableComponent("RejectedRequestsCount");
        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public void setServerViewCount(UInteger serverViewCount) {
        getVariableComponent("ServerViewCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(serverViewCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setCurrentSessionCount(UInteger currentSessionCount) {
        getVariableComponent("CurrentSessionCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(currentSessionCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setCumulatedSessionCount(UInteger cumulatedSessionCount) {
        getVariableComponent("CumulatedSessionCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(cumulatedSessionCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setSecurityRejectedSessionCount(UInteger securityRejectedSessionCount) {
        getVariableComponent("SecurityRejectedSessionCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(securityRejectedSessionCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setRejectedSessionCount(UInteger rejectedSessionCount) {
        getVariableComponent("RejectedSessionCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(rejectedSessionCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setSessionTimeoutCount(UInteger sessionTimeoutCount) {
        getVariableComponent("SessionTimeoutCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(sessionTimeoutCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setSessionAbortCount(UInteger sessionAbortCount) {
        getVariableComponent("SessionAbortCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(sessionAbortCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setPublishingIntervalCount(UInteger publishingIntervalCount) {
        getVariableComponent("PublishingIntervalCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(publishingIntervalCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setCurrentSubscriptionCount(UInteger currentSubscriptionCount) {
        getVariableComponent("CurrentSubscriptionCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(currentSubscriptionCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setCumulatedSubscriptionCount(UInteger cumulatedSubscriptionCount) {
        getVariableComponent("CumulatedSubscriptionCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(cumulatedSubscriptionCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setSecurityRejectedRequestsCount(UInteger securityRejectedRequestsCount) {
        getVariableComponent("SecurityRejectedRequestsCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(securityRejectedRequestsCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setRejectedRequestsCount(UInteger rejectedRequestsCount) {
        getVariableComponent("RejectedRequestsCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(rejectedRequestsCount)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void atomicSet(Runnable runnable) {
        runnable.run();
    }

}
