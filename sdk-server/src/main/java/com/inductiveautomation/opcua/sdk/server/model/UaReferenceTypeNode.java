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

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.core.ValueRank;
import com.inductiveautomation.opcua.sdk.core.model.UaOptional;
import com.inductiveautomation.opcua.sdk.core.nodes.ReferenceTypeNode;
import com.inductiveautomation.opcua.sdk.server.api.UaNamespace;
import com.inductiveautomation.opcua.sdk.server.model.Property.BasicProperty;
import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;

public class UaReferenceTypeNode extends UaNode implements ReferenceTypeNode {

    private volatile boolean isAbstract;
    private volatile boolean symmetric;
    private volatile Optional<LocalizedText> inverseName;

    public UaReferenceTypeNode(UaNamespace namespace,
                               NodeId nodeId,
                               QualifiedName browseName,
                               LocalizedText displayName,
                               Optional<LocalizedText> description,
                               Optional<UInteger> writeMask,
                               Optional<UInteger> userWriteMask,
                               boolean isAbstract,
                               boolean symmetric,
                               Optional<LocalizedText> inverseName) {

        super(namespace, nodeId, NodeClass.ReferenceType, browseName, displayName, description, writeMask, userWriteMask);

        this.isAbstract = isAbstract;
        this.symmetric = symmetric;
        this.inverseName = inverseName;
    }


    @Override
    public Boolean getIsAbstract() {
        return isAbstract;
    }

    @Override
    public Boolean getSymmetric() {
        return symmetric;
    }

    @Override
    public Optional<LocalizedText> getInverseName() {
        return inverseName;
    }

    @Override
    public synchronized void setIsAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;

        fireAttributeChanged(AttributeIds.IsAbstract, isAbstract);
    }

    @Override
    public synchronized void setSymmetric(boolean symmetric) {
        this.symmetric = symmetric;

        fireAttributeChanged(AttributeIds.Symmetric, symmetric);
    }

    @Override
    public synchronized void setInverseName(Optional<LocalizedText> inverseName) {
        this.inverseName = inverseName;

        inverseName.ifPresent(v -> fireAttributeChanged(AttributeIds.InverseName, v));
    }

    @UaOptional("NodeVersion")
    public String getNodeVersion() {
        return getProperty(NodeVersion).orElse(null);
    }

    public void setNodeVersion(String nodeVersion) {
        setProperty(NodeVersion, nodeVersion);
    }

    public static final Property<String> NodeVersion = new BasicProperty<>(
            new QualifiedName(0, "NodeVersion"),
            Identifiers.String,
            ValueRank.Scalar,
            String.class
    );

}
