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

package com.digitalpetri.opcua.sdk.server.api.config;

import java.util.EnumSet;
import java.util.List;

import com.digitalpetri.opcua.sdk.server.identity.AnonymousIdentityValidator;
import com.digitalpetri.opcua.sdk.server.identity.IdentityValidator;
import com.digitalpetri.opcua.sdk.server.identity.UsernameIdentityValidator;
import com.digitalpetri.opcua.stack.core.application.CertificateManager;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.enumerated.UserTokenType;
import com.digitalpetri.opcua.stack.core.types.structured.ApplicationDescription;
import com.digitalpetri.opcua.stack.core.types.structured.BuildInfo;
import com.digitalpetri.opcua.stack.core.types.structured.UserTokenPolicy;

public interface OpcUaServerConfig {

    /**
     * A {@link UserTokenPolicy} for anonymous access.
     */
    UserTokenPolicy USER_TOKEN_POLICY_ANONYMOUS = new UserTokenPolicy(
            "anonymous",
            UserTokenType.Anonymous,
            null, null, null);

    /**
     * A {@link UserTokenPolicy} for username-based access.
     */
    UserTokenPolicy USER_TOKEN_POLICY_USERNAME = new UserTokenPolicy(
            "username",
            UserTokenType.UserName,
            null, null, null);

    /**
     * @return the {@link CertificateManager} for the server.
     */
    CertificateManager getCertificateManager();

    /**
     * The set of {@link SecurityPolicy}s supported by this server.
     * <p>
     * Any policies other than {@link SecurityPolicy#None} require the server to have a certificate configured.
     *
     * @return the set of {@link SecurityPolicy}s supported by this server.
     */
    EnumSet<SecurityPolicy> getSecurityPolicies();

    /**
     * Get the hostname to use in endpoint URLs.
     * <p>
     * Endpoint URLs will be of the format "opc.tcp://{hostname}:{port}/{serverName}".
     *
     * @return the hostname to use in endpoint URLs.
     */
    String getHostname();

    /**
     * @return the list of addresses to bind to.
     */
    List<String> getBindAddresses();

    /**
     * @return the port to bind to.
     */
    int getBindPort();

    /**
     * The server name to use when building endpoint URLs: "opc.tcp://{hostname}:{port}/{serverName}.".
     * <p>
     * If empty, endpoint URLs will be of the format "opc.tcp://{hostname}:{port}".
     *
     * @return The server name to use when building endpoint URLs.
     */
    String getServerName();

    /**
     * Get the list of {@link UserTokenPolicy}s supported by the server.
     *
     * @return the list of {@link UserTokenPolicy}s supported by the server.
     * @see #USER_TOKEN_POLICY_ANONYMOUS
     * @see #USER_TOKEN_POLICY_USERNAME
     */
    List<UserTokenPolicy> getUserTokenPolicies();

    /**
     * Get the {@link IdentityValidator} for the server.
     *
     * @return the {@link IdentityValidator} for the server.
     * @see AnonymousIdentityValidator
     * @see UsernameIdentityValidator
     */
    IdentityValidator getIdentityValidator();

    /**
     * Get the application name for the server.
     * <p>
     * This will be used in the {@link ApplicationDescription} returned to clients.
     *
     * @return the application name for the server.
     */
    LocalizedText getApplicationName();

    /**
     * Get the application uri for the server.
     * <p>
     * This will be used in the {@link ApplicationDescription} returned to clients.
     * <p>
     * <b>The application uri must match the application uri used on the server's application instance certificate.</b>
     *
     * @return the application uri for the server.
     */
    String getApplicationUri();

    /**
     * Get the product uri for the server.
     * <p>
     * This will be used in the {@link ApplicationDescription} returned to clients.
     *
     * @return the product uri for the server.
     */
    String getProductUri();

    /**
     * @return the server {@link BuildInfo}.
     */
    BuildInfo getBuildInfo();

    /**
     * @return the {@link OpcUaServerConfigLimits}.
     */
    OpcUaServerConfigLimits getLimits();

    /**
     * @return a {@link OpcUaServerConfigBuilder}.
     */
    static OpcUaServerConfigBuilder builder() {
        return new OpcUaServerConfigBuilder();
    }

}
