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

package com.inductiveautomation.opcua.sdk.core.nodes;

import java.util.List;
import java.util.Optional;

import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.types.structured.Argument;

public interface MethodNode extends Node {

    /**
     * The Executable attribute indicates whether the Method is executable, not taking user access rights into account.
     * If the OPC UA Server cannot get the Executable information from the underlying system, it should state that it
     * is executable.
     * <p>
     * See OPC-UA Part 3, Section 5.7.
     *
     * @return {@code true} if this method is executable, not taking user access rights into account.
     */
    Boolean isExecutable();

    /**
     * The UserExecutable attribute indicates whether the Method is executable, taking user access rights into account.
     * If the OPC UA Server cannot get any user rights related information from the underlying system, it should use
     * the same value as used in the Executable attribute.
     * <p>
     * See OPC-UA Part 3, Section 5.7.
     *
     * @return {@code true} if this method is executable, taking user access rights into account.
     */
    Boolean isUserExecutable();

    /**
     * Set the Executable attribute of this Method.
     *
     * @param executable {@code true} if the method is executable.
     */
    void setExecutable(boolean executable);

    /**
     * Set the UserExecutable attribute of this Method.
     *
     * @param userExecutable {@code true} if the method is executable, taking access rights into account.
     */
    void setUserExecutable(boolean userExecutable);

    Optional<String> getNodeVersion();

    Optional<Argument[]> getInputArguments();

    Optional<Argument[]> getOutputArguments();

    void setNodeVersion(Optional<String> nodeVersion);

    void setInputArguments(Optional<Argument[]> inputArguments);

    void setOutputArguments(Optional<Argument[]> outputArguments);

}
