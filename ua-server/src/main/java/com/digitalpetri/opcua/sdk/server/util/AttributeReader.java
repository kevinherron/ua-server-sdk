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

package com.digitalpetri.opcua.sdk.server.util;

import java.util.function.Supplier;
import javax.annotation.Nullable;

import com.digitalpetri.opcua.sdk.core.NumericRange;
import com.digitalpetri.opcua.sdk.core.nodes.DataTypeNode;
import com.digitalpetri.opcua.sdk.core.nodes.MethodNode;
import com.digitalpetri.opcua.sdk.core.nodes.Node;
import com.digitalpetri.opcua.sdk.core.nodes.ObjectNode;
import com.digitalpetri.opcua.sdk.core.nodes.ObjectTypeNode;
import com.digitalpetri.opcua.sdk.core.nodes.ReferenceTypeNode;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.core.nodes.VariableTypeNode;
import com.digitalpetri.opcua.stack.core.AttributeId;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.enumerated.TimestampsToReturn;

public class AttributeReader {

    private static final Supplier<UaException> ATTRIBUTE_ID_INVALID_EXCEPTION =
            () -> new UaException(StatusCodes.Bad_AttributeIdInvalid);

    public static DataValue readAttribute(Node node,
                                          AttributeId attributeId,
                                          @Nullable TimestampsToReturn timestamps,
                                          @Nullable String indexRange) {
        try {
            DataValue value = readAttribute(node, attributeId);

            if (indexRange != null) {
                NumericRange range = NumericRange.parse(indexRange);

                Object valueAtRange = NumericRange.readFromValueAtRange(value.getValue(), range);

                value = new DataValue(
                        new Variant(valueAtRange),
                        value.getStatusCode(),
                        value.getSourceTime(),
                        value.getServerTime());
            }

            if (timestamps != null) {
                value = (attributeId == AttributeId.VALUE) ?
                        DataValue.derivedValue(value, timestamps) :
                        DataValue.derivedNonValue(value, timestamps);
            }

            return value;
        } catch (UaException e) {
            return new DataValue(e.getStatusCode());
        }
    }

    private static DataValue readAttribute(Node node, AttributeId attributeId) throws UaException {
        switch (node.getNodeClass()) {
            case DataType:
                return readDataTypeAttribute((DataTypeNode) node, attributeId);

            case Method:
                return readMethodAttribute((MethodNode) node, attributeId);

            case Object:
                return readObjectAttribute((ObjectNode) node, attributeId);

            case ObjectType:
                return readObjectTypeAttribute((ObjectTypeNode) node, attributeId);

            case ReferenceType:
                return readReferenceTypeAttribute((ReferenceTypeNode) node, attributeId);

            case Variable:
                return readVariableAttribute((VariableNode) node, attributeId);

            case VariableType:
                return readVariableTypeAttribute((VariableTypeNode) node, attributeId);

            default:
                throw new UaException(StatusCodes.Bad_NodeClassInvalid);
        }

    }

    private static DataValue readNodeAttribute(Node node, AttributeId attributeId) throws UaException {
        switch (attributeId) {
            case NODE_ID:
                return dv(node.getNodeId());

            case NODE_CLASS:
                return dv(node.getNodeClass());

            case BROWSE_NAME:
                return dv(node.getBrowseName());

            case DISPLAY_NAME:
                return dv(node.getDisplayName());

            case DESCRIPTION:
                return node.getDescription().map(AttributeReader::dv)
                        .orElseThrow(ATTRIBUTE_ID_INVALID_EXCEPTION);

            case WRITE_MASK:
                return node.getWriteMask().map(AttributeReader::dv)
                        .orElseThrow(ATTRIBUTE_ID_INVALID_EXCEPTION);

            case USER_WRITE_MASK:
                return node.getUserWriteMask().map(AttributeReader::dv)
                        .orElseThrow(ATTRIBUTE_ID_INVALID_EXCEPTION);

            default:
                throw ATTRIBUTE_ID_INVALID_EXCEPTION.get();
        }
    }

    private static DataValue readDataTypeAttribute(DataTypeNode node, AttributeId attributeId) throws UaException {
        switch (attributeId) {
            case IS_ABSTRACT:
                return dv(node.getIsAbstract());

            default:
                return readNodeAttribute(node, attributeId);
        }
    }

    private static DataValue readMethodAttribute(MethodNode node, AttributeId attributeId) throws UaException {
        switch (attributeId) {
            case EXECUTABLE:
                return dv(node.isExecutable());

            case USER_EXECUTABLE:
                return dv(node.isUserExecutable());

            default:
                return readNodeAttribute(node, attributeId);
        }
    }

    private static DataValue readObjectAttribute(ObjectNode node, AttributeId attributeId) throws UaException {
        switch (attributeId) {
            case EVENT_NOTIFIER:
                return dv(node.getEventNotifier());

            default:
                return readNodeAttribute(node, attributeId);
        }
    }

    private static DataValue readObjectTypeAttribute(ObjectTypeNode node, AttributeId attributeId) throws UaException {
        switch (attributeId) {
            case IS_ABSTRACT:
                return dv(node.getIsAbstract());

            default:
                return readNodeAttribute(node, attributeId);
        }
    }

    private static DataValue readReferenceTypeAttribute(ReferenceTypeNode node, AttributeId attributeId) throws UaException {
        switch (attributeId) {
            case IS_ABSTRACT:
                return dv(node.getIsAbstract());

            case SYMMETRIC:
                return dv(node.getSymmetric());

            case INVERSE_NAME:
                return node.getInverseName().map(AttributeReader::dv)
                        .orElseThrow(ATTRIBUTE_ID_INVALID_EXCEPTION);

            default:
                return readNodeAttribute(node, attributeId);
        }
    }

    private static DataValue readVariableAttribute(VariableNode node, AttributeId attributeId) throws UaException {
        switch (attributeId) {
            case VALUE:
                return node.getValue();

            case DATA_TYPE:
                return dv(node.getDataType());

            case VALUE_RANK:
                return dv(node.getValueRank());

            case ARRAY_DIMENSIONS:
                return node.getArrayDimensions().map(AttributeReader::dv)
                        .orElseThrow(ATTRIBUTE_ID_INVALID_EXCEPTION);

            case ACCESS_LEVEL:
                return dv(node.getAccessLevel());

            case USER_ACCESS_LEVEL:
                return dv(node.getUserAccessLevel());

            case MINIMUM_SAMPLING_INTERVAL:
                return node.getMinimumSamplingInterval().map(AttributeReader::dv)
                        .orElseThrow(ATTRIBUTE_ID_INVALID_EXCEPTION);

            case HISTORIZING:
                return dv(node.getHistorizing());

            default:
                return readNodeAttribute(node, attributeId);
        }
    }

    private static DataValue readVariableTypeAttribute(VariableTypeNode node, AttributeId attributeId) throws UaException {
        switch (attributeId) {
            case VALUE:
                return node.getValue().orElseThrow(ATTRIBUTE_ID_INVALID_EXCEPTION);

            case DATA_TYPE:
                return dv(node.getDataType());

            case VALUE_RANK:
                return dv(node.getValueRank());

            case ARRAY_DIMENSIONS:
                return node.getArrayDimensions().map(AttributeReader::dv)
                        .orElseThrow(ATTRIBUTE_ID_INVALID_EXCEPTION);

            case IS_ABSTRACT:
                return dv(node.getIsAbstract());

            default:
                return readNodeAttribute(node, attributeId);
        }
    }

    /** DataValue for a non-value attribute; no source timestamp included. */
    private static DataValue dv(Object o) {
        return new DataValue(new Variant(o), StatusCode.GOOD, null, DateTime.now());
    }

}
