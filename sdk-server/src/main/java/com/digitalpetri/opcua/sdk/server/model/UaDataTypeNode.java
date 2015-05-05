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
import java.util.concurrent.atomic.AtomicBoolean;

import com.digitalpetri.opcua.sdk.core.AttributeIds;
import com.digitalpetri.opcua.sdk.core.ValueRank;
import com.digitalpetri.opcua.sdk.core.model.UaOptional;
import com.digitalpetri.opcua.sdk.core.nodes.DataTypeNode;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.model.Property.BasicProperty;
import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.NodeClass;
import com.digitalpetri.opcua.stack.core.types.structured.EnumValueType;

public class UaDataTypeNode extends UaNode implements DataTypeNode {

    private final AtomicBoolean isAbstract;

    public UaDataTypeNode(UaNamespace namespace,
                          NodeId nodeId,
                          QualifiedName browseName,
                          LocalizedText displayName,
                          Optional<LocalizedText> description,
                          Optional<UInteger> writeMask,
                          Optional<UInteger> userWriteMask,
                          boolean isAbstract) {

        super(namespace, nodeId, NodeClass.DataType, browseName, displayName, description, writeMask, userWriteMask);

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

    @UaOptional("NodeVersion")
    public String getNodeVersion() {
        return getProperty(NodeVersion).orElse(null);
    }

    @UaOptional("EnumStrings")
    public LocalizedText[] getEnumStrings() {
        return getProperty(EnumStrings).orElse(null);
    }

    @UaOptional("EnumValues")
    public EnumValueType[] getEnumValues() {
        return getProperty(EnumValues).orElse(null);
    }

    public void setNodeVersion(String nodeVersion) {
        setProperty(NodeVersion, nodeVersion);
    }

    public void setEnumStrings(LocalizedText[] enumStrings) {
        setProperty(EnumStrings, enumStrings);
    }

    public void setEnumValues(EnumValueType[] enumValues) {
        setProperty(EnumValues, enumValues);
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
