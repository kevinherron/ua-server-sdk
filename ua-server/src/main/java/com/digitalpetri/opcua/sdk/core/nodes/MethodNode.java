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

package com.digitalpetri.opcua.sdk.core.nodes;

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

}
