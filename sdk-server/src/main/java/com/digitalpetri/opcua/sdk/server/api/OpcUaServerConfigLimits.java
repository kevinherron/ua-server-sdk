/*
 * digitalpetri OPC-UA SDK
 *
 * Copyright (C) 2015 Kevin Herron
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

package com.digitalpetri.opcua.sdk.server.api;

import java.util.concurrent.TimeUnit;

import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;
import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.ushort;

public interface OpcUaServerConfigLimits {

    default UInteger getMaxSessionCount() {
        return uint(550);
    }

    default Double getMinSupportedSampleRate() {
        return 100.0;
    }

    default Double getMaxSupportedSampleRate() {
        return (double) TimeUnit.MILLISECONDS.convert(24, TimeUnit.HOURS);
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
