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

    void setHighHigh(StateType highHigh);

    void setHigh(StateType high);

    void setLow(StateType low);

    void setLowLow(StateType lowLow);

    void setLowLowToLow(TransitionType lowLowToLow);

    void setLowToLowLow(TransitionType lowToLowLow);

    void setHighHighToHigh(TransitionType highHighToHigh);

    void setHighToHighHigh(TransitionType highToHighHigh);

    void atomicSet(Runnable runnable);

}
