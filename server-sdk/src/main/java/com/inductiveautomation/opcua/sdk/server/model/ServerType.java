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

package com.inductiveautomation.opcua.sdk.server.model;

import java.util.function.Function;

import com.inductiveautomation.opcua.sdk.core.model.BaseObjectType;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.structured.ServerStatusDataType;

public interface ServerType extends BaseObjectType {

    String[] getServerArray();

    String[] getNamespaceArray();

    ServerStatusDataType getServerStatus();

    UByte getServiceLevel();

    Boolean getAuditing();

    void setServerArray(Function<String[], String[]> updater);

    void setNamespaceArray(Function<String[], String[]> updater);

    void setServerStatus(Function<ServerStatusDataType, ServerStatusDataType> updater);

    void setServiceLevel(Function<UByte, UByte> updater);

    void setAuditing(Function<Boolean, Boolean> updater);

}
