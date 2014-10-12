/*
 * OPC-UA SDK
 *
 * Copyright (C) 2014 Kevin Herron
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.inductiveautomation.opcua.sdk.server.api;

import java.util.List;

import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;

public interface MonitoredDataItem extends MonitoredItem {

    /**
     * Set the latest sampled value.
     *
     * @param value the latest sampled value.
     */
    void setValue(DataValue value);

    /**
     * Apply a quality to the last value that passed the filter and then attempt to set the derived value.
     *
     * @param quality the quality to apply.
     */
    void setQuality(StatusCode quality);

    /**
     * @return the rate to sample this item at. Only valid for the duration of a call to
     * {@link MonitoredItemManager#onSampledItemsCreated(List)} or
     * {@link MonitoredItemManager#onSampledItemsModified(List)}.
     */
    double getSamplingInterval();

    /**
     * Revise the sampling interval.
     * <p>
     * Only applicable for the duration of a call to {@link MonitoredItemManager#onSampledItemsCreated(List)} or
     * {@link MonitoredItemManager#onSampledItemsModified(List)}.
     *
     * @param samplingInterval the revised sampling interval.
     */
    void setSamplingInterval(Double samplingInterval);

    /**
     * @return <code>true</code> if this item should be sampled.
     */
    boolean isSamplingEnabled();

}
