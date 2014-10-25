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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import com.inductiveautomation.opcua.sdk.core.nodes.ReferenceTypeNode;
import com.inductiveautomation.opcua.sdk.core.Reference;
import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;

public class UaReferenceTypeNode extends UaNode implements ReferenceTypeNode {

    private final AtomicReference<Attributes> attributes;

    private final ListMultimap<NodeId, Reference> referenceMap =
            Multimaps.synchronizedListMultimap(ArrayListMultimap.create());

    public UaReferenceTypeNode(NodeId nodeId,
                               NodeClass nodeClass,
                               QualifiedName browseName,
                               LocalizedText displayName,
                               Optional<LocalizedText> description,
                               Optional<UInteger> writeMask,
                               Optional<UInteger> userWriteMask,
                               boolean isAbstract,
                               boolean symmetric,
                               Optional<LocalizedText> inverseName,
                               List<Reference> references) {

        super(nodeId, nodeClass, browseName, displayName, description, writeMask, userWriteMask);

        Preconditions.checkArgument(nodeClass == NodeClass.ReferenceType);

        Attributes as = new Attributes(isAbstract, symmetric, inverseName);
        this.attributes = new AtomicReference<>(as);

        references.stream().forEach(reference -> {
            referenceMap.put(reference.getReferenceTypeId(), reference);
        });
    }

    public void addHasPropertyReference(ExpandedNodeId targetNodeId) {
        Reference reference = new Reference(
                getNodeId(),
                Identifiers.HasProperty,
                targetNodeId,
                NodeClass.Variable,
                true
        );

        referenceMap.put(Identifiers.HasProperty, reference);
    }

    public void addHasSubtypeReference(ExpandedNodeId targetNodeId, NodeClass targetNodeClass, boolean forward) {
        Reference reference = new Reference(
                getNodeId(),
                Identifiers.HasSubtype,
                targetNodeId,
                targetNodeClass,
                forward
        );

        referenceMap.put(Identifiers.HasSubtype, reference);
    }

    public boolean removeReference(Reference reference) {
        return referenceMap.remove(reference.getReferenceTypeId(), reference);
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

    public List<Reference> getHasPropertyReferences() {
        synchronized (referenceMap) {
            return ImmutableList.copyOf(referenceMap.get(Identifiers.HasProperty));
        }
    }

    public List<Reference> getHasSubtypeReferences() {
        synchronized (referenceMap) {
            return ImmutableList.copyOf(referenceMap.get(Identifiers.HasSubtype));
        }
    }

    @Override
    public Boolean getIsAbstract() {
        return attributes.get().isAbstract();
    }

    @Override
    public Boolean getIsSymmetric() {
        return attributes.get().isSymmetric();
    }

    @Override
    public Optional<LocalizedText> getInverseName() {
        return attributes.get().getInverseName();
    }

    public void setIsAbstract(boolean isAbstract) {
        safeSet(b -> b.setIsAbstract(isAbstract));
    }

    public void setSymmetric(boolean symmetric) {
        safeSet(b -> b.setSymmetric(symmetric));
    }

    public void setInverseName(Optional<LocalizedText> inverseName) {
        safeSet(b -> b.setInverseName(inverseName));
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
        private final boolean isAbstract;
        private final boolean symmetric;
        private final Optional<LocalizedText> inverseName;

        private Attributes(boolean isAbstract, boolean symmetric, Optional<LocalizedText> inverseName) {
            this.isAbstract = isAbstract;
            this.symmetric = symmetric;
            this.inverseName = inverseName;
        }

        public boolean isAbstract() {
            return isAbstract;
        }

        public boolean isSymmetric() {
            return symmetric;
        }

        public Optional<LocalizedText> getInverseName() {
            return inverseName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Attributes that = (Attributes) o;

            return isAbstract == that.isAbstract &&
                    symmetric == that.symmetric &&
                    inverseName.equals(that.inverseName);
        }

        @Override
        public int hashCode() {
            int result = (isAbstract ? 1 : 0);
            result = 31 * result + (symmetric ? 1 : 0);
            result = 31 * result + inverseName.hashCode();
            return result;
        }

        private static class Builder implements Supplier<Attributes> {
            private boolean isAbstract;
            private boolean symmetric;
            private Optional<LocalizedText> inverseName;

            @Override
            public Attributes get() {
                return new Attributes(isAbstract, symmetric, inverseName);
            }

            public Builder setIsAbstract(boolean isAbstract) {
                this.isAbstract = isAbstract;
                return this;
            }

            public Builder setSymmetric(boolean symmetric) {
                this.symmetric = symmetric;
                return this;
            }

            public Builder setInverseName(Optional<LocalizedText> inverseName) {
                this.inverseName = inverseName;
                return this;
            }

            public static Builder copy(Attributes attributes) {
                return new Builder()
                        .setIsAbstract(attributes.isAbstract())
                        .setSymmetric(attributes.isSymmetric())
                        .setInverseName(attributes.getInverseName());
            }
        }
    }
}
