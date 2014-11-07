package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;

public interface AggregateConfigurationType extends BaseObjectType {

    Boolean getTreatUncertainAsBad();

    UByte getPercentDataBad();

    UByte getPercentDataGood();

    Boolean getUseSlopedExtrapolation();

    void setTreatUncertainAsBad(Boolean treatUncertainAsBad);

    void setPercentDataBad(UByte percentDataBad);

    void setPercentDataGood(UByte percentDataGood);

    void setUseSlopedExtrapolation(Boolean useSlopedExtrapolation);

}
