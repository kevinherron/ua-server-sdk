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

package com.inductiveautomation.opcua.sdk.core;

import java.lang.reflect.Array;
import java.util.Arrays;

import com.google.common.base.Objects;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.util.ArrayUtil;

public final class NumericRange {

    private final String range;
    private final Bounds[] bounds;

    public NumericRange(String range, Bounds[] bounds) {
        this.range = range;
        this.bounds = bounds;
    }

    public String getRange() {
        return range;
    }

    public Bounds[] getBounds() {
        return bounds;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("range", range)
                .toString();
    }

    private int[] getDimensionLengths() {
        int[] dimensionLengths = new int[bounds.length];
        for (int i = 0; i < bounds.length; i++) {
            dimensionLengths[i] = bounds[i].high - bounds[i].low + 1;
        }
        return dimensionLengths;
    }

    private Bounds getBounds(int dimension) {
        return bounds[dimension - 1];
    }

    public static final class Bounds {
        private final int low;
        private final int high;

        private Bounds(int low, int high) {
            this.low = low;
            this.high = high;
        }
    }

    public static NumericRange parse(String range) throws UaException {
        try {
            String[] ss = range.split(",");
            Bounds[] bounds = new Bounds[ss.length];

            for (int i = 0; i < ss.length; i++) {
                String s = ss[i];
                String[] bs = s.split(":");
                int low = Integer.parseInt(bs[0]);
                int high = Integer.parseInt(bs[1]);
                bounds[i] = new Bounds(low, high);
            }

            return new NumericRange(range, bounds);
        } catch (Throwable ex) {
            throw new UaException(StatusCodes.Bad_IndexRangeInvalid, ex);
        }
    }

    public static Object readFromValueAtRange(Variant value, NumericRange range) throws UaException {
        Object array = value.getValue();
        if (array == null) throw new UaException(StatusCodes.Bad_IndexRangeNoData);

        Class<?> type = ArrayUtil.getType(array);

        try {
            return readDimension(array, type, range, range.getDimensionLengths(), 1);
        } catch (Throwable ex) {
            throw new UaException(StatusCodes.Bad_IndexRangeNoData, ex);
        }
    }

    private static Object readDimension(Object array, Class<?> type, NumericRange range, int[] dimensions, int dimension) throws UaException {
        Bounds bounds = range.getBounds(dimension);
        int low = bounds.low, high = bounds.high;

        if (dimensions.length == 1) {
            int len = high - low + 1;

            Object a = Array.newInstance(type, len);

            for (int i = 0; i < len; i++) {
                Object element = Array.get(array, low + i);
                Array.set(a, i, element);
            }

            return a;
        } else {
            int len = high - low + 1;
            Object a = Array.newInstance(type, dimensions);

            int[] tail = Arrays.copyOfRange(dimensions, 1, dimensions.length);

            for (int i = 0; i < len; i++) {
                Object na = Array.get(array, low + i);
                Object element = readDimension(na, type, range, tail, dimension + 1);
                Array.set(a, i, element);
            }

            return a;
        }
    }

    public static Object writeToValueAtRange(Variant write, Variant value, NumericRange range) {
        return null; // TODO
    }

}
