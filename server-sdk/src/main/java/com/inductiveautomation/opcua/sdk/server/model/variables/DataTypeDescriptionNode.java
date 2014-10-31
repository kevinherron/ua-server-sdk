package com.inductiveautomation.opcua.sdk.server.model.variables;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.model.UaOptional;
import com.inductiveautomation.opcua.sdk.core.model.variables.DataTypeDescriptionType;
import com.inductiveautomation.opcua.sdk.server.api.UaNodeManager;
import com.inductiveautomation.opcua.sdk.server.util.UaVariableType;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaVariableType(name = "DataTypeDescriptionType")
public class DataTypeDescriptionNode extends BaseDataVariableNode implements DataTypeDescriptionType {

    public DataTypeDescriptionNode(UaNodeManager nodeManager,
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
    @UaOptional("DataTypeVersion")
    public String getDataTypeVersion() {
        Optional<String> dataTypeVersion = getProperty("DataTypeVersion");

        return dataTypeVersion.orElse(null);
    }

    @Override
    @UaOptional("DictionaryFragment")
    public ByteString getDictionaryFragment() {
        Optional<ByteString> dictionaryFragment = getProperty("DictionaryFragment");

        return dictionaryFragment.orElse(null);
    }

    @Override
    public synchronized void setDataTypeVersion(String dataTypeVersion) {
        getPropertyNode("DataTypeVersion").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(dataTypeVersion)));
        });
    }

    @Override
    public synchronized void setDictionaryFragment(ByteString dictionaryFragment) {
        getPropertyNode("DictionaryFragment").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(dictionaryFragment)));
        });
    }

    @Override
    public void atomicAction(Runnable runnable) {
        runnable.run();
    }

}
