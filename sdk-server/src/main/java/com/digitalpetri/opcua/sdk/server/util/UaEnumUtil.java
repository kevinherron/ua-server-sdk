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

package com.digitalpetri.opcua.sdk.server.util;

import java.util.EnumSet;
import java.util.List;

import com.google.common.collect.Lists;
import com.digitalpetri.opcua.stack.core.types.enumerated.BrowseResultMask;
import com.digitalpetri.opcua.stack.core.types.enumerated.NodeClass;

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
