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
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.sdk.server.model.variables.PropertyNode;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@com.digitalpetri.opcua.sdk.server.util.UaObjectNode(typeName = "0:AggregateConfigurationType")
public class AggregateConfigurationNode extends BaseObjectNode implements AggregateConfigurationType {

    public AggregateConfigurationNode(
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
    public Boolean getTreatUncertainAsBad() {
        Optional<Boolean> property = getProperty(AggregateConfigurationType.TREAT_UNCERTAIN_AS_BAD);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getTreatUncertainAsBadNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(AggregateConfigurationType.TREAT_UNCERTAIN_AS_BAD.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setTreatUncertainAsBad(Boolean value) {
        setProperty(AggregateConfigurationType.TREAT_UNCERTAIN_AS_BAD, value);
    }

    @Override
    public UByte getPercentDataBad() {
        Optional<UByte> property = getProperty(AggregateConfigurationType.PERCENT_DATA_BAD);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getPercentDataBadNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(AggregateConfigurationType.PERCENT_DATA_BAD.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setPercentDataBad(UByte value) {
        setProperty(AggregateConfigurationType.PERCENT_DATA_BAD, value);
    }

    @Override
    public UByte getPercentDataGood() {
        Optional<UByte> property = getProperty(AggregateConfigurationType.PERCENT_DATA_GOOD);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getPercentDataGoodNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(AggregateConfigurationType.PERCENT_DATA_GOOD.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setPercentDataGood(UByte value) {
        setProperty(AggregateConfigurationType.PERCENT_DATA_GOOD, value);
    }

    @Override
    public Boolean getUseSlopedExtrapolation() {
        Optional<Boolean> property = getProperty(AggregateConfigurationType.USE_SLOPED_EXTRAPOLATION);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getUseSlopedExtrapolationNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(AggregateConfigurationType.USE_SLOPED_EXTRAPOLATION.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setUseSlopedExtrapolation(Boolean value) {
        setProperty(AggregateConfigurationType.USE_SLOPED_EXTRAPOLATION, value);
    }

}
