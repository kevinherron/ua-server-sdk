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
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimaps;
import com.inductiveautomation.opcua.sdk.core.AccessLevel;
import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.core.Reference;
import com.inductiveautomation.opcua.sdk.core.nodes.VariableNode;
import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
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

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.ubyte;
import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class UaVariableNode extends UaNode implements VariableNode {

    private final ListMultimap<NodeId, Reference> referenceMap =
            Multimaps.synchronizedListMultimap(ArrayListMultimap.create());

    private final AtomicReference<DataValue> value;
    private final AtomicReference<Attributes> attributes;

    public UaVariableNode(NodeId nodeId,
                          NodeClass nodeClass,
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
                          boolean historizing,
                          List<Reference> references) {

        super(nodeId, nodeClass, browseName, displayName, description, writeMask, userWriteMask);

        Preconditions.checkArgument(nodeClass == NodeClass.Variable);

        Attributes attributes = new Attributes(dataType, valueRank, arrayDimensions,
                accessLevel, userAccessLevel, minimumSamplingInterval, historizing);

        this.attributes = new AtomicReference<>(attributes);
        this.value = new AtomicReference<>(value);

        for (Reference reference : references) {
            referenceMap.put(reference.getReferenceTypeId(), reference);
        }
    }

    @Override
    public void addReference(Reference reference) {
        referenceMap.put(reference.getReferenceTypeId(), reference);
    }

    @Override
    public List<Reference> getReferences() {
        synchronized (referenceMap) {
            return ImmutableList.copyOf(referenceMap.values());
        }
    }

    @Override
    public DataValue getValue() {
        return value.get();
    }

    @Override
    public NodeId getDataType() {
        return attributes.get().getDataType();
    }

    @Override
    public Integer getValueRank() {
        return attributes.get().getValueRank();
    }

    @Override
    public Optional<UInteger[]> getArrayDimensions() {
        return attributes.get().getArrayDimensions();
    }

    @Override
    public UByte getAccessLevel() {
        return attributes.get().getAccessLevel();
    }

    @Override
    public UByte getUserAccessLevel() {
        return attributes.get().getUserAccessLevel();
    }

    @Override
    public Optional<Double> getMinimumSamplingInterval() {
        return attributes.get().getMinimumSamplingInterval();
    }

    @Override
    public Boolean isHistorizing() {
        return attributes.get().isHistorizing();
    }

    @Override
    public synchronized void setValue(DataValue value) {
        this.value.set(value);

        fireAttributeChanged(AttributeIds.Value, value);
    }

    @Override
    public void setDataType(NodeId dataType) {
        setAttribute(AttributeIds.DataType, dataType, b -> b::setDataType);
    }

    @Override
    public void setValueRank(Integer valueRank) {
        setAttribute(AttributeIds.ValueRank, valueRank, b -> b::setValueRank);
    }

    @Override
    public void setArrayDimensions(Optional<UInteger[]> arrayDimensions) {
        setAttribute(AttributeIds.ArrayDimensions, arrayDimensions, b -> b::setArrayDimensions);
    }

    @Override
    public void setAccessLevel(UByte accessLevel) {
        setAttribute(AttributeIds.AccessLevel, accessLevel, b -> b::setAccessLevel);
    }

    @Override
    public void setUserAccessLevel(UByte userAccessLevel) {
        setAttribute(AttributeIds.UserAccessLevel, userAccessLevel, b -> b::setUserAccessLevel);
    }

    @Override
    public void setHistorizing(boolean historizing) {
        setAttribute(AttributeIds.Historizing, historizing, b -> b::setHistorizing);
    }

    private synchronized <T> void setAttribute(int attributeId, T value,
                                               Function<Attributes.Builder, Consumer<T>> setter) {

        Attributes current = attributes.get();

        Attributes.Builder builder = Attributes.Builder.copy(current);
        setter.apply(builder).accept(value);
        Attributes updated = builder.get();

        attributes.set(updated);

        fireAttributeChanged(attributeId, value);
    }

    public static UaVariableNodeBuilder builder() {
        return new UaVariableNodeBuilder();
    }

    private static class Attributes {

        private final NodeId dataType;
        private final Integer valueRank;
        private final Optional<UInteger[]> arrayDimensions;
        private final UByte accessLevel;
        private final UByte userAccessLevel;
        private final Optional<Double> minimumSamplingInterval;
        private final boolean historizing;

        private Attributes(NodeId dataType,
                           Integer valueRank,
                           Optional<UInteger[]> arrayDimensions,
                           UByte accessLevel,
                           UByte  userAccessLevel,
                           Optional<Double> minimumSamplingInterval,
                           boolean historizing) {

            this.dataType = dataType;
            this.valueRank = valueRank;
            this.arrayDimensions = arrayDimensions;
            this.accessLevel = accessLevel;
            this.userAccessLevel = userAccessLevel;
            this.minimumSamplingInterval = minimumSamplingInterval;
            this.historizing = historizing;
        }

        public NodeId getDataType() {
            return dataType;
        }

        public int getValueRank() {
            return valueRank;
        }

        public Optional<UInteger[]> getArrayDimensions() {
            return arrayDimensions;
        }

        public UByte getAccessLevel() {
            return accessLevel;
        }

        public UByte getUserAccessLevel() {
            return userAccessLevel;
        }

        public Optional<Double> getMinimumSamplingInterval() {
            return minimumSamplingInterval;
        }

        public boolean isHistorizing() {
            return historizing;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Attributes that = (Attributes) o;

            return historizing == that.historizing &&
                    accessLevel.equals(that.accessLevel) &&
                    arrayDimensions.equals(that.arrayDimensions) &&
                    dataType.equals(that.dataType) &&
                    minimumSamplingInterval.equals(that.minimumSamplingInterval) &&
                    userAccessLevel.equals(that.userAccessLevel) &&
                    valueRank.equals(that.valueRank);
        }

        @Override
        public int hashCode() {
            int result = dataType.hashCode();
            result = 31 * result + valueRank.hashCode();
            result = 31 * result + arrayDimensions.hashCode();
            result = 31 * result + accessLevel.hashCode();
            result = 31 * result + userAccessLevel.hashCode();
            result = 31 * result + minimumSamplingInterval.hashCode();
            result = 31 * result + (historizing ? 1 : 0);
            return result;
        }

        private static class Builder implements Supplier<Attributes> {

            private NodeId dataType;
            private Integer valueRank;
            private Optional<UInteger[]> arrayDimensions;
            private UByte accessLevel;
            private UByte userAccessLevel;
            private Optional<Double> minimumSamplingInterval;
            private boolean historizing;

            @Override
            public Attributes get() {
                return new Attributes(dataType, valueRank, arrayDimensions, accessLevel, userAccessLevel, minimumSamplingInterval, historizing);
            }

            public Builder setDataType(NodeId dataType) {
                this.dataType = dataType;
                return this;
            }

            public Builder setValueRank(Integer valueRank) {
                this.valueRank = valueRank;
                return this;
            }

            public Builder setArrayDimensions(Optional<UInteger[]> arrayDimensions) {
                this.arrayDimensions = arrayDimensions;
                return this;
            }

            public Builder setAccessLevel(UByte accessLevel) {
                this.accessLevel = accessLevel;
                return this;
            }

            public Builder setUserAccessLevel(UByte userAccessLevel) {
                this.userAccessLevel = userAccessLevel;
                return this;
            }

            public Builder setMinimumSamplingInterval(Optional<Double> minimumSamplingInterval) {
                this.minimumSamplingInterval = minimumSamplingInterval;
                return this;
            }

            public Builder setHistorizing(boolean historizing) {
                this.historizing = historizing;
                return this;
            }

            public static Builder copy(Attributes attributes) {
                return new Builder()
                        .setDataType(attributes.getDataType())
                        .setValueRank(attributes.getValueRank())
                        .setArrayDimensions(attributes.getArrayDimensions())
                        .setAccessLevel(attributes.getAccessLevel())
                        .setUserAccessLevel(attributes.getUserAccessLevel())
                        .setMinimumSamplingInterval(attributes.getMinimumSamplingInterval())
                        .setHistorizing(attributes.isHistorizing());
            }

        }

    }

    public static class UaVariableNodeBuilder implements Supplier<UaVariableNode> {

        private final List<Reference> references = Lists.newArrayList();

        private final NodeClass nodeClass = NodeClass.Variable;

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
        private int valueRank = -1;
        private Optional<UInteger[]> arrayDimensions = Optional.empty();
        private UByte accessLevel = ubyte(AccessLevel.getMask(AccessLevel.CurrentRead));
        private UByte userAccessLevel = ubyte(AccessLevel.getMask(AccessLevel.CurrentRead));
        private Optional<Double> minimumSamplingInterval = Optional.empty();
        private boolean historizing = false;

        @Override
        public UaVariableNode get() {
            return build();
        }

        public UaVariableNode build() {
            Preconditions.checkNotNull(nodeId, "NodeId cannot be null");
            Preconditions.checkNotNull(nodeClass, "NodeClass cannot be null");
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

            return new UaVariableNode(
                    nodeId, nodeClass,
                    browseName, displayName,
                    description, writeMask,
                    userWriteMask, value,
                    dataType, valueRank,
                    arrayDimensions, accessLevel,
                    userAccessLevel, minimumSamplingInterval,
                    historizing, references
            );
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
