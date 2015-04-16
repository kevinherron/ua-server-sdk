package com.digitalpetri.opcua.sdk.server.model.objects;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.model.objects.ExclusiveLimitStateMachineType;
import com.digitalpetri.opcua.sdk.core.model.objects.StateType;
import com.digitalpetri.opcua.sdk.core.model.objects.TransitionType;
import com.digitalpetri.opcua.sdk.core.nodes.ObjectNode;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.util.UaObjectType;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;


@UaObjectType(name = "ExclusiveLimitStateMachineType")
public class ExclusiveLimitStateMachineNode extends FiniteStateMachineNode implements ExclusiveLimitStateMachineType {

    public ExclusiveLimitStateMachineNode(
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

    public StateType getHighHigh() {
        Optional<ObjectNode> highHigh = getObjectComponent("HighHigh");

        return highHigh.map(node -> (StateType) node).orElse(null);
    }

    public StateType getHigh() {
        Optional<ObjectNode> high = getObjectComponent("High");

        return high.map(node -> (StateType) node).orElse(null);
    }

    public StateType getLow() {
        Optional<ObjectNode> low = getObjectComponent("Low");

        return low.map(node -> (StateType) node).orElse(null);
    }

    public StateType getLowLow() {
        Optional<ObjectNode> lowLow = getObjectComponent("LowLow");

        return lowLow.map(node -> (StateType) node).orElse(null);
    }

    public TransitionType getLowLowToLow() {
        Optional<ObjectNode> lowLowToLow = getObjectComponent("LowLowToLow");

        return lowLowToLow.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getLowToLowLow() {
        Optional<ObjectNode> lowToLowLow = getObjectComponent("LowToLowLow");

        return lowToLowLow.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getHighHighToHigh() {
        Optional<ObjectNode> highHighToHigh = getObjectComponent("HighHighToHigh");

        return highHighToHigh.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getHighToHighHigh() {
        Optional<ObjectNode> highToHighHigh = getObjectComponent("HighToHighHigh");

        return highToHighHigh.map(node -> (TransitionType) node).orElse(null);
    }

}
