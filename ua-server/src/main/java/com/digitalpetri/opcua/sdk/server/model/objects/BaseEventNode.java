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

import com.digitalpetri.opcua.sdk.core.model.objects.BaseEventType;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.sdk.server.model.variables.PropertyNode;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;
import com.digitalpetri.opcua.stack.core.types.structured.TimeZoneDataType;

@com.digitalpetri.opcua.sdk.server.util.UaObjectNode(typeName = "0:BaseEventType")
public class BaseEventNode extends BaseObjectNode implements BaseEventType {

    public BaseEventNode(
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
    public ByteString getEventId() {
        Optional<ByteString> property = getProperty(BaseEventType.EVENT_ID);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getEventIdNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(BaseEventType.EVENT_ID.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setEventId(ByteString value) {
        setProperty(BaseEventType.EVENT_ID, value);
    }

    @Override
    public NodeId getEventType() {
        Optional<NodeId> property = getProperty(BaseEventType.EVENT_TYPE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getEventTypeNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(BaseEventType.EVENT_TYPE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setEventType(NodeId value) {
        setProperty(BaseEventType.EVENT_TYPE, value);
    }

    @Override
    public NodeId getSourceNode() {
        Optional<NodeId> property = getProperty(BaseEventType.SOURCE_NODE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getSourceNodeNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(BaseEventType.SOURCE_NODE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setSourceNode(NodeId value) {
        setProperty(BaseEventType.SOURCE_NODE, value);
    }

    @Override
    public String getSourceName() {
        Optional<String> property = getProperty(BaseEventType.SOURCE_NAME);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getSourceNameNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(BaseEventType.SOURCE_NAME.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setSourceName(String value) {
        setProperty(BaseEventType.SOURCE_NAME, value);
    }

    @Override
    public DateTime getTime() {
        Optional<DateTime> property = getProperty(BaseEventType.TIME);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getTimeNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(BaseEventType.TIME.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setTime(DateTime value) {
        setProperty(BaseEventType.TIME, value);
    }

    @Override
    public DateTime getReceiveTime() {
        Optional<DateTime> property = getProperty(BaseEventType.RECEIVE_TIME);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getReceiveTimeNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(BaseEventType.RECEIVE_TIME.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setReceiveTime(DateTime value) {
        setProperty(BaseEventType.RECEIVE_TIME, value);
    }

    @Override
    public TimeZoneDataType getLocalTime() {
        Optional<TimeZoneDataType> property = getProperty(BaseEventType.LOCAL_TIME);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getLocalTimeNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(BaseEventType.LOCAL_TIME.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setLocalTime(TimeZoneDataType value) {
        setProperty(BaseEventType.LOCAL_TIME, value);
    }

    @Override
    public LocalizedText getMessage() {
        Optional<LocalizedText> property = getProperty(BaseEventType.MESSAGE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getMessageNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(BaseEventType.MESSAGE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setMessage(LocalizedText value) {
        setProperty(BaseEventType.MESSAGE, value);
    }

    @Override
    public UShort getSeverity() {
        Optional<UShort> property = getProperty(BaseEventType.SEVERITY);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getSeverityNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(BaseEventType.SEVERITY.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setSeverity(UShort value) {
        setProperty(BaseEventType.SEVERITY, value);
    }

}
