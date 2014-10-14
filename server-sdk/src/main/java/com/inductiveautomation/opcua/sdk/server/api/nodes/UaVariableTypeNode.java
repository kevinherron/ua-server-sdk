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

package com.inductiveautomation.opcua.sdk.server.api.nodes;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import com.inductiveautomation.opcua.sdk.server.api.Reference;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;

public class UaVariableTypeNode extends UaNode implements VariableTypeNode {

    private final ListMultimap<NodeId, Reference> referenceMap =
            Multimaps.synchronizedListMultimap(ArrayListMultimap.create());

    private final AtomicReference<Attributes> attributes;

    public UaVariableTypeNode(NodeId nodeId,
                              NodeClass nodeClass,
                              QualifiedName browseName,
                              LocalizedText displayName,
                              Optional<LocalizedText> description,
                              Optional<UInteger> writeMask,
                              Optional<UInteger> userWriteMask,
                              Optional<DataValue> value,
                              NodeId dataType,
                              int valueRank,
                              Optional<UInteger[]> arrayDimensions,
                              boolean isAbstract,
                              List<Reference> references) {

        super(nodeId, nodeClass, browseName, displayName, description, writeMask, userWriteMask);

        Preconditions.checkArgument(nodeClass == NodeClass.VariableType);

        Attributes as = new Attributes(value, dataType, valueRank, arrayDimensions, isAbstract);
        attributes = new AtomicReference<>(as);

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
    public Optional<DataValue> getValue() {
        return attributes.get().getValue();
    }

    @Override
    public NodeId getDataType() {
        return attributes.get().getDataType();
    }

    @Override
    public int getValueRank() {
        return attributes.get().getValueRank();
    }

    @Override
    public Optional<UInteger[]> getArrayDimensions() {
        return attributes.get().getArrayDimensions();
    }

    @Override
    public boolean isAbstract() {
        return attributes.get().isAbstract();
    }

    public void setValue(Optional<DataValue> value) {
        safeSet(b -> b.setValue(value));
    }

    public void setDataType(NodeId dataType) {
        safeSet(b -> b.setDataType(dataType));
    }

    public void setValueRank(int valueRank) {
        safeSet(b -> b.setValueRank(valueRank));
    }

    public void setArrayDimensions(Optional<UInteger[]> arrayDimensions) {
        safeSet(b -> b.setArrayDimensions(arrayDimensions));
    }

    public void setIsAbstract(boolean isAbstract) {
        safeSet(b -> b.setIsAbstract(isAbstract));
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

    private static class Attributes {

        private final Optional<DataValue> value;
        private final NodeId dataType;
        private final int valueRank;
        private final Optional<UInteger[]> arrayDimensions;
        private final boolean isAbstract;

        private Attributes(Optional<DataValue> value,
                           NodeId dataType,
                           int valueRank,
                           Optional<UInteger[]> arrayDimensions,
                           boolean isAbstract) {

            this.value = value;
            this.dataType = dataType;
            this.valueRank = valueRank;
            this.arrayDimensions = arrayDimensions;
            this.isAbstract = isAbstract;
        }

        public Optional<DataValue> getValue() {
            return value;
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

        public boolean isAbstract() {
            return isAbstract;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Attributes that = (Attributes) o;

            return isAbstract == that.isAbstract &&
                    valueRank == that.valueRank &&
                    arrayDimensions.equals(that.arrayDimensions) &&
                    dataType.equals(that.dataType) &&
                    value.equals(that.value);
        }

        @Override
        public int hashCode() {
            int result = value.hashCode();
            result = 31 * result + dataType.hashCode();
            result = 31 * result + valueRank;
            result = 31 * result + arrayDimensions.hashCode();
            result = 31 * result + (isAbstract ? 1 : 0);
            return result;
        }

        private static class Builder implements Supplier<Attributes> {

            private Optional<DataValue> value;
            private NodeId dataType;
            private int valueRank;
            private Optional<UInteger[]> arrayDimensions;
            private boolean isAbstract;

            @Override
            public Attributes get() {
                return new Attributes(value, dataType, valueRank, arrayDimensions, isAbstract);
            }

            public Builder setValue(Optional<DataValue> value) {
                this.value = value;
                return this;
            }

            public Builder setDataType(NodeId dataType) {
                this.dataType = dataType;
                return this;
            }

            public Builder setValueRank(int valueRank) {
                this.valueRank = valueRank;
                return this;
            }

            public Builder setArrayDimensions(Optional<UInteger[]> arrayDimensions) {
                this.arrayDimensions = arrayDimensions;
                return this;
            }

            public Builder setIsAbstract(boolean isAbstract) {
                this.isAbstract = isAbstract;
                return this;
            }

            public static Builder copy(Attributes attributes) {
                return new Builder()
                        .setValue(attributes.getValue())
                        .setDataType(attributes.getDataType())
                        .setValueRank(attributes.getValueRank())
                        .setArrayDimensions(attributes.getArrayDimensions())
                        .setIsAbstract(attributes.isAbstract());
            }
        }
    }
}
