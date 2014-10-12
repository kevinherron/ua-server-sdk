package com.inductiveautomation.opcua.sdk.server.util;

import java.lang.reflect.Array;
import java.util.List;

public class ConversionUtil {

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(List<T> list, Class<T> c) {
        Object array = Array.newInstance(c, list.size());
        for (int i = 0; i < list.size(); i++) {
            Array.set(array, i, list.get(i));
        }
        return (T[]) array;
    }

    public static <T> T[] a(List<T> list, Class<T> c) {
        return toArray(list, c);
    }

}
