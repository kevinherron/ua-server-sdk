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

import com.digitalpetri.opcua.sdk.core.model.objects.HistoricalDataConfigurationType;
import com.digitalpetri.opcua.sdk.core.nodes.ObjectNode;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.sdk.server.model.variables.PropertyNode;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.ExceptionDeviationFormat;

@com.digitalpetri.opcua.sdk.server.util.UaObjectNode(typeName = "0:HistoricalDataConfigurationType")
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

    @Override
    public Boolean getStepped() {
        Optional<Boolean> property = getProperty(HistoricalDataConfigurationType.STEPPED);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getSteppedNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(HistoricalDataConfigurationType.STEPPED.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setStepped(Boolean value) {
        setProperty(HistoricalDataConfigurationType.STEPPED, value);
    }

    @Override
    public String getDefinition() {
        Optional<String> property = getProperty(HistoricalDataConfigurationType.DEFINITION);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getDefinitionNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(HistoricalDataConfigurationType.DEFINITION.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setDefinition(String value) {
        setProperty(HistoricalDataConfigurationType.DEFINITION, value);
    }

    @Override
    public Double getMaxTimeInterval() {
        Optional<Double> property = getProperty(HistoricalDataConfigurationType.MAX_TIME_INTERVAL);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getMaxTimeIntervalNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(HistoricalDataConfigurationType.MAX_TIME_INTERVAL.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setMaxTimeInterval(Double value) {
        setProperty(HistoricalDataConfigurationType.MAX_TIME_INTERVAL, value);
    }

    @Override
    public Double getMinTimeInterval() {
        Optional<Double> property = getProperty(HistoricalDataConfigurationType.MIN_TIME_INTERVAL);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getMinTimeIntervalNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(HistoricalDataConfigurationType.MIN_TIME_INTERVAL.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setMinTimeInterval(Double value) {
        setProperty(HistoricalDataConfigurationType.MIN_TIME_INTERVAL, value);
    }

    @Override
    public Double getExceptionDeviation() {
        Optional<Double> property = getProperty(HistoricalDataConfigurationType.EXCEPTION_DEVIATION);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getExceptionDeviationNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(HistoricalDataConfigurationType.EXCEPTION_DEVIATION.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setExceptionDeviation(Double value) {
        setProperty(HistoricalDataConfigurationType.EXCEPTION_DEVIATION, value);
    }

    @Override
    public ExceptionDeviationFormat getExceptionDeviationFormat() {
        Optional<ExceptionDeviationFormat> property = getProperty(HistoricalDataConfigurationType.EXCEPTION_DEVIATION_FORMAT);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getExceptionDeviationFormatNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(HistoricalDataConfigurationType.EXCEPTION_DEVIATION_FORMAT.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setExceptionDeviationFormat(ExceptionDeviationFormat value) {
        setProperty(HistoricalDataConfigurationType.EXCEPTION_DEVIATION_FORMAT, value);
    }

    @Override
    public DateTime getStartOfArchive() {
        Optional<DateTime> property = getProperty(HistoricalDataConfigurationType.START_OF_ARCHIVE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getStartOfArchiveNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(HistoricalDataConfigurationType.START_OF_ARCHIVE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setStartOfArchive(DateTime value) {
        setProperty(HistoricalDataConfigurationType.START_OF_ARCHIVE, value);
    }

    @Override
    public DateTime getStartOfOnlineArchive() {
        Optional<DateTime> property = getProperty(HistoricalDataConfigurationType.START_OF_ONLINE_ARCHIVE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getStartOfOnlineArchiveNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(HistoricalDataConfigurationType.START_OF_ONLINE_ARCHIVE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setStartOfOnlineArchive(DateTime value) {
        setProperty(HistoricalDataConfigurationType.START_OF_ONLINE_ARCHIVE, value);
    }

    @Override
    public AggregateConfigurationNode getAggregateConfigurationNode() {
        Optional<ObjectNode> component = getObjectComponent("AggregateConfiguration");

        return component.map(node -> (AggregateConfigurationNode) node).orElse(null);
    }

    @Override
    public FolderNode getAggregateFunctionsNode() {
        Optional<ObjectNode> component = getObjectComponent("AggregateFunctions");

        return component.map(node -> (FolderNode) node).orElse(null);
    }

}
