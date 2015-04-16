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

package com.digitalpetri.opcua.sdk.core;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaRuntimeException;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;

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
