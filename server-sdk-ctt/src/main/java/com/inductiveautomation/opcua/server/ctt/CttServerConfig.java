/*
 * OPC-UA SDK
 *
 * Copyright (C) 2014 Kevin Herron
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

package com.inductiveautomation.opcua.server.ctt;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

import com.inductiveautomation.opcua.sdk.server.api.OpcUaServerConfig;
import com.inductiveautomation.opcua.sdk.server.objects.OperationLimits;
import com.inductiveautomation.opcua.sdk.server.objects.ServerCapabilities;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.structured.SignedSoftwareCertificate;
import org.slf4j.LoggerFactory;

public class CttServerConfig implements OpcUaServerConfig {

    private static final String SERVER_ALIAS = "server-test-certificate";
    private static final char[] PASSWORD = "test".toCharArray();

    private volatile Certificate certificate;
    private volatile KeyPair keyPair;

    public CttServerConfig() {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");

            keyStore.load(getClass().getClassLoader().getResourceAsStream("keystore.pfx"), PASSWORD);

            Key serverPrivateKey = keyStore.getKey(SERVER_ALIAS, PASSWORD);

            if (serverPrivateKey instanceof PrivateKey) {
                certificate = keyStore.getCertificate(SERVER_ALIAS);
                PublicKey serverPublicKey = certificate.getPublicKey();
                keyPair = new KeyPair(serverPublicKey, (PrivateKey) serverPrivateKey);
            }
        } catch (Exception e) {
            LoggerFactory.getLogger(getClass()).error("Error loading server certificate: {}.", e.getMessage(), e);

            certificate = null;
            keyPair = null;
        }
    }

    @Override
    public LocalizedText getApplicationName() {
        return LocalizedText.english("CTT OPC-UA Server");
    }

    @Override
    public String getServerName() {
        return "ctt-server";
    }

    @Override
    public KeyPair getKeyPair() {
        return keyPair;
    }

    @Override
    public Certificate getCertificate() {
        return certificate;
    }

    @Override
    public ServerCapabilities getServerCapabilities() {
        return new ServerCapabilities() {
            @Override
            public double getMinSupportedSampleRate() {
                return 0;
            }

            @Override
            public String[] getServerProfileArray() {
                return new String[0];
            }

            @Override
            public SignedSoftwareCertificate[] getSoftwareCertificates() {
                return new SignedSoftwareCertificate[0];
            }

            @Override
            public OperationLimits getOperationLimits() {
                return new OperationLimits() {};
            }
        };
    }
}
