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

import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.server.api.nodes.DataTypeNode;
import com.inductiveautomation.opcua.sdk.server.api.nodes.MethodNode;
import com.inductiveautomation.opcua.sdk.server.api.nodes.Node;
import com.inductiveautomation.opcua.sdk.server.api.nodes.ObjectNode;
import com.inductiveautomation.opcua.sdk.server.api.nodes.ObjectTypeNode;
import com.inductiveautomation.opcua.sdk.server.api.nodes.ReferenceTypeNode;
import com.inductiveautomation.opcua.sdk.server.api.nodes.VariableNode;
import com.inductiveautomation.opcua.sdk.server.api.nodes.VariableTypeNode;
import com.inductiveautomation.opcua.sdk.server.api.nodes.ViewNode;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.OverloadedType;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;

public class AttributeReader {

    private static final StatusCode AttributeIdInvalidStatus = new StatusCode(StatusCodes.Bad_AttributeIdInvalid);
    private static final DataValue AttributeIdInvalidValue = new DataValue(AttributeIdInvalidStatus);

    private AttributeReader() {
    }

    public static DataValue readAttribute(int attribute, Node node) {
        if (node instanceof DataTypeNode) return readDataTypeAttribute(attribute, (DataTypeNode) node);
        if (node instanceof MethodNode) return readMethodAttribute(attribute, (MethodNode) node);
        if (node instanceof ObjectNode) return readObjectAttribute(attribute, (ObjectNode) node);
        if (node instanceof ObjectTypeNode) return readObjectTypeAttribute(attribute, (ObjectTypeNode) node);
        if (node instanceof ReferenceTypeNode) return readReferenceTypeAttribute(attribute, (ReferenceTypeNode) node);
        if (node instanceof VariableNode) return readVariableNodeAttribute(attribute, (VariableNode) node);
        if (node instanceof VariableTypeNode) return readVariableTypeNodeAttribute(attribute, (VariableTypeNode) node);
        if (node instanceof ViewNode) return readViewNodeAttribute(attribute, (ViewNode) node);

        return AttributeIdInvalidValue;
    }

    private static DataValue readNodeAttribute(int attribute, Node node) {
        if (attribute == AttributeIds.NodeId) return dv(node.getNodeId());
        if (attribute == AttributeIds.NodeClass) return dv(node.getNodeClass());
        if (attribute == AttributeIds.BrowseName) return dv(node.getBrowseName());
        if (attribute == AttributeIds.DisplayName) return dv(node.getDisplayName());
        if (attribute == AttributeIds.Description) {
            return node.getDescription().map(AttributeReader::dv).orElse(AttributeIdInvalidValue);
        }
        if (attribute == AttributeIds.WriteMask) {
            return node.getWriteMask().map(AttributeReader::dv).orElse(AttributeIdInvalidValue);
        }
        if (attribute == AttributeIds.UserWriteMask) {
            return node.getUserWriteMask().map(AttributeReader::dv).orElse(AttributeIdInvalidValue);
        }

        return new DataValue(new StatusCode(StatusCodes.Bad_AttributeIdInvalid));
    }

    private static DataValue readDataTypeAttribute(int attribute, DataTypeNode node) {
        if (attribute == AttributeIds.IsAbstract) return dv(node.isAbstract());

        return readNodeAttribute(attribute, node);
    }

    private static DataValue readMethodAttribute(int attribute, MethodNode node) {
        if (attribute == AttributeIds.Executable) return dv(node.isExecutable());
        if (attribute == AttributeIds.UserExecutable) return dv(node.isUserExecutable());

        return readNodeAttribute(attribute, node);
    }

    private static DataValue readObjectAttribute(int attribute, ObjectNode node) {
        if (attribute == AttributeIds.EventNotifier) return dv(node.getEventNotifier(), OverloadedType.UByte);

        return readNodeAttribute(attribute, node);
    }

    private static DataValue readObjectTypeAttribute(int attribute, ObjectTypeNode node) {
        if (attribute == AttributeIds.IsAbstract) return dv(node.isAbstract());

        return readNodeAttribute(attribute, node);
    }

    private static DataValue readReferenceTypeAttribute(int attribute, ReferenceTypeNode node) {
        if (attribute == AttributeIds.IsAbstract) return dv(node.isAbstract());
        if (attribute == AttributeIds.Symmetric) return dv(node.isSymmetric());
        if (attribute == AttributeIds.InverseName) {
            return node.getInverseName().map(AttributeReader::dv).orElse(AttributeIdInvalidValue);
        }

        return readNodeAttribute(attribute, node);
    }

    private static DataValue readVariableNodeAttribute(int attribute, VariableNode node) {
        if (attribute == AttributeIds.Value) return node.getValue();
        if (attribute == AttributeIds.DataType) return dv(node.getDataType());
        if (attribute == AttributeIds.ValueRank) return dv(node.getValueRank());
        if (attribute == AttributeIds.ArrayDimensions) {
            return node.getArrayDimensions().map(AttributeReader::dv).orElse(AttributeIdInvalidValue);
        }
        if (attribute == AttributeIds.AccessLevel) return dv(node.getAccessLevel(), OverloadedType.UByte);
        if (attribute == AttributeIds.UserAccessLevel) return dv(node.getUserAccessLevel(), OverloadedType.UByte);
        if (attribute == AttributeIds.MinimumSamplingInterval) {
            return node.getMinimumSamplingInterval().map(AttributeReader::dv).orElse(AttributeIdInvalidValue);
        }
        if (attribute == AttributeIds.Historizing) return dv(node.isHistorizing());

        return readNodeAttribute(attribute, node);
    }

    private static DataValue readVariableTypeNodeAttribute(int attribute, VariableTypeNode node) {
        if (attribute == AttributeIds.Value) {
            return node.getValue().orElse(AttributeIdInvalidValue);
        }
        if (attribute == AttributeIds.DataType) return dv(node.getDataType());
        if (attribute == AttributeIds.ValueRank) return dv(node.getValueRank());
        if (attribute == AttributeIds.ArrayDimensions) {
            return node.getArrayDimensions().map(AttributeReader::dv).orElse(AttributeIdInvalidValue);
        }
        if (attribute == AttributeIds.IsAbstract) return dv(node.isAbstract());

        return readNodeAttribute(attribute, node);
    }

    private static DataValue readViewNodeAttribute(int attribute, ViewNode node) {
        if (attribute == AttributeIds.ContainsNoLoops) return dv(node.containsNoLoops());
        if (attribute == AttributeIds.EventNotifier) return dv(node.getEventNotifier(), OverloadedType.UByte);

        return readNodeAttribute(attribute, node);
    }

    /** DataValue for a non-value attribute; no source timestamp included. */
    private static DataValue dv(Object o) {
        return new DataValue(new Variant(o), StatusCode.Good, DateTime.now(), null);
    }

    private static DataValue dv(Object o, OverloadedType type) {
        return new DataValue(new Variant(o, type), StatusCode.Good, DateTime.now(), null);
    }

}
