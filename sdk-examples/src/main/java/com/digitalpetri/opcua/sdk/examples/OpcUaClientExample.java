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

package com.digitalpetri.opcua.sdk.examples;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.codahale.metrics.Clock;
import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SlidingWindowReservoir;
import com.codahale.metrics.Timer;
import com.codahale.metrics.Timer.Context;
import com.digitalpetri.opcua.sdk.client.OpcUaClient;
import com.digitalpetri.opcua.sdk.client.OpcUaClientConfig;
import com.digitalpetri.opcua.sdk.core.ReferenceType;
import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.client.UaTcpStackClientConfig;
import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.enumerated.BrowseDirection;
import com.digitalpetri.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseDescription;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseResult;
import com.digitalpetri.opcua.stack.core.types.structured.EndpointDescription;
import com.digitalpetri.opcua.stack.core.types.structured.ReadRequest;
import com.digitalpetri.opcua.stack.core.types.structured.ReadValueId;
import com.digitalpetri.opcua.stack.core.types.structured.ReferenceDescription;
import com.google.common.collect.Lists;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class OpcUaClientExample {

    private static final MetricRegistry METRIC_REGISTRY = new MetricRegistry();

    private static final ConsoleReporter REPORTER;

    static {
        REPORTER = ConsoleReporter.forRegistry(METRIC_REGISTRY)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();

        REPORTER.start(10, TimeUnit.SECONDS);
    }

    private static final int N_CONCURRENT_REQUESTS = 16;
    private static final int N_DESIRED_REQUEST_COUNT = 1000000;
    private static final int N_NODES_PER_REQUEST = 100;

    private static Timer REQUEST_TIMER;

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("usage: java -jar app.jar <endpoint url> <client count>");
            System.exit(-1);
        }

        REQUEST_TIMER = new Timer(new SlidingWindowReservoir(N_DESIRED_REQUEST_COUNT), Clock.defaultClock());
        METRIC_REGISTRY.register("request-timer", REQUEST_TIMER);

        String endpointUrl = args[0];
        int clientCount = Integer.parseInt(args[1]);

        List<OpcUaClient> clients = Lists.newArrayList();
        List<CompletableFuture<OpcUaClient>> futures = Lists.newArrayList();

        for (int i = 0; i < clientCount; i++) {
            clients.add(getOpcUaClient(endpointUrl));
            futures.add(new CompletableFuture<>());
        }

        for (int i = 0; i < clients.size(); i++) {
            read(clients.get(i), new AtomicLong(0), futures.get(i));
        }

        CompletableFuture<Void> allFinished =
                CompletableFuture.allOf(futures.toArray(new CompletableFuture<?>[futures.size()]));

        allFinished.thenRun(() -> {
            clients.forEach(OpcUaClient::disconnect);

            REPORTER.report();

            System.exit(0);
        });

        Thread.sleep(999999999);
    }

    private static void read(OpcUaClient client, AtomicLong count, CompletableFuture<OpcUaClient> future) {
        for (int i = 0; i < N_CONCURRENT_REQUESTS; i++) {
            sendReadRequest(client, count, future);
        }
    }

    private static void sendReadRequest(OpcUaClient client, AtomicLong count, CompletableFuture<OpcUaClient> future) {
        ReadValueId[] nodesToRead = new ReadValueId[N_NODES_PER_REQUEST];

        for (int i = 0; i < N_NODES_PER_REQUEST; i++) {
            nodesToRead[i] = new ReadValueId(
                    Identifiers.Server_ServerStatus_CurrentTime,
                    uint(13), null, QualifiedName.NULL_VALUE);
        }

        client.getSession().thenAccept(session -> {
            ReadRequest request = new ReadRequest(
                    client.newRequestHeader(session.getAuthToken()),
                    0.0, TimestampsToReturn.Both, nodesToRead);

            Context context = REQUEST_TIMER.time();

            client.sendRequest(request).whenComplete((r, ex) -> {
                if (ex != null) ex.printStackTrace();

                context.stop();

                if (count.incrementAndGet() < N_DESIRED_REQUEST_COUNT) {
                    sendReadRequest(client, count, future);
                } else {
                    future.complete(client);
                }
            });
        });
    }

    private static OpcUaClient getOpcUaClient(String endpointUrl) throws Exception {
        EndpointDescription[] endpoints = UaTcpStackClient.getEndpoints(endpointUrl).get();

        EndpointDescription endpoint = Arrays.stream(endpoints)
                .filter(e -> e.getSecurityPolicyUri().equals(SecurityPolicy.None.getSecurityPolicyUri()))
                .findFirst().orElseThrow(() -> new Exception("no desired endpoints returned"));

        System.out.println("Connecting to endpoint: " + endpoint.getEndpointUrl() + " [" + endpoint.getSecurityPolicyUri() + "]");

        KeyStoreLoader loader = new KeyStoreLoader().load();

        UaTcpStackClientConfig stackConfig = UaTcpStackClientConfig.builder()
                .setApplicationName(LocalizedText.english("SDK Example Client"))
                .setApplicationUri("urn:digitalpetri:sdk-example-client")
                .setCertificate(loader.getClientCertificate())
                .setKeyPair(loader.getClientKeyPair())
                .setEndpoint(endpoint)
                .build();

        UaTcpStackClient stackClient = new UaTcpStackClient(stackConfig);

        OpcUaClientConfig configConfig = OpcUaClientConfig.builder()
                .setStackClient(stackClient)
                .setRequestTimeout(120000)
                .build();

        OpcUaClient client = new OpcUaClient(configConfig);

        client.connect().get();

        return client;
    }

    private static class BrowseAction extends RecursiveAction {

        private final OpcUaClient client;
        private final Set<NodeId> browsedNodes;
        private final NodeId nodeToBrowse;

        public BrowseAction(OpcUaClient client, Set<NodeId> browsedNodes, NodeId nodeToBrowse) {
            this.client = client;
            this.browsedNodes = browsedNodes;
            this.nodeToBrowse = nodeToBrowse;
        }

        @Override
        protected void compute() {
            if (browsedNodes.contains(nodeToBrowse)) return;

            BrowseDescription browseDescription = new BrowseDescription(
                    nodeToBrowse,
                    BrowseDirection.Forward,
                    ReferenceType.HIERARCHICAL_REFERENCES.getNodeId(),
                    true, uint(0), uint(0));

            try {
                BrowseResult result = client.browse(browseDescription).get();

                browsedNodes.add(nodeToBrowse);
                System.out.println("browsedNodes.size()=" + browsedNodes.size());

                ReferenceDescription[] references = result.getReferences();
                List<BrowseAction> browseActions = Lists.newArrayListWithCapacity(references.length);

                for (ReferenceDescription reference : references) {
                    reference.getNodeId().local().ifPresent(nodeId -> {
//                        if (nodeId.getNamespaceIndex().intValue() == 0) {
                        browseActions.add(new BrowseAction(client, browsedNodes, nodeId));
//                        }
                    });
                }

                invokeAll(browseActions);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

    }

    private static class KeyStoreLoader {

        private static final String CLIENT_ALIAS = "sdk-example-client";
        private static final char[] PASSWORD = "test".toCharArray();

        private X509Certificate clientCertificate;
        private KeyPair clientKeyPair;

        public KeyStoreLoader load() throws Exception {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(getClass().getClassLoader().getResourceAsStream("example-keystore.pfx"), PASSWORD);

            Key clientPrivateKey = keyStore.getKey(CLIENT_ALIAS, PASSWORD);
            if (clientPrivateKey instanceof PrivateKey) {
                clientCertificate = (X509Certificate) keyStore.getCertificate(CLIENT_ALIAS);
                PublicKey clientPublicKey = clientCertificate.getPublicKey();
                clientKeyPair = new KeyPair(clientPublicKey, (PrivateKey) clientPrivateKey);
            }

            return this;
        }

        public X509Certificate getClientCertificate() {
            return clientCertificate;
        }

        public KeyPair getClientKeyPair() {
            return clientKeyPair;
        }

    }

}
