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

package com.digitalpetri.opcua.sdk.client;

import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

import com.digitalpetri.opcua.sdk.client.api.identity.AnonymousProvider;
import com.digitalpetri.opcua.sdk.client.api.identity.IdentityProvider;
import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.core.Stack;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class OpcUaClientConfig {

    private final UaTcpStackClient stackClient;
    private final Supplier<String> sessionName;
    private final double sessionTimeout;
    private final UInteger maxResponseMessageSize;
    private final double requestTimeout;
    private final IdentityProvider identityProvider;
    private final ExecutorService executorService;

    public OpcUaClientConfig(UaTcpStackClient stackClient,
                             Supplier<String> sessionName,
                             double sessionTimeout,
                             UInteger maxResponseMessageSize,
                             double requestTimeout,
                             IdentityProvider identityProvider,
                             ExecutorService executorService) {

        this.stackClient = stackClient;
        this.sessionName = sessionName;
        this.sessionTimeout = sessionTimeout;
        this.maxResponseMessageSize = maxResponseMessageSize;
        this.requestTimeout = requestTimeout;
        this.identityProvider = identityProvider;
        this.executorService = executorService;
    }

    public UaTcpStackClient getStackClient() {
        return stackClient;
    }

    public Supplier<String> getSessionName() {
        return sessionName;
    }

    public double getSessionTimeout() {
        return sessionTimeout;
    }

    public UInteger getMaxResponseMessageSize() {
        return maxResponseMessageSize;
    }

    public double getRequestTimeout() {
        return requestTimeout;
    }

    public IdentityProvider getIdentityProvider() {
        return identityProvider;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public static OpcUaClientConfigBuilder builder() {
        return new OpcUaClientConfigBuilder();
    }

    public static class OpcUaClientConfigBuilder {

        private UaTcpStackClient stackClient;

        private Supplier<String> sessionName =
                () -> String.format("UaSession:%s:%s",
                        stackClient.getApplication().getApplicationName().getText(),
                        System.currentTimeMillis());

        private double sessionTimeout = 120000;
        private UInteger maxResponseMessageSize = uint(0);
        private double requestTimeout = 60000;
        private ExecutorService executorService = Stack.sharedExecutor();

        private IdentityProvider identityProvider = new AnonymousProvider();

        public OpcUaClientConfigBuilder setStackClient(UaTcpStackClient stackClient) {
            this.stackClient = stackClient;
            return this;
        }

        public OpcUaClientConfigBuilder setSessionName(Supplier<String> sessionName) {
            this.sessionName = sessionName;
            return this;
        }

        public OpcUaClientConfigBuilder setSessionTimeout(double sessionTimeout) {
            this.sessionTimeout = sessionTimeout;
            return this;
        }

        public OpcUaClientConfigBuilder setMaxResponseMessageSize(UInteger maxResponseMessageSize) {
            this.maxResponseMessageSize = maxResponseMessageSize;
            return this;
        }

        public OpcUaClientConfigBuilder setRequestTimeout(double requestTimeout) {
            this.requestTimeout = requestTimeout;
            return this;
        }

        public OpcUaClientConfigBuilder setIdentityProvider(IdentityProvider identityProvider) {
            this.identityProvider = identityProvider;
            return this;
        }

        public OpcUaClientConfigBuilder setExecutorService(ExecutorService executorService) {
            this.executorService = executorService;
            return this;
        }

        public OpcUaClientConfig build() {
            return new OpcUaClientConfig(
                    stackClient,
                    sessionName,
                    sessionTimeout,
                    maxResponseMessageSize,
                    requestTimeout,
                    identityProvider,
                    executorService);
        }

    }

}
