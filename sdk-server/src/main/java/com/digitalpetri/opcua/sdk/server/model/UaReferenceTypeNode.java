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

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.AttributeIds;
import com.digitalpetri.opcua.sdk.core.ValueRank;
import com.digitalpetri.opcua.sdk.core.model.UaOptional;
import com.digitalpetri.opcua.sdk.core.nodes.ReferenceTypeNode;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.model.Property.BasicProperty;
import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.NodeClass;

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
