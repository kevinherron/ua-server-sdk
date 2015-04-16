/*
 * Copyright 2014
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

package com.digitalpetri.opcua.sdk.server.model;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.AttributeIds;
import com.digitalpetri.opcua.sdk.core.ValueRank;
import com.digitalpetri.opcua.sdk.core.model.UaOptional;
import com.digitalpetri.opcua.sdk.core.nodes.VariableTypeNode;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.model.Property.BasicProperty;
import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.NodeClass;

public class UaVariableTypeNode extends UaNode implements VariableTypeNode {

    private volatile Optional<DataValue> value;
    private volatile NodeId dataType;
    private volatile int valueRank;
    private volatile Optional<UInteger[]> arrayDimensions;
    private volatile boolean isAbstract;

    public UaVariableTypeNode(UaNamespace namespace,
                              NodeId nodeId,
                              QualifiedName browseName,
                              LocalizedText displayName,
                              Optional<LocalizedText> description,
                              Optional<UInteger> writeMask,
                              Optional<UInteger> userWriteMask,
                              Optional<DataValue> value,
                              NodeId dataType,
                              int valueRank,
                              Optional<UInteger[]> arrayDimensions,
                              boolean isAbstract) {

        super(namespace, nodeId, NodeClass.VariableType, browseName, displayName, description, writeMask, userWriteMask);

        this.value = value;
        this.dataType = dataType;
        this.valueRank = valueRank;
        this.arrayDimensions = arrayDimensions;
        this.isAbstract = isAbstract;
    }

    @Override
    public Optional<DataValue> getValue() {
        return value;
    }

    @Override
    public NodeId getDataType() {
        return dataType;
    }

    @Override
    public Integer getValueRank() {
        return valueRank;
    }

    @Override
    public Optional<UInteger[]> getArrayDimensions() {
        return arrayDimensions;
    }

    @Override
    public Boolean getIsAbstract() {
        return isAbstract;
    }

    @Override
    public synchronized void setValue(Optional<DataValue> value) {
        this.value = value;

        value.ifPresent(v -> fireAttributeChanged(AttributeIds.Value, v));
    }

    @Override
    public synchronized void setDataType(NodeId dataType) {
        this.dataType = dataType;

        fireAttributeChanged(AttributeIds.Value, dataType);
    }

    @Override
    public synchronized void setValueRank(int valueRank) {
        this.valueRank = valueRank;

        fireAttributeChanged(AttributeIds.ValueRank, valueRank);
    }

    @Override
    public synchronized void setArrayDimensions(Optional<UInteger[]> arrayDimensions) {
        this.arrayDimensions = arrayDimensions;

        arrayDimensions.ifPresent(v -> fireAttributeChanged(AttributeIds.ArrayDimensions, v));
    }

    @Override
    public synchronized void setIsAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;

        fireAttributeChanged(AttributeIds.IsAbstract, isAbstract);
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
