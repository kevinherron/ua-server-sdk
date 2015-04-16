/*
 * Copyright 2015
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.digitalpetri.opcua.sdk.client;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

import com.digitalpetri.opcua.sdk.client.api.UaClient;
import com.digitalpetri.opcua.sdk.client.api.UaClient.IdentityTokenProvider;
import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.core.Stack;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.UserTokenType;
import com.digitalpetri.opcua.stack.core.types.structured.AnonymousIdentityToken;
import com.digitalpetri.opcua.stack.core.types.structured.SignatureData;
import com.digitalpetri.opcua.stack.core.types.structured.UserTokenPolicy;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class OpcUaClientConfig {

    private final UaTcpStackClient stackClient;
    private final Supplier<String> sessionName;
    private final double sessionTimeout;
    private final UInteger maxResponseMessageSize;
    private final double requestTimeout;
    private final IdentityTokenProvider identityTokenProvider;
    private final ExecutorService executorService;

    public OpcUaClientConfig(UaTcpStackClient stackClient,
                             Supplier<String> sessionName,
                             double sessionTimeout,
                             UInteger maxResponseMessageSize,
                             double requestTimeout,
                             IdentityTokenProvider identityTokenProvider,
                             ExecutorService executorService) {

        this.stackClient = stackClient;
        this.sessionName = sessionName;
        this.sessionTimeout = sessionTimeout;
        this.maxResponseMessageSize = maxResponseMessageSize;
        this.requestTimeout = requestTimeout;
        this.identityTokenProvider = identityTokenProvider;
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

    public UaClient.IdentityTokenProvider getIdentityTokenProvider() {
        return identityTokenProvider;
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
                () -> "UaSession:" + System.currentTimeMillis();
        private double sessionTimeout = 60000;
        private UInteger maxResponseMessageSize = uint(0);
        private double requestTimeout = 60000;
        private ExecutorService executorService = Stack.sharedExecutor();

        private UaClient.IdentityTokenProvider identityTokenProvider = e -> {
            String policyId = Arrays.stream(e.getUserIdentityTokens())
                    .filter(t -> t.getTokenType() == UserTokenType.Anonymous)
                    .findFirst()
                    .map(UserTokenPolicy::getPolicyId)
                    .orElseThrow(() -> new Exception("no anonymous token policy found"));

            return new Object[] {
                    new AnonymousIdentityToken(policyId),
                    new SignatureData()
            };
        };

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

        public OpcUaClientConfigBuilder setIdentityTokenProvider(IdentityTokenProvider identityTokenProvider) {
            this.identityTokenProvider = identityTokenProvider;
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
                    identityTokenProvider,
                    executorService);
        }

    }

}
