package com.inductiveautomation.opcua.sdk.server.model.objects;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.model.objects.AggregateConfigurationType;
import com.inductiveautomation.opcua.sdk.server.api.UaNamespace;
import com.inductiveautomation.opcua.sdk.server.util.UaObjectType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;


@UaObjectType(name = "AggregateConfigurationType")
public class AggregateConfigurationNode extends BaseObjectNode implements AggregateConfigurationType {

    public AggregateConfigurationNode(
            UaNamespace namespace,
            NodeId nodeId,
            QualifiedName browseName,
            LocalizedText displayName,
            Optional<LocalizedText> description,
            Optional<UInteger> writeMask,
            Optional<UInteger> userWriteMask,
            UByte eventNotifier) {

        super(namespace, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
    }

    public Boolean getTreatUncertainAsBad() {
        Optional<Boolean> treatUncertainAsBad = getProperty("TreatUncertainAsBad");

        return treatUncertainAsBad.orElse(null);
    }

    public UByte getPercentDataBad() {
        Optional<UByte> percentDataBad = getProperty("PercentDataBad");

        return percentDataBad.orElse(null);
    }

    public UByte getPercentDataGood() {
        Optional<UByte> percentDataGood = getProperty("PercentDataGood");

        return percentDataGood.orElse(null);
    }

    public Boolean getUseSlopedExtrapolation() {
        Optional<Boolean> useSlopedExtrapolation = getProperty("UseSlopedExtrapolation");

        return useSlopedExtrapolation.orElse(null);
    }

    public synchronized void setTreatUncertainAsBad(Boolean treatUncertainAsBad) {
        getPropertyNode("TreatUncertainAsBad").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(treatUncertainAsBad)));
        });
    }

    public synchronized void setPercentDataBad(UByte percentDataBad) {
        getPropertyNode("PercentDataBad").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(percentDataBad)));
        });
    }

    public synchronized void setPercentDataGood(UByte percentDataGood) {
        getPropertyNode("PercentDataGood").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(percentDataGood)));
        });
    }

    public synchronized void setUseSlopedExtrapolation(Boolean useSlopedExtrapolation) {
        getPropertyNode("UseSlopedExtrapolation").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(useSlopedExtrapolation)));
        });
    }
}
