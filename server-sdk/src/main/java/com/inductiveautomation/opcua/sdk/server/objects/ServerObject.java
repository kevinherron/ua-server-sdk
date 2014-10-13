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

package com.inductiveautomation.opcua.sdk.server.objects;

import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.structured.ServerStatusDataType;
import com.inductiveautomation.opcua.stack.core.util.annotations.UByte;

public interface ServerObject extends ObjectPrototype {

    @Override
    default NodeId getType() { return Identifiers.Server; }

    String[] getServerArray();

    String[] getNamespaceArray();

    ServerStatusDataType getServerStatus();

    @UByte
    short getServiceLevel();

    boolean isAuditing();

    ServerCapabilities getServerCapabilities();

    ServerDiagnostics getServerDiagnostics();

}
