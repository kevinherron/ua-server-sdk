/*
 * Copyright 2014 Inductive Automation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
