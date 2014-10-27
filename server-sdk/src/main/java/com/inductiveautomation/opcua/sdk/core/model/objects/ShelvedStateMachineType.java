package com.inductiveautomation.opcua.sdk.core.model.objects;

public interface ShelvedStateMachineType extends FiniteStateMachineType {

    Double getUnshelveTime();

    StateType getUnshelved();

    StateType getTimedShelved();

    StateType getOneShotShelved();

    TransitionType getUnshelvedToTimedShelved();

    TransitionType getUnshelvedToOneShotShelved();

    TransitionType getTimedShelvedToUnshelved();

    TransitionType getTimedShelvedToOneShotShelved();

    TransitionType getOneShotShelvedToUnshelved();

    TransitionType getOneShotShelvedToTimedShelved();

    void setUnshelveTime(Double unshelveTime);

    void atomicSet(Runnable runnable);

}
