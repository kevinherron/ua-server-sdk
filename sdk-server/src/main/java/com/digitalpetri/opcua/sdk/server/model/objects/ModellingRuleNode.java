package com.digitalpetri.opcua.sdk.server.model.objects;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.model.objects.ModellingRuleType;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.util.UaObjectType;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.NamingRuleType;


@UaObjectType(name = "ModellingRuleType")
public class ModellingRuleNode extends BaseObjectNode implements ModellingRuleType {

    public ModellingRuleNode(
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

    public NamingRuleType getNamingRule() {
        Optional<Integer> namingRule = getProperty("NamingRule");

        return namingRule.map(NamingRuleType::from).orElse(null);
    }

    public synchronized void setNamingRule(NamingRuleType namingRule) {
        getPropertyNode("NamingRule").ifPresent(n -> {
            Integer value = namingRule.getValue();

            n.setValue(new DataValue(new Variant(value)));
        });
    }
}
