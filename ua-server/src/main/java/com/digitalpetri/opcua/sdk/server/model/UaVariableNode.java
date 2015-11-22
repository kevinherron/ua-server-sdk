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

package com.digitalpetri.opcua.sdk.server.model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.digitalpetri.opcua.sdk.core.AccessLevel;
import com.digitalpetri.opcua.sdk.core.AttributeIds;
import com.digitalpetri.opcua.sdk.core.Reference;
import com.digitalpetri.opcua.sdk.core.ValueRank;
import com.digitalpetri.opcua.sdk.core.model.UaOptional;
import com.digitalpetri.opcua.sdk.core.nodes.Node;
import com.digitalpetri.opcua.sdk.core.nodes.ObjectNode;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.core.nodes.VariableTypeNode;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.model.Property.BasicProperty;
import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.NodeClass;
import com.digitalpetri.opcua.stack.core.types.structured.EUInformation;
import com.digitalpetri.opcua.stack.core.types.structured.TimeZoneDataType;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import static com.digitalpetri.opcua.sdk.core.Reference.HAS_COMPONENT_PREDICATE;
import static com.digitalpetri.opcua.sdk.core.Reference.HAS_MODELLING_RULE_PREDICATE;
import static com.digitalpetri.opcua.sdk.core.Reference.HAS_PROPERTY_PREDICATE;
import static com.digitalpetri.opcua.sdk.core.Reference.HAS_TYPE_DEFINITION_PREDICATE;
import static com.digitalpetri.opcua.sdk.server.util.StreamUtil.opt2stream;
import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.ubyte;
import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class UaVariableNode extends UaNode implements VariableNode {

    private static final DataValue INITIAL_VALUE = new DataValue(new StatusCode(StatusCodes.Uncertain_InitialValue));

    private volatile DataValue value = INITIAL_VALUE;
    private volatile NodeId dataType = Identifiers.BaseDataType;
    private volatile Integer valueRank = ValueRank.Scalar;
    private volatile Optional<UInteger[]> arrayDimensions = Optional.empty();
    private volatile UByte accessLevel = ubyte(AccessLevel.getMask(AccessLevel.CurrentRead));
    private volatile UByte userAccessLevel = ubyte(AccessLevel.getMask(AccessLevel.CurrentRead));
    private volatile Optional<Double> minimumSamplingInterval = Optional.empty();
    private volatile boolean historizing = false;

    public UaVariableNode(UaNamespace namespace,
                          NodeId nodeId,
                          QualifiedName browseName,
                          LocalizedText displayName) {

        super(namespace, nodeId, NodeClass.Variable, browseName, displayName);
    }

    public UaVariableNode(UaNamespace namespace,
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

        super(namespace, nodeId, NodeClass.Variable, browseName, displayName, description, writeMask, userWriteMask);

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

    /**
     * Add a 'HasComponent' reference from this Object to {@code node} and an inverse 'ComponentOf' reference from
     * {@code node} back to this Object.
     *
     * @param node the node to add as a component of this Object.
     */
    public void addComponent(UaNode node) {
        addReference(new Reference(
                getNodeId(),
                Identifiers.HasComponent,
                node.getNodeId().expanded(),
                node.getNodeClass(),
                true
        ));

        node.addReference(new Reference(
                node.getNodeId(),
                Identifiers.HasComponent,
                getNodeId().expanded(),
                getNodeClass(),
                false
        ));
    }

    /**
     * Remove the 'HasComponent' reference from this Object to {@code node} and the inverse 'ComponentOf' reference
     * from {@code node} back to this Object.
     *
     * @param node the node to remove as a component of this Object.
     */
    public void removeComponent(UaNode node) {
        removeReference(new Reference(
                getNodeId(),
                Identifiers.HasComponent,
                node.getNodeId().expanded(),
                node.getNodeClass(),
                true
        ));

        node.removeReference(new Reference(
                node.getNodeId(),
                Identifiers.HasComponent,
                getNodeId().expanded(),
                getNodeClass(),
                false
        ));
    }

    @UaOptional("NodeVersion")
    public String getNodeVersion() {
        return getProperty(NodeVersion).orElse(null);
    }

    @UaOptional("LocalTime")
    public TimeZoneDataType getLocalTime() {
        return getProperty(LocalTime).orElse(null);
    }

    @UaOptional("DataTypeVersion")
    public String getDataTypeVersion() {
        return getProperty(DataTypeVersion).orElse(null);
    }

    @UaOptional("DictionaryFragment")
    public ByteString getDictionaryFragment() {
        return getProperty(DictionaryFragment).orElse(null);
    }

    @UaOptional("AllowNulls")
    public Boolean getAllowNulls() {
        return getProperty(AllowNulls).orElse(null);
    }

    @UaOptional("MaxStringLength")
    public UInteger getMaxStringLength() {
        return getProperty(MaxStringLength).orElse(null);
    }

    @UaOptional("MaxArrayLength")
    public UInteger getMaxArrayLength() {
        return getProperty(MaxArrayLength).orElse(null);
    }

    @UaOptional("EngineeringUnits")
    public EUInformation getEngineeringUnits() {
        return getProperty(EngineeringUnits).orElse(null);
    }

    public void setNodeVersion(String nodeVersion) {
        setProperty(NodeVersion, nodeVersion);
    }

    public void setLocalTime(TimeZoneDataType localTime) {
        setProperty(LocalTime, localTime);
    }

    public void setDataTypeVersion(String dataTypeVersion) {
        setProperty(DataTypeVersion, dataTypeVersion);
    }

    public void setDictionaryFragment(ByteString dictionaryFragment) {
        setProperty(DictionaryFragment, dictionaryFragment);
    }

    public void setAllowNulls(Boolean allowNulls) {
        setProperty(AllowNulls, allowNulls);
    }

    public void setMaxStringLength(UInteger maxStringLength) {
        setProperty(MaxStringLength, maxStringLength);
    }

    public void setMaxArrayLength(UInteger maxArrayLength) {
        setProperty(MaxArrayLength, maxArrayLength);
    }

    public void setEngineeringUnits(EUInformation engineeringUnits) {
        setProperty(EngineeringUnits, engineeringUnits);
    }

    public static final Property<String> NodeVersion = new BasicProperty<>(
            new QualifiedName(0, "NodeVersion"),
            Identifiers.String,
            ValueRank.Scalar,
            String.class
    );

    public static final Property<TimeZoneDataType> LocalTime = new BasicProperty<>(
            new QualifiedName(0, "LocalTime"),
            Identifiers.TimeZoneDataType,
            ValueRank.Scalar,
            TimeZoneDataType.class
    );

    public static final Property<String> DataTypeVersion = new BasicProperty<>(
            new QualifiedName(0, "DataTypeVersion"),
            Identifiers.String,
            ValueRank.Scalar,
            String.class
    );

    public static final Property<ByteString> DictionaryFragment = new BasicProperty<>(
            new QualifiedName(0, "DictionaryFragment"),
            Identifiers.ByteString,
            ValueRank.Scalar,
            ByteString.class
    );

    public static final Property<Boolean> AllowNulls = new BasicProperty<>(
            new QualifiedName(0, "AllowNulls"),
            Identifiers.Boolean,
            ValueRank.Scalar,
            Boolean.class
    );

    public static final Property<LocalizedText> ValueAsText = new BasicProperty<>(
            new QualifiedName(0, "ValueAsText"),
            Identifiers.LocalizedText,
            ValueRank.Scalar,
            LocalizedText.class
    );

    public static final Property<UInteger> MaxStringLength = new BasicProperty<>(
            new QualifiedName(0, "MaxStringLength"),
            Identifiers.UInt32,
            ValueRank.Scalar,
            UInteger.class
    );

    public static final Property<UInteger> MaxArrayLength = new BasicProperty<>(
            new QualifiedName(0, "MaxArrayLength"),
            Identifiers.UInt32,
            ValueRank.Scalar,
            UInteger.class
    );

    public static final Property<EUInformation> EngineeringUnits = new BasicProperty<>(
            new QualifiedName(0, "EngineeringUnits"),
            Identifiers.EUInformation,
            ValueRank.Scalar,
            EUInformation.class
    );

    public static UaVariableNodeBuilder builder(UaNamespace nodeManager) {
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
                Variant.NULL_VALUE, new StatusCode(StatusCodes.Uncertain_InitialValue),
                DateTime.now(), DateTime.now()
        );

        private NodeId dataType;
        private int valueRank = ValueRank.Scalar;
        private Optional<UInteger[]> arrayDimensions = Optional.empty();
        private UByte accessLevel = ubyte(AccessLevel.getMask(AccessLevel.CurrentRead));
        private UByte userAccessLevel = ubyte(AccessLevel.getMask(AccessLevel.CurrentRead));
        private Optional<Double> minimumSamplingInterval = Optional.empty();
        private boolean historizing = false;

        private final UaNamespace nodeManager;

        public UaVariableNodeBuilder(UaNamespace nodeManager) {
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
