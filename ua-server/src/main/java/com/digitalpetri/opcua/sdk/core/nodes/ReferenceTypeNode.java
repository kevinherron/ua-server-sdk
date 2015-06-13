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

import java.util.Optional;

import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;

public interface ReferenceTypeNode extends Node {

    Boolean getIsAbstract();

    Boolean getSymmetric();

    Optional<LocalizedText> getInverseName();

    void setIsAbstract(boolean isAbstract);

    void setSymmetric(boolean isSymmetric);

    void setInverseName(Optional<LocalizedText> inverseName);

}
