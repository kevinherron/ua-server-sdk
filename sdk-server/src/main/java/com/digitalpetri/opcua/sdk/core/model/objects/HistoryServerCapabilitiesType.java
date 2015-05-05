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

import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface HistoryServerCapabilitiesType extends BaseObjectType {

    Boolean getAccessHistoryDataCapability();

    Boolean getAccessHistoryEventsCapability();

    UInteger getMaxReturnDataValues();

    UInteger getMaxReturnEventValues();

    Boolean getInsertDataCapability();

    Boolean getReplaceDataCapability();

    Boolean getUpdateDataCapability();

    Boolean getDeleteRawCapability();

    Boolean getDeleteAtTimeCapability();

    Boolean getInsertEventCapability();

    Boolean getReplaceEventCapability();

    Boolean getUpdateEventCapability();

    Boolean getDeleteEventCapability();

    Boolean getInsertAnnotationCapability();

    FolderType getAggregateFunctions();

    void setAccessHistoryDataCapability(Boolean accessHistoryDataCapability);

    void setAccessHistoryEventsCapability(Boolean accessHistoryEventsCapability);

    void setMaxReturnDataValues(UInteger maxReturnDataValues);

    void setMaxReturnEventValues(UInteger maxReturnEventValues);

    void setInsertDataCapability(Boolean insertDataCapability);

    void setReplaceDataCapability(Boolean replaceDataCapability);

    void setUpdateDataCapability(Boolean updateDataCapability);

    void setDeleteRawCapability(Boolean deleteRawCapability);

    void setDeleteAtTimeCapability(Boolean deleteAtTimeCapability);

    void setInsertEventCapability(Boolean insertEventCapability);

    void setReplaceEventCapability(Boolean replaceEventCapability);

    void setUpdateEventCapability(Boolean updateEventCapability);

    void setDeleteEventCapability(Boolean deleteEventCapability);

    void setInsertAnnotationCapability(Boolean insertAnnotationCapability);

}
