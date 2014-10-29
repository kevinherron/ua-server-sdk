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
import com.inductiveautomation.opcua.sdk.core.AccessLevel;
import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.core.Reference;
import com.inductiveautomation.opcua.sdk.core.ValueRank;
import com.inductiveautomation.opcua.sdk.core.nodes.Node;
import com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode;
import com.inductiveautomation.opcua.sdk.core.nodes.VariableNode;
import com.inductiveautomation.opcua.sdk.core.nodes.VariableTypeNode;
import com.inductiveautomation.opcua.sdk.server.api.UaNodeManager;
import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;
import com.inductiveautomation.opcua.stack.core.types.structured.EUInformation;
import com.inductiveautomation.opcua.stack.core.types.structured.TimeZoneDataType;

import static com.inductiveautomation.opcua.sdk.core.Reference.HAS_COMPONENT_PREDICATE;
import static com.inductiveautomation.opcua.sdk.core.Reference.HAS_MODELLING_RULE_PREDICATE;
import static com.inductiveautomation.opcua.sdk.core.Reference.HAS_PROPERTY_PREDICATE;
import static com.inductiveautomation.opcua.sdk.core.Reference.HAS_TYPE_DEFINITION_PREDICATE;
import static com.inductiveautomation.opcua.sdk.server.util.StreamUtil.opt2stream;
import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.ubyte;
import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class UaVariableNode extends UaNode implements VariableNode {

    private static final DataValue INITIAL_VALUE = new DataValue(new StatusCode(StatusCodes.Uncertain_InitialValue));

    public static final QualifiedName NODE_VERSION = new QualifiedName(0, "NodeVersion");
    public static final QualifiedName LOCAL_TIME = new QualifiedName(0, "LocalTime");
    public static final QualifiedName DATA_TYPE_VERSION = new QualifiedName(0, "DataTypeVersion");
    public static final QualifiedName DICTIONARY_FRAGMENT = new QualifiedName(0, "DictionaryFragment");
    public static final QualifiedName ALLOW_NULLS = new QualifiedName(0, "AllowNulls");
    public static final QualifiedName VALUE_AS_TEXT = new QualifiedName(0, "ValueAsText");
    public static final QualifiedName MAX_STRING_LENGTH = new QualifiedName(0, "MaxStringLength");
    public static final QualifiedName MAX_ARRAY_LENGTH = new QualifiedName(0, "MaxArrayLength");
    public static final QualifiedName ENGINEERING_UNITS = new QualifiedName(0, "EngineeringUnits");

    private volatile DataValue value = INITIAL_VALUE;
    private volatile NodeId dataType = Identifiers.BaseDataType;
    private volatile Integer valueRank = ValueRank.Scalar;
    private volatile Optional<UInteger[]> arrayDimensions = Optional.empty();
    private volatile UByte accessLevel = ubyte(AccessLevel.getMask(AccessLevel.CurrentRead));
    private volatile UByte userAccessLevel = ubyte(AccessLevel.getMask(AccessLevel.CurrentRead));
    private volatile Optional<Double> minimumSamplingInterval = Optional.empty();
    private volatile boolean historizing = false;

    public UaVariableNode(UaNodeManager nodeManager,
                          NodeId nodeId,
                          QualifiedName browseName,
                          LocalizedText displayName) {

        super(nodeManager, nodeId, NodeClass.Variable, browseName, displayName);
    }

    public UaVariableNode(UaNodeManager nodeManager,
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

        super(nodeManager, nodeId, NodeClass.Variable, browseName, displayName, description, writeMask, userWriteMask);

        this.value = value;
        this.dataType = dataType;
        this.valueRank = valueRank;
        this.arrayDimensions = arrayDimensions;
        this.accessLevel = accessLevel;
        this.userAccessLevel = userAccessLevel;
        this.minimumSamplingInterval = minimumSamplingInterval;
        this.historizing = historizing;
    }

    @Override
    public DataValue getValue() {
        return value;
    }

    @Override
    public NodeId getDataType() {
        return dataType;
    }

    @Override
    public Integer getValueRank() {
        return valueRank;
    }

    @Override
    public Optional<UInteger[]> getArrayDimensions() {
        return arrayDimensions;
    }

    @Override
    public UByte getAccessLevel() {
        return accessLevel;
    }

    @Override
    public UByte getUserAccessLevel() {
        return userAccessLevel;
    }

    @Override
    public Optional<Double> getMinimumSamplingInterval() {
        return minimumSamplingInterval;
    }

    @Override
    public Boolean getHistorizing() {
        return historizing;
    }

    @Override
    public synchronized void setValue(DataValue value) {
        this.value = value;

        fireAttributeChanged(AttributeIds.Value, value);
    }

    @Override
    public synchronized void setDataType(NodeId dataType) {
        this.dataType = dataType;

        fireAttributeChanged(AttributeIds.DataType, dataType);
    }

    @Override
    public synchronized void setValueRank(Integer valueRank) {
        this.valueRank = valueRank;

        fireAttributeChanged(AttributeIds.ValueRank, valueRank);
    }

    @Override
    public synchronized void setArrayDimensions(Optional<UInteger[]> arrayDimensions) {
        this.arrayDimensions = arrayDimensions;

        arrayDimensions.ifPresent(v -> fireAttributeChanged(AttributeIds.ArrayDimensions, v));
    }

    @Override
    public synchronized void setAccessLevel(UByte accessLevel) {
        this.accessLevel = accessLevel;

        fireAttributeChanged(AttributeIds.AccessLevel, accessLevel);
    }

    @Override
    public synchronized void setUserAccessLevel(UByte userAccessLevel) {
        this.userAccessLevel = userAccessLevel;

        fireAttributeChanged(AttributeIds.UserAccessLevel, userAccessLevel);
    }

    @Override
    public void setMinimumSamplingInterval(Optional<Double> minimumSamplingInterval) {
        this.minimumSamplingInterval = minimumSamplingInterval;

        minimumSamplingInterval.ifPresent(v -> fireAttributeChanged(AttributeIds.MinimumSamplingInterval, v));
    }

    @Override
    public void setHistorizing(boolean historizing) {
        this.historizing = historizing;

        fireAttributeChanged(AttributeIds.Historizing, historizing);
    }

    public Optional<ObjectNode> getModellingRuleNode() {
        Node node = getReferences().stream()
                .filter(HAS_MODELLING_RULE_PREDICATE)
                .findFirst()
                .flatMap(r -> getNode(r.getTargetNodeId()))
                .orElse(null);

        ObjectNode objectNode = (node instanceof ObjectNode) ? (ObjectNode) node : null;

        return Optional.ofNullable(objectNode);
    }

    public List<Node> getPropertyNodes() {
        return getReferences().stream()
                .filter(HAS_PROPERTY_PREDICATE)
                .flatMap(r -> opt2stream(getNode(r.getTargetNodeId())))
                .collect(Collectors.toList());
    }

    public List<Node> getComponentNodes() {
        return getReferences().stream()
                .filter(HAS_COMPONENT_PREDICATE)
                .flatMap(r -> opt2stream(getNode(r.getTargetNodeId())))
                .collect(Collectors.toList());
    }

    public VariableTypeNode getTypeDefinitionNode() {
        Node node = getReferences().stream()
                .filter(HAS_TYPE_DEFINITION_PREDICATE)
                .findFirst()
                .flatMap(r -> getNode(r.getTargetNodeId()))
                .orElse(null);

        return (node instanceof VariableTypeNode) ? (VariableTypeNode) node : null;
    }

    @Override
    public Optional<String> getNodeVersion() {
        return getProperty(NODE_VERSION);
    }

    @Override
    public Optional<TimeZoneDataType> getLocalTime() {
        return getProperty(LOCAL_TIME);
    }

    @Override
    public Optional<String> getDataTypeVersion() {
        return getProperty(DATA_TYPE_VERSION);
    }

    @Override
    public Optional<ByteString> getDictionaryFragment() {
        return getProperty(DICTIONARY_FRAGMENT);
    }

    @Override
    public Optional<Boolean> getAllowNulls() {
        return getProperty(ALLOW_NULLS);
    }

    @Override
    public Optional<LocalizedText> getValueAsText() {
        return getProperty(VALUE_AS_TEXT);
    }

    @Override
    public Optional<UInteger> getMaxStringLength() {
        return getProperty(MAX_STRING_LENGTH);
    }

    @Override
    public Optional<UInteger> getMaxArrayLength() {
        return getProperty(MAX_ARRAY_LENGTH);
    }

    @Override
    public Optional<EUInformation> getEngineeringUnits() {
        return getProperty(ENGINEERING_UNITS);
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
    public void setLocalTime(Optional<TimeZoneDataType> localTime) {
        if (localTime.isPresent()) {
            VariableNode node = getPropertyNode(LOCAL_TIME).orElseGet(() -> {
                UaPropertyNode propertyNode = createPropertyNode(LOCAL_TIME);

                propertyNode.setDataType(Identifiers.TimeZoneDataType);

                addPropertyNode(propertyNode);

                return propertyNode;
            });

            node.setValue(new DataValue(new Variant(localTime.get())));
        } else {
            removePropertyNode(LOCAL_TIME);
        }
    }

    @Override
    public void setDataTypeVersion(Optional<String> dataTypeVersion) {
        if (dataTypeVersion.isPresent()) {
            VariableNode node = getPropertyNode(DATA_TYPE_VERSION).orElseGet(() -> {
                UaPropertyNode propertyNode = createPropertyNode(DATA_TYPE_VERSION);

                propertyNode.setDataType(Identifiers.String);

                addPropertyNode(propertyNode);

                return propertyNode;
            });

            node.setValue(new DataValue(new Variant(dataTypeVersion.get())));
        } else {
            removePropertyNode(DATA_TYPE_VERSION);
        }
    }

    @Override
    public void setDictionaryFragment(Optional<ByteString> dictionaryFragment) {
        if (dictionaryFragment.isPresent()) {
            VariableNode node = getPropertyNode(DICTIONARY_FRAGMENT).orElseGet(() -> {
                UaPropertyNode propertyNode = createPropertyNode(DICTIONARY_FRAGMENT);

                propertyNode.setDataType(Identifiers.ByteString);

                addPropertyNode(propertyNode);

                return propertyNode;
            });

            node.setValue(new DataValue(new Variant(dictionaryFragment.get())));
        } else {
            removePropertyNode(DICTIONARY_FRAGMENT);
        }
    }

    @Override
    public void setAllowNulls(Optional<Boolean> allowNulls) {
        if (allowNulls.isPresent()) {
            VariableNode node = getPropertyNode(ALLOW_NULLS).orElseGet(() -> {
                UaPropertyNode propertyNode = createPropertyNode(ALLOW_NULLS);

                propertyNode.setDataType(Identifiers.Boolean);

                addPropertyNode(propertyNode);

                return propertyNode;
            });

            node.setValue(new DataValue(new Variant(allowNulls.get())));
        } else {
            removePropertyNode(ALLOW_NULLS);
        }
    }

    @Override
    public void setValueAsText(Optional<LocalizedText> valueAsText) {
        if (valueAsText.isPresent()) {
            VariableNode node = getPropertyNode(VALUE_AS_TEXT).orElseGet(() -> {
                UaPropertyNode propertyNode = createPropertyNode(VALUE_AS_TEXT);

                propertyNode.setDataType(Identifiers.LocalizedText);

                addPropertyNode(propertyNode);

                return propertyNode;
            });

            node.setValue(new DataValue(new Variant(valueAsText.get())));
        } else {
            removePropertyNode(VALUE_AS_TEXT);
        }
    }

    @Override
    public void setMaxStringLength(Optional<UInteger> maxStringLength) {
        if (maxStringLength.isPresent()) {
            VariableNode node = getPropertyNode(MAX_STRING_LENGTH).orElseGet(() -> {
                UaPropertyNode propertyNode = createPropertyNode(MAX_STRING_LENGTH);

                propertyNode.setDataType(Identifiers.UInt32);

                addPropertyNode(propertyNode);

                return propertyNode;
            });

            node.setValue(new DataValue(new Variant(maxStringLength.get())));
        } else {
            removePropertyNode(MAX_STRING_LENGTH);
        }
    }

    @Override
    public void setMaxArrayLength(Optional<UInteger> maxArrayLength) {
        if (maxArrayLength.isPresent()) {
            VariableNode node = getPropertyNode(MAX_ARRAY_LENGTH).orElseGet(() -> {
                UaPropertyNode propertyNode = createPropertyNode(MAX_ARRAY_LENGTH);

                propertyNode.setDataType(Identifiers.UInt32);

                addPropertyNode(propertyNode);

                return propertyNode;
            });

            node.setValue(new DataValue(new Variant(maxArrayLength.get())));
        } else {
            removePropertyNode(MAX_ARRAY_LENGTH);
        }
    }

    @Override
    public void setEngineeringUnits(Optional<EUInformation> engineeringUnits) {
        if (engineeringUnits.isPresent()) {
            VariableNode node = getPropertyNode(ENGINEERING_UNITS).orElseGet(() -> {
                UaPropertyNode propertyNode = createPropertyNode(ENGINEERING_UNITS);

                propertyNode.setDataType(Identifiers.EUInformation);

                addPropertyNode(propertyNode);

                return propertyNode;
            });

            node.setValue(new DataValue(new Variant(engineeringUnits.get())));
        } else {
            removePropertyNode(ENGINEERING_UNITS);
        }
    }

    public static UaVariableNodeBuilder builder(UaNodeManager nodeManager) {
        return new UaVariableNodeBuilder(nodeManager);
    }

    public static class UaVariableNodeBuilder implements Supplier<UaVariableNode> {

        private final List<Reference> references = Lists.newArrayList();

        private NodeId nodeId;
        private QualifiedName browseName;
        private LocalizedText displayName;
        private Optional<LocalizedText> description = Optional.empty();
        private Optional<UInteger> writeMask = Optional.of(uint(0));
        private Optional<UInteger> userWriteMask = Optional.of(uint(0));

        private DataValue value = new DataValue(
                Variant.NullValue, new StatusCode(StatusCodes.Uncertain_InitialValue),
                DateTime.now(), DateTime.now()
        );

        private NodeId dataType;
        private int valueRank = ValueRank.Scalar;
        private Optional<UInteger[]> arrayDimensions = Optional.empty();
        private UByte accessLevel = ubyte(AccessLevel.getMask(AccessLevel.CurrentRead));
        private UByte userAccessLevel = ubyte(AccessLevel.getMask(AccessLevel.CurrentRead));
        private Optional<Double> minimumSamplingInterval = Optional.empty();
        private boolean historizing = false;

        private final UaNodeManager nodeManager;

        public UaVariableNodeBuilder(UaNodeManager nodeManager) {
            this.nodeManager = nodeManager;
        }

        @Override
        public UaVariableNode get() {
            return build();
        }

        public UaVariableNode build() {
            Preconditions.checkNotNull(nodeId, "NodeId cannot be null");
            Preconditions.checkNotNull(browseName, "BrowseName cannot be null");
            Preconditions.checkNotNull(displayName, "DisplayName cannot be null");
            Preconditions.checkNotNull(dataType, "DataType cannot be null");

            long hasTypeDefinitionCount = references.stream()
                    .filter(r -> Identifiers.HasTypeDefinition.equals(r.getReferenceTypeId())).count();

            if (hasTypeDefinitionCount == 0) {
                setTypeDefinition(Identifiers.BaseDataVariableType);
            } else {
                Preconditions.checkState(
                        hasTypeDefinitionCount == 1,
                        "Variable Node must have exactly one HasTypeDefinition reference.");
            }

            // TODO More validation on references.

            UaVariableNode node = new UaVariableNode(
                    nodeManager,
                    nodeId, browseName, displayName,
                    description, writeMask, userWriteMask,
                    value, dataType, valueRank,
                    arrayDimensions, accessLevel,
                    userAccessLevel, minimumSamplingInterval,
                    historizing
            );

            node.addReferences(references);

            return node;
        }

        public UaVariableNodeBuilder setNodeId(NodeId nodeId) {
            this.nodeId = nodeId;
            return this;
        }

        public UaVariableNodeBuilder setBrowseName(QualifiedName browseName) {
            this.browseName = browseName;
            return this;
        }

        public UaVariableNodeBuilder setDisplayName(LocalizedText displayName) {
            this.displayName = displayName;
            return this;
        }

        public UaVariableNodeBuilder setDescription(LocalizedText description) {
            this.description = Optional.of(description);
            return this;
        }

        public UaVariableNodeBuilder setWriteMask(UInteger writeMask) {
            this.writeMask = Optional.of(writeMask);
            return this;
        }

        public UaVariableNodeBuilder setUserWriteMask(UInteger userWriteMask) {
            this.userWriteMask = Optional.of(userWriteMask);
            return this;
        }

        public UaVariableNodeBuilder setValue(DataValue value) {
            this.value = value;
            return this;
        }

        public UaVariableNodeBuilder setDataType(NodeId dataType) {
            this.dataType = dataType;
            return this;
        }

        public UaVariableNodeBuilder setValueRank(int valueRank) {
            this.valueRank = valueRank;
            return this;
        }

        public UaVariableNodeBuilder setArrayDimensions(UInteger[] arrayDimensions) {
            this.arrayDimensions = Optional.of(arrayDimensions);
            return this;
        }

        public UaVariableNodeBuilder setAccessLevel(UByte accessLevel) {
            this.accessLevel = accessLevel;
            return this;
        }

        public UaVariableNodeBuilder setUserAccessLevel(UByte userAccessLevel) {
            this.userAccessLevel = userAccessLevel;
            return this;
        }

        public UaVariableNodeBuilder setMinimumSamplingInterval(Double minimumSamplingInterval) {
            this.minimumSamplingInterval = Optional.of(minimumSamplingInterval);
            return this;
        }

        public UaVariableNodeBuilder setHistorizing(boolean historizing) {
            this.historizing = historizing;
            return this;
        }

        public UaVariableNodeBuilder addReference(Reference reference) {
            references.add(reference);
            return this;
        }

        /**
         * Convenience method for adding the required HasTypeDefinition reference.
         * <p>
         * {@link #setNodeId(NodeId)} must have already been called before invoking this method.
         *
         * @param typeDefinition The {@link NodeId} of the TypeDefinition.
         * @return this {@link UaVariableNodeBuilder}.
         */
        public UaVariableNodeBuilder setTypeDefinition(NodeId typeDefinition) {
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
