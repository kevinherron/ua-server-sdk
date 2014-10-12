package com.inductiveautomation.opcua.sdk.server.util;

import java.util.EnumSet;
import java.util.List;

import com.inductiveautomation.opcua.stack.core.types.enumerated.BrowseResultMask;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;
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
