package com.inductiveautomation.opcua.sdk.server.nodes.generated.variables;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.model.variables.XYArrayItemType;
import com.inductiveautomation.opcua.sdk.server.api.UaNodeManager;
import com.inductiveautomation.opcua.sdk.server.util.UaVariableType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.structured.AxisInformation;

@UaVariableType(name = "XYArrayItemType")
public class XYArrayItemNode extends ArrayItemNode implements XYArrayItemType {

    public XYArrayItemNode(UaNodeManager nodeManager,
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
    public AxisInformation getXAxisDefinition() {
        Optional<AxisInformation> xAxisDefinition = getProperty("XAxisDefinition");

        return xAxisDefinition.orElse(null);
    }

    @Override
    public void setXAxisDefinition(AxisInformation xAxisDefinition) {
        getPropertyNode("XAxisDefinition").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(xAxisDefinition)));
        });
    }

    @Override
    public void atomicSet(Runnable runnable) {
        runnable.run();
    }

}
