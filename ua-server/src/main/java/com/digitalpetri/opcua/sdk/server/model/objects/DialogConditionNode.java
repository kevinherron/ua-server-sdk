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

import com.digitalpetri.opcua.sdk.core.model.objects.DialogConditionType;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.sdk.server.model.variables.PropertyNode;
import com.digitalpetri.opcua.sdk.server.model.variables.TwoStateVariableNode;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@com.digitalpetri.opcua.sdk.server.util.UaObjectNode(typeName = "0:DialogConditionType")
public class DialogConditionNode extends ConditionNode implements DialogConditionType {

    public DialogConditionNode(
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
    public LocalizedText getPrompt() {
        Optional<LocalizedText> property = getProperty(DialogConditionType.PROMPT);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getPromptNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(DialogConditionType.PROMPT.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setPrompt(LocalizedText value) {
        setProperty(DialogConditionType.PROMPT, value);
    }

    @Override
    public LocalizedText[] getResponseOptionSet() {
        Optional<LocalizedText[]> property = getProperty(DialogConditionType.RESPONSE_OPTION_SET);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getResponseOptionSetNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(DialogConditionType.RESPONSE_OPTION_SET.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setResponseOptionSet(LocalizedText[] value) {
        setProperty(DialogConditionType.RESPONSE_OPTION_SET, value);
    }

    @Override
    public Integer getDefaultResponse() {
        Optional<Integer> property = getProperty(DialogConditionType.DEFAULT_RESPONSE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getDefaultResponseNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(DialogConditionType.DEFAULT_RESPONSE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setDefaultResponse(Integer value) {
        setProperty(DialogConditionType.DEFAULT_RESPONSE, value);
    }

    @Override
    public Integer getOkResponse() {
        Optional<Integer> property = getProperty(DialogConditionType.OK_RESPONSE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getOkResponseNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(DialogConditionType.OK_RESPONSE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setOkResponse(Integer value) {
        setProperty(DialogConditionType.OK_RESPONSE, value);
    }

    @Override
    public Integer getCancelResponse() {
        Optional<Integer> property = getProperty(DialogConditionType.CANCEL_RESPONSE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getCancelResponseNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(DialogConditionType.CANCEL_RESPONSE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setCancelResponse(Integer value) {
        setProperty(DialogConditionType.CANCEL_RESPONSE, value);
    }

    @Override
    public Integer getLastResponse() {
        Optional<Integer> property = getProperty(DialogConditionType.LAST_RESPONSE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getLastResponseNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(DialogConditionType.LAST_RESPONSE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setLastResponse(Integer value) {
        setProperty(DialogConditionType.LAST_RESPONSE, value);
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
    public LocalizedText getDialogState() {
        Optional<VariableNode> component = getVariableComponent("DialogState");

        return component.map(node -> (LocalizedText) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public TwoStateVariableNode getDialogStateNode() {
        Optional<VariableNode> component = getVariableComponent("DialogState");

        return component.map(node -> (TwoStateVariableNode) node).orElse(null);
    }

    @Override
    public void setDialogState(LocalizedText value) {
        getVariableComponent("DialogState")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

}
