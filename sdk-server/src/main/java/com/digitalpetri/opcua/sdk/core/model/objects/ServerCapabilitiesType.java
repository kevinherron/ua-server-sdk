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

import com.digitalpetri.opcua.sdk.core.model.variables.ServerVendorCapabilityType;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;
import com.digitalpetri.opcua.stack.core.types.structured.SignedSoftwareCertificate;

public interface ServerCapabilitiesType extends BaseObjectType {

    String[] getServerProfileArray();

    String[] getLocaleIdArray();

    Double getMinSupportedSampleRate();

    UShort getMaxBrowseContinuationPoints();

    UShort getMaxQueryContinuationPoints();

    UShort getMaxHistoryContinuationPoints();

    SignedSoftwareCertificate[] getSoftwareCertificates();

    UInteger getMaxArrayLength();

    UInteger getMaxStringLength();

    OperationLimitsType getOperationLimits();

    FolderType getModellingRules();

    FolderType getAggregateFunctions();

    ServerVendorCapabilityType getVendorCapability();

    void setServerProfileArray(String[] serverProfileArray);

    void setLocaleIdArray(String[] localeIdArray);

    void setMinSupportedSampleRate(Double minSupportedSampleRate);

    void setMaxBrowseContinuationPoints(UShort maxBrowseContinuationPoints);

    void setMaxQueryContinuationPoints(UShort maxQueryContinuationPoints);

    void setMaxHistoryContinuationPoints(UShort maxHistoryContinuationPoints);

    void setSoftwareCertificates(SignedSoftwareCertificate[] softwareCertificates);

    void setMaxArrayLength(UInteger maxArrayLength);

    void setMaxStringLength(UInteger maxStringLength);

}
