/*
 * Copyright 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.digitalpetri.opcua.sdk.server.util;

import java.util.EnumSet;
import java.util.Optional;
import javax.annotation.Nullable;

import com.digitalpetri.opcua.sdk.core.AccessLevel;
import com.digitalpetri.opcua.sdk.core.AttributeIds;
import com.digitalpetri.opcua.sdk.core.NumericRange;
import com.digitalpetri.opcua.sdk.core.ValueRank;
import com.digitalpetri.opcua.sdk.core.WriteMask;
import com.digitalpetri.opcua.sdk.core.nodes.DataTypeNode;
import com.digitalpetri.opcua.sdk.core.nodes.MethodNode;
import com.digitalpetri.opcua.sdk.core.nodes.Node;
import com.digitalpetri.opcua.sdk.core.nodes.ObjectNode;
import com.digitalpetri.opcua.sdk.core.nodes.ObjectTypeNode;
import com.digitalpetri.opcua.sdk.core.nodes.ReferenceTypeNode;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.core.nodes.VariableTypeNode;
import com.digitalpetri.opcua.sdk.server.NamespaceManager;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.util.ArrayUtil;
import com.digitalpetri.opcua.stack.core.util.TypeUtil;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class AttributeWriter {

    public static void writeAttribute(NamespaceManager ns, Node node,
                                      int attribute, DataValue value,
                                      @Nullable String indexRange) throws UaException {

        Variant updateVariant = value.getValue();

        if (indexRange != null) {
            NumericRange range = NumericRange.parse(indexRange);

            DataValue current = node.readAttribute(attribute);
            Variant currentVariant = current.getValue();

            Object valueAtRange = NumericRange.writeToValueAtRange(
                    currentVariant,
                    updateVariant,
                    range);

            updateVariant = new Variant(valueAtRange);
        }

        DateTime sourceTime = value.getSourceTime();
        DateTime serverTime = value.getServerTime();

        value = new DataValue(
                updateVariant,
                value.getStatusCode(),
                (sourceTime == null || sourceTime.isNull()) ? DateTime.now() : sourceTime,
                (serverTime == null || serverTime.isNull()) ? DateTime.now() : serverTime);

        writeNode(ns, node, attribute, value);
    }

    private static void writeNode(NamespaceManager ns, Node node, int attribute, DataValue value) throws UaException {
        switch (node.getNodeClass()) {
            case DataType:
                writeDataTypeAttribute(ns, (DataTypeNode) node, attribute, value);
                break;

            case Method:
                writeMethodAttribute(ns, (MethodNode) node, attribute, value);
                break;

            case Object:
                writeObjectAttribute(ns, (ObjectNode) node, attribute, value);
                break;

            case ObjectType:
                writeObjectTypeAttribute(ns, (ObjectTypeNode) node, attribute, value);
                break;

            case ReferenceType:
                writeReferenceTypeAttribute(ns, (ReferenceTypeNode) node, attribute, value);
                break;

            case Variable:
                writeVariableAttribute(ns, (VariableNode) node, attribute, value);
                break;

            case VariableType:
                writeVariableTypeAttribute(ns, (VariableTypeNode) node, attribute, value);
                break;

            default:
                throw new UaException(StatusCodes.Bad_NodeClassInvalid);
        }
    }

    private static void writeNodeAttribute(NamespaceManager ns, Node node, int attribute, DataValue value) throws UaException {
        UInteger writeMask = node.getWriteMask().orElse(uint(0));
        EnumSet<WriteMask> writeMasks = WriteMask.fromMask(writeMask);

        switch (attribute) {
            case AttributeIds.NodeId:
                if (writeMasks.contains(WriteMask.NodeId)) {
                    node.setNodeId(extract(value));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            case AttributeIds.NodeClass:
                if (writeMasks.contains(WriteMask.NodeClass)) {
                    node.setNodeClass(extract(value));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            case AttributeIds.BrowseName:
                if (writeMasks.contains(WriteMask.BrowseName)) {
                    node.setBrowseName(extract(value));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            case AttributeIds.DisplayName:
                if (writeMasks.contains(WriteMask.DisplayName)) {
                    node.setDisplayName(extract(value));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            case AttributeIds.Description:
                if (writeMasks.contains(WriteMask.Description)) {
                    node.setDescription(extract(value));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            case AttributeIds.WriteMask:
                if (writeMasks.contains(WriteMask.WriteMask)) {
                    node.setWriteMask(Optional.ofNullable(extract(value)));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            case AttributeIds.UserWriteMask:
                if (writeMasks.contains(WriteMask.UserWriteMask)) {
                    node.setUserWriteMask(Optional.ofNullable(extract(value)));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            default:
                throw new UaException(StatusCodes.Bad_AttributeIdInvalid);
        }
    }

    private static void writeDataTypeAttribute(NamespaceManager ns, DataTypeNode node, int attribute, DataValue value) throws UaException {
        UInteger writeMask = node.getWriteMask().orElse(uint(0));
        EnumSet<WriteMask> writeMasks = WriteMask.fromMask(writeMask);

        switch (attribute) {
            case AttributeIds.IsAbstract:
                if (writeMasks.contains(WriteMask.IsAbstract)) {
                    node.setIsAbstract(extract(value));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            default:
                writeNodeAttribute(ns, node, attribute, value);
        }
    }

    private static void writeMethodAttribute(NamespaceManager ns, MethodNode node, int attribute, DataValue value) throws UaException {
        UInteger writeMask = node.getWriteMask().orElse(uint(0));
        EnumSet<WriteMask> writeMasks = WriteMask.fromMask(writeMask);

        switch (attribute) {
            case AttributeIds.Executable:
                if (writeMasks.contains(WriteMask.Executable)) {
                    node.setExecutable(extract(value));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            case AttributeIds.UserExecutable:
                if (writeMasks.contains(WriteMask.UserExecutable)) {
                    node.setUserExecutable(extract(value));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            default:
                writeNodeAttribute(ns, node, attribute, value);
        }
    }

    private static void writeObjectAttribute(NamespaceManager ns, ObjectNode node, int attribute, DataValue value) throws UaException {
        UInteger writeMask = node.getWriteMask().orElse(uint(0));
        EnumSet<WriteMask> writeMasks = WriteMask.fromMask(writeMask);

        switch (attribute) {
            case AttributeIds.EventNotifier:
                if (writeMasks.contains(WriteMask.EventNotifier)) {
                    node.setWriteMask(Optional.ofNullable(extract(value)));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            default:
                writeNodeAttribute(ns, node, attribute, value);
        }
    }

    private static void writeObjectTypeAttribute(NamespaceManager ns, ObjectTypeNode node, int attribute, DataValue value) throws UaException {
        UInteger writeMask = node.getWriteMask().orElse(uint(0));
        EnumSet<WriteMask> writeMasks = WriteMask.fromMask(writeMask);

        switch (attribute) {
            case AttributeIds.IsAbstract:
                if (writeMasks.contains(WriteMask.IsAbstract)) {
                    node.setIsAbstract(extract(value));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            default:
                writeNodeAttribute(ns, node, attribute, value);
        }
    }

    private static void writeReferenceTypeAttribute(NamespaceManager ns, ReferenceTypeNode node, int attribute, DataValue value) throws UaException {
        UInteger writeMask = node.getWriteMask().orElse(uint(0));
        EnumSet<WriteMask> writeMasks = WriteMask.fromMask(writeMask);

        switch (attribute) {
            case AttributeIds.IsAbstract:
                if (writeMasks.contains(WriteMask.IsAbstract)) {
                    node.setIsAbstract(extract(value));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            case AttributeIds.Symmetric:
                if (writeMasks.contains(WriteMask.Symmetric)) {
                    node.setSymmetric(extract(value));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            case AttributeIds.InverseName:
                if (writeMasks.contains(WriteMask.InverseName)) {
                    node.setInverseName(Optional.ofNullable(extract(value)));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            default:
                writeNodeAttribute(ns, node, attribute, value);
        }
    }

    private static void writeVariableAttribute(NamespaceManager ns, VariableNode node, int attribute, DataValue value) throws UaException {
        EnumSet<AccessLevel> accessLevels = AccessLevel.fromMask(node.getAccessLevel());

        UInteger writeMask = node.getWriteMask().orElse(uint(0));
        EnumSet<WriteMask> writeMasks = WriteMask.fromMask(writeMask);

        switch (attribute) {
            case AttributeIds.Value:
                if (accessLevels.contains(AccessLevel.CurrentWrite)) {
                    value = validateDataType(ns, node.getDataType().expanded(), value);
                    validateArrayType(node.getValueRank(), node.getArrayDimensions(), value);

                    node.setValue(value);
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            case AttributeIds.DataType:
                if (writeMasks.contains(WriteMask.DataType)) {
                    node.setDataType(extract(value));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            case AttributeIds.ValueRank:
                if (writeMasks.contains(WriteMask.ValueRank)) {
                    node.setValueRank(extract(value));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            case AttributeIds.ArrayDimensions:
                if (writeMasks.contains(WriteMask.ArrayDimensions)) {
                    node.setArrayDimensions(Optional.of(extract(value)));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            case AttributeIds.AccessLevel:
                if (writeMasks.contains(WriteMask.AccessLevel)) {
                    node.setAccessLevel(extract(value));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            case AttributeIds.UserAccessLevel:
                if (writeMasks.contains(WriteMask.UserAccessLevel)) {
                    node.setUserAccessLevel(extract(value));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            case AttributeIds.MinimumSamplingInterval:
                if (writeMasks.contains(WriteMask.MinimumSamplingInterval)) {
                    node.setMinimumSamplingInterval(Optional.of(extract(value)));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            case AttributeIds.Historizing:
                if (writeMasks.contains(WriteMask.Historizing)) {
                    node.setHistorizing(extract(value));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            default:
                writeNodeAttribute(ns, node, attribute, value);
        }
    }

    private static void writeVariableTypeAttribute(NamespaceManager ns, VariableTypeNode node, int attribute, DataValue value) throws UaException {
        UInteger writeMask = node.getWriteMask().orElse(uint(0));
        EnumSet<WriteMask> writeMasks = WriteMask.fromMask(writeMask);

        switch (attribute) {
            case AttributeIds.Value:
                if (writeMasks.contains(WriteMask.ValueForVariableType)) {
                    value = validateDataType(ns, node.getDataType().expanded(), value);
                    validateArrayType(node.getValueRank(), node.getArrayDimensions(), value);

                    node.setValue(Optional.ofNullable(value));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            case AttributeIds.DataType:
                if (writeMasks.contains(WriteMask.DataType)) {
                    node.setDataType(extract(value));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            case AttributeIds.ValueRank:
                if (writeMasks.contains(WriteMask.ValueRank)) {
                    node.setValueRank(extract(value));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            case AttributeIds.IsAbstract:
                if (writeMasks.contains(WriteMask.IsAbstract)) {
                    node.setIsAbstract(extract(value));
                } else {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                break;

            default:
                writeNodeAttribute(ns, node, attribute, value);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T extract(DataValue value) throws UaException {
        Variant variant = value.getValue();
        if (variant == null) return null;

        Object o = variant.getValue();
        if (o == null) return null;

        try {
            return (T) o;
        } catch (ClassCastException e) {
            throw new UaException(StatusCodes.Bad_TypeMismatch);
        }
    }

    private static DataValue validateDataType(NamespaceManager ns, ExpandedNodeId dataType, DataValue value) throws UaException {
        Variant variant = value.getValue();
        if (variant == null) return value;

        Object o = variant.getValue();
        if (o == null) throw new UaException(StatusCodes.Bad_TypeMismatch);

        Class<?> expected = TypeUtil.getBackingClass(dataType);

        Class<?> actual = o.getClass().isArray() ?
                o.getClass().getComponentType() : o.getClass();

        if (expected == null) {
            throw new UaException(StatusCodes.Bad_TypeMismatch);
        } else {
            if (!expected.isAssignableFrom(actual)) {
                /*
                 * Writing a ByteString to a UByte[] is explicitly allowed by the spec.
                 */
                boolean byteStringToByteArray = (o instanceof ByteString && expected == UByte.class);

                if (byteStringToByteArray) {
                    ByteString byteString = (ByteString) o;

                    return new DataValue(
                            new Variant(byteString.uBytes()),
                            value.getStatusCode(),
                            value.getSourceTime(),
                            value.getServerTime());
                } else {
                    throw new UaException(StatusCodes.Bad_TypeMismatch);
                }
            }
        }

        return value;
    }

    private static void validateArrayType(int valueRank, Optional<UInteger[]> arrayDimensionsOpt, DataValue value) throws UaException {
        Variant variant = value.getValue();
        if (variant == null) return;

        Object o = variant.getValue();
        if (o == null) return;

        boolean valueIsArray = o.getClass().isArray();

        switch (valueRank) {
            case ValueRank.ScalarOrOneDimension:
                if (valueIsArray) {
                    int[] valueDimensions = ArrayUtil.getDimensions(o);

                    if (valueDimensions.length > 1) {
                        throw new UaException(StatusCodes.Bad_TypeMismatch);
                    }
                }
                break;

            case ValueRank.Any:
                break;

            case ValueRank.Scalar:
                if (valueIsArray) {
                    throw new UaException(StatusCodes.Bad_TypeMismatch);
                }
                break;

            case ValueRank.OneOrMoreDimensions:
                if (!valueIsArray) {
                    throw new UaException(StatusCodes.Bad_TypeMismatch);
                }
                break;

            case ValueRank.OneDimension:
            default:
                if (!valueIsArray) {
                    throw new UaException(StatusCodes.Bad_TypeMismatch);
                }

                int[] valueDimensions = ArrayUtil.getDimensions(o);

                if (valueDimensions.length != valueRank) {
                    throw new UaException(StatusCodes.Bad_TypeMismatch);
                }

                int[] nodeDimensions = arrayDimensionsOpt.map(uia -> {
                    int[] arrayDimensions = new int[uia.length];
                    for (int i = 0; i < uia.length; i++) {
                        arrayDimensions[i] = uia[i].intValue();
                    }
                    return arrayDimensions;
                }).orElse(new int[0]);

                if (nodeDimensions.length > 0) {
                    if (nodeDimensions.length != valueDimensions.length) {
                        throw new UaException(StatusCodes.Bad_TypeMismatch);
                    }

                    for (int i = 0; i < nodeDimensions.length; i++) {
                        if (nodeDimensions[i] > 0 && valueDimensions[i] > nodeDimensions[i]) {
                            throw new UaException(StatusCodes.Bad_TypeMismatch);
                        }
                    }
                }
                break;
        }
    }

}
