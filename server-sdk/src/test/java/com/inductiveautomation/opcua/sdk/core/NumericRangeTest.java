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

import java.util.Arrays;

import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class NumericRangeTest {

    @Test(dataProvider = "getArray1dRanges")
    public void testArray1d(String range, int[] expected) throws UaException {
        NumericRange nr = NumericRange.parse(range);
        Variant value = new Variant(array1d);

        Object result = NumericRange.readFromValueAtRange(value, nr);

        assertTrue(result instanceof int[]);
        assertTrue(Arrays.equals(expected, (int[]) result));
    }

    @Test(dataProvider = "getArray2dRanges")
    public void testArray2d(String range, int[][] expected) throws UaException {
        NumericRange nr = NumericRange.parse(range);
        Variant value = new Variant(array2d);

        Object result = NumericRange.readFromValueAtRange(value, nr);

        assertTrue(result instanceof int[][]);
        assertTrue(Arrays.deepEquals(expected, (int[][]) result));
    }

    @Test(dataProvider = "getArray3dRanges")
    public void testArray3d(String range, int[][][] expected) throws UaException {
        NumericRange nr = NumericRange.parse(range);
        Variant value = new Variant(array3d);

        Object result = NumericRange.readFromValueAtRange(value, nr);

        assertTrue(result instanceof int[][][]);
        assertTrue(Arrays.deepEquals(expected, (int[][][]) result));
    }

    @Test
    public void testString1d() throws UaException {
        NumericRange nr = NumericRange.parse("1:2");
        Variant value = new Variant("abcdef");

        Object result = NumericRange.readFromValueAtRange(value, nr);

        assertTrue(result instanceof String);
        assertTrue("bc".equals(result));
    }

    @Test
    public void testString2d_1() throws UaException {
        NumericRange nr = NumericRange.parse("0:1,3:4");
        Variant value = new Variant(new String[]{
                "abcdef",
                "ghijkl"
        });

        Object result = NumericRange.readFromValueAtRange(value, nr);

        assertTrue(result instanceof String[]);
        assertTrue(Arrays.deepEquals(new String[]{"de", "jk"}, (String[]) result));
    }

    @Test
    public void testString2d_2() throws UaException {
        NumericRange nr = NumericRange.parse("0:2,0:2");
        Variant value = new Variant(new String[]{
                "abcdef",
                "ghijkl",
                "mnopqr"
        });

        Object result = NumericRange.readFromValueAtRange(value, nr);

        assertTrue(result instanceof String[]);
        assertTrue(Arrays.deepEquals(new String[]{"abc", "ghi", "mno"}, (String[]) result));
    }

    @Test
    public void testByteString1d() throws UaException {
        NumericRange nr = NumericRange.parse("1:2");
        Variant value = new Variant(new ByteString(new byte[]{1, 2, 3, 4}));

        Object result = NumericRange.readFromValueAtRange(value, nr);

        assertTrue(result instanceof ByteString);
        assertEquals(result, new ByteString(new byte[]{2, 3}));
    }

    @Test
    public void testByteString2d() throws UaException {
        NumericRange nr = NumericRange.parse("0:1,1:2");
        Variant value = new Variant(new ByteString[]{
                new ByteString(new byte[]{1, 2, 3, 4}),
                new ByteString(new byte[]{5, 6, 7, 8})
        });

        Object result = NumericRange.readFromValueAtRange(value, nr);

        assertTrue(result instanceof ByteString[]);

        ByteString[] expected = {
                new ByteString(new byte[]{2, 3}),
                new ByteString(new byte[]{6, 7})
        };

        assertTrue(Arrays.deepEquals((ByteString[]) result, expected));
    }

    private static final int[] array1d = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    @DataProvider
    private static Object[][] getArray1dRanges() {
        return new Object[][]{
                {"0:3", new int[]{0, 1, 2, 3}},
                {"4:9", new int[]{4, 5, 6, 7, 8, 9}},
                {"1:4", new int[]{1, 2, 3, 4}},
                {"5:8", new int[]{5, 6, 7, 8}},
                {"0:0", new int[]{0}},
                {"5:5", new int[]{5}},
                {"5", new int[]{5}},
                {"9:9", new int[]{9}},
                {"9", new int[]{9}}
        };
    }


    private static final int[][] array2d = new int[][]{
            {0, 1, 2, 3},
            {4, 5, 6, 7},
            {8, 9, 10, 11},
            {12, 13, 14, 15}
    };

    @DataProvider
    private static Object[][] getArray2dRanges() {
        return new Object[][]{
                {"0:1,0:1", new int[][]{{0, 1}, {4, 5}}},
                {"1:2,1:3", new int[][]{{5, 6, 7}, {9, 10, 11}}},
                {"3:3,3:3", new int[][]{{15}}},
                {"3,3", new int[][]{{15}}}
        };
    }

    private static final int[][][] array3d = new int[][][]{
            {{0, 1}, {2, 3}, {4, 5}},
            {{6, 7}, {8, 9}, {10, 11}},
            {{12, 13}, {14, 15}, {16, 17}, {18, 19}},
            {{20, 21}, {22, 23}, {24, 25}, {26, 27}}
    };

    @DataProvider
    private static Object[][] getArray3dRanges() {
        return new Object[][]{
                {"0:1,0:1,0:1", new int[][][]{
                        {{0, 1}, {2, 3}},
                        {{6, 7}, {8, 9}}}
                },
                {"1:2,1:2,0:1", new int[][][]{
                        {{8, 9}, {10, 11}},
                        {{14, 15}, {16, 17}}}
                },
                {"3:3,2:2,0:0,", new int[][][]{
                        {{24}}}
                },
                {"3,2,0", new int[][][]{
                        {{24}}}
                }

        };
    }

}
