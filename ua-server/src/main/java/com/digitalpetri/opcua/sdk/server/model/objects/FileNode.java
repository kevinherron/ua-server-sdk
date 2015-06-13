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
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.util.UaObjectType;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.ULong;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;


@UaObjectType(name = "FileType")
public class FileNode extends BaseObjectNode implements FileType {

    public FileNode(
            UaNamespace namespace,
            NodeId nodeId,
            QualifiedName browseName,
            LocalizedText displayName,
            Optional<LocalizedText> description,
            Optional<UInteger> writeMask,
            Optional<UInteger> userWriteMask,
            UByte eventNotifier) {

        super(namespace, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
    }

    public ULong getSize() {
        Optional<ULong> size = getProperty("Size");

        return size.orElse(null);
    }

    public Boolean getWriteable() {
        Optional<Boolean> writeable = getProperty("Writeable");

        return writeable.orElse(null);
    }

    public Boolean getUserWriteable() {
        Optional<Boolean> userWriteable = getProperty("UserWriteable");

        return userWriteable.orElse(null);
    }

    public UShort getOpenCount() {
        Optional<UShort> openCount = getProperty("OpenCount");

        return openCount.orElse(null);
    }

    public synchronized void setSize(ULong size) {
        getPropertyNode("Size").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(size)));
        });
    }

    public synchronized void setWriteable(Boolean writeable) {
        getPropertyNode("Writeable").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(writeable)));
        });
    }

    public synchronized void setUserWriteable(Boolean userWriteable) {
        getPropertyNode("UserWriteable").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(userWriteable)));
        });
    }

    public synchronized void setOpenCount(UShort openCount) {
        getPropertyNode("OpenCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(openCount)));
        });
    }
}
