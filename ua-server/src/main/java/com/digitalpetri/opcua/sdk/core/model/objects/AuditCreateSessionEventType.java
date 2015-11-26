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
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;

public interface AuditCreateSessionEventType extends AuditSessionEventType {

    Property<String> SECURE_CHANNEL_ID = new Property.BasicProperty<>(
            QualifiedName.parse("0:SecureChannelId"),
            NodeId.parse("ns=0;i=12"),
            -1,
            String.class
    );

    Property<ByteString> CLIENT_CERTIFICATE = new Property.BasicProperty<>(
            QualifiedName.parse("0:ClientCertificate"),
            NodeId.parse("ns=0;i=15"),
            -1,
            ByteString.class
    );

    Property<String> CLIENT_CERTIFICATE_THUMBPRINT = new Property.BasicProperty<>(
            QualifiedName.parse("0:ClientCertificateThumbprint"),
            NodeId.parse("ns=0;i=12"),
            -1,
            String.class
    );

    Property<Double> REVISED_SESSION_TIMEOUT = new Property.BasicProperty<>(
            QualifiedName.parse("0:RevisedSessionTimeout"),
            NodeId.parse("ns=0;i=290"),
            -1,
            Double.class
    );

    String getSecureChannelId();

    PropertyType getSecureChannelIdNode();

    void setSecureChannelId(String value);

    ByteString getClientCertificate();

    PropertyType getClientCertificateNode();

    void setClientCertificate(ByteString value);

    String getClientCertificateThumbprint();

    PropertyType getClientCertificateThumbprintNode();

    void setClientCertificateThumbprint(String value);

    Double getRevisedSessionTimeout();

    PropertyType getRevisedSessionTimeoutNode();

    void setRevisedSessionTimeout(Double value);
}
