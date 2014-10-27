package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.enumerated.ExceptionDeviationFormat;

public interface HistoricalDataConfigurationType extends BaseObjectType {

    Boolean getStepped();

    String getDefinition();

    Double getMaxTimeInterval();

    Double getMinTimeInterval();

    Double getExceptionDeviation();

    ExceptionDeviationFormat getExceptionDeviationFormat();

    DateTime getStartOfArchive();

    DateTime getStartOfOnlineArchive();

    AggregateConfigurationType getAggregateConfiguration();

    FolderType getAggregateFunctions();

    void setStepped(Boolean stepped);

    void setDefinition(String definition);

    void setMaxTimeInterval(Double maxTimeInterval);

    void setMinTimeInterval(Double minTimeInterval);

    void setExceptionDeviation(Double exceptionDeviation);

    void setExceptionDeviationFormat(ExceptionDeviationFormat exceptionDeviationFormat);

    void setStartOfArchive(DateTime startOfArchive);

    void setStartOfOnlineArchive(DateTime startOfOnlineArchive);

    void atomicSet(Runnable runnable);

}
