package com.inductiveautomation.opcua.sdk.server.model.variables;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.core.model.UaMandatory;
import com.inductiveautomation.opcua.sdk.core.model.variables.BuildInfoType;
import com.inductiveautomation.opcua.sdk.core.nodes.VariableNode;
import com.inductiveautomation.opcua.sdk.server.api.UaNamespace;
import com.inductiveautomation.opcua.sdk.server.util.UaVariableType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.structured.BuildInfo;

@UaVariableType(name = "BuildInfoType")
public class BuildInfoNode extends BaseDataVariableNode implements BuildInfoType {

    public BuildInfoNode(UaNamespace nodeManager,
                         NodeId nodeId,
                         QualifiedName browseName,
                         LocalizedText displayName,
                         Optional<LocalizedText> description,
                         Optional<UInteger> writeMask,
                         Optional<UInteger> userWriteMask,
                         DataValue value,
                         NodeId dataType,
                         Integer valueRank,
                         Optional<UInteger[]> arrayDimensions,
                         UByte accessLevel,
                         UByte userAccessLevel,
                         Optional<Double> minimumSamplingInterval,
                         boolean historizing) {

        super(nodeManager, nodeId, browseName, displayName, description, writeMask, userWriteMask,
                value, dataType, valueRank, arrayDimensions, accessLevel, userAccessLevel, minimumSamplingInterval, historizing);

    }

    @Override
    public DataValue getValue() {
        BuildInfo value = new BuildInfo(
                getProductUri(),
                getManufacturerName(),
                getProductName(),
                getSoftwareVersion(),
                getBuildNumber(),
                getBuildDate()
        );

        return new DataValue(new Variant(value));
    }

    @Override
    public synchronized void setValue(DataValue value) {
        BuildInfo v = (BuildInfo) value.getValue().getValue();

        setProductUri(v.getProductUri());
        setManufacturerName(v.getManufacturerName());
        setProductName(v.getProductName());
        setSoftwareVersion(v.getSoftwareVersion());
        setBuildNumber(v.getBuildNumber());
        setBuildDate(v.getBuildDate());

        fireAttributeChanged(AttributeIds.Value, value);
    }

    @Override
    @UaMandatory("ProductUri")
    public String getProductUri() {
        Optional<VariableNode> node = getVariableComponent("ProductUri");

        return node.map(n -> (String) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("ManufacturerName")
    public String getManufacturerName() {
        Optional<VariableNode> node = getVariableComponent("ManufacturerName");

        return node.map(n -> (String) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("ProductName")
    public String getProductName() {
        Optional<VariableNode> node = getVariableComponent("ProductName");

        return node.map(n -> (String) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("SoftwareVersion")
    public String getSoftwareVersion() {
        Optional<VariableNode> node = getVariableComponent("SoftwareVersion");

        return node.map(n -> (String) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("BuildNumber")
    public String getBuildNumber() {
        Optional<VariableNode> node = getVariableComponent("BuildNumber");

        return node.map(n -> (String) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    @UaMandatory("BuildDate")
    public DateTime getBuildDate() {
        Optional<VariableNode> node = getVariableComponent("BuildDate");

        return node.map(n -> (DateTime) n.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public synchronized void setProductUri(String productUri) {
        getVariableComponent("ProductUri").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(productUri)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setManufacturerName(String manufacturerName) {
        getVariableComponent("ManufacturerName").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(manufacturerName)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setProductName(String productName) {
        getVariableComponent("ProductName").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(productName)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setSoftwareVersion(String softwareVersion) {
        getVariableComponent("SoftwareVersion").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(softwareVersion)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setBuildNumber(String buildNumber) {
        getVariableComponent("BuildNumber").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(buildNumber)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public synchronized void setBuildDate(DateTime buildDate) {
        getVariableComponent("BuildDate").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(buildDate)));

            fireAttributeChanged(AttributeIds.Value, getValue());
        });
    }

    @Override
    public void atomicAction(Runnable runnable) {
        runnable.run();
    }

}
