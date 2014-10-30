package com.inductiveautomation.opcua.sdk.server.nodes.generated.objects;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.model.objects.ExclusiveLimitStateMachineType;
import com.inductiveautomation.opcua.sdk.core.model.objects.StateType;
import com.inductiveautomation.opcua.sdk.core.model.objects.TransitionType;
import com.inductiveautomation.opcua.sdk.server.api.UaNodeManager;
import com.inductiveautomation.opcua.sdk.server.util.UaObjectType;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;


@UaObjectType(name = "ExclusiveLimitStateMachineType")
public class ExclusiveLimitStateMachineNode extends FiniteStateMachineNode implements ExclusiveLimitStateMachineType {

    public ExclusiveLimitStateMachineNode(
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

    public StateType getHighHigh() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> highHigh = getObjectComponent("HighHigh");

        return highHigh.map(node -> (StateType) node).orElse(null);
    }

    public StateType getHigh() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> high = getObjectComponent("High");

        return high.map(node -> (StateType) node).orElse(null);
    }

    public StateType getLow() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> low = getObjectComponent("Low");

        return low.map(node -> (StateType) node).orElse(null);
    }

    public StateType getLowLow() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> lowLow = getObjectComponent("LowLow");

        return lowLow.map(node -> (StateType) node).orElse(null);
    }

    public TransitionType getLowLowToLow() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> lowLowToLow = getObjectComponent("LowLowToLow");

        return lowLowToLow.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getLowToLowLow() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> lowToLowLow = getObjectComponent("LowToLowLow");

        return lowToLowLow.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getHighHighToHigh() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> highHighToHigh = getObjectComponent("HighHighToHigh");

        return highHighToHigh.map(node -> (TransitionType) node).orElse(null);
    }

    public TransitionType getHighToHighHigh() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> highToHighHigh = getObjectComponent("HighToHighHigh");

        return highToHighHigh.map(node -> (TransitionType) node).orElse(null);
    }

    public synchronized void atomicSet(Runnable runnable) {
        runnable.run();
    }

}
