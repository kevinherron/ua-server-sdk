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

import com.digitalpetri.opcua.sdk.core.model.objects.AddressSpaceFileType;
import com.digitalpetri.opcua.sdk.core.model.objects.NamespaceMetadataType;
import com.digitalpetri.opcua.sdk.core.nodes.ObjectNode;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.util.UaObjectType;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.IdType;


@UaObjectType(name = "NamespaceMetadataType")
public class NamespaceMetadataNode extends BaseObjectNode implements NamespaceMetadataType {

    public NamespaceMetadataNode(
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

    public String getNamespaceUri() {
        Optional<String> namespaceUri = getProperty("NamespaceUri");

        return namespaceUri.orElse(null);
    }

    public String getNamespaceVersion() {
        Optional<String> namespaceVersion = getProperty("NamespaceVersion");

        return namespaceVersion.orElse(null);
    }

    public DateTime getNamespacePublicationDate() {
        Optional<DateTime> namespacePublicationDate = getProperty("NamespacePublicationDate");

        return namespacePublicationDate.orElse(null);
    }

    public Boolean getIsNamespaceSubset() {
        Optional<Boolean> isNamespaceSubset = getProperty("IsNamespaceSubset");

        return isNamespaceSubset.orElse(null);
    }

    public IdType[] getStaticNodeIdIdentifierTypes() {
        Optional<Integer[]> staticNodeIdIdentifierTypes = getProperty("StaticNodeIdIdentifierTypes");

        return staticNodeIdIdentifierTypes.map(values -> {
            IdType[] staticNodeIdentifierTypes = new IdType[values.length];
            for (int i = 0; i < values.length; i++) {
                staticNodeIdentifierTypes[i] = IdType.from(values[i]);
            }
            return staticNodeIdentifierTypes;
        }).orElse(null);
    }

    public String[] getStaticNumericNodeIdRange() {
        Optional<String[]> staticNumericNodeIdRange = getProperty("StaticNumericNodeIdRange");

        return staticNumericNodeIdRange.orElse(null);
    }

    public String[] getStaticStringNodeIdPattern() {
        Optional<String[]> staticStringNodeIdPattern = getProperty("StaticStringNodeIdPattern");

        return staticStringNodeIdPattern.orElse(null);
    }

    public AddressSpaceFileType getNamespaceFile() {
        Optional<ObjectNode> namespaceFile = getObjectComponent("NamespaceFile");

        return namespaceFile.map(node -> (AddressSpaceFileType) node).orElse(null);
    }

    public synchronized void setNamespaceUri(String namespaceUri) {
        getPropertyNode("NamespaceUri").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(namespaceUri)));
        });
    }

    public synchronized void setNamespaceVersion(String namespaceVersion) {
        getPropertyNode("NamespaceVersion").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(namespaceVersion)));
        });
    }

    public synchronized void setNamespacePublicationDate(DateTime namespacePublicationDate) {
        getPropertyNode("NamespacePublicationDate").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(namespacePublicationDate)));
        });
    }

    public synchronized void setIsNamespaceSubset(Boolean isNamespaceSubset) {
        getPropertyNode("IsNamespaceSubset").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(isNamespaceSubset)));
        });
    }

    public synchronized void setStaticNodeIdIdentifierTypes(IdType[] staticNodeIdIdentifierTypes) {
        getPropertyNode("StaticNodeIdIdentifierTypes").ifPresent(n -> {
            if (staticNodeIdIdentifierTypes != null) {
                Integer[] values = new Integer[staticNodeIdIdentifierTypes.length];
                for (int i = 0; i < values.length; i++) {
                    values[i] = staticNodeIdIdentifierTypes[i].getValue();
                }
                n.setValue(new DataValue(new Variant(values)));
            } else {
                n.setValue(new DataValue(new Variant(new Integer[0])));
            }
        });
    }

    public synchronized void setStaticNumericNodeIdRange(String[] staticNumericNodeIdRange) {
        getPropertyNode("StaticNumericNodeIdRange").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(staticNumericNodeIdRange)));
        });
    }

    public synchronized void setStaticStringNodeIdPattern(String[] staticStringNodeIdPattern) {
        getPropertyNode("StaticStringNodeIdPattern").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(staticStringNodeIdPattern)));
        });
    }
}
