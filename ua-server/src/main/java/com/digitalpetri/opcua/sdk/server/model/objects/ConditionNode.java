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

import com.digitalpetri.opcua.sdk.core.model.objects.ConditionType;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.sdk.server.model.variables.ConditionVariableNode;
import com.digitalpetri.opcua.sdk.server.model.variables.PropertyNode;
import com.digitalpetri.opcua.sdk.server.model.variables.TwoStateVariableNode;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;

@com.digitalpetri.opcua.sdk.server.util.UaObjectNode(typeName = "0:ConditionType")
public class ConditionNode extends BaseEventNode implements ConditionType {

    public ConditionNode(
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
    public NodeId getConditionClassId() {
        Optional<NodeId> property = getProperty(ConditionType.CONDITION_CLASS_ID);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getConditionClassIdNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ConditionType.CONDITION_CLASS_ID.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setConditionClassId(NodeId value) {
        setProperty(ConditionType.CONDITION_CLASS_ID, value);
    }

    @Override
    public LocalizedText getConditionClassName() {
        Optional<LocalizedText> property = getProperty(ConditionType.CONDITION_CLASS_NAME);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getConditionClassNameNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ConditionType.CONDITION_CLASS_NAME.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setConditionClassName(LocalizedText value) {
        setProperty(ConditionType.CONDITION_CLASS_NAME, value);
    }

    @Override
    public String getConditionName() {
        Optional<String> property = getProperty(ConditionType.CONDITION_NAME);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getConditionNameNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ConditionType.CONDITION_NAME.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setConditionName(String value) {
        setProperty(ConditionType.CONDITION_NAME, value);
    }

    @Override
    public NodeId getBranchId() {
        Optional<NodeId> property = getProperty(ConditionType.BRANCH_ID);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getBranchIdNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ConditionType.BRANCH_ID.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setBranchId(NodeId value) {
        setProperty(ConditionType.BRANCH_ID, value);
    }

    @Override
    public Boolean getRetain() {
        Optional<Boolean> property = getProperty(ConditionType.RETAIN);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getRetainNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ConditionType.RETAIN.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setRetain(Boolean value) {
        setProperty(ConditionType.RETAIN, value);
    }

    @Override
    public String getClientUserId() {
        Optional<String> property = getProperty(ConditionType.CLIENT_USER_ID);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getClientUserIdNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ConditionType.CLIENT_USER_ID.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setClientUserId(String value) {
        setProperty(ConditionType.CLIENT_USER_ID, value);
    }

    @Override
    public LocalizedText getEnabledState() {
        Optional<VariableNode> component = getVariableComponent("EnabledState");

        return component.map(node -> (LocalizedText) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public TwoStateVariableNode getEnabledStateNode() {
        Optional<VariableNode> component = getVariableComponent("EnabledState");

        return component.map(node -> (TwoStateVariableNode) node).orElse(null);
    }

    @Override
    public void setEnabledState(LocalizedText value) {
        getVariableComponent("EnabledState")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public StatusCode getQuality() {
        Optional<VariableNode> component = getVariableComponent("Quality");

        return component.map(node -> (StatusCode) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ConditionVariableNode getQualityNode() {
        Optional<VariableNode> component = getVariableComponent("Quality");

        return component.map(node -> (ConditionVariableNode) node).orElse(null);
    }

    @Override
    public void setQuality(StatusCode value) {
        getVariableComponent("Quality")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public UShort getLastSeverity() {
        Optional<VariableNode> component = getVariableComponent("LastSeverity");

        return component.map(node -> (UShort) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ConditionVariableNode getLastSeverityNode() {
        Optional<VariableNode> component = getVariableComponent("LastSeverity");

        return component.map(node -> (ConditionVariableNode) node).orElse(null);
    }

    @Override
    public void setLastSeverity(UShort value) {
        getVariableComponent("LastSeverity")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public LocalizedText getComment() {
        Optional<VariableNode> component = getVariableComponent("Comment");

        return component.map(node -> (LocalizedText) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ConditionVariableNode getCommentNode() {
        Optional<VariableNode> component = getVariableComponent("Comment");

        return component.map(node -> (ConditionVariableNode) node).orElse(null);
    }

    @Override
    public void setComment(LocalizedText value) {
        getVariableComponent("Comment")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

}
