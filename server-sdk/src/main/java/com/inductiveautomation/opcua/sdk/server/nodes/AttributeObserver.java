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

package com.inductiveautomation.opcua.sdk.server.nodes;

public interface AttributeObserver {

    /**
     * The Attribute indicated by {@code attributeId} on {@code node} changed.
     *
     * @param node        the {@link UaNode} the change originated from.
     * @param attributeId the id of the Attribute that changed.
     * @param value       the new value of the attribute.
     */
    void attributeChanged(UaNode node, int attributeId, Object value);

}
