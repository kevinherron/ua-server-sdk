package com.digitalpetri.opcua.sdk.client;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.digitalpetri.opcua.sdk.client.api.UaSession;
import com.digitalpetri.opcua.sdk.client.api.config.OpcUaClientConfig;
import com.digitalpetri.opcua.sdk.client.api.nodes.attached.UaVariableNode;
import com.digitalpetri.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import com.digitalpetri.opcua.sdk.client.api.subscriptions.UaSubscription;
import com.digitalpetri.opcua.sdk.client.api.subscriptions.UaSubscriptionManager.SubscriptionListener;
import com.digitalpetri.opcua.stack.core.AttributeId;
import com.digitalpetri.opcua.sdk.server.OpcUaServer;
import com.digitalpetri.opcua.sdk.server.api.config.OpcUaServerConfig;
import com.digitalpetri.opcua.server.ctt.CttNamespace;
import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.enumerated.MonitoringMode;
import com.digitalpetri.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.digitalpetri.opcua.stack.core.types.structured.EndpointDescription;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoringParameters;
import com.digitalpetri.opcua.stack.core.types.structured.ReadValueId;
import com.digitalpetri.opcua.stack.server.tcp.SocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;
import static com.google.common.collect.Lists.newArrayList;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class OpcUaClientIT {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    OpcUaClient client;
    OpcUaServer server;

    @BeforeTest
    public void startClientAndServer() throws Exception {
        logger.info("startClientAndServer()");

        OpcUaServerConfig serverConfig = OpcUaServerConfig.builder()
                .setApplicationName(LocalizedText.english("digitalpetri opc-ua server"))
                .setApplicationUri("urn:digitalpetri:opcua:server")
                .setBindAddresses(newArrayList("localhost"))
                .setBindPort(12686)
                .setProductUri("urn:digitalpetri:opcua:sdk")
                .setServerName("test-server")
                .setHostname("localhost")
                .build();

        server = new OpcUaServer(serverConfig);

        // register a CttNamespace so we have some nodes to play with
        server.getNamespaceManager().registerAndAdd(
                CttNamespace.NAMESPACE_URI,
                idx -> new CttNamespace(server, idx));

        server.startup();

        EndpointDescription[] endpoints = UaTcpStackClient.getEndpoints("opc.tcp://localhost:12686/test-server").get();

        EndpointDescription endpoint = Arrays.stream(endpoints)
                .filter(e -> e.getSecurityPolicyUri().equals(SecurityPolicy.None.getSecurityPolicyUri()))
                .findFirst().orElseThrow(() -> new Exception("no desired endpoints returned"));

        OpcUaClientConfig clientConfig = OpcUaClientConfig.builder()
                .setApplicationName(LocalizedText.english("digitalpetri opc-ua client"))
                .setApplicationUri("urn:digitalpetri:opcua:client")
                .setEndpoint(endpoint)
                .setRequestTimeout(uint(60000))
                .build();

        client = new OpcUaClient(clientConfig);
    }

    @AfterTest
    public void stopClientAndServer() {
        logger.info("stopClientAndServer()");

        client.disconnect();
        server.shutdown();
        SocketServer.shutdownAll();
    }

    @Test
    public void testRead() throws Exception {
        logger.info("testRead()");

        UaVariableNode currentTimeNode = client.getAddressSpace()
                .getVariableNode(Identifiers.Server_ServerStatus_CurrentTime);

        assertNotNull(currentTimeNode.readValueAttribute().get());
    }

    @Test
    public void testWrite() throws Exception {
        logger.info("testWrite()");

        NodeId nodeId = new NodeId(2, "/Static/AllProfiles/Scalar/Int32");

        UaVariableNode variableNode = client.getAddressSpace().getVariableNode(nodeId);

        // read the existing value
        Object valueBefore = variableNode.readValueAttribute().get();
        assertNotNull(valueBefore);

        // write a new random value
        DataValue newValue = new DataValue(new Variant(new Random().nextInt()));
        StatusCode writeStatus = variableNode.writeValue(newValue).get();
        assertTrue(writeStatus.isGood());

        // read the value again
        Object valueAfter = variableNode.readValueAttribute().get();
        assertNotNull(valueAfter);

        assertNotEquals(valueBefore, valueAfter);
    }

    @Test
    public void testSubscribe() throws Exception {
        logger.info("testSubscribe()");

        // create a subscription and a monitored item
        UaSubscription subscription = client.getSubscriptionManager().createSubscription(1000.0).get();

        ReadValueId readValueId = new ReadValueId(
                Identifiers.Server_ServerStatus_CurrentTime,
                AttributeId.VALUE.uid(), null, QualifiedName.NULL_VALUE);

        MonitoringParameters parameters = new MonitoringParameters(
                uint(1),    // client handle
                1000.0,     // sampling interval
                null,       // no (default) filter
                uint(10),   // queue size
                true);      // discard oldest

        MonitoredItemCreateRequest request = new MonitoredItemCreateRequest(
                readValueId, MonitoringMode.Reporting, parameters);

        List<UaMonitoredItem> items = subscription
                .createMonitoredItems(TimestampsToReturn.Both, newArrayList(request)).get();

        // do something with the value updates
        UaMonitoredItem item = items.get(0);

        CompletableFuture<DataValue> f = new CompletableFuture<>();
        item.setValueConsumer(f::complete);

        assertNotNull(f.get(5, TimeUnit.SECONDS));
    }

    @Test
    public void testTransferSubscriptions() throws Exception {
        logger.info("testTransferSubscriptions()");

        // create a subscription and a monitored item
        UaSubscription subscription = client.getSubscriptionManager().createSubscription(1000.0).get();

        NodeId nodeId = new NodeId(2, "/Static/AllProfiles/Scalar/Int32");

        ReadValueId readValueId = new ReadValueId(
                nodeId, AttributeId.VALUE.uid(),
                null, QualifiedName.NULL_VALUE);

        MonitoringParameters parameters = new MonitoringParameters(
                uint(1),    // client handle
                100.0,      // sampling interval
                null,       // no (default) filter
                uint(10),   // queue size
                true);      // discard oldest

        MonitoredItemCreateRequest request = new MonitoredItemCreateRequest(
                readValueId, MonitoringMode.Reporting, parameters);

        List<UaMonitoredItem> items = subscription
                .createMonitoredItems(TimestampsToReturn.Both, newArrayList(request)).get();

        // do something with the value updates
        UaMonitoredItem item = items.get(0);

        AtomicInteger updateCount = new AtomicInteger(0);

        item.setValueConsumer(v -> {
            int count = updateCount.incrementAndGet();
            logger.info("updateCount={}", count);
        });

        AtomicBoolean subscriptionTransferred = new AtomicBoolean(true);

        client.getSubscriptionManager().addSubscriptionListener(new SubscriptionListener() {
            @Override
            public void onKeepAlive(UaSubscription subscription, DateTime publishTime) {

            }

            @Override
            public void onStatusChanged(UaSubscription subscription, StatusCode status) {

            }

            @Override
            public void onPublishFailure(UaException exception) {

            }

            @Override
            public void onNotificationDataLost(UaSubscription subscription) {

            }

            @Override
            public void onSubscriptionTransferFailed(UaSubscription subscription, StatusCode statusCode) {
                subscriptionTransferred.set(false);
            }
        });

        UaSession uaSession = client.getSession().get();
        server.getSessionManager().killSession(uaSession.getSessionId(), false);

        Thread.sleep(2000);

        // one update for the initial subscribe, another after transfer
        assertEquals(updateCount.get(), 2);

        assertTrue(subscriptionTransferred.get());
    }

}
