package com.inductiveautomation.opcua.sdk.server.api;

import java.util.List;

import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;

public interface SampledItem extends MonitoredItem {

    /**
     * Set the latest sampled value.
     *
     * @param value the latest sampled value.
     */
    void setValue(DataValue value);

    /**
     * Apply a new {@link StatusCode} to the last value that passed the filter and then attempt to set the derived value.
     *
     * @param quality the {@link StatusCode} to apply.
     */
    void setQuality(StatusCode quality);

    /**
     * Set the sampling interval.
     * <p>
     * Only has any effect during a {@link MonitoredItemManager#onSampledItemsCreated(List)} or
     * {@link MonitoredItemManager#onSampledItemsModified(List)} call.
     *
     * @param samplingInterval the revised sampling interval.
     */
    void setSamplingInterval(double samplingInterval);

    /**
     * @return the rate to sample this item at.
     */
    double getSamplingInterval();

    /**
     * @return {@code true} if this item should be sampled.
     */
    boolean isSamplingEnabled();

}
