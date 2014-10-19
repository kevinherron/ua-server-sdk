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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimaps;
import com.inductiveautomation.opcua.sdk.core.ValueRank;
import com.inductiveautomation.opcua.sdk.server.api.MethodInvocationHandler;
import com.inductiveautomation.opcua.sdk.server.api.Reference;
import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;
import com.inductiveautomation.opcua.stack.core.types.structured.Argument;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class UaMethodNode extends UaNode implements MethodNode {

    private final ListMultimap<NodeId, Reference> referenceMap =
            Multimaps.synchronizedListMultimap(ArrayListMultimap.create());

    private volatile Optional<Argument[]> inputArguments = Optional.empty();
    private volatile Optional<Argument[]> outputArguments = Optional.empty();
    private volatile Optional<MethodInvocationHandler> handler = Optional.empty();

    private final AtomicBoolean executable;
    private final AtomicBoolean userExecutable;

    public UaMethodNode(NodeId nodeId,
                        NodeClass nodeClass,
                        QualifiedName browseName,
                        LocalizedText displayName,
                        Optional<LocalizedText> description,
                        Optional<UInteger> writeMask,
                        Optional<UInteger> userWriteMask,
                        boolean executable,
                        boolean userExecutable,
                        List<Reference> references) {

        super(nodeId, nodeClass, browseName, displayName, description, writeMask, userWriteMask);

        Preconditions.checkArgument(nodeClass == NodeClass.Method);

        this.executable = new AtomicBoolean(executable);
        this.userExecutable = new AtomicBoolean(userExecutable);

        references.stream().forEach(reference -> {
            referenceMap.put(reference.getReferenceTypeId(), reference);
        });
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
    public boolean isExecutable() {
        return executable.get();
    }

    @Override
    public boolean isUserExecutable() {
        return userExecutable.get();
    }

    public void setExecutable(boolean executable) {
        this.executable.set(executable);
    }

    public void setUserExecutable(boolean userExecutable) {
        this.userExecutable.set(userExecutable);
    }

    public void setInputArguments(Argument[] arguments, BiConsumer<NodeId, UaNode> put) {
        setArguments(arguments, put, true);
    }

    public void setOutputArguments(Argument[] arguments, BiConsumer<NodeId, UaNode> put) {
        setArguments(arguments, put, false);
    }

    private void setArguments(Argument[] arguments, BiConsumer<NodeId, UaNode> put, boolean input) {
        String inputOrOutput = input ? "InputArguments" : "OutputArguments";
        String identifier = String.format("%s.%s", getNodeId().getIdentifier().toString(), inputOrOutput);
        NodeId nodeId = new NodeId(getNodeId().getNamespaceIndex(), identifier);

        UaVariableNode node = UaVariableNode.builder()
                .setNodeId(nodeId)
                .setBrowseName(new QualifiedName(0, inputOrOutput))
                .setDisplayName(LocalizedText.english(inputOrOutput))
                .setValue(new DataValue(new Variant(arguments)))
                .setDataType(input ? Identifiers.InputArguments : Identifiers.OutputArguments)
                .setTypeDefinition(Identifiers.PropertyType)
                .setValueRank(ValueRank.OneDimension)
                .setArrayDimensions(new UInteger[]{uint(arguments.length)})
                .build();

        Reference reference = new Reference(
                getNodeId(),
                Identifiers.HasProperty,
                nodeId.expanded(),
                NodeClass.Variable,
                true
        );

        put.accept(nodeId, node);

        synchronized (referenceMap) {
            referenceMap.put(reference.getReferenceTypeId(), reference);

            if (input) {
                inputArguments = Optional.of(arguments);
            } else {
                outputArguments = Optional.of(arguments);
            }
        }
    }

    public void setInvocationHandler(MethodInvocationHandler handler) {
        this.handler = Optional.of(handler);
    }

    public Optional<Argument[]> getInputArguments(Function<NodeId, Node> get) {
        return inputArguments;
    }

    public Optional<Argument[]> getOutputArguments() {
        return outputArguments;
    }

    public Optional<MethodInvocationHandler> getHandler() {
        return handler;
    }

    /**
     * @return a new {@link UaMethodNodeBuilder}.
     */
    public static UaMethodNodeBuilder builder() {
        return new UaMethodNodeBuilder();
    }

    public static class UaMethodNodeBuilder implements Supplier<UaMethodNode> {

        private final List<Reference> references = Lists.newArrayList();

        private final NodeClass nodeClass = NodeClass.Method;

        private NodeId nodeId;
        private QualifiedName browseName;
        private LocalizedText displayName;
        private Optional<LocalizedText> description = Optional.empty();
        private Optional<UInteger> writeMask = Optional.of(uint(0));
        private Optional<UInteger> userWriteMask = Optional.of(uint(0));

        private boolean executable = true;
        private boolean userExecutable = true;

        @Override
        public UaMethodNode get() {
            return build();
        }

        public UaMethodNode build() {
            Preconditions.checkNotNull(nodeId, "NodeId cannot be null");
            Preconditions.checkNotNull(nodeClass, "NodeClass cannot be null");
            Preconditions.checkNotNull(browseName, "BrowseName cannot be null");
            Preconditions.checkNotNull(displayName, "DisplayName cannot be null");

            return new UaMethodNode(
                    nodeId,
                    nodeClass,
                    browseName,
                    displayName,
                    description,
                    writeMask,
                    userWriteMask,
                    executable,
                    userExecutable,
                    references
            );
        }

        public UaMethodNodeBuilder setNodeId(NodeId nodeId) {
            this.nodeId = nodeId;
            return this;
        }

        public UaMethodNodeBuilder setBrowseName(QualifiedName browseName) {
            this.browseName = browseName;
            return this;
        }

        public UaMethodNodeBuilder setDisplayName(LocalizedText displayName) {
            this.displayName = displayName;
            return this;
        }

        public UaMethodNodeBuilder setDescription(LocalizedText description) {
            this.description = Optional.of(description);
            return this;
        }

        public UaMethodNodeBuilder setWriteMask(UInteger writeMask) {
            this.writeMask = Optional.of(writeMask);
            return this;
        }

        public UaMethodNodeBuilder setUserWriteMask(UInteger userWriteMask) {
            this.userWriteMask = Optional.of(userWriteMask);
            return this;
        }

        public UaMethodNodeBuilder setExecutable(boolean executable) {
            this.executable = executable;
            return this;
        }

        public UaMethodNodeBuilder setUserExecutable(boolean userExecutable) {
            this.userExecutable = userExecutable;
            return this;
        }

    }

}
