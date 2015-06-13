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

import com.digitalpetri.opcua.sdk.core.model.objects.ServerConfigurationType;
import com.digitalpetri.opcua.sdk.core.model.objects.TrustListType;
import com.digitalpetri.opcua.sdk.core.nodes.ObjectNode;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.util.UaObjectType;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;


@UaObjectType(name = "ServerConfigurationType")
public class ServerConfigurationNode extends BaseObjectNode implements ServerConfigurationType {

    public ServerConfigurationNode(
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

    public String[] getSupportedCertificateFormats() {
        Optional<String[]> supportedCertificateFormats = getProperty("SupportedCertificateFormats");

        return supportedCertificateFormats.orElse(null);
    }

    public String[] getSupportedPrivateKeyFormats() {
        Optional<String[]> supportedPrivateKeyFormats = getProperty("SupportedPrivateKeyFormats");

        return supportedPrivateKeyFormats.orElse(null);
    }

    public UInteger getMaxTrustListSize() {
        Optional<UInteger> maxTrustListSize = getProperty("MaxTrustListSize");

        return maxTrustListSize.orElse(null);
    }

    public Boolean getNoSupportForLocalCRLs() {
        Optional<Boolean> noSupportForLocalCRLs = getProperty("NoSupportForLocalCRLs");

        return noSupportForLocalCRLs.orElse(null);
    }

    public Boolean getMulticastDnsEnabled() {
        Optional<Boolean> multicastDnsEnabled = getProperty("MulticastDnsEnabled");

        return multicastDnsEnabled.orElse(null);
    }

    public TrustListType getTrustList() {
        Optional<ObjectNode> trustList = getObjectComponent("TrustList");

        return trustList.map(node -> (TrustListType) node).orElse(null);
    }

    public TrustListType getHttpsTrustList() {
        Optional<ObjectNode> httpsTrustList = getObjectComponent("HttpsTrustList");

        return httpsTrustList.map(node -> (TrustListType) node).orElse(null);
    }

    public synchronized void setSupportedCertificateFormats(String[] supportedCertificateFormats) {
        getPropertyNode("SupportedCertificateFormats").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(supportedCertificateFormats)));
        });
    }

    public synchronized void setSupportedPrivateKeyFormats(String[] supportedPrivateKeyFormats) {
        getPropertyNode("SupportedPrivateKeyFormats").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(supportedPrivateKeyFormats)));
        });
    }

    public synchronized void setMaxTrustListSize(UInteger maxTrustListSize) {
        getPropertyNode("MaxTrustListSize").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxTrustListSize)));
        });
    }

    public synchronized void setNoSupportForLocalCRLs(Boolean noSupportForLocalCRLs) {
        getPropertyNode("NoSupportForLocalCRLs").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(noSupportForLocalCRLs)));
        });
    }

    public synchronized void setMulticastDnsEnabled(Boolean multicastDnsEnabled) {
        getPropertyNode("MulticastDnsEnabled").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(multicastDnsEnabled)));
        });
    }
}
