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

public class OpcUaClientConfig {

    private final UaTcpClient stackClient;

    public OpcUaClientConfig(UaTcpClient stackClient) {
        this.stackClient = stackClient;
    }

    public UaTcpClient getStackClient() {
        return stackClient;
    }

    public static OpcUaClientConfigBuilder builder() {
        return new OpcUaClientConfigBuilder();
    }

    public static class OpcUaClientConfigBuilder {

        private UaTcpClient stackClient;

        public OpcUaClientConfigBuilder setStackClient(UaTcpClient stackClient) {
            this.stackClient = stackClient;
            return this;
        }

        public OpcUaClientConfig build() {
            return new OpcUaClientConfig(stackClient);
        }

    }

}
