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

import com.digitalpetri.opcua.sdk.core.model.objects.LimitAlarmType;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.sdk.server.model.variables.PropertyNode;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@com.digitalpetri.opcua.sdk.server.util.UaObjectNode(typeName = "0:LimitAlarmType")
public class LimitAlarmNode extends AlarmConditionNode implements LimitAlarmType {

    public LimitAlarmNode(
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
    public Double getHighHighLimit() {
        Optional<Double> property = getProperty(LimitAlarmType.HIGH_HIGH_LIMIT);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getHighHighLimitNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(LimitAlarmType.HIGH_HIGH_LIMIT.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setHighHighLimit(Double value) {
        setProperty(LimitAlarmType.HIGH_HIGH_LIMIT, value);
    }

    @Override
    public Double getHighLimit() {
        Optional<Double> property = getProperty(LimitAlarmType.HIGH_LIMIT);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getHighLimitNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(LimitAlarmType.HIGH_LIMIT.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setHighLimit(Double value) {
        setProperty(LimitAlarmType.HIGH_LIMIT, value);
    }

    @Override
    public Double getLowLimit() {
        Optional<Double> property = getProperty(LimitAlarmType.LOW_LIMIT);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getLowLimitNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(LimitAlarmType.LOW_LIMIT.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setLowLimit(Double value) {
        setProperty(LimitAlarmType.LOW_LIMIT, value);
    }

    @Override
    public Double getLowLowLimit() {
        Optional<Double> property = getProperty(LimitAlarmType.LOW_LOW_LIMIT);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getLowLowLimitNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(LimitAlarmType.LOW_LOW_LIMIT.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setLowLowLimit(Double value) {
        setProperty(LimitAlarmType.LOW_LOW_LIMIT, value);
    }

}
