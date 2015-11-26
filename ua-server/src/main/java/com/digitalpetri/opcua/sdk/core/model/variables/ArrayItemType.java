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

import com.digitalpetri.opcua.sdk.server.model.Property;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.enumerated.AxisScaleEnumeration;
import com.digitalpetri.opcua.stack.core.types.structured.EUInformation;
import com.digitalpetri.opcua.stack.core.types.structured.Range;


public interface ArrayItemType extends DataItemType {

    Property<Range> INSTRUMENT_RANGE = new Property.BasicProperty<>(
            QualifiedName.parse("0:InstrumentRange"),
            NodeId.parse("ns=0;i=884"),
            -1,
            Range.class
    );

    Property<Range> E_U_RANGE = new Property.BasicProperty<>(
            QualifiedName.parse("0:EURange"),
            NodeId.parse("ns=0;i=884"),
            -1,
            Range.class
    );

    Property<EUInformation> ENGINEERING_UNITS = new Property.BasicProperty<>(
            QualifiedName.parse("0:EngineeringUnits"),
            NodeId.parse("ns=0;i=887"),
            -1,
            EUInformation.class
    );

    Property<LocalizedText> TITLE = new Property.BasicProperty<>(
            QualifiedName.parse("0:Title"),
            NodeId.parse("ns=0;i=21"),
            -1,
            LocalizedText.class
    );

    Property<AxisScaleEnumeration> AXIS_SCALE_TYPE = new Property.BasicProperty<>(
            QualifiedName.parse("0:AxisScaleType"),
            NodeId.parse("ns=0;i=12077"),
            -1,
            AxisScaleEnumeration.class
    );


    Range getInstrumentRange();

    PropertyType getInstrumentRangeNode();

    void setInstrumentRange(Range value);

    Range getEURange();

    PropertyType getEURangeNode();

    void setEURange(Range value);

    EUInformation getEngineeringUnits();

    PropertyType getEngineeringUnitsNode();

    void setEngineeringUnits(EUInformation value);

    LocalizedText getTitle();

    PropertyType getTitleNode();

    void setTitle(LocalizedText value);

    AxisScaleEnumeration getAxisScaleType();

    PropertyType getAxisScaleTypeNode();

    void setAxisScaleType(AxisScaleEnumeration value);

}
