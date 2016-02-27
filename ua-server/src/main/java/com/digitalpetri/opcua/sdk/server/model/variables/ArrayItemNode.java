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

package com.digitalpetri.opcua.sdk.server.model.variables;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.model.variables.ArrayItemType;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.core.nodes.VariableTypeNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.AxisScaleEnumeration;
import com.digitalpetri.opcua.stack.core.types.structured.EUInformation;
import com.digitalpetri.opcua.stack.core.types.structured.Range;

@com.digitalpetri.opcua.sdk.server.util.UaVariableNode(typeName = "0:ArrayItemType")
public class ArrayItemNode extends DataItemNode implements ArrayItemType {

    public ArrayItemNode(
            UaNodeManager nodeManager,
            NodeId nodeId,
            VariableTypeNode variableTypeNode) {

        super(nodeManager, nodeId, variableTypeNode);
    }

    public ArrayItemNode(
            UaNodeManager nodeManager,
            NodeId nodeId,
            QualifiedName browseName,
            LocalizedText displayName,
            Optional<LocalizedText> description,
            Optional<UInteger> writeMask,
            Optional<UInteger> userWriteMask,
            DataValue value,
            NodeId dataType,
            Integer valueRank,
            Optional<UInteger[]> arrayDimensions,
            UByte accessLevel,
            UByte userAccessLevel,
            Optional<Double> minimumSamplingInterval,
            boolean historizing) {

        super(nodeManager, nodeId, browseName, displayName, description, writeMask, userWriteMask,
                value, dataType, valueRank, arrayDimensions, accessLevel, userAccessLevel, minimumSamplingInterval, historizing);
    }


    @Override
    public Range getInstrumentRange() {
        Optional<Range> property = getProperty(ArrayItemType.INSTRUMENT_RANGE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getInstrumentRangeNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ArrayItemType.INSTRUMENT_RANGE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setInstrumentRange(Range value) {
        setProperty(ArrayItemType.INSTRUMENT_RANGE, value);
    }

    @Override
    public Range getEURange() {
        Optional<Range> property = getProperty(ArrayItemType.E_U_RANGE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getEURangeNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ArrayItemType.E_U_RANGE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setEURange(Range value) {
        setProperty(ArrayItemType.E_U_RANGE, value);
    }

    @Override
    public EUInformation getEngineeringUnits() {
        Optional<EUInformation> property = getProperty(ArrayItemType.ENGINEERING_UNITS);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getEngineeringUnitsNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ArrayItemType.ENGINEERING_UNITS.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setEngineeringUnits(EUInformation value) {
        setProperty(ArrayItemType.ENGINEERING_UNITS, value);
    }

    @Override
    public LocalizedText getTitle() {
        Optional<LocalizedText> property = getProperty(ArrayItemType.TITLE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getTitleNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ArrayItemType.TITLE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setTitle(LocalizedText value) {
        setProperty(ArrayItemType.TITLE, value);
    }

    @Override
    public AxisScaleEnumeration getAxisScaleType() {
        Optional<AxisScaleEnumeration> property = getProperty(ArrayItemType.AXIS_SCALE_TYPE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getAxisScaleTypeNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ArrayItemType.AXIS_SCALE_TYPE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setAxisScaleType(AxisScaleEnumeration value) {
        setProperty(ArrayItemType.AXIS_SCALE_TYPE, value);
    }

}
