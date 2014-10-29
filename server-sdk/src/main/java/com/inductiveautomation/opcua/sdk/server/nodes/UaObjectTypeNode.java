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

import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.core.nodes.ObjectTypeNode;
import com.inductiveautomation.opcua.sdk.core.nodes.VariableNode;
import com.inductiveautomation.opcua.sdk.server.api.UaNodeManager;
import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;

public class UaObjectTypeNode extends UaNode implements ObjectTypeNode {

    public static final QualifiedName NODE_VERSION = new QualifiedName(0, "NodeVersion");
    public static final QualifiedName ICON = new QualifiedName(0, "Icon");

    private volatile boolean isAbstract;

    public UaObjectTypeNode(UaNodeManager nodeManager,
                            NodeId nodeId,
                            QualifiedName browseName,
                            LocalizedText displayName,
                            Optional<LocalizedText> description,
                            Optional<UInteger> writeMask,
                            Optional<UInteger> userWriteMask,
                            boolean isAbstract) {

        super(nodeManager, nodeId, NodeClass.ObjectType, browseName, displayName, description, writeMask, userWriteMask);

        this.isAbstract = isAbstract;
    }

    @Override
    public Boolean getIsAbstract() {
        return isAbstract;
    }

    @Override
    public synchronized void setIsAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;

        fireAttributeChanged(AttributeIds.IsAbstract, isAbstract);
    }

    @Override
    public Optional<String> getNodeVersion() {
        return getProperty(NODE_VERSION);
    }

    @Override
    public Optional<ByteString> getIcon() {
        return getProperty(ICON);
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
    public void setIcon(Optional<ByteString> icon) {
        if (icon.isPresent()) {
            VariableNode node = getPropertyNode(ICON).orElseGet(() -> {
                UaPropertyNode propertyNode = createPropertyNode(ICON);

                propertyNode.setDataType(Identifiers.ByteString);

                addPropertyNode(propertyNode);

                return propertyNode;
            });

            node.setValue(new DataValue(new Variant(icon.get())));
        } else {
            removePropertyNode(ICON);
        }
    }

}
