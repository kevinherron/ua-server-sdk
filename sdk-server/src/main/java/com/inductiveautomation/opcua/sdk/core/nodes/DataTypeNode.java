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

public interface DataTypeNode extends Node {

    /**
     * The IsAbstract attribute specifies if the DataType is abstract or not.
     * <p>
     * Abstract DataTypes can be used in the AddressSpace, i.e. Variables and VariableTypes can point with their
     * DataType Attribute to an abstract DataType. However, concrete values can never be of an abstract DataType and
     * shall always be of a concrete subtype of the abstract DataType.
     *
     * @return {@code true} if the DataType is abstract.
     */
    Boolean getIsAbstract();

    /**
     * Set the IsAbstract attribute of this DataType.
     *
     * @param isAbstract {@code true} if this
     */
    void setIsAbstract(boolean isAbstract);

}
