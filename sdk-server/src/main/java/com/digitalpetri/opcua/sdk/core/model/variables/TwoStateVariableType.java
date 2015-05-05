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

package com.digitalpetri.opcua.sdk.core.model.variables;

import com.digitalpetri.opcua.sdk.core.model.UaMandatory;
import com.digitalpetri.opcua.sdk.core.model.UaOptional;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;

public interface TwoStateVariableType extends StateVariableType {

    @UaMandatory("Id")
    Boolean getId();

    @UaOptional("TransitionTime")
    DateTime getTransitionTime();

    @UaOptional("EffectiveTransitionTime")
    DateTime getEffectiveTransitionTime();

    @UaOptional("TrueState")
    LocalizedText getTrueState();

    @UaOptional("FalseState")
    LocalizedText getFalseState();

    void setId(Boolean id);

    void setTransitionTime(DateTime transitionTime);

    void setEffectiveTransitionTime(DateTime effectiveTransitionTime);

    void setTrueState(LocalizedText trueState);

    void setFalseState(LocalizedText falseState);

}
