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

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.core.Reference;
import com.inductiveautomation.opcua.sdk.core.nodes.Node;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;

public abstract class UaNode implements Node {

    private List<WeakReference<AttributeObserver>> observers;

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
                     Optional<UInteger> writeMask,
                     Optional<UInteger> userWriteMask) {

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
    public Optional<UInteger> getWriteMask() {
        return attributes.get().getWriteMask();
    }

    @Override
    public Optional<UInteger> getUserWriteMask() {
        return attributes.get().getUserWriteMask();
    }

    @Override
    public void setNodeId(NodeId nodeId) {
        setAttribute(AttributeIds.NodeId, nodeId, b -> b::setNodeId);
    }

    @Override
    public void setNodeClass(NodeClass nodeClass) {
        setAttribute(AttributeIds.NodeClass, nodeClass, b -> b::setNodeClass);
    }

    @Override
    public void setBrowseName(QualifiedName browseName) {
        setAttribute(AttributeIds.BrowseName, browseName, b -> b::setBrowseName);
    }

    @Override
    public void setDisplayName(LocalizedText displayName) {
        setAttribute(AttributeIds.DisplayName, displayName, b -> b::setDisplayName);
    }

    @Override
    public void setDescription(Optional<LocalizedText> description) {
        setAttribute(AttributeIds.Description, description, b -> b::setDescription);
    }

    @Override
    public void setWriteMask(Optional<UInteger> writeMask) {
        setAttribute(AttributeIds.WriteMask, writeMask, b -> b::setWriteMask);
    }

    @Override
    public void setUserWriteMask(Optional<UInteger> userWriteMask) {
        setAttribute(AttributeIds.UserWriteMask, userWriteMask, b -> b::setUserWriteMask);
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

    public synchronized void addAttributeObserver(AttributeObserver observer) {
        if (observers == null) {
            observers = new LinkedList<>();
        }

        observers.add(new WeakReference<>(observer));
    }

    public synchronized void removeAttributeObserver(AttributeObserver observer) {
        if (observers == null) return;

        Iterator<WeakReference<AttributeObserver>> iterator = observers.iterator();

        while (iterator.hasNext()) {
            WeakReference<AttributeObserver> ref = iterator.next();
            if (ref.get() == null || ref.get() == observer) {
                iterator.remove();
            }
        }

        if (observers.isEmpty()) observers = null;
    }

    protected synchronized void fireAttributeChanged(int attributeId, Object attributeValue) {
        if (observers == null) return;

        Iterator<WeakReference<AttributeObserver>> iterator = observers.iterator();

        while (iterator.hasNext()) {
            WeakReference<AttributeObserver> ref = iterator.next();
            AttributeObserver observer = ref.get();
            if (observer != null) {
                observer.attributeChanged(this, attributeId, attributeValue);
            } else {
                iterator.remove();
            }
        }
    }

    private static class Attributes {

        private final NodeId nodeId;
        private final NodeClass nodeClass;
        private final QualifiedName browseName;
        private final LocalizedText displayName;
        private final Optional<LocalizedText> description;
        private final Optional<UInteger> writeMask;
        private final Optional<UInteger> userWriteMask;

        private Attributes(NodeId nodeId,
                           NodeClass nodeClass,
                           QualifiedName browseName,
                           LocalizedText displayName,
                           Optional<LocalizedText> description,
                           Optional<UInteger> writeMask,
                           Optional<UInteger> userWriteMask) {
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

        public Optional<UInteger> getWriteMask() {
            return writeMask;
        }

        public Optional<UInteger> getUserWriteMask() {
            return userWriteMask;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Attributes that = (Attributes) o;

            return browseName.equals(that.browseName) &&
                    description.equals(that.description) &&
                    displayName.equals(that.displayName) &&
                    nodeClass == that.nodeClass &&
                    nodeId.equals(that.nodeId) &&
                    userWriteMask.equals(that.userWriteMask) &&
                    writeMask.equals(that.writeMask);
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
            private Optional<UInteger> writeMask = Optional.empty();
            private Optional<UInteger> userWriteMask = Optional.empty();

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

            public Builder setWriteMask(Optional<UInteger> writeMask) {
                this.writeMask = writeMask;
                return this;
            }

            public Builder setUserWriteMask(Optional<UInteger> userWriteMask) {
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
