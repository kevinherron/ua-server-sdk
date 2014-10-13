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

import com.inductiveautomation.opcua.sdk.server.api.Reference;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;
import com.inductiveautomation.opcua.stack.core.util.annotations.UInt32;

public abstract class UaNode implements Node {

    private final AtomicReference<Attributes> attributes;

    protected UaNode(NodeId nodeId,
                     NodeClass nodeClass,
                     QualifiedName browseName,
                     LocalizedText displayName) {

        this(nodeId, nodeClass, browseName, displayName,
                Optional.empty(), Optional.empty(), Optional.empty());
    }

    protected UaNode(NodeId nodeId,
                     NodeClass nodeClass,
                     QualifiedName browseName,
                     LocalizedText displayName,
                     Optional<LocalizedText> description,
                     @UInt32 Optional<Long> writeMask,
                     @UInt32 Optional<Long> userWriteMask) {

        Attributes a = new Attributes(nodeId, nodeClass, browseName, displayName,
                description, writeMask, userWriteMask);

        attributes = new AtomicReference<>(a);
    }

    public abstract void addReference(Reference reference);

    public abstract List<Reference> getReferences();

    @Override
    public NodeId getNodeId() {
        return attributes.get().getNodeId();
    }

    @Override
    public NodeClass getNodeClass() {
        return attributes.get().getNodeClass();
    }

    @Override
    public QualifiedName getBrowseName() {
        return attributes.get().getBrowseName();
    }

    @Override
    public LocalizedText getDisplayName() {
        return attributes.get().getDisplayName();
    }

    @Override
    public Optional<LocalizedText> getDescription() {
        return attributes.get().getDescription();
    }

    @Override
    public Optional<Long> getWriteMask() {
        return attributes.get().getWriteMask();
    }

    @Override
    public Optional<Long> getUserWriteMask() {
        return attributes.get().getUserWriteMask();
    }

    public void setNodeId(NodeId nodeId) {
        safeSet(b -> b.setNodeId(nodeId));
    }

    public void setNodeClass(NodeClass nodeClass) {
        safeSet(b -> b.setNodeClass(nodeClass));
    }

    public void setBrowseName(QualifiedName browseName) {
        safeSet(b -> b.setBrowseName(browseName));
    }

    public void setDisplayName(LocalizedText displayName) {
        safeSet(b -> b.setDisplayName(displayName));
    }

    public void setDescription(Optional<LocalizedText> description) {
        safeSet(b -> b.setDescription(description));
    }

    public void setWriteMask(@UInt32 Optional<Long> writeMask) {
        safeSet(b -> b.setWriteMask(writeMask));
    }

    public void setUserWriteMask(@UInt32 Optional<Long> userWriteMask) {
        safeSet(b -> b.setUserWriteMask(userWriteMask));
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

        private final NodeId nodeId;
        private final NodeClass nodeClass;
        private final QualifiedName browseName;
        private final LocalizedText displayName;
        private final Optional<LocalizedText> description;
        private final Optional<Long> writeMask;
        private final Optional<Long> userWriteMask;

        private Attributes(NodeId nodeId,
                           NodeClass nodeClass,
                           QualifiedName browseName,
                           LocalizedText displayName,
                           Optional<LocalizedText> description,
                           @UInt32 Optional<Long> writeMask,
                           @UInt32 Optional<Long> userWriteMask) {
            this.nodeId = nodeId;
            this.nodeClass = nodeClass;
            this.browseName = browseName;
            this.displayName = displayName;
            this.description = description;
            this.writeMask = writeMask;
            this.userWriteMask = userWriteMask;
        }

        public NodeId getNodeId() {
            return nodeId;
        }

        public NodeClass getNodeClass() {
            return nodeClass;
        }

        public QualifiedName getBrowseName() {
            return browseName;
        }

        public LocalizedText getDisplayName() {
            return displayName;
        }

        public Optional<LocalizedText> getDescription() {
            return description;
        }

        @UInt32
        public Optional<Long> getWriteMask() {
            return writeMask;
        }

        @UInt32
        public Optional<Long> getUserWriteMask() {
            return userWriteMask;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Attributes that = (Attributes) o;

            if (!browseName.equals(that.browseName)) return false;
            if (!description.equals(that.description)) return false;
            if (!displayName.equals(that.displayName)) return false;
            if (nodeClass != that.nodeClass) return false;
            if (!nodeId.equals(that.nodeId)) return false;
            if (!userWriteMask.equals(that.userWriteMask)) return false;
            if (!writeMask.equals(that.writeMask)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = nodeId.hashCode();
            result = 31 * result + nodeClass.hashCode();
            result = 31 * result + browseName.hashCode();
            result = 31 * result + displayName.hashCode();
            result = 31 * result + description.hashCode();
            result = 31 * result + writeMask.hashCode();
            result = 31 * result + userWriteMask.hashCode();
            return result;
        }

        private static class Builder implements Supplier<Attributes> {

            private NodeId nodeId;
            private NodeClass nodeClass;
            private QualifiedName browseName;
            private LocalizedText displayName;
            private Optional<LocalizedText> description = Optional.empty();
            private Optional<Long> writeMask = Optional.empty();
            private Optional<Long> userWriteMask = Optional.empty();

            @Override
            public Attributes get() {
                return new Attributes(nodeId, nodeClass, browseName, displayName, description, writeMask, userWriteMask);
            }

            public Builder setNodeId(NodeId nodeId) {
                this.nodeId = nodeId;
                return this;
            }

            public Builder setNodeClass(NodeClass nodeClass) {
                this.nodeClass = nodeClass;
                return this;
            }

            public Builder setBrowseName(QualifiedName browseName) {
                this.browseName = browseName;
                return this;
            }

            public Builder setDisplayName(LocalizedText displayName) {
                this.displayName = displayName;
                return this;
            }

            public Builder setDescription(Optional<LocalizedText> description) {
                this.description = description;
                return this;
            }

            public Builder setWriteMask(@UInt32 Optional<Long> writeMask) {
                this.writeMask = writeMask;
                return this;
            }

            public Builder setUserWriteMask(@UInt32 Optional<Long> userWriteMask) {
                this.userWriteMask = userWriteMask;
                return this;
            }

            public static Builder copy(Attributes attributes) {
                return new Builder()
                        .setNodeId(attributes.getNodeId())
                        .setNodeClass(attributes.getNodeClass())
                        .setBrowseName(attributes.getBrowseName())
                        .setDisplayName(attributes.getDisplayName())
                        .setDescription(attributes.getDescription())
                        .setWriteMask(attributes.getWriteMask())
                        .setUserWriteMask(attributes.getUserWriteMask());
            }

        }
    }

}
