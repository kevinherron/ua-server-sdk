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
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.enumerated.ExceptionDeviationFormat;

public interface HistoricalDataConfigurationType extends BaseObjectType {

    Property<Boolean> STEPPED = new Property.BasicProperty<>(
            QualifiedName.parse("0:Stepped"),
            NodeId.parse("ns=0;i=1"),
            -1,
            Boolean.class
    );

    Property<String> DEFINITION = new Property.BasicProperty<>(
            QualifiedName.parse("0:Definition"),
            NodeId.parse("ns=0;i=12"),
            -1,
            String.class
    );

    Property<Double> MAX_TIME_INTERVAL = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxTimeInterval"),
            NodeId.parse("ns=0;i=290"),
            -1,
            Double.class
    );

    Property<Double> MIN_TIME_INTERVAL = new Property.BasicProperty<>(
            QualifiedName.parse("0:MinTimeInterval"),
            NodeId.parse("ns=0;i=290"),
            -1,
            Double.class
    );

    Property<Double> EXCEPTION_DEVIATION = new Property.BasicProperty<>(
            QualifiedName.parse("0:ExceptionDeviation"),
            NodeId.parse("ns=0;i=11"),
            -1,
            Double.class
    );

    Property<ExceptionDeviationFormat> EXCEPTION_DEVIATION_FORMAT = new Property.BasicProperty<>(
            QualifiedName.parse("0:ExceptionDeviationFormat"),
            NodeId.parse("ns=0;i=890"),
            -1,
            ExceptionDeviationFormat.class
    );

    Property<DateTime> START_OF_ARCHIVE = new Property.BasicProperty<>(
            QualifiedName.parse("0:StartOfArchive"),
            NodeId.parse("ns=0;i=294"),
            -1,
            DateTime.class
    );

    Property<DateTime> START_OF_ONLINE_ARCHIVE = new Property.BasicProperty<>(
            QualifiedName.parse("0:StartOfOnlineArchive"),
            NodeId.parse("ns=0;i=294"),
            -1,
            DateTime.class
    );

    Boolean getStepped();

    PropertyType getSteppedNode();

    void setStepped(Boolean value);

    String getDefinition();

    PropertyType getDefinitionNode();

    void setDefinition(String value);

    Double getMaxTimeInterval();

    PropertyType getMaxTimeIntervalNode();

    void setMaxTimeInterval(Double value);

    Double getMinTimeInterval();

    PropertyType getMinTimeIntervalNode();

    void setMinTimeInterval(Double value);

    Double getExceptionDeviation();

    PropertyType getExceptionDeviationNode();

    void setExceptionDeviation(Double value);

    ExceptionDeviationFormat getExceptionDeviationFormat();

    PropertyType getExceptionDeviationFormatNode();

    void setExceptionDeviationFormat(ExceptionDeviationFormat value);

    DateTime getStartOfArchive();

    PropertyType getStartOfArchiveNode();

    void setStartOfArchive(DateTime value);

    DateTime getStartOfOnlineArchive();

    PropertyType getStartOfOnlineArchiveNode();

    void setStartOfOnlineArchive(DateTime value);

    AggregateConfigurationType getAggregateConfigurationNode();

    FolderType getAggregateFunctionsNode();

}
