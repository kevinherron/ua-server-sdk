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
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.core.ValueRank;
import com.inductiveautomation.opcua.sdk.core.nodes.MethodNode;
import com.inductiveautomation.opcua.sdk.core.nodes.Node;
import com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode;
import com.inductiveautomation.opcua.sdk.core.nodes.VariableNode;
import com.inductiveautomation.opcua.sdk.server.api.MethodInvocationHandler;
import com.inductiveautomation.opcua.sdk.server.api.UaNodeManager;
import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;
import com.inductiveautomation.opcua.stack.core.types.structured.Argument;

import static com.inductiveautomation.opcua.sdk.core.Reference.ALWAYS_GENERATES_EVENT_PREDICATE;
import static com.inductiveautomation.opcua.sdk.core.Reference.HAS_MODELLING_RULE_PREDICATE;
import static com.inductiveautomation.opcua.sdk.core.Reference.HAS_PROPERTY_PREDICATE;
import static com.inductiveautomation.opcua.sdk.server.util.StreamUtil.opt2stream;
import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class UaMethodNode extends UaNode implements MethodNode {

    public static final QualifiedName INPUT_ARGUMENTS = new QualifiedName(0, "InputArguments");
    public static final QualifiedName NODE_VERSION = new QualifiedName(0, "NodeVersion");
    public static final QualifiedName OUTPUT_ARGUMENTS = new QualifiedName(0, "OutputArguments");

    private volatile Optional<MethodInvocationHandler> handler = Optional.empty();

    private volatile boolean executable;
    private volatile boolean userExecutable;

    public UaMethodNode(UaNodeManager nodeManager,
                        NodeId nodeId,
                        QualifiedName browseName,
                        LocalizedText displayName,
                        Optional<LocalizedText> description,
                        Optional<UInteger> writeMask,
                        Optional<UInteger> userWriteMask,
                        boolean executable,
                        boolean userExecutable) {

        super(nodeManager, nodeId, NodeClass.Method, browseName, displayName, description, writeMask, userWriteMask);

        this.executable = executable;
        this.userExecutable = userExecutable;
    }

    @Override
    public Boolean isExecutable() {
        return executable;
    }

    @Override
    public Boolean isUserExecutable() {
        return userExecutable;
    }

    @Override
    public synchronized void setExecutable(boolean executable) {
        this.executable = executable;

        fireAttributeChanged(AttributeIds.Executable, executable);
    }

    @Override
    public synchronized void setUserExecutable(boolean userExecutable) {
        this.userExecutable = userExecutable;

        fireAttributeChanged(AttributeIds.UserExecutable, userExecutable);
    }

    public List<Node> getPropertyNodes() {
        return getReferences().stream()
                .filter(HAS_PROPERTY_PREDICATE)
                .flatMap(r -> opt2stream(getNode(r.getTargetNodeId())))
                .collect(Collectors.toList());
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

    public List<Node> getAlwaysGeneratesEventNodes() {
        return getReferences().stream()
                .filter(ALWAYS_GENERATES_EVENT_PREDICATE)
                .flatMap(r -> opt2stream(getNode(r.getTargetNodeId())))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<String> getNodeVersion() {
        return getProperty(NODE_VERSION);
    }

    @Override
    public Optional<Argument[]> getInputArguments() {
        return getProperty(INPUT_ARGUMENTS);
    }

    @Override
    public Optional<Argument[]> getOutputArguments() {
        return getProperty(OUTPUT_ARGUMENTS);
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
    public void setInputArguments(Optional<Argument[]> inputArguments) {
        if (inputArguments.isPresent()) {
            VariableNode node = getPropertyNode(INPUT_ARGUMENTS).orElseGet(() -> {
                UaPropertyNode propertyNode = createPropertyNode(INPUT_ARGUMENTS);

                propertyNode.setDataType(Identifiers.InputArguments);
                propertyNode.setValueRank(ValueRank.OneDimension);
                propertyNode.setArrayDimensions(Optional.of(new UInteger[]{uint(0)}));

                addPropertyNode(propertyNode);

                return propertyNode;
            });

            node.setValue(new DataValue(new Variant(inputArguments.get())));
        } else {
            removePropertyNode(INPUT_ARGUMENTS);
        }
    }

    @Override
    public void setOutputArguments(Optional<Argument[]> outputArguments) {
        if (outputArguments.isPresent()) {
            VariableNode node = getPropertyNode(OUTPUT_ARGUMENTS).orElseGet(() -> {
                UaPropertyNode propertyNode = createPropertyNode(OUTPUT_ARGUMENTS);

                propertyNode.setDataType(Identifiers.OutputArguments);
                propertyNode.setValueRank(ValueRank.OneDimension);
                propertyNode.setArrayDimensions(Optional.of(new UInteger[]{uint(0)}));

                addPropertyNode(propertyNode);

                return propertyNode;
            });

            node.setValue(new DataValue(new Variant(outputArguments.get())));
        } else {
            removePropertyNode(OUTPUT_ARGUMENTS);
        }
    }

    public Optional<MethodInvocationHandler> getInvocationHandler() {
        return handler;
    }

    public void setInvocationHandler(MethodInvocationHandler handler) {
        this.handler = Optional.of(handler);
    }

    /**
     * @return a new {@link UaMethodNodeBuilder}.
     */
    public static UaMethodNodeBuilder builder(UaNodeManager nodeManager) {
        return new UaMethodNodeBuilder(nodeManager);
    }

    public static class UaMethodNodeBuilder implements Supplier<UaMethodNode> {

        private NodeId nodeId;
        private QualifiedName browseName;
        private LocalizedText displayName;
        private Optional<LocalizedText> description = Optional.empty();
        private Optional<UInteger> writeMask = Optional.of(uint(0));
        private Optional<UInteger> userWriteMask = Optional.of(uint(0));

        private boolean executable = true;
        private boolean userExecutable = true;

        private final UaNodeManager nodeManager;

        public UaMethodNodeBuilder(UaNodeManager nodeManager) {
            this.nodeManager = nodeManager;
        }

        @Override
        public UaMethodNode get() {
            return build();
        }

        public UaMethodNode build() {
            Preconditions.checkNotNull(nodeId, "NodeId cannot be null");
            Preconditions.checkNotNull(browseName, "BrowseName cannot be null");
            Preconditions.checkNotNull(displayName, "DisplayName cannot be null");

            return new UaMethodNode(
                    nodeManager,
                    nodeId,
                    browseName,
                    displayName,
                    description,
                    writeMask,
                    userWriteMask,
                    executable,
                    userExecutable
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
