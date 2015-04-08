package com.inductiveautomation.opcua.sdk.server.namespaces.loader;

import com.inductiveautomation.opcua.sdk.server.api.UaNamespace;

public class UaNodeLoader {

    private final UaNamespace namespace;

    public UaNodeLoader(UaNamespace namespace)
            throws Exception {
        this.namespace = namespace;
        new UaDataTypeLoader(namespace).buildNodes();
        new UaMethodLoader(namespace).buildNodes();
        new UaObjectLoader(namespace).buildNodes();
        new UaObjectTypeLoader(namespace).buildNodes();
        new UaReferenceTypeLoader(namespace).buildNodes();
        new UaVariableLoader(namespace).buildNodes();
        new UaVariableTypeLoader(namespace).buildNodes();
        new UaViewLoader(namespace).buildNodes();
    }

}
