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
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;
import com.digitalpetri.opcua.stack.core.types.structured.SignedSoftwareCertificate;

public interface ServerCapabilitiesType extends BaseObjectType {

    Property<String[]> SERVER_PROFILE_ARRAY = new Property.BasicProperty<>(
            QualifiedName.parse("0:ServerProfileArray"),
            NodeId.parse("ns=0;i=12"),
            1,
            String[].class
    );

    Property<String[]> LOCALE_ID_ARRAY = new Property.BasicProperty<>(
            QualifiedName.parse("0:LocaleIdArray"),
            NodeId.parse("ns=0;i=295"),
            1,
            String[].class
    );

    Property<Double> MIN_SUPPORTED_SAMPLE_RATE = new Property.BasicProperty<>(
            QualifiedName.parse("0:MinSupportedSampleRate"),
            NodeId.parse("ns=0;i=290"),
            -1,
            Double.class
    );

    Property<UShort> MAX_BROWSE_CONTINUATION_POINTS = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxBrowseContinuationPoints"),
            NodeId.parse("ns=0;i=5"),
            -1,
            UShort.class
    );

    Property<UShort> MAX_QUERY_CONTINUATION_POINTS = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxQueryContinuationPoints"),
            NodeId.parse("ns=0;i=5"),
            -1,
            UShort.class
    );

    Property<UShort> MAX_HISTORY_CONTINUATION_POINTS = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxHistoryContinuationPoints"),
            NodeId.parse("ns=0;i=5"),
            -1,
            UShort.class
    );

    Property<SignedSoftwareCertificate[]> SOFTWARE_CERTIFICATES = new Property.BasicProperty<>(
            QualifiedName.parse("0:SoftwareCertificates"),
            NodeId.parse("ns=0;i=344"),
            1,
            SignedSoftwareCertificate[].class
    );

    Property<UInteger> MAX_ARRAY_LENGTH = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxArrayLength"),
            NodeId.parse("ns=0;i=7"),
            -1,
            UInteger.class
    );

    Property<UInteger> MAX_STRING_LENGTH = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxStringLength"),
            NodeId.parse("ns=0;i=7"),
            -1,
            UInteger.class
    );

    Property<UInteger> MAX_BYTE_STRING_LENGTH = new Property.BasicProperty<>(
            QualifiedName.parse("0:MaxByteStringLength"),
            NodeId.parse("ns=0;i=7"),
            -1,
            UInteger.class
    );

    String[] getServerProfileArray();

    PropertyType getServerProfileArrayNode();

    void setServerProfileArray(String[] value);

    String[] getLocaleIdArray();

    PropertyType getLocaleIdArrayNode();

    void setLocaleIdArray(String[] value);

    Double getMinSupportedSampleRate();

    PropertyType getMinSupportedSampleRateNode();

    void setMinSupportedSampleRate(Double value);

    UShort getMaxBrowseContinuationPoints();

    PropertyType getMaxBrowseContinuationPointsNode();

    void setMaxBrowseContinuationPoints(UShort value);

    UShort getMaxQueryContinuationPoints();

    PropertyType getMaxQueryContinuationPointsNode();

    void setMaxQueryContinuationPoints(UShort value);

    UShort getMaxHistoryContinuationPoints();

    PropertyType getMaxHistoryContinuationPointsNode();

    void setMaxHistoryContinuationPoints(UShort value);

    SignedSoftwareCertificate[] getSoftwareCertificates();

    PropertyType getSoftwareCertificatesNode();

    void setSoftwareCertificates(SignedSoftwareCertificate[] value);

    UInteger getMaxArrayLength();

    PropertyType getMaxArrayLengthNode();

    void setMaxArrayLength(UInteger value);

    UInteger getMaxStringLength();

    PropertyType getMaxStringLengthNode();

    void setMaxStringLength(UInteger value);

    UInteger getMaxByteStringLength();

    PropertyType getMaxByteStringLengthNode();

    void setMaxByteStringLength(UInteger value);

    OperationLimitsType getOperationLimitsNode();

    FolderType getModellingRulesNode();

    FolderType getAggregateFunctionsNode();

}
