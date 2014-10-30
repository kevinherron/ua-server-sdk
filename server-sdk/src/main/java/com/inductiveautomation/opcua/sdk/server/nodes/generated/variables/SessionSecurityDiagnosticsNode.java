package com.inductiveautomation.opcua.sdk.server.nodes.generated.variables;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.core.model.variables.SessionSecurityDiagnosticsType;
import com.inductiveautomation.opcua.sdk.core.nodes.VariableNode;
import com.inductiveautomation.opcua.sdk.server.api.UaNodeManager;
import com.inductiveautomation.opcua.sdk.server.util.UaVariableType;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.MessageSecurityMode;
import com.inductiveautomation.opcua.stack.core.types.structured.SessionSecurityDiagnosticsDataType;

@UaVariableType(name = "SessionSecurityDiagnosticsType")
public class SessionSecurityDiagnosticsNode extends BaseDataVariableNode implements SessionSecurityDiagnosticsType {

    public SessionSecurityDiagnosticsNode(UaNodeManager nodeManager,
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
        SessionSecurityDiagnosticsDataType value = new SessionSecurityDiagnosticsDataType(
                getSessionId(),
                getClientUserIdOfSession(),
                getClientUserIdHistory(),
                getAuthenticationMechanism(),
                getEncoding(),
                getTransportProtocol(),
                getSecurityMode(),
                getSecurityPolicyUri(),
                getClientCertificate()
        );

        return new DataValue(new Variant(value));
    }

    @Override
    public synchronized void setValue(DataValue value) {
        SessionSecurityDiagnosticsDataType v = (SessionSecurityDiagnosticsDataType) value.getValue().getValue();

        setSessionId(v.getSessionId());
        setClientUserIdOfSession(v.getClientUserIdOfSession());
        setClientUserIdHistory(v.getClientUserIdHistory());
        setAuthenticationMechanism(v.getAuthenticationMechanism());
        setEncoding(v.getEncoding());
        setTransportProtocol(v.getTransportProtocol());
        setSecurityMode(v.getSecurityMode());
        setSecurityPolicyUri(v.getSecurityPolicyUri());
        setClientCertificate(v.getClientCertificate());

        fireAttributeChanged(AttributeIds.Value, value);
    }

    @Override
    public NodeId getSessionId() {
        Optional<VariableNode> node = getVariableComponent("SessionId");
        return node.map(n -> (NodeId) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public String getClientUserIdOfSession() {
        Optional<VariableNode> node = getVariableComponent("ClientUserIdOfSession");
        return node.map(n -> (String) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public String[] getClientUserIdHistory() {
        Optional<VariableNode> node = getVariableComponent("ClientUserIdHistory");
        return node.map(n -> (String[]) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public String getAuthenticationMechanism() {
        Optional<VariableNode> node = getVariableComponent("AuthenticationMechanism");
        return node.map(n -> (String) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public String getEncoding() {
        Optional<VariableNode> node = getVariableComponent("Encoding");
        return node.map(n -> (String) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public String getTransportProtocol() {
        Optional<VariableNode> node = getVariableComponent("TransportProtocol");
        return node.map(n -> (String) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public MessageSecurityMode getSecurityMode() {
        Optional<VariableNode> node = getVariableComponent("SecurityMode");
        return node.map(n -> {
            Integer value = (Integer) n.getValue().getValue().getValue();

            return MessageSecurityMode.from(value);
        }).orElse(null);
    }

    @Override
    public String getSecurityPolicyUri() {
        Optional<VariableNode> node = getVariableComponent("SecurityPolicyUri");
        return node.map(n -> (String) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ByteString getClientCertificate() {
        Optional<VariableNode> node = getVariableComponent("ClientCertificate");
        return node.map(n -> (ByteString) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public void setSessionId(NodeId sessionId) {
        getVariableComponent("SessionId").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(sessionId)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setClientUserIdOfSession(String clientUserIdOfSession) {
        getVariableComponent("ClientUserIdOfSession").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(clientUserIdOfSession)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setClientUserIdHistory(String[] clientUserIdHistory) {
        getVariableComponent("ClientUserIdHistory").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(clientUserIdHistory)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setAuthenticationMechanism(String authenticationMechanism) {
        getVariableComponent("AuthenticationMechanism").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(authenticationMechanism)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setEncoding(String encoding) {
        getVariableComponent("Encoding").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(encoding)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setTransportProtocol(String transportProtocol) {
        getVariableComponent("TransportProtocol").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(transportProtocol)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setSecurityMode(MessageSecurityMode securityMode) {
        getVariableComponent("SecurityMode").ifPresent(n -> {
            Integer value = securityMode.getValue();

            n.setValue(new DataValue(new Variant(value)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setSecurityPolicyUri(String securityPolicyUri) {
        getVariableComponent("SecurityPolicyUri").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(securityPolicyUri)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void setClientCertificate(ByteString clientCertificate) {
        getVariableComponent("ClientCertificate").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(clientCertificate)));
            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void atomicSet(Runnable runnable) {
        runnable.run();
    }

}
