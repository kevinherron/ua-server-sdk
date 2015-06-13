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
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.digitalpetri.opcua.sdk.core.AttributeIds;
import com.digitalpetri.opcua.sdk.core.ValueRank;
import com.digitalpetri.opcua.sdk.core.model.UaOptional;
import com.digitalpetri.opcua.sdk.core.nodes.MethodNode;
import com.digitalpetri.opcua.sdk.core.nodes.Node;
import com.digitalpetri.opcua.sdk.core.nodes.ObjectNode;
import com.digitalpetri.opcua.sdk.server.api.MethodInvocationHandler;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.model.Property.BasicProperty;
import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.NodeClass;
import com.digitalpetri.opcua.stack.core.types.structured.Argument;
import com.google.common.base.Preconditions;

import static com.digitalpetri.opcua.sdk.core.Reference.ALWAYS_GENERATES_EVENT_PREDICATE;
import static com.digitalpetri.opcua.sdk.core.Reference.HAS_MODELLING_RULE_PREDICATE;
import static com.digitalpetri.opcua.sdk.core.Reference.HAS_PROPERTY_PREDICATE;
import static com.digitalpetri.opcua.sdk.server.util.StreamUtil.opt2stream;
import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class UaMethodNode extends UaNode implements MethodNode {

    private volatile Optional<MethodInvocationHandler> handler = Optional.empty();

    private volatile boolean executable;
    private volatile boolean userExecutable;

    public UaMethodNode(UaNamespace namespace,
                        NodeId nodeId,
                        QualifiedName browseName,
                        LocalizedText displayName,
                        Optional<LocalizedText> description,
                        Optional<UInteger> writeMask,
                        Optional<UInteger> userWriteMask,
                        boolean executable,
                        boolean userExecutable) {

        super(namespace, nodeId, NodeClass.Method, browseName, displayName, description, writeMask, userWriteMask);

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

    public Optional<MethodInvocationHandler> getInvocationHandler() {
        return handler;
    }

    public void setInvocationHandler(MethodInvocationHandler handler) {
        this.handler = Optional.of(handler);
    }

    @UaOptional("NodeVersion")
    public String getNodeVersion() {
        return getProperty(NodeVersion).orElse(null);
    }

    @UaOptional("InputArguments")
    public Argument[] getInputArguments() {
        return getProperty(InputArguments).orElse(null);
    }

    @UaOptional("OutputArguments")
    public Argument[] getOutputArguments() {
        return getProperty(OutputArguments).orElse(null);
    }

    public void setNodeVersion(String nodeVersion) {
        setProperty(NodeVersion, nodeVersion);
    }

    public void setInputArguments(Argument[] inputArguments) {
        setProperty(InputArguments, inputArguments);
    }

    public void setOutputArguments(Argument[] outputArguments) {
        setProperty(OutputArguments, outputArguments);
    }

    public static final Property<Argument[]> InputArguments = new BasicProperty<>(
            new QualifiedName(0, "InputArguments"),
            Identifiers.Argument,
            ValueRank.OneDimension,
            Argument[].class
    );

    public static final Property<Argument[]> OutputArguments = new BasicProperty<>(
            new QualifiedName(0, "OutputArguments"),
            Identifiers.Argument,
            ValueRank.OneDimension,
            Argument[].class
    );

    public static final Property<String> NodeVersion = new BasicProperty<>(
            new QualifiedName(0, "NodeVersion"),
            Identifiers.String,
            ValueRank.Scalar,
            String.class
    );

    /**
     * @return a new {@link UaMethodNodeBuilder}.
     */
    public static UaMethodNodeBuilder builder(UaNamespace nodeManager) {
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

        private final UaNamespace nodeManager;

        public UaMethodNodeBuilder(UaNamespace nodeManager) {
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
