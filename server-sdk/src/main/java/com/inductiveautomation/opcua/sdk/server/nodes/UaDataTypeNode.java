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

package com.inductiveautomation.opcua.sdk.server.nodes;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.core.ValueRank;
import com.inductiveautomation.opcua.sdk.core.nodes.DataTypeNode;
import com.inductiveautomation.opcua.sdk.core.nodes.VariableNode;
import com.inductiveautomation.opcua.sdk.server.api.UaNodeManager;
import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;
import com.inductiveautomation.opcua.stack.core.types.structured.EnumValueType;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint_a;

public class UaDataTypeNode extends UaNode implements DataTypeNode {

    public static final QualifiedName NODE_VERSION = new QualifiedName(0, "NodeVersion");
    public static final QualifiedName ENUM_STRINGS = new QualifiedName(0, "EnumStrings");
    public static final QualifiedName ENUM_VALUES = new QualifiedName(0, "EnumValues");

    private final AtomicBoolean isAbstract;

    public UaDataTypeNode(UaNodeManager nodeManager,
                          NodeId nodeId,
                          QualifiedName browseName,
                          LocalizedText displayName,
                          Optional<LocalizedText> description,
                          Optional<UInteger> writeMask,
                          Optional<UInteger> userWriteMask,
                          boolean isAbstract) {

        super(nodeManager, nodeId, NodeClass.DataType, browseName, displayName, description, writeMask, userWriteMask);

        this.isAbstract = new AtomicBoolean(isAbstract);
    }

    @Override
    public Boolean getIsAbstract() {
        return isAbstract.get();
    }

    @Override
    public synchronized void setIsAbstract(boolean isAbstract) {
        this.isAbstract.set(isAbstract);

        fireAttributeChanged(AttributeIds.IsAbstract, isAbstract);
    }

    @Override
    public Optional<String> getNodeVersion() {
        return getProperty(NODE_VERSION);
    }

    @Override
    public Optional<LocalizedText[]> getEnumStrings() {
        return getProperty(ENUM_STRINGS);
    }

    @Override
    public Optional<EnumValueType[]> getEnumValues() {
        return getProperty(ENUM_VALUES);
    }

    @Override
    public void setNodeVersion(Optional<String> nodeVersion) {
        if (nodeVersion.isPresent()) {
            VariableNode node = getPropertyNode(NODE_VERSION).orElseGet(() -> {
                UaPropertyNode propertyNode = createPropertyNode(NODE_VERSION);

                propertyNode.setDataType(Identifiers.String);

                addPropertyNode(propertyNode);

                return propertyNode;
            });

            node.setValue(new DataValue(new Variant(nodeVersion.get())));
        } else {
            removePropertyNode(NODE_VERSION);
        }
    }

    @Override
    public void setEnumStrings(Optional<LocalizedText[]> enumStrings) {
        if (enumStrings.isPresent()) {
            VariableNode node = getPropertyNode(ENUM_STRINGS).orElseGet(() -> {
                UaPropertyNode propertyNode = createPropertyNode(ENUM_STRINGS);

                propertyNode.setDataType(Identifiers.LocalizedText);
                propertyNode.setValueRank(ValueRank.OneDimension);
                propertyNode.setArrayDimensions(Optional.of(uint_a(0)));

                addPropertyNode(propertyNode);

                return propertyNode;
            });

            node.setValue(new DataValue(new Variant(enumStrings.get())));
        } else {
            removePropertyNode(ENUM_STRINGS);
        }
    }


    @Override
    public void setEnumValues(Optional<EnumValueType[]> enumValues) {
        if (enumValues.isPresent()) {
            VariableNode node = getPropertyNode(ENUM_VALUES).orElseGet(() -> {
                UaPropertyNode propertyNode = createPropertyNode(ENUM_VALUES);

                propertyNode.setDataType(Identifiers.EnumValueType);
                propertyNode.setValueRank(ValueRank.OneDimension);
                propertyNode.setArrayDimensions(Optional.of(uint_a(0)));

                addPropertyNode(propertyNode);

                return propertyNode;
            });

            node.setValue(new DataValue(new Variant(enumValues.get())));
        } else {
            removePropertyNode(ENUM_VALUES);
        }
    }

}
