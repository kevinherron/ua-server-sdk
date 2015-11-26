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

import com.digitalpetri.opcua.sdk.core.model.objects.AuditEventType;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.sdk.server.model.variables.PropertyNode;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@com.digitalpetri.opcua.sdk.server.util.UaObjectNode(typeName = "0:AuditEventType")
public class AuditEventNode extends BaseEventNode implements AuditEventType {

    public AuditEventNode(
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
    public DateTime getActionTimeStamp() {
        Optional<DateTime> property = getProperty(AuditEventType.ACTION_TIME_STAMP);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getActionTimeStampNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(AuditEventType.ACTION_TIME_STAMP.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setActionTimeStamp(DateTime value) {
        setProperty(AuditEventType.ACTION_TIME_STAMP, value);
    }

    @Override
    public Boolean getStatus() {
        Optional<Boolean> property = getProperty(AuditEventType.STATUS);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getStatusNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(AuditEventType.STATUS.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setStatus(Boolean value) {
        setProperty(AuditEventType.STATUS, value);
    }

    @Override
    public String getServerId() {
        Optional<String> property = getProperty(AuditEventType.SERVER_ID);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getServerIdNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(AuditEventType.SERVER_ID.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setServerId(String value) {
        setProperty(AuditEventType.SERVER_ID, value);
    }

    @Override
    public String getClientAuditEntryId() {
        Optional<String> property = getProperty(AuditEventType.CLIENT_AUDIT_ENTRY_ID);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getClientAuditEntryIdNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(AuditEventType.CLIENT_AUDIT_ENTRY_ID.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setClientAuditEntryId(String value) {
        setProperty(AuditEventType.CLIENT_AUDIT_ENTRY_ID, value);
    }

    @Override
    public String getClientUserId() {
        Optional<String> property = getProperty(AuditEventType.CLIENT_USER_ID);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getClientUserIdNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(AuditEventType.CLIENT_USER_ID.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setClientUserId(String value) {
        setProperty(AuditEventType.CLIENT_USER_ID, value);
    }

}
