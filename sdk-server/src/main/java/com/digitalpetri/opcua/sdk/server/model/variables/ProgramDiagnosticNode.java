package com.digitalpetri.opcua.sdk.server.model.variables;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.AttributeIds;
import com.digitalpetri.opcua.sdk.core.model.UaMandatory;
import com.digitalpetri.opcua.sdk.core.model.variables.ProgramDiagnosticType;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.util.UaVariableType;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.structured.Argument;
import com.digitalpetri.opcua.stack.core.types.structured.ProgramDiagnosticDataType;
import com.digitalpetri.opcua.stack.core.types.structured.StatusResult;

@UaVariableType(name = "ProgramDiagnosticType")
public class ProgramDiagnosticNode extends BaseDataVariableNode implements ProgramDiagnosticType {

    public ProgramDiagnosticNode(UaNamespace namespace,
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
        ProgramDiagnosticDataType value = new ProgramDiagnosticDataType(
                getCreateSessionId(),
                getCreateClientName(),
                getInvocationCreationTime(),
                getLastTransitionTime(),
                getLastMethodCall(),
                getLastMethodSessionId(),
                getLastMethodInputArguments(),
                getLastMethodOutputArguments(),
                getLastMethodCallTime(),
                getLastMethodReturnStatus()
        );

        return new DataValue(new Variant(value));
    }

    @Override
    public synchronized void setValue(DataValue value) {
        ProgramDiagnosticDataType v = (ProgramDiagnosticDataType) value.getValue().getValue();

        setCreateSessionId(v.getCreateSessionId());
        setCreateClientName(v.getCreateClientName());
        setInvocationCreationTime(v.getInvocationCreationTime());
        setLastTransitionTime(v.getLastTransitionTime());
        setLastMethodCall(v.getLastMethodCall());
        setLastMethodSessionId(v.getLastMethodSessionId());
        setLastMethodInputArguments(v.getLastMethodInputArguments());
        setLastMethodOutputArguments(v.getLastMethodOutputArguments());
        setLastMethodCallTime(v.getLastMethodCallTime());
        setLastMethodReturnStatus(v.getLastMethodReturnStatus());

        fireAttributeChanged(AttributeIds.Value, value);
    }

    @Override
    @UaMandatory("CreateSessionId")
    public NodeId getCreateSessionId() {
        Optional<NodeId> createSessionId = getProperty("CreateSessionId");

        return createSessionId.orElse(null);
    }

    @Override
    @UaMandatory("CreateClientName")
    public String getCreateClientName() {
        Optional<String> createClientName = getProperty("CreateClientName");

        return createClientName.orElse(null);
    }

    @Override
    @UaMandatory("InvocationCreationTime")
    public DateTime getInvocationCreationTime() {
        Optional<DateTime> invocationCreationTime = getProperty("InvocationCreationTime");

        return invocationCreationTime.orElse(null);
    }

    @Override
    @UaMandatory("LastTransitionTime")
    public DateTime getLastTransitionTime() {
        Optional<DateTime> lastTransitionTime = getProperty("LastTransitionTime");

        return lastTransitionTime.orElse(null);
    }

    @Override
    @UaMandatory("LastMethodCall")
    public String getLastMethodCall() {
        Optional<String> lastMethodCall = getProperty("LastMethodCall");

        return lastMethodCall.orElse(null);
    }

    @Override
    @UaMandatory("LastMethodSessionId")
    public NodeId getLastMethodSessionId() {
        Optional<NodeId> lastMethodSessionId = getProperty("LastMethodSessionId");

        return lastMethodSessionId.orElse(null);
    }

    @Override
    @UaMandatory("LastMethodInputArguments")
    public Argument[] getLastMethodInputArguments() {
        Optional<Argument[]> lastMethodInputArguments = getProperty("LastMethodInputArguments");

        return lastMethodInputArguments.orElse(null);
    }

    @Override
    @UaMandatory("LastMethodOutputArguments")
    public Argument[] getLastMethodOutputArguments() {
        Optional<Argument[]> lastMethodOutputArguments = getProperty("LastMethodOutputArguments");

        return lastMethodOutputArguments.orElse(null);
    }

    @Override
    @UaMandatory("LastMethodCallTime")
    public DateTime getLastMethodCallTime() {
        Optional<DateTime> lastMethodCallTime = getProperty("LastMethodCallTime");

        return lastMethodCallTime.orElse(null);
    }

    @Override
    @UaMandatory("LastMethodReturnStatus")
    public StatusResult getLastMethodReturnStatus() {
        Optional<StatusResult> lastMethodReturnStatus = getProperty("LastMethodReturnStatus");

        return lastMethodReturnStatus.orElse(null);
    }

    @Override
    public synchronized void setCreateSessionId(NodeId createSessionId) {
        getPropertyNode("CreateSessionId").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(createSessionId)));
        });
    }

    @Override
    public synchronized void setCreateClientName(String createClientName) {
        getPropertyNode("CreateClientName").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(createClientName)));
        });
    }

    @Override
    public synchronized void setInvocationCreationTime(DateTime invocationCreationTime) {
        getPropertyNode("InvocationCreationTime").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(invocationCreationTime)));
        });
    }

    @Override
    public synchronized void setLastTransitionTime(DateTime lastTransitionTime) {
        getPropertyNode("LastTransitionTime").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(lastTransitionTime)));
        });
    }

    @Override
    public synchronized void setLastMethodCall(String lastMethodCall) {
        getPropertyNode("LastMethodCall").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(lastMethodCall)));
        });
    }

    @Override
    public synchronized void setLastMethodSessionId(NodeId lastMethodSessionId) {
        getPropertyNode("LastMethodSessionId").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(lastMethodSessionId)));
        });
    }

    @Override
    public synchronized void setLastMethodInputArguments(Argument[] lastMethodInputArguments) {
        getPropertyNode("LastMethodInputArguments").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(lastMethodInputArguments)));
        });
    }

    @Override
    public synchronized void setLastMethodOutputArguments(Argument[] lastMethodOutputArguments) {
        getPropertyNode("LastMethodOutputArguments").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(lastMethodOutputArguments)));
        });
    }

    @Override
    public synchronized void setLastMethodCallTime(DateTime lastMethodCallTime) {
        getPropertyNode("LastMethodCallTime").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(lastMethodCallTime)));
        });
    }

    @Override
    public synchronized void setLastMethodReturnStatus(StatusResult lastMethodReturnStatus) {
        getPropertyNode("LastMethodReturnStatus").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(lastMethodReturnStatus)));
        });
    }

}
