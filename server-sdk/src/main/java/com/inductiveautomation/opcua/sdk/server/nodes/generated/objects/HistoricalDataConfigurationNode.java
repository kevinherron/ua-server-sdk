package com.inductiveautomation.opcua.sdk.server.nodes.generated.objects;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.model.objects.AggregateConfigurationType;
import com.inductiveautomation.opcua.sdk.core.model.objects.FolderType;
import com.inductiveautomation.opcua.sdk.core.model.objects.HistoricalDataConfigurationType;
import com.inductiveautomation.opcua.sdk.server.api.UaNodeManager;
import com.inductiveautomation.opcua.sdk.server.util.UaObjectType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.ExceptionDeviationFormat;


@UaObjectType(name = "HistoricalDataConfigurationType")
public class HistoricalDataConfigurationNode extends BaseObjectNode implements HistoricalDataConfigurationType {

    public HistoricalDataConfigurationNode(
            UaNodeManager nodeManager,
            NodeId nodeId,
            QualifiedName browseName,
            LocalizedText displayName,
            Optional<LocalizedText> description,
            Optional<UInteger> writeMask,
            Optional<UInteger> userWriteMask,
            UByte eventNotifier) {

        super(nodeManager, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
    }

    public AggregateConfigurationType getAggregateConfiguration() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> aggregateConfiguration = getObjectComponent("AggregateConfiguration");

        return aggregateConfiguration.map(node -> (AggregateConfigurationType) node).orElse(null);
    }

    public FolderType getAggregateFunctions() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> aggregateFunctions = getObjectComponent("AggregateFunctions");

        return aggregateFunctions.map(node -> (FolderType) node).orElse(null);
    }

    public Boolean getStepped() {
        Optional<Boolean> stepped = getProperty("Stepped");

        return stepped.orElse(null);
    }

    public String getDefinition() {
        Optional<String> definition = getProperty("Definition");

        return definition.orElse(null);
    }

    public Double getMaxTimeInterval() {
        Optional<Double> maxTimeInterval = getProperty("MaxTimeInterval");

        return maxTimeInterval.orElse(null);
    }

    public Double getMinTimeInterval() {
        Optional<Double> minTimeInterval = getProperty("MinTimeInterval");

        return minTimeInterval.orElse(null);
    }

    public Double getExceptionDeviation() {
        Optional<Double> exceptionDeviation = getProperty("ExceptionDeviation");

        return exceptionDeviation.orElse(null);
    }

    public ExceptionDeviationFormat getExceptionDeviationFormat() {
        Optional<ExceptionDeviationFormat> exceptionDeviationFormat = getProperty("ExceptionDeviationFormat");

        return exceptionDeviationFormat.orElse(null);
    }

    public DateTime getStartOfArchive() {
        Optional<DateTime> startOfArchive = getProperty("StartOfArchive");

        return startOfArchive.orElse(null);
    }

    public DateTime getStartOfOnlineArchive() {
        Optional<DateTime> startOfOnlineArchive = getProperty("StartOfOnlineArchive");

        return startOfOnlineArchive.orElse(null);
    }

    public synchronized void setStepped(Boolean stepped) {
        getPropertyNode("Stepped").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(stepped)));
        });
    }

    public synchronized void setDefinition(String definition) {
        getPropertyNode("Definition").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(definition)));
        });
    }

    public synchronized void setMaxTimeInterval(Double maxTimeInterval) {
        getPropertyNode("MaxTimeInterval").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxTimeInterval)));
        });
    }

    public synchronized void setMinTimeInterval(Double minTimeInterval) {
        getPropertyNode("MinTimeInterval").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(minTimeInterval)));
        });
    }

    public synchronized void setExceptionDeviation(Double exceptionDeviation) {
        getPropertyNode("ExceptionDeviation").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(exceptionDeviation)));
        });
    }

    public synchronized void setExceptionDeviationFormat(ExceptionDeviationFormat exceptionDeviationFormat) {
        getPropertyNode("ExceptionDeviationFormat").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(exceptionDeviationFormat)));
        });
    }

    public synchronized void setStartOfArchive(DateTime startOfArchive) {
        getPropertyNode("StartOfArchive").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(startOfArchive)));
        });
    }

    public synchronized void setStartOfOnlineArchive(DateTime startOfOnlineArchive) {
        getPropertyNode("StartOfOnlineArchive").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(startOfOnlineArchive)));
        });
    }

    public synchronized void atomicSet(Runnable runnable) {
        runnable.run();
    }

}
