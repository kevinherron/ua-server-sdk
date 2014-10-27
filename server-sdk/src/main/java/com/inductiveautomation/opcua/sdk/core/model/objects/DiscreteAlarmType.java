package com.inductiveautomation.opcua.sdk.core.model.objects;

public interface DiscreteAlarmType extends AlarmConditionType {


    void atomicSet(Runnable runnable);

}
