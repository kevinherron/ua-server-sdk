package com.inductiveautomation.opcua.sdk.server.objects;

import java.util.Locale;

import com.inductiveautomation.opcua.stack.core.channel.ChannelConfig;
import com.inductiveautomation.opcua.stack.core.types.structured.SignedSoftwareCertificate;
import com.inductiveautomation.opcua.stack.core.util.annotations.UInt16;
import com.inductiveautomation.opcua.stack.core.util.annotations.UInt32;

public interface ServerCapabilities {

    /**
     * ServerProfileArray defines the conformance profiles of the Server.
     * <p>
     * See Part 7 for the definitions of Server profiles.
     *
     * @return the conformance profiles of the Server.
     */
    String[] getServerProfileArray();

    /**
     * LocaleIdArray is an array of LocaleIds that are known to be supported by the Server. The Server might not be
     * aware of all LocaleIds that it supports because it may provide access to underlying servers, systems or devices
     * that do not report the LocaleIds that they support.
     *
     * @return an array of LocaleIds that are known to be supported by the Server.
     */
    default String[] getLocaleIdArray() { return new String[] {Locale.ENGLISH.getLanguage()}; }

    /**
     * MinSupportedSampleRate defines the minimum supported sample rate, including 0, which is supported by the Server.
     *
     * @return the minimum supported sample rate, including 0, which is supported by the Server.
     */
    double getMinSupportedSampleRate();

    /**
     * MaxBrowseContinuationPoints is an integer specifying the maximum number of parallel continuation points of the
     * Browse Service that the Server can support per session. The value specifies the maximum the Server can support
     * under normal circumstances, so there is no guarantee the Server can always support the maximum.
     *
     * @return the maximum number of parallel continuation points of the Browse Service that the Server can support per
     * session.
     */
    @UInt16
    default int getMaxBrowseContinuationPoints() { return 32; }

    /**
     * MaxQueryContinuationPoints is an integer specifying the maximum number of parallel continuation points of the
     * QueryFirst Services that the Server can support per session. The value specifies the maximum the Server can
     * support under normal circumstances, so there is no guarantee the Server can always support the maximum.
     * <p>
     * The client should not open more QueryFirst calls with open continuation points than exposed in this Variable.
     * <p>
     * The value 0 indicates that the Server does not restrict the number of parallel continuation points the client
     * should use.
     *
     * @return the maximum number of parallel continuation points of the QueryFirst Services that the Server can
     * support per session.
     */
    @UInt16
    default int getMaxQueryContinuationPoints() { return 32; }

    /**
     * MaxHistoryContinuationPoints is an integer specifying the maximum number of parallel continuation points of the
     * HistoryRead Services that the Server can support per session. The value specifies the maximum the Server can
     * support under normal circumstances, so there is no guarantee the Server can always support the maximum.
     * <p>
     * The client should not open more HistoryRead calls with open continuation points than exposed in this Variable.
     * <p>
     * The value 0 indicates that the Server does not restrict the number of parallel continuation points the client
     * should use.
     *
     * @return the maximum number of parallel continuation points of the HistoryRead Services that the Server can
     * support per session.
     */
    @UInt16
    default int getMaxHistoryContinuationPoints() { return 32; }

    /**
     * SoftwareCertificates is an array of SignedSoftwareCertificates containing all SoftwareCertificates supported by
     * the Server. A SoftwareCertificate identifies capabilities of the Server. It contains the list of Profiles
     * supported by the Server. Profiles are described in Part 7.
     *
     * @return an array of SignedSoftwareCertificates containing all SoftwareCertificates supported by the Server.
     */
    SignedSoftwareCertificate[] getSoftwareCertificates();

    /**
     * The MaxArrayLength Property indicates the maximum length of a one or multidimensional array supported by
     * Variables of the Server. In a multidimensional array it indicates the overall length. For example, a
     * three-dimensional array of 2x3x10 has the array length of 60.
     * <p>
     * The Server might further restrict the length for individual Variables without notice to the client. Servers may
     * use the Property MaxArrayLength defined in Part 3 on individual DataVariables to specify the size on individual
     * values. The individual Property may have a larger or smaller value than MaxArrayLength.
     * <p>
     * Defaults to {@link ChannelConfig#DEFAULT_MAX_ARRAY_LENGTH}. If a ChannelConfig with a non-default value is used
     * this should be modified accordingly.
     *
     * @return the maximum length of a one or multidimensional array supported by Variables of the Server.
     */
    @UInt32
    default long getMaxArrayLength() { return ChannelConfig.DEFAULT_MAX_ARRAY_LENGTH; }

    /**
     * The MaxStringLength Property indicates the maximum length of Strings supported by Variables of the Server.
     * <p>
     * The Server might further restrict the String length for individual Variables without notice to the client.
     * Servers may use the Property MaxStringLength defined in Part 3 on individual DataVariables to specify the length
     * on individual values. The individual Property may have larger or smaller values than MaxStringLength.
     * <p>
     * Defaults to {@link ChannelConfig#DEFAULT_MAX_STRING_LENGTH}. If a ChannelConfig with a non-default value is used
     * this should be modified accordingly.
     *
     * @return the maximum length of Strings supported by Variables of the Server.
     */
    @UInt32
    default long getMaxStringLength() { return ChannelConfig.DEFAULT_MAX_STRING_LENGTH; }

    /**
     * OperationLimits is an entry point to access information on operation limits of the Server, for example the
     * maximum length of an array in a read Service call.
     *
     * @return an entry point to access information on operation limits of the Server.
     */
    OperationLimits getOperationLimits();

}
