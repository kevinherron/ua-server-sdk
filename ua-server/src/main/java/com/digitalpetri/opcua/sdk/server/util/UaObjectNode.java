package com.digitalpetri.opcua.sdk.server.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface UaObjectNode {
    String typeName();
}
