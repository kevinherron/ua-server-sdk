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

import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadValueId;

public interface MonitoredItem {

    /**
     * @return the server-side id of this item.
     */
    UInteger getId();

    /**
     * @return the {@link ReadValueId} being monitored.
     */
    ReadValueId getReadValueId();

    /**
     * @return the {@link TimestampsToReturn}.
     */
    TimestampsToReturn getTimestampsToReturn();

    /**
     * @return {@code true} if this item should be sampled.
     */
    boolean isSamplingEnabled();

}
