package com.inductiveautomation.opcua.sdk.server.model.variables;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.core.model.UaMandatory;
import com.inductiveautomation.opcua.sdk.core.model.variables.SamplingIntervalDiagnosticsType;
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
import com.inductiveautomation.opcua.stack.core.types.structured.SamplingIntervalDiagnosticsDataType;

@UaVariableType(name = "SamplingIntervalDiagnosticsType")
public class SamplingIntervalDiagnosticsNode extends BaseDataVariableNode implements SamplingIntervalDiagnosticsType {

    public SamplingIntervalDiagnosticsNode(UaNamespace namespace,
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
        SamplingIntervalDiagnosticsDataType value = new SamplingIntervalDiagnosticsDataType(
                getSamplingInterval(),
                getSampledMonitoredItemsCount(),
                getMaxSampledMonitoredItemsCount(),
                getDisabledMonitoredItemsSamplingCount()
        );

        return new DataValue(new Variant(value));
    }

    @Override
    public synchronized void setValue(DataValue value) {
        SamplingIntervalDiagnosticsDataType v = (SamplingIntervalDiagnosticsDataType) value.getValue().getValue();

        setSamplingInterval(v.getSamplingInterval());
        setSampledMonitoredItemsCount(v.getMonitoredItemCount());
        setMaxSampledMonitoredItemsCount(v.getMaxMonitoredItemCount());
        setDisabledMonitoredItemsSamplingCount(v.getDisabledMonitoredItemCount());

        fireAttributeChanged(AttributeIds.Value, value);
    }

    @Override
    @UaMandatory("SamplingInterval")
    public Double getSamplingInterval() {
        Optional<VariableNode> node = getVariableComponent("SamplingInterval");

        return node.map(n -> (Double) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("SampledMonitoredItemsCount")
    public UInteger getSampledMonitoredItemsCount() {
        Optional<VariableNode> node = getVariableComponent("SampledMonitoredItemsCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("MaxSampledMonitoredItemsCount")
    public UInteger getMaxSampledMonitoredItemsCount() {
        Optional<VariableNode> node = getVariableComponent("MaxSampledMonitoredItemsCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("DisabledMonitoredItemsSamplingCount")
    public UInteger getDisabledMonitoredItemsSamplingCount() {
        Optional<VariableNode> node = getVariableComponent("DisabledMonitoredItemsSamplingCount");

        return node.map(n -> (UInteger) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public synchronized void setSamplingInterval(Double samplingInterval) {
        getVariableComponent("SamplingInterval").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(samplingInterval)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setSampledMonitoredItemsCount(UInteger sampledMonitoredItemsCount) {
        getVariableComponent("SampledMonitoredItemsCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(sampledMonitoredItemsCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setMaxSampledMonitoredItemsCount(UInteger maxSampledMonitoredItemsCount) {
        getVariableComponent("MaxSampledMonitoredItemsCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxSampledMonitoredItemsCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setDisabledMonitoredItemsSamplingCount(UInteger disabledMonitoredItemsSamplingCount) {
        getVariableComponent("DisabledMonitoredItemsSamplingCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(disabledMonitoredItemsSamplingCount)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

}
