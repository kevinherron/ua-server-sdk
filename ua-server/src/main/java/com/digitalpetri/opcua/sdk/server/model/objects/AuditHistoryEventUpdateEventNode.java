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

import com.digitalpetri.opcua.sdk.core.model.objects.AuditHistoryEventUpdateEventType;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.sdk.server.model.variables.PropertyNode;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.PerformUpdateType;
import com.digitalpetri.opcua.stack.core.types.structured.EventFilter;
import com.digitalpetri.opcua.stack.core.types.structured.HistoryEventFieldList;

@com.digitalpetri.opcua.sdk.server.util.UaObjectNode(typeName = "0:AuditHistoryEventUpdateEventType")
public class AuditHistoryEventUpdateEventNode extends AuditHistoryUpdateEventNode implements AuditHistoryEventUpdateEventType {

    public AuditHistoryEventUpdateEventNode(
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
    public NodeId getUpdatedNode() {
        Optional<NodeId> property = getProperty(AuditHistoryEventUpdateEventType.UPDATED_NODE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getUpdatedNodeNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(AuditHistoryEventUpdateEventType.UPDATED_NODE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setUpdatedNode(NodeId value) {
        setProperty(AuditHistoryEventUpdateEventType.UPDATED_NODE, value);
    }

    @Override
    public PerformUpdateType getPerformInsertReplace() {
        Optional<PerformUpdateType> property = getProperty(AuditHistoryEventUpdateEventType.PERFORM_INSERT_REPLACE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getPerformInsertReplaceNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(AuditHistoryEventUpdateEventType.PERFORM_INSERT_REPLACE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setPerformInsertReplace(PerformUpdateType value) {
        setProperty(AuditHistoryEventUpdateEventType.PERFORM_INSERT_REPLACE, value);
    }

    @Override
    public EventFilter getFilter() {
        Optional<EventFilter> property = getProperty(AuditHistoryEventUpdateEventType.FILTER);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getFilterNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(AuditHistoryEventUpdateEventType.FILTER.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setFilter(EventFilter value) {
        setProperty(AuditHistoryEventUpdateEventType.FILTER, value);
    }

    @Override
    public HistoryEventFieldList[] getNewValues() {
        Optional<HistoryEventFieldList[]> property = getProperty(AuditHistoryEventUpdateEventType.NEW_VALUES);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getNewValuesNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(AuditHistoryEventUpdateEventType.NEW_VALUES.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setNewValues(HistoryEventFieldList[] value) {
        setProperty(AuditHistoryEventUpdateEventType.NEW_VALUES, value);
    }

    @Override
    public HistoryEventFieldList[] getOldValues() {
        Optional<HistoryEventFieldList[]> property = getProperty(AuditHistoryEventUpdateEventType.OLD_VALUES);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getOldValuesNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(AuditHistoryEventUpdateEventType.OLD_VALUES.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setOldValues(HistoryEventFieldList[] value) {
        setProperty(AuditHistoryEventUpdateEventType.OLD_VALUES, value);
    }

}
