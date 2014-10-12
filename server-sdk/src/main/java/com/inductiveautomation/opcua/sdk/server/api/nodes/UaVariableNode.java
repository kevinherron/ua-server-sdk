/*
 * OPC-UA SDK
 *
 * Copyright (C) 2014 Kevin Herron
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

package com.inductiveautomation.opcua.sdk.server.api.nodes;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.inductiveautomation.opcua.sdk.core.AccessLevel;
import com.inductiveautomation.opcua.sdk.server.api.Reference;
import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.types.builtin.*;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;
import com.inductiveautomation.opcua.stack.core.util.annotations.UByte;
import com.inductiveautomation.opcua.stack.core.util.annotations.UInt32;
import com.google.common.base.Preconditions;
import com.google.common.collect.*;

public class UaVariableNode extends UaNode implements VariableNode {

    private final ListMultimap<NodeId, Reference> referenceMap =
            Multimaps.synchronizedListMultimap(ArrayListMultimap.create());

    private final AtomicReference<ValueDelegate> valueDelegate;
    private final AtomicReference<Attributes> attributes;

    public UaVariableNode(NodeId nodeId,
                          NodeClass nodeClass,
                          QualifiedName browseName,
                          LocalizedText displayName,
                          Optional<LocalizedText> description,
                          @UInt32 Optional<Long> writeMask,
                          @UInt32 Optional<Long> userWriteMask,
                          DataValue value,
                          NodeId dataType,
                          int valueRank,
                          @UInt32 Optional<Long[]> arrayDimensions,
                          @UByte Short accessLevel,
                          @UByte Short userAccessLevel,
                          Optional<Double> minimumSamplingInterval,
                          boolean historizing,
                          List<Reference> references) {

        super(nodeId, nodeClass, browseName, displayName, description, writeMask, userWriteMask);

        Preconditions.checkArgument(nodeClass == NodeClass.Variable);

        Attributes as = new Attributes(dataType, valueRank, arrayDimensions,
                                       accessLevel, userAccessLevel, minimumSamplingInterval, historizing);

        attributes = new AtomicReference<>(as);
        valueDelegate = new AtomicReference<>(new AtomicValueDelegate(value));

        references.stream().forEach(reference -> {
            referenceMap.put(reference.getReferenceTypeId(), reference);
        });
    }

    public void setValueDelegate(ValueDelegate delegate) {
        valueDelegate.set(delegate);
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
        return valueDelegate.get().get();
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
    public Optional<Long[]> getArrayDimensions() {
        return attributes.get().getArrayDimensions();
    }

    @Override
    public Short getAccessLevel() {
        return attributes.get().getAccessLevel();
    }

    @Override
    public Short getUserAccessLevel() {
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

    public void setValue(DataValue value) {
        valueDelegate.get().accept(value);
    }

    public void setDataType(NodeId dataType) {
        safeSet(b -> b.setDataType(dataType));
    }

    public void setValueRank(int valueRank) {
        safeSet(b -> b.setValueRank(valueRank));
    }

    public void setArrayDimensions(Optional<Long[]> arrayDimensions) {
        safeSet(b -> b.setArrayDimensions(arrayDimensions));
    }

    public void setAccessLevel(Short accessLevel) {
        safeSet(b -> b.setAccessLevel(accessLevel));
    }

    public void setUserAccessLevel(Short userAccessLevel) {
        safeSet(b -> b.setUserAccessLevel(userAccessLevel));
    }

    public void setHistorizing(boolean historizing) {
        safeSet(b -> b.setHistorizing(historizing));
    }

    public static UaVariableNodeBuilder builder() {
        return new UaVariableNodeBuilder();
    }

    private void safeSet(Consumer<Attributes.Builder> consumer) {
        Attributes as = attributes.get();

        Attributes.Builder builder = Attributes.Builder.copy(as);
        consumer.accept(builder);
        Attributes mutatedCopy = builder.get();

        while (!attributes.compareAndSet(as, mutatedCopy)) {
            as = attributes.get();

            builder = Attributes.Builder.copy(as);
            consumer.accept(builder);
            mutatedCopy = builder.get();
        }
    }

    private static Reference hasTypeDefinition(NodeId sourceNodeId, NodeId typeDefinition) {
        return hasTypeDefinition(sourceNodeId, new ExpandedNodeId(typeDefinition));
    }

    private static Reference hasTypeDefinition(NodeId sourceNodeId, ExpandedNodeId typeDefinition) {
        return new Reference(sourceNodeId, Identifiers.HasTypeDefinition, typeDefinition, NodeClass.VariableType, true);
    }

    /**
     * A union of {@link Consumer} and {@link Supplier} that provides the container for this Node's DataValue.
     */
    public static interface ValueDelegate extends Consumer<DataValue>, Supplier<DataValue> {

    }

    /**
     * A thread-safe {@link ValueDelegate} that stores the {@link DataValue} in an {@link AtomicReference}.
     */
    private static class AtomicValueDelegate implements ValueDelegate {
        private final AtomicReference<DataValue> value = new AtomicReference<>();

        private AtomicValueDelegate(DataValue value) {
            this.value.set(value);
        }

        @Override
        public void accept(DataValue dataValue) {
            value.set(dataValue);
        }

        @Override
        public DataValue get() {
            return value.get();
        }
    }

    private static class Attributes {

        private final NodeId dataType;
        private final int valueRank;
        private final Optional<Long[]> arrayDimensions;
        private final Short accessLevel;
        private final Short userAccessLevel;
        private final Optional<Double> minimumSamplingInterval;
        private final boolean historizing;

        private Attributes(NodeId dataType,
                           int valueRank,
                           @UInt32 Optional<Long[]> arrayDimensions,
                           @UByte Short accessLevel,
                           @UByte Short userAccessLevel,
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

        @UInt32
        public Optional<Long[]> getArrayDimensions() {
            return arrayDimensions;
        }

        @UByte
        public Short getAccessLevel() {
            return accessLevel;
        }

        @UByte
        public Short getUserAccessLevel() {
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
                    valueRank == that.valueRank &&
                    accessLevel.equals(that.accessLevel) &&
                    arrayDimensions.equals(that.arrayDimensions) &&
                    dataType.equals(that.dataType) &&
                    minimumSamplingInterval.equals(that.minimumSamplingInterval) &&
                    userAccessLevel.equals(that.userAccessLevel);
        }

        @Override
        public int hashCode() {
            int result = dataType.hashCode();
            result = 31 * result + valueRank;
            result = 31 * result + arrayDimensions.hashCode();
            result = 31 * result + accessLevel.hashCode();
            result = 31 * result + userAccessLevel.hashCode();
            result = 31 * result + minimumSamplingInterval.hashCode();
            result = 31 * result + (historizing ? 1 : 0);
            return result;
        }

        private static class Builder implements Supplier<Attributes> {

            private NodeId dataType;
            private int valueRank;
            private Optional<Long[]> arrayDimensions;
            private Short accessLevel;
            private Short userAccessLevel;
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

            public Builder setValueRank(int valueRank) {
                this.valueRank = valueRank;
                return this;
            }

            public Builder setArrayDimensions(@UInt32 Optional<Long[]> arrayDimensions) {
                this.arrayDimensions = arrayDimensions;
                return this;
            }

            public Builder setAccessLevel(@UByte Short accessLevel) {
                this.accessLevel = accessLevel;
                return this;
            }

            public Builder setUserAccessLevel(@UByte Short userAccessLevel) {
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
        private Optional<Long> writeMask = Optional.of(0L);
        private Optional<Long> userWriteMask = Optional.of(0L);

        private DataValue value = new DataValue(
                Variant.NullValue, new StatusCode(StatusCodes.Uncertain_InitialValue),
                DateTime.now(), DateTime.now()
        );

        private NodeId dataType;
        private int valueRank = -1;
        private Optional<Long[]> arrayDimensions = Optional.empty();
        private Short accessLevel = AccessLevel.getMask(AccessLevel.CurrentRead);
        private Short userAccessLevel = AccessLevel.getMask(AccessLevel.CurrentRead);
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

        public UaVariableNodeBuilder setWriteMask(@UInt32 Long writeMask) {
            this.writeMask = Optional.of(writeMask);
            return this;
        }

        public UaVariableNodeBuilder setUserWriteMask(@UInt32 Long userWriteMask) {
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

        public UaVariableNodeBuilder setArrayDimensions(@UInt32 Long[] arrayDimensions) {
            this.arrayDimensions = Optional.of(arrayDimensions);
            return this;
        }

        public UaVariableNodeBuilder setAccessLevel(@UByte Short accessLevel) {
            this.accessLevel = accessLevel;
            return this;
        }

        public UaVariableNodeBuilder setUserAccessLevel(@UByte Short userAccessLevel) {
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
