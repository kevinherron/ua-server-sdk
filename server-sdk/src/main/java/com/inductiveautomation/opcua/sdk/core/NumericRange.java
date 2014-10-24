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
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;

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

    private int getDimensionCount() {
        return bounds.length;
    }

    private Bounds getBounds(int dimension) {
        return bounds[dimension - 1];
    }

    public static final class Bounds {
        private final int low;
        private final int high;

        private Bounds(int low, int high) throws UaException {
            if (low < 0 || high < 0 || low > high) throw new UaException(StatusCodes.Bad_IndexRangeInvalid);

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

                if (bs.length == 1) {
                    int index = Integer.parseInt(bs[0]);
                    bounds[i] = new Bounds(index, index);
                } else if (bs.length == 2) {
                    int low = Integer.parseInt(bs[0]);
                    int high = Integer.parseInt(bs[1]);

                    if (low == high) throw new UaException(StatusCodes.Bad_IndexRangeInvalid);

                    bounds[i] = new Bounds(low, high);
                } else {
                    throw new UaException(StatusCodes.Bad_IndexRangeInvalid);
                }
            }

            return new NumericRange(range, bounds);
        } catch (Throwable ex) {
            throw new UaException(StatusCodes.Bad_IndexRangeInvalid, ex);
        }
    }

    public static Object readFromValueAtRange(Variant value, NumericRange range) throws UaException {
        Object array = value.getValue();
        if (array == null) throw new UaException(StatusCodes.Bad_IndexRangeNoData);

        try {
            return readFromValueAtRange(array, range, 1);
        } catch (Throwable ex) {
            throw new UaException(StatusCodes.Bad_IndexRangeNoData, ex);
        }
    }

    private static Object readFromValueAtRange(Object array, NumericRange range, int dimension) throws UaException {
        int dimensionCount = range.getDimensionCount();
        Bounds bounds = range.getBounds(dimension);
        int low = bounds.low, high = bounds.high;
        int len = high - low + 1;

        if (dimension == dimensionCount) {
            if (array.getClass().isArray()) {
                Class<?> type = array.getClass().getComponentType();
                Object a = Array.newInstance(type, len);

                for (int i = 0; i < len; i++) {
                    Object element = Array.get(array, low + i);
                    Array.set(a, i, element);
                }

                return a;
            } else if (array instanceof String) {
                String s = (String) array;

                return s.substring(low, high + 1);
            } else if (array instanceof ByteString) {
                ByteString bs = (ByteString) array;
                byte[] copy = Arrays.copyOfRange(bs.bytes(), low, high + 1);
                return new ByteString(copy);
            } else {
                throw new UaException(StatusCodes.Bad_IndexRangeNoData);
            }
        } else {
            Class<?> type = array.getClass().getComponentType();
            Object a = Array.newInstance(type, len);

            for (int i = 0; i < len; i++) {
                Object na = Array.get(array, low + i);
                Object element = readFromValueAtRange(na, range, dimension + 1);
                Array.set(a, i, element);
            }

            return a;
        }
    }

    public static Object writeToValueAtRange(Variant currentVariant, Variant updateVariant, NumericRange range) throws UaException {
        Object current = currentVariant.getValue();
        Object update = updateVariant.getValue();

        if (current == null || update == null) {
            throw new UaException(StatusCodes.Bad_IndexRangeNoData);
        }

        try {
            return writeToValueAtRange(current, update, range, 1);
        } catch (Throwable ex) {
            throw new UaException(StatusCodes.Bad_IndexRangeNoData, ex);
        }
    }

    private static Object writeToValueAtRange(Object current, Object update, NumericRange range, int dimension) throws UaException {
        int dimensionCount = range.getDimensionCount();
        Bounds bounds = range.getBounds(dimension);
        int low = bounds.low, high = bounds.high;

        if (dimension == dimensionCount) {
            if (current.getClass().isArray()) {
                Class<?> type = current.getClass().getComponentType();
                int length = Array.getLength(current);
                Object copy = Array.newInstance(type, length);

                if (low >= length || high >= length) {
                    throw new UaException(StatusCodes.Bad_IndexRangeNoData);
                }

                for (int i = 0; i < length; i++) {
                    if (i < low || i > high) {
                        Object element = Array.get(current, i);
                        Array.set(copy, i, element);
                    } else {
                        Object element = Array.get(update, i - low);
                        Array.set(copy, i, element);
                    }
                }

                return copy;
            } else if (current instanceof String) {
                String cs = (String) current;
                String us = (String) update;
                int length = cs.length();
                StringBuilder copy = new StringBuilder();

                if (low >= length || high >= length) {
                    throw new UaException(StatusCodes.Bad_IndexRangeNoData);
                }

                for (int i = 0; i < length; i++) {
                    if (i < low || i > high) {
                        copy.append(cs.charAt(i));
                    } else {
                        copy.append(us.charAt(i - low));
                    }
                }

                return copy.toString();
            } else if (current instanceof ByteString) {
                ByteString bs = (ByteString) current;
                ByteString us = (ByteString) update;
                int length = bs.length();
                byte[] copy = new byte[length];

                if (low >= length || high >= length) {
                    throw new UaException(StatusCodes.Bad_IndexRangeNoData);
                }

                for (int i = 0; i < length; i++) {
                    if (i < low || i > high) {
                        copy[i] = bs.byteAt(i);
                    } else {
                        copy[i] = us.byteAt(i - low);
                    }
                }

                return new ByteString(copy);
            } else {
                throw new UaException(StatusCodes.Bad_IndexRangeNoData);
            }
        } else {
            Class<?> type = current.getClass().getComponentType();
            int length = Array.getLength(current);
            Object copy = Array.newInstance(type, length);

            if (low >= length || high >= length) {
                throw new UaException(StatusCodes.Bad_IndexRangeNoData);
            }

            for (int i = 0; i < length; i++) {
                if (i < low || i > high) {
                    Object element = Array.get(current, i);
                    Array.set(copy, i, element);
                } else {
                    Object c = Array.get(current, i);
                    Object u = Array.get(update, i - low);
                    Object element = writeToValueAtRange(c, u, range, dimension + 1);
                    Array.set(copy, i, element);
                }
            }

            return copy;
        }
    }


}
