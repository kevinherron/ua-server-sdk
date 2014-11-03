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
import java.util.concurrent.atomic.AtomicBoolean;

import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.core.ValueRank;
import com.inductiveautomation.opcua.sdk.core.nodes.DataTypeNode;
import com.inductiveautomation.opcua.sdk.server.api.UaNamespace;
import com.inductiveautomation.opcua.sdk.server.model.Property.BasicProperty;
import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;
import com.inductiveautomation.opcua.stack.core.types.structured.EnumValueType;

public class UaDataTypeNode extends UaNode implements DataTypeNode {

    private final AtomicBoolean isAbstract;

    public UaDataTypeNode(UaNamespace nodeManager,
                          NodeId nodeId,
                          QualifiedName browseName,
                          LocalizedText displayName,
                          Optional<LocalizedText> description,
                          Optional<UInteger> writeMask,
                          Optional<UInteger> userWriteMask,
                          boolean isAbstract) {

        super(nodeManager, nodeId, NodeClass.DataType, browseName, displayName, description, writeMask, userWriteMask);

        this.isAbstract = new AtomicBoolean(isAbstract);
    }

    @Override
    public Boolean getIsAbstract() {
        return isAbstract.get();
    }

    @Override
    public synchronized void setIsAbstract(boolean isAbstract) {
        this.isAbstract.set(isAbstract);

        fireAttributeChanged(AttributeIds.IsAbstract, isAbstract);
    }

    public static final Property<String> NodeVersion = new BasicProperty<>(
            new QualifiedName(0, "NodeVersion"),
            Identifiers.String,
            ValueRank.Scalar,
            String.class
    );

    public static final Property<LocalizedText[]> EnumStrings = new BasicProperty<>(
            new QualifiedName(0, "EnumStrings"),
            Identifiers.LocalizedText,
            ValueRank.OneDimension,
            LocalizedText[].class
    );

    public static final Property<EnumValueType[]> EnumValues = new BasicProperty<>(
            new QualifiedName(0, "EnumValues"),
            Identifiers.EnumValueType,
            ValueRank.OneDimension,
            EnumValueType[].class
    );

}
