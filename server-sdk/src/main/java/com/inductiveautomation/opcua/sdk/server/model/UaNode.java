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

package com.inductiveautomation.opcua.sdk.server.model;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.core.Reference;
import com.inductiveautomation.opcua.sdk.core.nodes.Node;
import com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode;
import com.inductiveautomation.opcua.sdk.core.nodes.VariableNode;
import com.inductiveautomation.opcua.sdk.server.api.UaNamespace;
import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.inductiveautomation.opcua.sdk.server.util.StreamUtil.opt2stream;

public abstract class UaNode implements Node {

    private static final Logger LOGGER = LoggerFactory.getLogger(UaNode.class);

    private final AtomicInteger refCount = new AtomicInteger(0);

    private final List<Reference> references = new CopyOnWriteArrayList<>();

    private List<WeakReference<AttributeObserver>> observers;

    private final UaNamespace namespace;

    private volatile NodeId nodeId;
    private volatile NodeClass nodeClass;
    private volatile QualifiedName browseName;
    private volatile LocalizedText displayName;
    private volatile Optional<LocalizedText> description;
    private volatile Optional<UInteger> writeMask;
    private volatile Optional<UInteger> userWriteMask;

    protected UaNode(UaNamespace namespace,
                     NodeId nodeId,
                     NodeClass nodeClass,
                     QualifiedName browseName,
                     LocalizedText displayName) {

        this(namespace, nodeId, nodeClass, browseName, displayName,
                Optional.empty(), Optional.empty(), Optional.empty());
    }

    protected UaNode(UaNamespace namespace,
                     NodeId nodeId,
                     NodeClass nodeClass,
                     QualifiedName browseName,
                     LocalizedText displayName,
                     Optional<LocalizedText> description,
                     Optional<UInteger> writeMask,
                     Optional<UInteger> userWriteMask) {

        this.namespace = namespace;

        this.nodeId = nodeId;
        this.nodeClass = nodeClass;
        this.browseName = browseName;
        this.displayName = displayName;
        this.description = description;
        this.writeMask = writeMask;
        this.userWriteMask = userWriteMask;
    }

    @Override
    public NodeId getNodeId() {
        return nodeId;
    }

    @Override
    public NodeClass getNodeClass() {
        return nodeClass;
    }

    @Override
    public QualifiedName getBrowseName() {
        return browseName;
    }

    @Override
    public LocalizedText getDisplayName() {
        return displayName;
    }

    @Override
    public Optional<LocalizedText> getDescription() {
        return description;
    }

    @Override
    public Optional<UInteger> getWriteMask() {
        return writeMask;
    }

    @Override
    public Optional<UInteger> getUserWriteMask() {
        return userWriteMask;
    }

    @Override
    public synchronized void setNodeId(NodeId nodeId) {
        this.nodeId = nodeId;

        fireAttributeChanged(AttributeIds.NodeId, nodeId);
    }

    @Override
    public synchronized void setNodeClass(NodeClass nodeClass) {
        this.nodeClass = nodeClass;

        fireAttributeChanged(AttributeIds.NodeClass, nodeClass);
    }

    @Override
    public synchronized void setBrowseName(QualifiedName browseName) {
        this.browseName = browseName;

        fireAttributeChanged(AttributeIds.BrowseName, browseName);
    }

    @Override
    public synchronized void setDisplayName(LocalizedText displayName) {
        this.displayName = displayName;

        fireAttributeChanged(AttributeIds.DisplayName, displayName);
    }

    @Override
    public synchronized void setDescription(Optional<LocalizedText> description) {
        this.description = description;

        description.ifPresent(v -> fireAttributeChanged(AttributeIds.Description, v));
    }

    @Override
    public synchronized void setWriteMask(Optional<UInteger> writeMask) {
        this.writeMask = writeMask;

        writeMask.ifPresent(v -> fireAttributeChanged(AttributeIds.WriteMask, v));
    }

    @Override
    public synchronized void setUserWriteMask(Optional<UInteger> userWriteMask) {
        this.userWriteMask = userWriteMask;

        userWriteMask.ifPresent(v -> fireAttributeChanged(AttributeIds.UserWriteMask, v));
    }

    public UaNamespace getNamespace() {
        return namespace;
    }

    protected Optional<UaNode> getNode(NodeId nodeId) {
        return namespace.getNode(nodeId);
    }

    protected Optional<UaNode> getNode(ExpandedNodeId nodeId) {
        return namespace.getNode(nodeId);
    }

    public ImmutableList<Reference> getReferences() {
        return ImmutableList.copyOf(references);
    }

    public synchronized void addReference(Reference reference) {
        references.add(reference);

        if (reference.isInverse()) {
            int count = refCount.incrementAndGet();
            LOGGER.debug("{} refCount={}", getNodeId(), count);

            if (count == 1) {
                namespace.addNode(this);
            }
        }
    }

    public synchronized void addReferences(Collection<Reference> c) {
        c.forEach(this::addReference);
    }

    public synchronized void removeReference(Reference reference) {
        references.remove(reference);

        if (reference.isInverse()) {
            int count = refCount.decrementAndGet();
            LOGGER.debug("{} refCount={}", getNodeId(), count);

            if (count == 0) {
                deallocate();
            }
        }
    }

    public synchronized void removeReferences(Collection<Reference> c) {
        c.forEach(this::removeReference);
    }

    protected synchronized void deallocate() {
        LOGGER.debug("{} deallocate()", getNodeId());

        ExpandedNodeId expanded = getNodeId().expanded();

        List<UaNode> referencedNodes = getReferences().stream()
                .filter(Reference::isForward)
                .flatMap(r -> opt2stream(getNode(r.getTargetNodeId())))
                .collect(Collectors.toList());

        for (UaNode node : referencedNodes) {
            List<Reference> inverseReferences = node.getReferences().stream()
                    .filter(Reference::isInverse)
                    .filter(r -> r.getTargetNodeId().equals(expanded))
                    .collect(Collectors.toList());

            node.removeReferences(inverseReferences);
        }

        namespace.removeNode(getNodeId());
    }

    public <T> Optional<T> getProperty(Property<T> property) {
        return getProperty(property.getBrowseName());
    }

    public <T> Optional<T> getProperty(String browseName) {
        return getProperty(new QualifiedName(getNodeId().getNamespaceIndex(), browseName));
    }

    public <T> Optional<T> getProperty(QualifiedName browseName) {
        Node node = getPropertyNode(browseName).orElse(null);

        try {
            return Optional.ofNullable((T) ((VariableNode) node).getValue().getValue().getValue());
        } catch (Throwable t) {
            return Optional.empty();
        }
    }

    public <T> void setProperty(Property<T> property, T value) {
        VariableNode node = getPropertyNode(property.getBrowseName()).orElseGet(() -> {
            NodeId propertyNodeId = new NodeId(
                    getNodeId().getNamespaceIndex(),
                    String.format("%s.%s", getNodeId().getIdentifier().toString(), browseName.getName())
            );

            UaPropertyNode propertyNode = new UaPropertyNode(
                    getNamespace(),
                    propertyNodeId,
                    browseName,
                    LocalizedText.english(browseName.getName())
            );

            propertyNode.setDataType(property.getDataType());
            propertyNode.setValueRank(property.getValueRank());
            propertyNode.setArrayDimensions(property.getArrayDimensions());

            addProperty(propertyNode);

            return propertyNode;
        });

        node.setValue(new DataValue(new Variant(value)));
    }

    public Optional<VariableNode> getPropertyNode(String browseName) {
        return getPropertyNode(new QualifiedName(getNodeId().getNamespaceIndex(), browseName));
    }

    public Optional<VariableNode> getPropertyNode(QualifiedName browseName) {
        Node node = references.stream()
                .filter(Reference.HAS_PROPERTY_PREDICATE)
                .flatMap(r -> opt2stream(getNode(r.getTargetNodeId())))
                .filter(n -> n.getBrowseName().equals(browseName))
                .findFirst().orElse(null);

        try {
            return Optional.ofNullable((VariableNode) node);
        } catch (Throwable t) {
            return Optional.empty();
        }
    }

    public void addProperty(UaPropertyNode node) {
        addReference(new Reference(
                getNodeId(),
                Identifiers.HasProperty,
                node.getNodeId().expanded(),
                NodeClass.Variable,
                true
        ));

        node.addReference(new Reference(
                node.getNodeId(),
                Identifiers.HasProperty,
                getNodeId().expanded(),
                getNodeClass(),
                false
        ));
    }

    public void removeProperty(UaPropertyNode node) {
        removeReference(new Reference(
                getNodeId(),
                Identifiers.HasProperty,
                node.getNodeId().expanded(),
                NodeClass.Variable,
                true
        ));

        node.removeReference(new Reference(
                node.getNodeId(),
                Identifiers.HasProperty,
                getNodeId().expanded(),
                getNodeClass(),
                false
        ));
    }

    protected Optional<ObjectNode> getObjectComponent(String browseName) {
        return getObjectComponent(new QualifiedName(getNodeId().getNamespaceIndex(), browseName));
    }

    protected Optional<ObjectNode> getObjectComponent(QualifiedName browseName) {
        ObjectNode node = (ObjectNode) references.stream()
                .filter(Reference.HAS_COMPONENT_PREDICATE.and(r -> r.getTargetNodeClass() == NodeClass.Object))
                .flatMap(r -> opt2stream(getNode(r.getTargetNodeId())))
                .filter(n -> n.getBrowseName().equals(browseName))
                .findFirst().orElse(null);

        return Optional.ofNullable(node);
    }

    protected Optional<VariableNode> getVariableComponent(String browseName) {
        return getVariableComponent(new QualifiedName(getNodeId().getNamespaceIndex(), browseName));
    }

    protected Optional<VariableNode> getVariableComponent(QualifiedName browseName) {
        VariableNode node = (VariableNode) references.stream()
                .filter(Reference.HAS_COMPONENT_PREDICATE.and(r -> r.getTargetNodeClass() == NodeClass.Variable))
                .flatMap(r -> opt2stream(getNode(r.getTargetNodeId())))
                .filter(n -> n.getBrowseName().equals(browseName))
                .findFirst().orElse(null);

        return Optional.ofNullable(node);
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

}
