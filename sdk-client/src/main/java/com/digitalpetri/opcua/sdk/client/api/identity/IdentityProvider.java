package com.digitalpetri.opcua.sdk.client.api.identity;

import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.structured.EndpointDescription;
import com.digitalpetri.opcua.stack.core.types.structured.SignatureData;
import com.digitalpetri.opcua.stack.core.types.structured.UserIdentityToken;
import org.jooq.lambda.tuple.Tuple2;

public interface IdentityProvider {

    /**
     * Return the {@link UserIdentityToken} and {@link SignatureData} (if applicable for the token) to use when
     * activating a session.
     *
     * @param endpoint the {@link EndpointDescription} being connected to.
     * @return a {@link Tuple2} containing the {@link UserIdentityToken} and {@link SignatureData}.
     */
    Tuple2<UserIdentityToken, SignatureData> getIdentityToken(EndpointDescription endpoint,
                                                              ByteString serverNonce) throws Exception;

}
