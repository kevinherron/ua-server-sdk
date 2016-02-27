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

package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.sdk.core.model.variables.PropertyType;
import com.digitalpetri.opcua.sdk.server.model.Property;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;

public interface AggregateConfigurationType extends BaseObjectType {

    Property<Boolean> TREAT_UNCERTAIN_AS_BAD = new Property.BasicProperty<>(
            QualifiedName.parse("0:TreatUncertainAsBad"),
            NodeId.parse("ns=0;i=1"),
            -1,
            Boolean.class
    );

    Property<UByte> PERCENT_DATA_BAD = new Property.BasicProperty<>(
            QualifiedName.parse("0:PercentDataBad"),
            NodeId.parse("ns=0;i=3"),
            -1,
            UByte.class
    );

    Property<UByte> PERCENT_DATA_GOOD = new Property.BasicProperty<>(
            QualifiedName.parse("0:PercentDataGood"),
            NodeId.parse("ns=0;i=3"),
            -1,
            UByte.class
    );

    Property<Boolean> USE_SLOPED_EXTRAPOLATION = new Property.BasicProperty<>(
            QualifiedName.parse("0:UseSlopedExtrapolation"),
            NodeId.parse("ns=0;i=1"),
            -1,
            Boolean.class
    );

    Boolean getTreatUncertainAsBad();

    PropertyType getTreatUncertainAsBadNode();

    void setTreatUncertainAsBad(Boolean value);

    UByte getPercentDataBad();

    PropertyType getPercentDataBadNode();

    void setPercentDataBad(UByte value);

    UByte getPercentDataGood();

    PropertyType getPercentDataGoodNode();

    void setPercentDataGood(UByte value);

    Boolean getUseSlopedExtrapolation();

    PropertyType getUseSlopedExtrapolationNode();

    void setUseSlopedExtrapolation(Boolean value);
}
