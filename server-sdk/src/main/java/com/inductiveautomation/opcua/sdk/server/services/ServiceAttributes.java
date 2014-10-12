package com.inductiveautomation.opcua.sdk.server.services;

import com.inductiveautomation.opcua.sdk.server.OpcUaServer;
import com.inductiveautomation.opcua.sdk.server.Session;
import io.netty.util.AttributeKey;

public interface ServiceAttributes {

    public static final AttributeKey<OpcUaServer> ServerKey = AttributeKey.valueOf("server");

    public static final AttributeKey<Session> SessionKey = AttributeKey.valueOf("session");

}
