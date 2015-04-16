package com.digitalpetri.opcua.sdk.server.model.objects;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.model.objects.FolderType;
import com.digitalpetri.opcua.sdk.core.model.objects.OperationLimitsType;
import com.digitalpetri.opcua.sdk.core.model.objects.ServerCapabilitiesType;
import com.digitalpetri.opcua.sdk.core.model.variables.ServerVendorCapabilityType;
import com.digitalpetri.opcua.sdk.core.nodes.ObjectNode;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.util.UaObjectType;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;
import com.digitalpetri.opcua.stack.core.types.structured.SignedSoftwareCertificate;


@UaObjectType(name = "ServerCapabilitiesType")
public class ServerCapabilitiesNode extends BaseObjectNode implements ServerCapabilitiesType {

    public ServerCapabilitiesNode(
            UaNamespace namespace,
            NodeId nodeId,
            QualifiedName browseName,
            LocalizedText displayName,
            Optional<LocalizedText> description,
            Optional<UInteger> writeMask,
            Optional<UInteger> userWriteMask,
            UByte eventNotifier) {

        super(namespace, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
    }

    public String[] getServerProfileArray() {
        Optional<String[]> serverProfileArray = getProperty("ServerProfileArray");

        return serverProfileArray.orElse(null);
    }

    public String[] getLocaleIdArray() {
        Optional<String[]> localeIdArray = getProperty("LocaleIdArray");

        return localeIdArray.orElse(null);
    }

    public Double getMinSupportedSampleRate() {
        Optional<Double> minSupportedSampleRate = getProperty("MinSupportedSampleRate");

        return minSupportedSampleRate.orElse(null);
    }

    public UShort getMaxBrowseContinuationPoints() {
        Optional<UShort> maxBrowseContinuationPoints = getProperty("MaxBrowseContinuationPoints");

        return maxBrowseContinuationPoints.orElse(null);
    }

    public UShort getMaxQueryContinuationPoints() {
        Optional<UShort> maxQueryContinuationPoints = getProperty("MaxQueryContinuationPoints");

        return maxQueryContinuationPoints.orElse(null);
    }

    public UShort getMaxHistoryContinuationPoints() {
        Optional<UShort> maxHistoryContinuationPoints = getProperty("MaxHistoryContinuationPoints");

        return maxHistoryContinuationPoints.orElse(null);
    }

    public SignedSoftwareCertificate[] getSoftwareCertificates() {
        Optional<SignedSoftwareCertificate[]> softwareCertificates = getProperty("SoftwareCertificates");

        return softwareCertificates.orElse(null);
    }

    public UInteger getMaxArrayLength() {
        Optional<UInteger> maxArrayLength = getProperty("MaxArrayLength");

        return maxArrayLength.orElse(null);
    }

    public UInteger getMaxStringLength() {
        Optional<UInteger> maxStringLength = getProperty("MaxStringLength");

        return maxStringLength.orElse(null);
    }

    public OperationLimitsType getOperationLimits() {
        Optional<ObjectNode> operationLimits = getObjectComponent("OperationLimits");

        return operationLimits.map(node -> (OperationLimitsType) node).orElse(null);
    }

    public FolderType getModellingRules() {
        Optional<ObjectNode> modellingRules = getObjectComponent("ModellingRules");

        return modellingRules.map(node -> (FolderType) node).orElse(null);
    }

    public FolderType getAggregateFunctions() {
        Optional<ObjectNode> aggregateFunctions = getObjectComponent("AggregateFunctions");

        return aggregateFunctions.map(node -> (FolderType) node).orElse(null);
    }

    public ServerVendorCapabilityType getVendorCapability() {
        Optional<VariableNode> vendorCapability = getVariableComponent("VendorCapability");

        return vendorCapability.map(node -> (ServerVendorCapabilityType) node).orElse(null);
    }

    public synchronized void setServerProfileArray(String[] serverProfileArray) {
        getPropertyNode("ServerProfileArray").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(serverProfileArray)));
        });
    }

    public synchronized void setLocaleIdArray(String[] localeIdArray) {
        getPropertyNode("LocaleIdArray").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(localeIdArray)));
        });
    }

    public synchronized void setMinSupportedSampleRate(Double minSupportedSampleRate) {
        getPropertyNode("MinSupportedSampleRate").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(minSupportedSampleRate)));
        });
    }

    public synchronized void setMaxBrowseContinuationPoints(UShort maxBrowseContinuationPoints) {
        getPropertyNode("MaxBrowseContinuationPoints").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxBrowseContinuationPoints)));
        });
    }

    public synchronized void setMaxQueryContinuationPoints(UShort maxQueryContinuationPoints) {
        getPropertyNode("MaxQueryContinuationPoints").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxQueryContinuationPoints)));
        });
    }

    public synchronized void setMaxHistoryContinuationPoints(UShort maxHistoryContinuationPoints) {
        getPropertyNode("MaxHistoryContinuationPoints").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxHistoryContinuationPoints)));
        });
    }

    public synchronized void setSoftwareCertificates(SignedSoftwareCertificate[] softwareCertificates) {
        getPropertyNode("SoftwareCertificates").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(softwareCertificates)));
        });
    }

    public synchronized void setMaxArrayLength(UInteger maxArrayLength) {
        getPropertyNode("MaxArrayLength").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxArrayLength)));
        });
    }

    public synchronized void setMaxStringLength(UInteger maxStringLength) {
        getPropertyNode("MaxStringLength").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(maxStringLength)));
        });
    }

    public synchronized void setVendorCapability(ServerVendorCapabilityType vendorCapability) {
        getVariableComponent("VendorCapability").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(vendorCapability)));
        });
    }
}
