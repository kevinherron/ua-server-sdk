package com.inductiveautomation.opcua.sdk.server.model.variables;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.core.model.UaMandatory;
import com.inductiveautomation.opcua.sdk.core.model.variables.ServerDiagnosticsSummaryType;
import com.inductiveautomation.opcua.sdk.core.nodes.VariableNode;
import com.inductiveautomation.opcua.sdk.server.api.UaNamespace;
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

    public ServerDiagnosticsSummaryNode(UaNamespace namespace,
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

        super(namespace, nodeId, browseName, displayName, description, writeMask, userWriteMask,
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
    @UaMandatory("ServerViewCount")
    public UInteger getServerViewCount() {
        Optional<VariableNode> node = getVariableComponent("ServerViewCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("CurrentSessionCount")
    public UInteger getCurrentSessionCount() {
        Optional<VariableNode> node = getVariableComponent("CurrentSessionCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("CumulatedSessionCount")
    public UInteger getCumulatedSessionCount() {
        Optional<VariableNode> node = getVariableComponent("CumulatedSessionCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("SecurityRejectedSessionCount")
    public UInteger getSecurityRejectedSessionCount() {
        Optional<VariableNode> node = getVariableComponent("SecurityRejectedSessionCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("RejectedSessionCount")
    public UInteger getRejectedSessionCount() {
        Optional<VariableNode> node = getVariableComponent("RejectedSessionCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("SessionTimeoutCount")
    public UInteger getSessionTimeoutCount() {
        Optional<VariableNode> node = getVariableComponent("SessionTimeoutCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("SessionAbortCount")
    public UInteger getSessionAbortCount() {
        Optional<VariableNode> node = getVariableComponent("SessionAbortCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("PublishingIntervalCount")
    public UInteger getPublishingIntervalCount() {
        Optional<VariableNode> node = getVariableComponent("PublishingIntervalCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("CurrentSubscriptionCount")
    public UInteger getCurrentSubscriptionCount() {
        Optional<VariableNode> node = getVariableComponent("CurrentSubscriptionCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("CumulatedSubscriptionCount")
    public UInteger getCumulatedSubscriptionCount() {
        Optional<VariableNode> node = getVariableComponent("CumulatedSubscriptionCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("SecurityRejectedRequestsCount")
    public UInteger getSecurityRejectedRequestsCount() {
        Optional<VariableNode> node = getVariableComponent("SecurityRejectedRequestsCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("RejectedRequestsCount")
    public UInteger getRejectedRequestsCount() {
        Optional<VariableNode> node = getVariableComponent("RejectedRequestsCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public synchronized void setServerViewCount(UInteger serverViewCount) {
        getVariableComponent("ServerViewCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(serverViewCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setCurrentSessionCount(UInteger currentSessionCount) {
        getVariableComponent("CurrentSessionCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(currentSessionCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setCumulatedSessionCount(UInteger cumulatedSessionCount) {
        getVariableComponent("CumulatedSessionCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(cumulatedSessionCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setSecurityRejectedSessionCount(UInteger securityRejectedSessionCount) {
        getVariableComponent("SecurityRejectedSessionCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(securityRejectedSessionCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setRejectedSessionCount(UInteger rejectedSessionCount) {
        getVariableComponent("RejectedSessionCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(rejectedSessionCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setSessionTimeoutCount(UInteger sessionTimeoutCount) {
        getVariableComponent("SessionTimeoutCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(sessionTimeoutCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setSessionAbortCount(UInteger sessionAbortCount) {
        getVariableComponent("SessionAbortCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(sessionAbortCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setPublishingIntervalCount(UInteger publishingIntervalCount) {
        getVariableComponent("PublishingIntervalCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(publishingIntervalCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setCurrentSubscriptionCount(UInteger currentSubscriptionCount) {
        getVariableComponent("CurrentSubscriptionCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(currentSubscriptionCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setCumulatedSubscriptionCount(UInteger cumulatedSubscriptionCount) {
        getVariableComponent("CumulatedSubscriptionCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(cumulatedSubscriptionCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setSecurityRejectedRequestsCount(UInteger securityRejectedRequestsCount) {
        getVariableComponent("SecurityRejectedRequestsCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(securityRejectedRequestsCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setRejectedRequestsCount(UInteger rejectedRequestsCount) {
        getVariableComponent("RejectedRequestsCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(rejectedRequestsCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

}
