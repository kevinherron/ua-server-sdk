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

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.server.api.nodes.Node;
import com.inductiveautomation.opcua.sdk.server.api.nodes.VariableNode;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class AttributeWriter {

    public static void writeAttribute(int attributeId, DataValue value, Node node) throws UaException {
        if (node instanceof VariableNode) {
            writeVariableNode(attributeId, value, (VariableNode) node);
        } else {
            throw new UaException(StatusCodes.Bad_NotWritable);
        }
    }

    private static void writeNode(int attributeId, DataValue value, VariableNode node) {

    }

    private static void writeVariableNode(int attributeId, DataValue value, VariableNode node) throws UaException {
        if (attributeId == AttributeIds.Value) {
            // TODO check AccessLevel first?
            node.setValue(value);
        } else {
            // TODO check WriteMask first?
            UInteger writeMask = node.getWriteMask().orElse(uint(0));

            if (attributeId == AttributeIds.DataType) {
                node.setDataType(extract(value));
            } else if (attributeId == AttributeIds.ValueRank) {
                node.setValueRank(extract(value));
            } else if (attributeId == AttributeIds.ArrayDimensions) {
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

}
