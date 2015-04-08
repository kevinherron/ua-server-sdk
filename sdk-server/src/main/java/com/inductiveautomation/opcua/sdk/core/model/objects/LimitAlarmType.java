package com.inductiveautomation.opcua.sdk.core.model.objects;

public interface LimitAlarmType extends AlarmConditionType {

    Double getHighHighLimit();

    Double getHighLimit();

    Double getLowLimit();

    Double getLowLowLimit();

    void setHighHighLimit(Double highHighLimit);

    void setHighLimit(Double highLimit);

    void setLowLimit(Double lowLimit);

    void setLowLowLimit(Double lowLowLimit);

}
