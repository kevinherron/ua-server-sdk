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

import com.digitalpetri.opcua.sdk.core.model.objects.HistoryServerCapabilitiesType;
import com.digitalpetri.opcua.sdk.core.nodes.ObjectNode;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.sdk.server.model.variables.PropertyNode;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@com.digitalpetri.opcua.sdk.server.util.UaObjectNode(typeName = "0:HistoryServerCapabilitiesType")
public class HistoryServerCapabilitiesNode extends BaseObjectNode implements HistoryServerCapabilitiesType {

    public HistoryServerCapabilitiesNode(
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
    public Boolean getAccessHistoryDataCapability() {
        Optional<Boolean> property = getProperty(HistoryServerCapabilitiesType.ACCESS_HISTORY_DATA_CAPABILITY);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getAccessHistoryDataCapabilityNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(HistoryServerCapabilitiesType.ACCESS_HISTORY_DATA_CAPABILITY.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setAccessHistoryDataCapability(Boolean value) {
        setProperty(HistoryServerCapabilitiesType.ACCESS_HISTORY_DATA_CAPABILITY, value);
    }

    @Override
    public Boolean getAccessHistoryEventsCapability() {
        Optional<Boolean> property = getProperty(HistoryServerCapabilitiesType.ACCESS_HISTORY_EVENTS_CAPABILITY);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getAccessHistoryEventsCapabilityNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(HistoryServerCapabilitiesType.ACCESS_HISTORY_EVENTS_CAPABILITY.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setAccessHistoryEventsCapability(Boolean value) {
        setProperty(HistoryServerCapabilitiesType.ACCESS_HISTORY_EVENTS_CAPABILITY, value);
    }

    @Override
    public UInteger getMaxReturnDataValues() {
        Optional<UInteger> property = getProperty(HistoryServerCapabilitiesType.MAX_RETURN_DATA_VALUES);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getMaxReturnDataValuesNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(HistoryServerCapabilitiesType.MAX_RETURN_DATA_VALUES.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setMaxReturnDataValues(UInteger value) {
        setProperty(HistoryServerCapabilitiesType.MAX_RETURN_DATA_VALUES, value);
    }

    @Override
    public UInteger getMaxReturnEventValues() {
        Optional<UInteger> property = getProperty(HistoryServerCapabilitiesType.MAX_RETURN_EVENT_VALUES);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getMaxReturnEventValuesNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(HistoryServerCapabilitiesType.MAX_RETURN_EVENT_VALUES.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setMaxReturnEventValues(UInteger value) {
        setProperty(HistoryServerCapabilitiesType.MAX_RETURN_EVENT_VALUES, value);
    }

    @Override
    public Boolean getInsertDataCapability() {
        Optional<Boolean> property = getProperty(HistoryServerCapabilitiesType.INSERT_DATA_CAPABILITY);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getInsertDataCapabilityNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(HistoryServerCapabilitiesType.INSERT_DATA_CAPABILITY.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setInsertDataCapability(Boolean value) {
        setProperty(HistoryServerCapabilitiesType.INSERT_DATA_CAPABILITY, value);
    }

    @Override
    public Boolean getReplaceDataCapability() {
        Optional<Boolean> property = getProperty(HistoryServerCapabilitiesType.REPLACE_DATA_CAPABILITY);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getReplaceDataCapabilityNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(HistoryServerCapabilitiesType.REPLACE_DATA_CAPABILITY.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setReplaceDataCapability(Boolean value) {
        setProperty(HistoryServerCapabilitiesType.REPLACE_DATA_CAPABILITY, value);
    }

    @Override
    public Boolean getUpdateDataCapability() {
        Optional<Boolean> property = getProperty(HistoryServerCapabilitiesType.UPDATE_DATA_CAPABILITY);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getUpdateDataCapabilityNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(HistoryServerCapabilitiesType.UPDATE_DATA_CAPABILITY.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setUpdateDataCapability(Boolean value) {
        setProperty(HistoryServerCapabilitiesType.UPDATE_DATA_CAPABILITY, value);
    }

    @Override
    public Boolean getDeleteRawCapability() {
        Optional<Boolean> property = getProperty(HistoryServerCapabilitiesType.DELETE_RAW_CAPABILITY);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getDeleteRawCapabilityNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(HistoryServerCapabilitiesType.DELETE_RAW_CAPABILITY.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setDeleteRawCapability(Boolean value) {
        setProperty(HistoryServerCapabilitiesType.DELETE_RAW_CAPABILITY, value);
    }

    @Override
    public Boolean getDeleteAtTimeCapability() {
        Optional<Boolean> property = getProperty(HistoryServerCapabilitiesType.DELETE_AT_TIME_CAPABILITY);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getDeleteAtTimeCapabilityNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(HistoryServerCapabilitiesType.DELETE_AT_TIME_CAPABILITY.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setDeleteAtTimeCapability(Boolean value) {
        setProperty(HistoryServerCapabilitiesType.DELETE_AT_TIME_CAPABILITY, value);
    }

    @Override
    public Boolean getInsertEventCapability() {
        Optional<Boolean> property = getProperty(HistoryServerCapabilitiesType.INSERT_EVENT_CAPABILITY);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getInsertEventCapabilityNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(HistoryServerCapabilitiesType.INSERT_EVENT_CAPABILITY.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setInsertEventCapability(Boolean value) {
        setProperty(HistoryServerCapabilitiesType.INSERT_EVENT_CAPABILITY, value);
    }

    @Override
    public Boolean getReplaceEventCapability() {
        Optional<Boolean> property = getProperty(HistoryServerCapabilitiesType.REPLACE_EVENT_CAPABILITY);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getReplaceEventCapabilityNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(HistoryServerCapabilitiesType.REPLACE_EVENT_CAPABILITY.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setReplaceEventCapability(Boolean value) {
        setProperty(HistoryServerCapabilitiesType.REPLACE_EVENT_CAPABILITY, value);
    }

    @Override
    public Boolean getUpdateEventCapability() {
        Optional<Boolean> property = getProperty(HistoryServerCapabilitiesType.UPDATE_EVENT_CAPABILITY);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getUpdateEventCapabilityNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(HistoryServerCapabilitiesType.UPDATE_EVENT_CAPABILITY.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setUpdateEventCapability(Boolean value) {
        setProperty(HistoryServerCapabilitiesType.UPDATE_EVENT_CAPABILITY, value);
    }

    @Override
    public Boolean getDeleteEventCapability() {
        Optional<Boolean> property = getProperty(HistoryServerCapabilitiesType.DELETE_EVENT_CAPABILITY);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getDeleteEventCapabilityNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(HistoryServerCapabilitiesType.DELETE_EVENT_CAPABILITY.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setDeleteEventCapability(Boolean value) {
        setProperty(HistoryServerCapabilitiesType.DELETE_EVENT_CAPABILITY, value);
    }

    @Override
    public Boolean getInsertAnnotationCapability() {
        Optional<Boolean> property = getProperty(HistoryServerCapabilitiesType.INSERT_ANNOTATION_CAPABILITY);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getInsertAnnotationCapabilityNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(HistoryServerCapabilitiesType.INSERT_ANNOTATION_CAPABILITY.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setInsertAnnotationCapability(Boolean value) {
        setProperty(HistoryServerCapabilitiesType.INSERT_ANNOTATION_CAPABILITY, value);
    }

    @Override
    public FolderNode getAggregateFunctionsNode() {
        Optional<ObjectNode> component = getObjectComponent("AggregateFunctions");

        return component.map(node -> (FolderNode) node).orElse(null);
    }

}
