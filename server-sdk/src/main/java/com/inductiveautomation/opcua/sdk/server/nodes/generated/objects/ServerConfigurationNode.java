package com.inductiveautomation.opcua.sdk.server.nodes.generated.objects;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.model.objects.ServerConfigurationType;
import com.inductiveautomation.opcua.sdk.core.model.objects.TrustListType;
import com.inductiveautomation.opcua.sdk.server.api.UaNodeManager;
import com.inductiveautomation.opcua.sdk.server.util.UaObjectType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;


@UaObjectType(name = "ServerConfigurationType")
public class ServerConfigurationNode extends BaseObjectNode implements ServerConfigurationType {

    public ServerConfigurationNode(
            UaNodeManager nodeManager,
            NodeId nodeId,
            QualifiedName browseName,
            LocalizedText displayName,
            Optional<LocalizedText> description,
            Optional<UInteger> writeMask,
            Optional<UInteger> userWriteMask,
            UByte eventNotifier) {

        super(nodeManager, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
    }

    public String[] getSupportedCertificateFormats() {
        Optional<String[]> supportedCertificateFormats = getProperty("SupportedCertificateFormats");

        return supportedCertificateFormats.orElse(null);
    }

    public String[] getSupportedPrivateKeyFormats() {
        Optional<String[]> supportedPrivateKeyFormats = getProperty("SupportedPrivateKeyFormats");

        return supportedPrivateKeyFormats.orElse(null);
    }

    public UInteger getMaxTrustListSize() {
        Optional<UInteger> maxTrustListSize = getProperty("MaxTrustListSize");

        return maxTrustListSize.orElse(null);
    }

    public Boolean getNoSupportForLocalCRLs() {
        Optional<Boolean> noSupportForLocalCRLs = getProperty("NoSupportForLocalCRLs");

        return noSupportForLocalCRLs.orElse(null);
    }

    public Boolean getMulticastDnsEnabled() {
        Optional<Boolean> multicastDnsEnabled = getProperty("MulticastDnsEnabled");

        return multicastDnsEnabled.orElse(null);
    }

    public TrustListType getTrustList() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> trustList = getObjectComponent("TrustList");

        return trustList.map(node -> (TrustListType) node).orElse(null);
    }

    public TrustListType getHttpsTrustList() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> httpsTrustList = getObjectComponent("HttpsTrustList");

        return httpsTrustList.map(node -> (TrustListType) node).orElse(null);
    }

    public synchronized void setSupportedCertificateFormats(String[] supportedCertificateFormats) {
        getPropertyNode("SupportedCertificateFormats").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(supportedCertificateFormats)));
        });
    }

    public synchronized void setSupportedPrivateKeyFormats(String[] supportedPrivateKeyFormats) {
        getPropertyNode("SupportedPrivateKeyFormats").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(supportedPrivateKeyFormats)));
        });
    }

    public synchronized void setMaxTrustListSize(UInteger maxTrustListSize) {
        getPropertyNode("MaxTrustListSize").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxTrustListSize)));
        });
    }

    public synchronized void setNoSupportForLocalCRLs(Boolean noSupportForLocalCRLs) {
        getPropertyNode("NoSupportForLocalCRLs").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(noSupportForLocalCRLs)));
        });
    }

    public synchronized void setMulticastDnsEnabled(Boolean multicastDnsEnabled) {
        getPropertyNode("MulticastDnsEnabled").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(multicastDnsEnabled)));
        });
    }

    public synchronized void atomicSet(Runnable runnable) {
        runnable.run();
    }

}
