package com.digitalpetri.opcua.sdk.server.model.variables;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.model.UaMandatory;
import com.digitalpetri.opcua.sdk.core.model.variables.ImageItemType;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.util.UaVariableType;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.structured.AxisInformation;

@UaVariableType(name = "ImageItemType")
public class ImageItemNode extends ArrayItemNode implements ImageItemType {

    public ImageItemNode(UaNamespace namespace,
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

        super(namespace, nodeId, browseName, displayName, description, writeMask, userWriteMask,
                value, dataType, valueRank, arrayDimensions, accessLevel, userAccessLevel, minimumSamplingInterval, historizing);

    }

    @Override
    @UaMandatory("XAxisDefinition")
    public AxisInformation getXAxisDefinition() {
        Optional<AxisInformation> xAxisDefinition = getProperty("XAxisDefinition");

        return xAxisDefinition.orElse(null);
    }

    @Override
    @UaMandatory("YAxisDefinition")
    public AxisInformation getYAxisDefinition() {
        Optional<AxisInformation> yAxisDefinition = getProperty("YAxisDefinition");

        return yAxisDefinition.orElse(null);
    }

    @Override
    public synchronized void setXAxisDefinition(AxisInformation xAxisDefinition) {
        getPropertyNode("XAxisDefinition").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(xAxisDefinition)));
        });
    }

    @Override
    public synchronized void setYAxisDefinition(AxisInformation yAxisDefinition) {
        getPropertyNode("YAxisDefinition").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(yAxisDefinition)));
        });
    }

}
