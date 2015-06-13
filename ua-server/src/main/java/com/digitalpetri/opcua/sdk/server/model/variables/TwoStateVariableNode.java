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

package com.digitalpetri.opcua.sdk.server.model.variables;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.model.UaMandatory;
import com.digitalpetri.opcua.sdk.core.model.UaOptional;
import com.digitalpetri.opcua.sdk.core.model.variables.TwoStateVariableType;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.util.UaVariableType;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaVariableType(name = "TwoStateVariableType")
public class TwoStateVariableNode extends StateVariableNode implements TwoStateVariableType {

    public TwoStateVariableNode(UaNamespace namespace,
                                NodeId nodeId,
                                QualifiedName browseName,
                                LocalizedText displayName,
                                Optional<LocalizedText> description,
                                Optional<UInteger> writeMask,
                                Optional<UInteger> userWriteMask,
                                DataValue value,
                                NodeId dataType,
                                Integer valueRank,
                                Optional<UInteger[]> arrayDimensions,
                                UByte accessLevel,
                                UByte userAccessLevel,
                                Optional<Double> minimumSamplingInterval,
                                boolean historizing) {

        super(namespace, nodeId, browseName, displayName, description, writeMask, userWriteMask,
                value, dataType, valueRank, arrayDimensions, accessLevel, userAccessLevel, minimumSamplingInterval, historizing);

    }

    @Override
    @UaMandatory("Id")
    public Boolean getId() {
        Optional<Boolean> id = getProperty("Id");

        return id.orElse(null);
    }

    @Override
    @UaOptional("TransitionTime")
    public DateTime getTransitionTime() {
        Optional<DateTime> transitionTime = getProperty("TransitionTime");

        return transitionTime.orElse(null);
    }

    @Override
    @UaOptional("EffectiveTransitionTime")
    public DateTime getEffectiveTransitionTime() {
        Optional<DateTime> effectiveTransitionTime = getProperty("EffectiveTransitionTime");

        return effectiveTransitionTime.orElse(null);
    }

    @Override
    @UaOptional("TrueState")
    public LocalizedText getTrueState() {
        Optional<LocalizedText> trueState = getProperty("TrueState");

        return trueState.orElse(null);
    }

    @Override
    @UaOptional("FalseState")
    public LocalizedText getFalseState() {
        Optional<LocalizedText> falseState = getProperty("FalseState");

        return falseState.orElse(null);
    }

    @Override
    public synchronized void setId(Boolean id) {
        getPropertyNode("Id").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(id)));
        });
    }

    @Override
    public synchronized void setTransitionTime(DateTime transitionTime) {
        getPropertyNode("TransitionTime").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(transitionTime)));
        });
    }

    @Override
    public synchronized void setEffectiveTransitionTime(DateTime effectiveTransitionTime) {
        getPropertyNode("EffectiveTransitionTime").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(effectiveTransitionTime)));
        });
    }

    @Override
    public synchronized void setTrueState(LocalizedText trueState) {
        getPropertyNode("TrueState").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(trueState)));
        });
    }

    @Override
    public synchronized void setFalseState(LocalizedText falseState) {
        getPropertyNode("FalseState").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(falseState)));
        });
    }

}
