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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.core.Reference;
import com.inductiveautomation.opcua.sdk.core.ValueRank;
import com.inductiveautomation.opcua.sdk.core.nodes.Node;
import com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode;
import com.inductiveautomation.opcua.sdk.core.nodes.ObjectTypeNode;
import com.inductiveautomation.opcua.sdk.core.nodes.VariableNode;
import com.inductiveautomation.opcua.sdk.server.api.UaNodeManager;
import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;
import com.inductiveautomation.opcua.stack.core.types.structured.EnumValueType;

import static com.inductiveautomation.opcua.sdk.core.Reference.HAS_COMPONENT_PREDICATE;
import static com.inductiveautomation.opcua.sdk.core.Reference.HAS_DESCRIPTION_PREDICATE;
import static com.inductiveautomation.opcua.sdk.core.Reference.HAS_EVENT_SOURCE_PREDICATE;
import static com.inductiveautomation.opcua.sdk.core.Reference.HAS_NOTIFIER_PREDICATE;
import static com.inductiveautomation.opcua.sdk.core.Reference.HAS_PROPERTY_PREDICATE;
import static com.inductiveautomation.opcua.sdk.core.Reference.HAS_TYPE_DEFINITION_PREDICATE;
import static com.inductiveautomation.opcua.sdk.core.Reference.ORGANIZES_PREDICATE;
import static com.inductiveautomation.opcua.sdk.server.util.StreamUtil.opt2stream;
import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.ubyte;
import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;
import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint_a;

public class UaObjectNode extends UaNode implements ObjectNode {

    public static final QualifiedName NODE_VERSION = new QualifiedName(0, "NodeVersion");
    public static final QualifiedName ENUM_STRINGS = new QualifiedName(0, "EnumStrings");
    public static final QualifiedName ENUM_VALUES = new QualifiedName(0, "EnumValues");

    private volatile UByte eventNotifier = ubyte(0);

    public UaObjectNode(UaNodeManager nodeManager,
                        NodeId nodeId,
                        QualifiedName browseName,
                        LocalizedText displayName) {

        super(nodeManager, nodeId, NodeClass.Object, browseName, displayName);
    }

    public UaObjectNode(UaNodeManager nodeManager,
                        NodeId nodeId,
                        QualifiedName browseName,
                        LocalizedText displayName,
                        Optional<LocalizedText> description,
                        Optional<UInteger> writeMask,
                        Optional<UInteger> userWriteMask,
                        UByte eventNotifier) {

        super(nodeManager, nodeId, NodeClass.Object, browseName, displayName, description, writeMask, userWriteMask);

        this.eventNotifier = eventNotifier;
    }

    @Override
    public UByte getEventNotifier() {
        return eventNotifier;
    }

    @Override
    public synchronized void setEventNotifier(UByte eventNotifier) {
        this.eventNotifier = eventNotifier;

        fireAttributeChanged(AttributeIds.EventNotifier, eventNotifier);
    }

    public List<Node> getComponentNodes() {
        return getReferences().stream()
                .filter(HAS_COMPONENT_PREDICATE)
                .flatMap(r -> opt2stream(getNode(r.getTargetNodeId())))
                .collect(Collectors.toList());
    }

    public List<Node> getPropertyNodes() {
        return getReferences().stream()
                .filter(HAS_PROPERTY_PREDICATE)
                .flatMap(r -> opt2stream(getNode(r.getTargetNodeId())))
                .collect(Collectors.toList());
    }

    public ObjectTypeNode getTypeDefinitionNode() {
        Node node = getReferences().stream()
                .filter(HAS_TYPE_DEFINITION_PREDICATE)
                .findFirst()
                .flatMap(r -> getNode(r.getTargetNodeId()))
                .orElse(null);

        return (node instanceof ObjectTypeNode) ? (ObjectTypeNode) node : null;
    }

    public List<Node> getEventSourceNodes() {
        return getReferences().stream()
                .filter(HAS_EVENT_SOURCE_PREDICATE)
                .flatMap(r -> opt2stream(getNode(r.getTargetNodeId())))
                .collect(Collectors.toList());
    }

    public List<Node> getNotifierNodes() {
        return getReferences().stream()
                .filter(HAS_NOTIFIER_PREDICATE)
                .flatMap(r -> opt2stream(getNode(r.getTargetNodeId())))
                .collect(Collectors.toList());
    }

    public List<Node> getOrganizesNodes() {
        return getReferences().stream()
                .filter(ORGANIZES_PREDICATE)
                .flatMap(r -> opt2stream(getNode(r.getTargetNodeId())))
                .collect(Collectors.toList());
    }

    public Optional<Node> getDescriptionNode() {
        Optional<UaNode> node = getReferences().stream()
                .filter(HAS_DESCRIPTION_PREDICATE)
                .findFirst()
                .flatMap(r -> getNode(r.getTargetNodeId()));

        return node.map(n -> n);
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

    public static UaObjectNodeBuilder builder(UaNodeManager nodeManager) {
        return new UaObjectNodeBuilder(nodeManager);
    }

    public static class UaObjectNodeBuilder implements Supplier<UaObjectNode> {

        private final List<Reference> references = Lists.newArrayList();

        private NodeId nodeId;
        private QualifiedName browseName;
        private LocalizedText displayName;
        private Optional<LocalizedText> description = Optional.empty();
        private Optional<UInteger> writeMask = Optional.of(uint(0));
        private Optional<UInteger> userWriteMask = Optional.of(uint(0));
        private UByte eventNotifier = ubyte(0);

        private final UaNodeManager nodeManager;

        public UaObjectNodeBuilder(UaNodeManager nodeManager) {
            this.nodeManager = nodeManager;
        }

        @Override
        public UaObjectNode get() {
            return build();
        }

        /**
         * Builds the configured {@link UaObjectNode}.
         * <p>
         * The following fields are required: NodeId, NodeClass, BrowseName, DisplayName.
         * <p>
         * Exactly one HasTypeDefinition reference must be present.
         *
         * @return a {@link UaObjectNode}.
         * @throws NullPointerException  if any of the required fields are null.
         * @throws IllegalStateException if exactly one HasTypeDefinition reference is not present.
         */
        public UaObjectNode build() {
            Preconditions.checkNotNull(nodeId, "NodeId cannot be null");
            Preconditions.checkNotNull(browseName, "BrowseName cannot be null");
            Preconditions.checkNotNull(displayName, "DisplayName cannot be null");

            long hasTypeDefinitionCount = references.stream()
                    .filter(r -> Identifiers.HasTypeDefinition.equals(r.getReferenceTypeId())).count();

            Preconditions.checkState(
                    hasTypeDefinitionCount == 1,
                    "Object Node must have exactly one HasTypeDefinition reference.");

            // TODO More validation on references.

            UaObjectNode node = new UaObjectNode(
                    nodeManager,
                    nodeId,
                    browseName,
                    displayName,
                    description,
                    writeMask,
                    userWriteMask,
                    eventNotifier
            );

            node.addReferences(references);

            return node;
        }

        public UaObjectNodeBuilder setNodeId(NodeId nodeId) {
            this.nodeId = nodeId;
            return this;
        }

        public UaObjectNodeBuilder setBrowseName(QualifiedName browseName) {
            this.browseName = browseName;
            return this;
        }

        public UaObjectNodeBuilder setDisplayName(LocalizedText displayName) {
            this.displayName = displayName;
            return this;
        }

        public UaObjectNodeBuilder setDescription(LocalizedText description) {
            this.description = Optional.of(description);
            return this;
        }

        public UaObjectNodeBuilder setWriteMask(UInteger writeMask) {
            this.writeMask = Optional.of(writeMask);
            return this;
        }

        public UaObjectNodeBuilder setUserWriteMask(UInteger userWriteMask) {
            this.userWriteMask = Optional.of(userWriteMask);
            return this;
        }

        public UaObjectNodeBuilder setEventNotifier(UByte eventNotifier) {
            this.eventNotifier = eventNotifier;
            return this;
        }

        public UaObjectNodeBuilder addReference(Reference reference) {
            references.add(reference);
            return this;
        }

        /**
         * Convenience method for adding the required HasTypeDefinition reference.
         * <p>
         * {@link #setNodeId(NodeId)} must have already been called before invoking this method.
         *
         * @param typeDefinition The {@link NodeId} of the TypeDefinition.
         * @return this {@link UaObjectNodeBuilder}.
         */
        public UaObjectNodeBuilder setTypeDefinition(NodeId typeDefinition) {
            Objects.requireNonNull(nodeId, "NodeId cannot be null");

            references.add(new Reference(
                    nodeId,
                    Identifiers.HasTypeDefinition,
                    new ExpandedNodeId(typeDefinition),
                    NodeClass.ObjectType,
                    true
            ));

            return this;
        }

    }

}
