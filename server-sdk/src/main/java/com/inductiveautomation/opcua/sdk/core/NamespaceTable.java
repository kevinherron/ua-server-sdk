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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaRuntimeException;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class NamespaceTable {

    public static String OpcUaNamespace = "http://opcfoundation.org/UA/";

    private final BiMap<Integer, String> uriTable = HashBiMap.create();

    public NamespaceTable() {
        uriTable.put(0, OpcUaNamespace);
    }

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

    /**
     * @param uri the namespace URI to look up.
     * @return the index of the namespace URI, or -1 if it is not present.
     */
    public synchronized int getIndex(String uri) {
        return uriTable.inverse().getOrDefault(uri, -1);
    }

    public synchronized String[] toArray() {
        List<String> uris = uriTable.entrySet().stream()
                .sorted((e1, e2) -> e1.getKey() - e2.getKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        return uris.toArray(new String[uris.size()]);
    }

}
