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

import com.digitalpetri.opcua.stack.core.types.structured.SignedSoftwareCertificate;
import com.digitalpetri.opcua.stack.core.types.structured.UserIdentityToken;

public interface AuditActivateSessionEventType extends AuditSessionEventType {

    SignedSoftwareCertificate[] getClientSoftwareCertificates();

    UserIdentityToken getUserIdentityToken();

    String getSecureChannelId();

    void setClientSoftwareCertificates(SignedSoftwareCertificate[] clientSoftwareCertificates);

    void setUserIdentityToken(UserIdentityToken userIdentityToken);

    void setSecureChannelId(String secureChannelId);

}
