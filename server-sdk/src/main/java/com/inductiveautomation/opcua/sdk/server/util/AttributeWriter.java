/*
 * Copyright 2014 Inductive Automation
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

package com.inductiveautomation.opcua.sdk.server.util;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.AccessLevel;
import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.core.DataType;
import com.inductiveautomation.opcua.sdk.core.WriteMask;
import com.inductiveautomation.opcua.sdk.server.NamespaceManager;
import com.inductiveautomation.opcua.sdk.server.api.Reference;
import com.inductiveautomation.opcua.sdk.server.api.nodes.Node;
import com.inductiveautomation.opcua.sdk.server.api.nodes.VariableNode;
import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class AttributeWriter {

    public static void writeAttribute(int attributeId, DataValue value, Node node, NamespaceManager ns) throws UaException {
        // Don't allow timestamps to be written.
        value = new DataValue(value.getValue(), value.getStatusCode());

        if (node instanceof VariableNode) {
            writeVariableNode(attributeId, value, (VariableNode) node, ns);
        } else {
            throw new UaException(StatusCodes.Bad_NotWritable);
        }
    }

    private static void writeNode(int attributeId, DataValue value, VariableNode node) throws UaException {
        throw new UaException(StatusCodes.Bad_AttributeIdInvalid);
    }

    private static void writeVariableNode(int attributeId, DataValue value, VariableNode node, NamespaceManager ns) throws UaException {
        if (attributeId == AttributeIds.Value) {
            EnumSet<AccessLevel> accessLevels = AccessLevel.fromMask(node.getAccessLevel());

            if (accessLevels.contains(AccessLevel.CurrentRead)) {
                validateDataType(ns, node.getDataType().expanded(), value);

                node.setValue(value);
            } else {
                throw new UaException(StatusCodes.Bad_NotWritable);
            }
        } else {
            UInteger writeMask = node.getWriteMask().orElse(uint(0));
            EnumSet<WriteMask> writeMasks = WriteMask.fromMask(writeMask);

            if (attributeId == AttributeIds.DataType) {
                if (!writeMasks.contains(WriteMask.DataType)) {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                node.setDataType(extract(value));
            } else if (attributeId == AttributeIds.ValueRank) {
                if (!writeMasks.contains(WriteMask.ValueRank)) {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                node.setValueRank(extract(value));
            } else if (attributeId == AttributeIds.ArrayDimensions) {
                if (!writeMasks.contains(WriteMask.ArrayDimensions)) {
                    throw new UaException(StatusCodes.Bad_NotWritable);
                }
                node.setArrayDimensions(Optional.of(extract(value)));
            } else {
                writeNode(attributeId, value, node);
            }
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

    private static void validateDataType(NamespaceManager ns, ExpandedNodeId dataType, DataValue value) throws UaException {
        Variant variant = value.getValue();
        if (variant == null) return;

        Object o = variant.getValue();
        if (o == null) return;

        if (!DataType.isBuiltin(dataType)) {
            dataType = findBuiltinSuperType(ns, dataType);
        }

        Class<?> expected = DataType.getBackingClass(dataType);

        if (!expected.isAssignableFrom(o.getClass())) {
            throw new UaException(StatusCodes.Bad_TypeMismatch);
        }
    }

    private static ExpandedNodeId findBuiltinSuperType(NamespaceManager ns, ExpandedNodeId dataType) throws UaException {
        Node node = ns.getNode(dataType).orElseThrow(() -> new UaException(StatusCodes.Bad_TypeMismatch));

        List<Reference> references = ns
                .getReferences(node.getNodeId())
                .orElse(Collections.emptyList());

        for (Reference reference : references) {
            if (reference.isInverse() && reference.getReferenceTypeId().equals(Identifiers.HasSubtype)) {
                ExpandedNodeId targetNodeId = reference.getTargetNodeId();

                if (DataType.isBuiltin(targetNodeId)) {
                    return targetNodeId;
                } else {
                    return findBuiltinSuperType(ns, targetNodeId);
                }
            }
        }

        throw new UaException(StatusCodes.Bad_TypeMismatch);
    }

}
