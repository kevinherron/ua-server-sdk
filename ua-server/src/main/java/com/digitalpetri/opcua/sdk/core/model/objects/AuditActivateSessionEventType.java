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
import com.digitalpetri.opcua.stack.core.types.structured.SignedSoftwareCertificate;
import com.digitalpetri.opcua.stack.core.types.structured.UserIdentityToken;

public interface AuditActivateSessionEventType extends AuditSessionEventType {

    Property<SignedSoftwareCertificate[]> CLIENT_SOFTWARE_CERTIFICATES = new Property.BasicProperty<>(
            QualifiedName.parse("0:ClientSoftwareCertificates"),
            NodeId.parse("ns=0;i=344"),
            1,
            SignedSoftwareCertificate[].class
    );

    Property<UserIdentityToken> USER_IDENTITY_TOKEN = new Property.BasicProperty<>(
            QualifiedName.parse("0:UserIdentityToken"),
            NodeId.parse("ns=0;i=316"),
            -1,
            UserIdentityToken.class
    );

    Property<String> SECURE_CHANNEL_ID = new Property.BasicProperty<>(
            QualifiedName.parse("0:SecureChannelId"),
            NodeId.parse("ns=0;i=12"),
            -1,
            String.class
    );

    SignedSoftwareCertificate[] getClientSoftwareCertificates();

    PropertyType getClientSoftwareCertificatesNode();

    void setClientSoftwareCertificates(SignedSoftwareCertificate[] value);

    UserIdentityToken getUserIdentityToken();

    PropertyType getUserIdentityTokenNode();

    void setUserIdentityToken(UserIdentityToken value);

    String getSecureChannelId();

    PropertyType getSecureChannelIdNode();

    void setSecureChannelId(String value);
}
