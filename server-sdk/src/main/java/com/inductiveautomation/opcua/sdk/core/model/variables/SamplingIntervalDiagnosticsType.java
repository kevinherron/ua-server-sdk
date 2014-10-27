package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface SamplingIntervalDiagnosticsType extends BaseDataVariableType {

    Double getSamplingInterval();

    UInteger getSampledMonitoredItemsCount();

    UInteger getMaxSampledMonitoredItemsCount();

    UInteger getDisabledMonitoredItemsSamplingCount();

    void setSamplingInterval(Double samplingInterval);

    void setSampledMonitoredItemsCount(UInteger sampledMonitoredItemsCount);

    void setMaxSampledMonitoredItemsCount(UInteger maxSampledMonitoredItemsCount);

    void setDisabledMonitoredItemsSamplingCount(UInteger disabledMonitoredItemsSamplingCount);

    void atomicSet(Runnable runnable);

}
