/*
 * OPC-UA SDK
 *
 * Copyright (C) 2014 Kevin Herron
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

package com.inductiveautomation.opcua.sdk.server.util;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class RingBuffer<E> {

    private int read = 0;
    private int write = 0;
    private int count = 0;

    private final E[] buffer;
    private final int maxSize;

    public RingBuffer(int maxSize) {
        this.maxSize = maxSize;

        //noinspection unchecked
        buffer = (E[]) new Object[maxSize];
    }

    /**
     * Get the element at the specified index. This does not affect the next element to be returned.
     *
     * @param index The specified index.
     * @return The element at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of bounds...
     */
    public E get(int index) {
        if (index >= count) {
            throw new IndexOutOfBoundsException("index=" + index);
        } else {
            return buffer[(read + index) % maxSize];
        }
    }

    /**
     * Set the element at the specified index. This does not affect the next element to be returned.
     *
     * @param index The specified index.
     * @param e     The element to set.
     * @throws IndexOutOfBoundsException if the index is out of bounds...
     */
    public void set(int index, E e) {
        if (index >= count) {
            throw new IndexOutOfBoundsException("index=" + index);
        } else {
            buffer[(read + index) % maxSize] = e;
        }
    }

    /**
     * Add an element to the buffer, replacing the oldest element in the buffer if full.
     *
     * @param e element to add.
     */
    public void add(E e) {
        buffer[write] = e;
        write = (write + 1) % maxSize;

        if (count == maxSize) {
            read = (read + 1) % maxSize;
        } else {
            count += 1;
        }
    }

    /**
     * @return The next (oldest) element in the buffer.
     * @throws NoSuchElementException if the buffer is empty.
     */
    public E remove() {
        if (count <= 0) {
            throw new NoSuchElementException();
        } else {
            E e = buffer[read];
            buffer[read] = null;
            read = (read + 1) % maxSize;
            count -= 1;
            return e;
        }
    }

    /**
     * Clear the contents of this buffer.
     */
    public void clear() {
        read = write = count = 0;
        Arrays.fill(buffer, null);
    }

    /**
     * @return {@code true} if the buffer is empty (size == 0).
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * @return The maximum allowed size (number of elements).
     */
    public int maxSize() {
        return maxSize;
    }

    /**
     * @return The current size (number of elements).
     */
    public int size() {
        return count;
    }

}
