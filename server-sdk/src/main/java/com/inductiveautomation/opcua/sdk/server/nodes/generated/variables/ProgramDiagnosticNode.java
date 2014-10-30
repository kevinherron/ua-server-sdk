package com.inductiveautomation.opcua.sdk.server.nodes.generated.variables;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.core.model.variables.ProgramDiagnosticType;
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
import com.inductiveautomation.opcua.stack.core.types.structured.Argument;
import com.inductiveautomation.opcua.stack.core.types.structured.ProgramDiagnosticDataType;
import com.inductiveautomation.opcua.stack.core.types.structured.StatusResult;

@UaVariableType(name = "ProgramDiagnosticType")
public class ProgramDiagnosticNode extends BaseDataVariableNode implements ProgramDiagnosticType {

    public ProgramDiagnosticNode(UaNodeManager nodeManager,
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
    public NodeId getCreateSessionId() {
        Optional<NodeId> createSessionId = getProperty("CreateSessionId");

        return createSessionId.orElse(null);
    }

    @Override
    public String getCreateClientName() {
        Optional<String> createClientName = getProperty("CreateClientName");

        return createClientName.orElse(null);
    }

    @Override
    public DateTime getInvocationCreationTime() {
        Optional<DateTime> invocationCreationTime = getProperty("InvocationCreationTime");

        return invocationCreationTime.orElse(null);
    }

    @Override
    public DateTime getLastTransitionTime() {
        Optional<DateTime> lastTransitionTime = getProperty("LastTransitionTime");

        return lastTransitionTime.orElse(null);
    }

    @Override
    public String getLastMethodCall() {
        Optional<String> lastMethodCall = getProperty("LastMethodCall");

        return lastMethodCall.orElse(null);
    }

    @Override
    public NodeId getLastMethodSessionId() {
        Optional<NodeId> lastMethodSessionId = getProperty("LastMethodSessionId");

        return lastMethodSessionId.orElse(null);
    }

    @Override
    public Argument[] getLastMethodInputArguments() {
        Optional<Argument[]> lastMethodInputArguments = getProperty("LastMethodInputArguments");

        return lastMethodInputArguments.orElse(null);
    }

    @Override
    public Argument[] getLastMethodOutputArguments() {
        Optional<Argument[]> lastMethodOutputArguments = getProperty("LastMethodOutputArguments");

        return lastMethodOutputArguments.orElse(null);
    }

    @Override
    public DateTime getLastMethodCallTime() {
        Optional<DateTime> lastMethodCallTime = getProperty("LastMethodCallTime");

        return lastMethodCallTime.orElse(null);
    }

    @Override
    public StatusResult getLastMethodReturnStatus() {
        Optional<StatusResult> lastMethodReturnStatus = getProperty("LastMethodReturnStatus");

        return lastMethodReturnStatus.orElse(null);
    }

    @Override
    public void setCreateSessionId(NodeId createSessionId) {
        getPropertyNode("CreateSessionId").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(createSessionId)));
        });
    }

    @Override
    public void setCreateClientName(String createClientName) {
        getPropertyNode("CreateClientName").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(createClientName)));
        });
    }

    @Override
    public void setInvocationCreationTime(DateTime invocationCreationTime) {
        getPropertyNode("InvocationCreationTime").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(invocationCreationTime)));
        });
    }

    @Override
    public void setLastTransitionTime(DateTime lastTransitionTime) {
        getPropertyNode("LastTransitionTime").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(lastTransitionTime)));
        });
    }

    @Override
    public void setLastMethodCall(String lastMethodCall) {
        getPropertyNode("LastMethodCall").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(lastMethodCall)));
        });
    }

    @Override
    public void setLastMethodSessionId(NodeId lastMethodSessionId) {
        getPropertyNode("LastMethodSessionId").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(lastMethodSessionId)));
        });
    }

    @Override
    public void setLastMethodInputArguments(Argument[] lastMethodInputArguments) {
        getPropertyNode("LastMethodInputArguments").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(lastMethodInputArguments)));
        });
    }

    @Override
    public void setLastMethodOutputArguments(Argument[] lastMethodOutputArguments) {
        getPropertyNode("LastMethodOutputArguments").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(lastMethodOutputArguments)));
        });
    }

    @Override
    public void setLastMethodCallTime(DateTime lastMethodCallTime) {
        getPropertyNode("LastMethodCallTime").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(lastMethodCallTime)));
        });
    }

    @Override
    public void setLastMethodReturnStatus(StatusResult lastMethodReturnStatus) {
        getPropertyNode("LastMethodReturnStatus").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(lastMethodReturnStatus)));
        });
    }

    @Override
    public void atomicSet(Runnable runnable) {
        runnable.run();
    }

}
