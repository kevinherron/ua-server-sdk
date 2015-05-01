package com.digitalpetri.opcua.sdk.client;

import com.digitalpetri.opcua.stack.core.types.structured.ServiceFault;

public interface ServiceFaultHandler {

    /**
     * Test if this handler should accept the {@link ServiceFault}.
     *
     * @param serviceFault the {@link ServiceFault}.
     * @return {@code true} true if this handler should accept the {@link ServiceFault}.
     */
    boolean accept(ServiceFault serviceFault);

    /**
     * A {@link ServiceFault} matching this handlers test criteria occurred; handle it.
     *
     * @param serviceFault the {@link ServiceFault}.
     */
    void handle(ServiceFault serviceFault);

}
