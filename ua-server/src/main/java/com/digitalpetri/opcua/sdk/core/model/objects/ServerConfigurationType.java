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

package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.sdk.core.model.variables.PropertyType;
import com.digitalpetri.opcua.sdk.server.model.Property;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface ServerConfigurationType extends BaseObjectType {

    Property<String[]> SERVER_CAPABILITIES = new Property.BasicProperty<>(
            QualifiedName.parse("0:ServerCapabilities"),
            NodeId.parse("ns=0;i=12"),
            1,
            String[].class
    );

    Property<String[]> SUPPORTED_PRIVATE_KEY_FORMATS = new Property.BasicProperty<>(
            QualifiedName.parse("0:SupportedPrivateKeyFormats"),
            NodeId.parse("ns=0;i=12"),
            1,
            String[].class
    );

    Property<UInteger> MAX_TRUST_LIST_SIZE = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxTrustListSize"),
            NodeId.parse("ns=0;i=7"),
            -1,
            UInteger.class
    );

    Property<Boolean> MULTICAST_DNS_ENABLED = new Property.BasicProperty<>(
            QualifiedName.parse("0:MulticastDnsEnabled"),
            NodeId.parse("ns=0;i=1"),
            -1,
            Boolean.class
    );

    String[] getServerCapabilities();

    PropertyType getServerCapabilitiesNode();

    void setServerCapabilities(String[] value);

    String[] getSupportedPrivateKeyFormats();

    PropertyType getSupportedPrivateKeyFormatsNode();

    void setSupportedPrivateKeyFormats(String[] value);

    UInteger getMaxTrustListSize();

    PropertyType getMaxTrustListSizeNode();

    void setMaxTrustListSize(UInteger value);

    Boolean getMulticastDnsEnabled();

    PropertyType getMulticastDnsEnabledNode();

    void setMulticastDnsEnabled(Boolean value);

    CertificateGroupFolderType getCertificateGroupsNode();

}
