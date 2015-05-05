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
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.ushort;

public class NamespaceTable {

    public static final String OpcUaNamespace = "http://opcfoundation.org/UA/";

    private final BiMap<UShort, String> uriTable = HashBiMap.create();

    public NamespaceTable() {
        uriTable.put(ushort(0), OpcUaNamespace);
    }

    public synchronized UShort addUri(String uri) {
        UShort index = ushort(1);
        while (uriTable.containsKey(index)) {
            index = ushort(index.intValue() + 1);
            if (index.intValue() == 65535) {
                throw new UaRuntimeException(StatusCodes.Bad_InternalError, "uri table full");
            }
        }
        uriTable.put(index, uri);

        return index;
    }

    public synchronized void putUri(String uri, UShort index) {
        uriTable.put(index, uri);
    }

    public synchronized String getUri(UShort index) {
        return uriTable.get(index);
    }

    /**
     * @param uri the namespace URI to look up.
     * @return the index of the namespace URI, or {@code null} if it is not present.
     */
    public synchronized UShort getIndex(String uri) {
        return uriTable.inverse().getOrDefault(uri, null);
    }

    public synchronized String[] toArray() {
        List<String> uris = uriTable.entrySet().stream()
                .sorted((e1, e2) -> e1.getKey().intValue() - e2.getKey().intValue())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        return uris.toArray(new String[uris.size()]);
    }

}
