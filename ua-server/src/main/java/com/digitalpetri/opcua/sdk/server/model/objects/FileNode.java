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

package com.digitalpetri.opcua.sdk.server.model.objects;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.model.objects.FileType;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.sdk.server.model.variables.PropertyNode;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.ULong;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;

@com.digitalpetri.opcua.sdk.server.util.UaObjectNode(typeName = "0:FileType")
public class FileNode extends BaseObjectNode implements FileType {

    public FileNode(
            UaNodeManager nodeManager,
            NodeId nodeId,
            QualifiedName browseName,
            LocalizedText displayName,
            Optional<LocalizedText> description,
            Optional<UInteger> writeMask,
            Optional<UInteger> userWriteMask,
            UByte eventNotifier) {

        super(nodeManager, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
    }

    @Override
    public ULong getSize() {
        Optional<ULong> property = getProperty(FileType.SIZE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getSizeNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(FileType.SIZE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setSize(ULong value) {
        setProperty(FileType.SIZE, value);
    }

    @Override
    public Boolean getWritable() {
        Optional<Boolean> property = getProperty(FileType.WRITABLE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getWritableNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(FileType.WRITABLE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setWritable(Boolean value) {
        setProperty(FileType.WRITABLE, value);
    }

    @Override
    public Boolean getUserWritable() {
        Optional<Boolean> property = getProperty(FileType.USER_WRITABLE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getUserWritableNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(FileType.USER_WRITABLE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setUserWritable(Boolean value) {
        setProperty(FileType.USER_WRITABLE, value);
    }

    @Override
    public UShort getOpenCount() {
        Optional<UShort> property = getProperty(FileType.OPEN_COUNT);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getOpenCountNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(FileType.OPEN_COUNT.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setOpenCount(UShort value) {
        setProperty(FileType.OPEN_COUNT, value);
    }

    @Override
    public String getMimeType() {
        Optional<String> property = getProperty(FileType.MIME_TYPE);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getMimeTypeNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(FileType.MIME_TYPE.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setMimeType(String value) {
        setProperty(FileType.MIME_TYPE, value);
    }

}
