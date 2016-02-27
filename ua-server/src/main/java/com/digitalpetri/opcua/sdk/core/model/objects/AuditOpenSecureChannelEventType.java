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
import com.digitalpetri.opcua.stack.core.types.enumerated.MessageSecurityMode;
import com.digitalpetri.opcua.stack.core.types.enumerated.SecurityTokenRequestType;

public interface AuditOpenSecureChannelEventType extends AuditChannelEventType {

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

    Property<SecurityTokenRequestType> REQUEST_TYPE = new Property.BasicProperty<>(
            QualifiedName.parse("0:RequestType"),
            NodeId.parse("ns=0;i=315"),
            -1,
            SecurityTokenRequestType.class
    );

    Property<String> SECURITY_POLICY_URI = new Property.BasicProperty<>(
            QualifiedName.parse("0:SecurityPolicyUri"),
            NodeId.parse("ns=0;i=12"),
            -1,
            String.class
    );

    Property<MessageSecurityMode> SECURITY_MODE = new Property.BasicProperty<>(
            QualifiedName.parse("0:SecurityMode"),
            NodeId.parse("ns=0;i=302"),
            -1,
            MessageSecurityMode.class
    );

    Property<Double> REQUESTED_LIFETIME = new Property.BasicProperty<>(
            QualifiedName.parse("0:RequestedLifetime"),
            NodeId.parse("ns=0;i=290"),
            -1,
            Double.class
    );

    ByteString getClientCertificate();

    PropertyType getClientCertificateNode();

    void setClientCertificate(ByteString value);

    String getClientCertificateThumbprint();

    PropertyType getClientCertificateThumbprintNode();

    void setClientCertificateThumbprint(String value);

    SecurityTokenRequestType getRequestType();

    PropertyType getRequestTypeNode();

    void setRequestType(SecurityTokenRequestType value);

    String getSecurityPolicyUri();

    PropertyType getSecurityPolicyUriNode();

    void setSecurityPolicyUri(String value);

    MessageSecurityMode getSecurityMode();

    PropertyType getSecurityModeNode();

    void setSecurityMode(MessageSecurityMode value);

    Double getRequestedLifetime();

    PropertyType getRequestedLifetimeNode();

    void setRequestedLifetime(Double value);
}
