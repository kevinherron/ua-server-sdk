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

package com.inductiveautomation.opcua.sdk.client;

import com.inductiveautomation.opcua.stack.client.UaTcpClient;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

import java.util.function.Supplier;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class OpcUaClientConfig {

    private final UaTcpClient stackClient;
    private final Supplier<String> sessionName;
    private final double sessionTimeout;
    private final UInteger maxResponseMessageSize;
    private final double requestTimeout;

    public OpcUaClientConfig(UaTcpClient stackClient,
                             Supplier<String> sessionName,
                             double sessionTimeout,
                             UInteger maxResponseMessageSize,
                             double requestTimeout) {

        this.stackClient = stackClient;
        this.sessionName = sessionName;
        this.sessionTimeout = sessionTimeout;
        this.maxResponseMessageSize = maxResponseMessageSize;
        this.requestTimeout = requestTimeout;
    }

    public UaTcpClient getStackClient() {
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

    public static OpcUaClientConfigBuilder builder() {
        return new OpcUaClientConfigBuilder();
    }

    public static class OpcUaClientConfigBuilder {

        private UaTcpClient stackClient;
        private Supplier<String> sessionName =
                () -> "UaSession:" + System.currentTimeMillis();
        private double sessionTimeout = 60000;
        private UInteger maxResponseMessageSize = uint(0);
        private double requestTimeout = 60000;

        public OpcUaClientConfigBuilder setStackClient(UaTcpClient stackClient) {
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

        public OpcUaClientConfig build() {
            return new OpcUaClientConfig(stackClient, sessionName, sessionTimeout, maxResponseMessageSize, requestTimeout);
        }

    }

}
