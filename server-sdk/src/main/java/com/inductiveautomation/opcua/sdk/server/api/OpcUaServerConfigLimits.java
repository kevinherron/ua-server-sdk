/*
 * Copyright 2014
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
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UShort;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;
import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.ushort;

public interface OpcUaServerConfigLimits {

    default Double getMinSupportedSampleRate() {
        return 100.0;
    }

    default UShort getMaxBrowseContinuationPoints() {
        return ushort(UShort.MAX_VALUE);
    }

    default UShort getMaxQueryContinuationPoints() {
        return ushort(UShort.MAX_VALUE);
    }

    default UShort getMaxHistoryContinuationPoints() {
        return ushort(UShort.MAX_VALUE);
    }

    default UInteger getMaxArrayLength() {
        return uint(0x1FFFF);
    }

    default UInteger getMaxStringLength() {
        return uint(0x1FFFF);
    }

    default UInteger getMaxNodesPerRead() {
        return uint(0x1FFFF);
    }

    default UInteger getMaxNodesPerHistoryReadData() {
        return uint(0x1FFFF);
    }

    default UInteger getMaxNodesPerHistoryReadEvents() {
        return uint(0x1FFFF);
    }

    default UInteger getMaxNodesPerWrite() {
        return uint(0x1FFFF);
    }

    default UInteger getMaxNodesPerHistoryUpdateData() {
        return uint(0x1FFFF);
    }

    default UInteger getMaxNodesPerHistoryUpdateEvents() {
        return uint(0x1FFFF);
    }

    default UInteger getMaxNodesPerMethodCall() {
        return uint(0x1FFFF);
    }

    default UInteger getMaxNodesPerBrowse() {
        return uint(0x1FFFF);
    }

    default UInteger getMaxNodesPerRegisterNodes() {
        return uint(0x1FFFF);
    }

    default UInteger getMaxNodesPerTranslateBrowsePathsToNodeIds() {
        return uint(0x1FFFF);
    }

    default UInteger getMaxNodesPerNodeManagement() {
        return uint(0x1FFFF);
    }

    default UInteger getMaxMonitoredItemsPerCall() {
        return uint(0x1FFFF);
    }

}
