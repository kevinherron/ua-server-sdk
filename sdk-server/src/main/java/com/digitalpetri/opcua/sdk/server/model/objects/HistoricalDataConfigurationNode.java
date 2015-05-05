/*
 * digitalpetri OPC-UA SDK
 *
 * Copyright (C) 2015 Kevin Herron
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.digitalpetri.opcua.sdk.server.model.objects;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.model.objects.AggregateConfigurationType;
import com.digitalpetri.opcua.sdk.core.model.objects.FolderType;
import com.digitalpetri.opcua.sdk.core.model.objects.HistoricalDataConfigurationType;
import com.digitalpetri.opcua.sdk.core.nodes.ObjectNode;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.util.UaObjectType;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.ExceptionDeviationFormat;


@UaObjectType(name = "HistoricalDataConfigurationType")
public class HistoricalDataConfigurationNode extends BaseObjectNode implements HistoricalDataConfigurationType {

    public HistoricalDataConfigurationNode(
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

    public AggregateConfigurationType getAggregateConfiguration() {
        Optional<ObjectNode> aggregateConfiguration = getObjectComponent("AggregateConfiguration");

        return aggregateConfiguration.map(node -> (AggregateConfigurationType) node).orElse(null);
    }

    public FolderType getAggregateFunctions() {
        Optional<ObjectNode> aggregateFunctions = getObjectComponent("AggregateFunctions");

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
        Optional<Integer> exceptionDeviationFormat = getProperty("ExceptionDeviationFormat");

        return exceptionDeviationFormat.map(ExceptionDeviationFormat::from).orElse(null);
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
            Integer value = exceptionDeviationFormat.getValue();

            n.setValue(new DataValue(new Variant(value)));
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
}
