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

    void setUnshelved(StateType unshelved);

    void setTimedShelved(StateType timedShelved);

    void setOneShotShelved(StateType oneShotShelved);

    void setUnshelvedToTimedShelved(TransitionType unshelvedToTimedShelved);

    void setUnshelvedToOneShotShelved(TransitionType unshelvedToOneShotShelved);

    void setTimedShelvedToUnshelved(TransitionType timedShelvedToUnshelved);

    void setTimedShelvedToOneShotShelved(TransitionType timedShelvedToOneShotShelved);

    void setOneShotShelvedToUnshelved(TransitionType oneShotShelvedToUnshelved);

    void setOneShotShelvedToTimedShelved(TransitionType oneShotShelvedToTimedShelved);

    void atomicSet(Runnable runnable);

}
