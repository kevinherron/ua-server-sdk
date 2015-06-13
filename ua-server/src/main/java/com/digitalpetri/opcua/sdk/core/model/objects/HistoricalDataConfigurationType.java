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

import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.enumerated.ExceptionDeviationFormat;

public interface HistoricalDataConfigurationType extends BaseObjectType {

    AggregateConfigurationType getAggregateConfiguration();

    FolderType getAggregateFunctions();

    Boolean getStepped();

    String getDefinition();

    Double getMaxTimeInterval();

    Double getMinTimeInterval();

    Double getExceptionDeviation();

    ExceptionDeviationFormat getExceptionDeviationFormat();

    DateTime getStartOfArchive();

    DateTime getStartOfOnlineArchive();

    void setStepped(Boolean stepped);

    void setDefinition(String definition);

    void setMaxTimeInterval(Double maxTimeInterval);

    void setMinTimeInterval(Double minTimeInterval);

    void setExceptionDeviation(Double exceptionDeviation);

    void setExceptionDeviationFormat(ExceptionDeviationFormat exceptionDeviationFormat);

    void setStartOfArchive(DateTime startOfArchive);

    void setStartOfOnlineArchive(DateTime startOfOnlineArchive);

}
