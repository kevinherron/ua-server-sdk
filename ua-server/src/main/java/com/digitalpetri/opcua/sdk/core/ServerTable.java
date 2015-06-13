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

package com.digitalpetri.opcua.sdk.core;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaRuntimeException;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class ServerTable {

    private final BiMap<Integer, String> uriTable = HashBiMap.create();

    public synchronized int addUri(String uri) {
        int index = 1;
        while (uriTable.containsKey(index)) {
            index++;
            if (index == 65535) {
                throw new UaRuntimeException(StatusCodes.Bad_InternalError, "uri table full");
            }
        }
        uriTable.put(index, uri);
        return index;
    }

    public synchronized void putUri(String uri, int index) {
        uriTable.put(index, uri);
    }

    public synchronized String getUri(int index) {
        return uriTable.get(index);
    }

    public synchronized int getIndex(String uri) {
        return uriTable.inverse().get(uri);
    }

    public synchronized String[] toArray() {
        List<String> uris = uriTable.entrySet().stream()
                .sorted((e1, e2) -> e1.getKey() - e2.getKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        return uris.toArray(new String[uris.size()]);
    }

}
