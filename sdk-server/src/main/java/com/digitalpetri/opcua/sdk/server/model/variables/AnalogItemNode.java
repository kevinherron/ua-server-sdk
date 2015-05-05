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
import com.digitalpetri.opcua.sdk.core.model.variables.AnalogItemType;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.util.UaVariableType;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.structured.EUInformation;
import com.digitalpetri.opcua.stack.core.types.structured.Range;

@UaVariableType(name = "AnalogItemType")
public class AnalogItemNode extends DataItemNode implements AnalogItemType {

    public AnalogItemNode(UaNamespace namespace,
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
    @UaOptional("InstrumentRange")
    public Range getInstrumentRange() {
        Optional<Range> instrumentRange = getProperty("InstrumentRange");

        return instrumentRange.orElse(null);
    }

    @Override
    @UaMandatory("EURange")
    public Range getEURange() {
        Optional<Range> eURange = getProperty("EURange");

        return eURange.orElse(null);
    }

    @Override
    @UaOptional("EngineeringUnits")
    public EUInformation getEngineeringUnits() {
        Optional<EUInformation> engineeringUnits = getProperty("EngineeringUnits");

        return engineeringUnits.orElse(null);
    }

    @Override
    public synchronized void setInstrumentRange(Range instrumentRange) {
        getPropertyNode("InstrumentRange").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(instrumentRange)));
        });
    }

    @Override
    public synchronized void setEURange(Range eURange) {
        getPropertyNode("EURange").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(eURange)));
        });
    }

    @Override
    public synchronized void setEngineeringUnits(EUInformation engineeringUnits) {
        getPropertyNode("EngineeringUnits").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(engineeringUnits)));
        });
    }

}
