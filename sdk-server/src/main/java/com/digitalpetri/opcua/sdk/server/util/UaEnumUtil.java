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

package com.digitalpetri.opcua.sdk.server.util;

import java.util.EnumSet;
import java.util.List;

import com.digitalpetri.opcua.stack.core.types.enumerated.BrowseResultMask;
import com.digitalpetri.opcua.stack.core.types.enumerated.NodeClass;
import com.google.common.collect.Lists;

public class UaEnumUtil {

    public static EnumSet<NodeClass> nodeClasses(long mask) {
        List<NodeClass> list = Lists.newArrayList();

        for (NodeClass nc : NodeClass.values()) {
            if ((mask & nc.getValue()) == nc.getValue()) {
                list.add(nc);
            }
        }

        return EnumSet.copyOf(list);
    }

    public static EnumSet<BrowseResultMask> browseResultMasks(long mask) {
        List<BrowseResultMask> list = Lists.newArrayList();

        for (BrowseResultMask brm : BrowseResultMask.values()) {
            if ((mask & brm.getValue()) == brm.getValue()) {
                list.add(brm);
            }
        }

        return EnumSet.copyOf(list);
    }

}
