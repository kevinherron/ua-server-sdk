package com.inductiveautomation.opcua.sdk.server.model.variables;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.model.UaMandatory;
import com.inductiveautomation.opcua.sdk.core.model.UaOptional;
import com.inductiveautomation.opcua.sdk.core.model.variables.TwoStateVariableType;
import com.inductiveautomation.opcua.sdk.server.api.UaNamespace;
import com.inductiveautomation.opcua.sdk.server.util.UaVariableType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaVariableType(name = "TwoStateVariableType")
public class TwoStateVariableNode extends StateVariableNode implements TwoStateVariableType {

    public TwoStateVariableNode(UaNamespace nodeManager,
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
    @UaMandatory("Id")
    public Boolean getId() {
        Optional<Boolean> id = getProperty("Id");

        return id.orElse(null);
    }

    @Override
    @UaOptional("TransitionTime")
    public DateTime getTransitionTime() {
        Optional<DateTime> transitionTime = getProperty("TransitionTime");

        return transitionTime.orElse(null);
    }

    @Override
    @UaOptional("EffectiveTransitionTime")
    public DateTime getEffectiveTransitionTime() {
        Optional<DateTime> effectiveTransitionTime = getProperty("EffectiveTransitionTime");

        return effectiveTransitionTime.orElse(null);
    }

    @Override
    @UaOptional("TrueState")
    public LocalizedText getTrueState() {
        Optional<LocalizedText> trueState = getProperty("TrueState");

        return trueState.orElse(null);
    }

    @Override
    @UaOptional("FalseState")
    public LocalizedText getFalseState() {
        Optional<LocalizedText> falseState = getProperty("FalseState");

        return falseState.orElse(null);
    }

    @Override
    public synchronized void setId(Boolean id) {
        getPropertyNode("Id").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(id)));
        });
    }

    @Override
    public synchronized void setTransitionTime(DateTime transitionTime) {
        getPropertyNode("TransitionTime").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(transitionTime)));
        });
    }

    @Override
    public synchronized void setEffectiveTransitionTime(DateTime effectiveTransitionTime) {
        getPropertyNode("EffectiveTransitionTime").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(effectiveTransitionTime)));
        });
    }

    @Override
    public synchronized void setTrueState(LocalizedText trueState) {
        getPropertyNode("TrueState").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(trueState)));
        });
    }

    @Override
    public synchronized void setFalseState(LocalizedText falseState) {
        getPropertyNode("FalseState").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(falseState)));
        });
    }

    @Override
    public void atomicAction(Runnable runnable) {
        runnable.run();
    }

}
