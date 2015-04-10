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

package com.inductiveautomation.opcua.sdk.examples;

import java.util.Arrays;
import java.util.UUID;

import com.inductiveautomation.opcua.sdk.client.OpcUaClient;
import com.inductiveautomation.opcua.sdk.client.OpcUaClientConfig;
import com.inductiveautomation.opcua.stack.client.UaTcpClient;
import com.inductiveautomation.opcua.stack.client.UaTcpClientBuilder;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.enumerated.MessageSecurityMode;
import com.inductiveautomation.opcua.stack.core.types.structured.EndpointDescription;

public class OpcUaClientExample {

    public static void main(String[] args) throws Exception {
        EndpointDescription[] endpoints = UaTcpClient.getEndpoints("opc.tcp://localhost:4096").get();

        EndpointDescription endpoint = Arrays.stream(endpoints)
                .filter(e -> e.getSecurityMode() == MessageSecurityMode.None)
                .findFirst()
                .orElseThrow(() -> new Exception("no desired endpoints returned"));

        System.out.println("Connecting to endpoint: " + endpoint.getEndpointUrl() + " [" + endpoint.getSecurityPolicyUri() + "]");

        UaTcpClient stackClient = new UaTcpClientBuilder()
                .setApplicationName(LocalizedText.english("Stack Example Client"))
                .setApplicationUri(String.format("urn:example-client:%s", UUID.randomUUID()))
//                .setCertificate(certificate)
//                .setKeyPair(keyPair)
                .build(endpoint);

        OpcUaClientConfig config = OpcUaClientConfig.builder()
                .setStackClient(stackClient)
                .build();

        OpcUaClient client = new OpcUaClient(config);

        client.connect().get();


        Thread.sleep(999999999);
    }

}
