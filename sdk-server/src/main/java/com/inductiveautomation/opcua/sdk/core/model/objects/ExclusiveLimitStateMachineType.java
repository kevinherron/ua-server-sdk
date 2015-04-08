package com.inductiveautomation.opcua.sdk.core.model.objects;

public interface ExclusiveLimitStateMachineType extends FiniteStateMachineType {

    StateType getHighHigh();

    StateType getHigh();

    StateType getLow();

    StateType getLowLow();

    TransitionType getLowLowToLow();

    TransitionType getLowToLowLow();

    TransitionType getHighHighToHigh();

    TransitionType getHighToHighHigh();


}
