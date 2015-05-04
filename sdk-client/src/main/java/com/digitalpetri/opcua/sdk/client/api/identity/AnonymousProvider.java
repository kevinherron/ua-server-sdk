package com.digitalpetri.opcua.sdk.client.api.identity;

import java.util.Arrays;

import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.enumerated.UserTokenType;
import com.digitalpetri.opcua.stack.core.types.structured.AnonymousIdentityToken;
import com.digitalpetri.opcua.stack.core.types.structured.EndpointDescription;
import com.digitalpetri.opcua.stack.core.types.structured.SignatureData;
import com.digitalpetri.opcua.stack.core.types.structured.UserIdentityToken;
import com.digitalpetri.opcua.stack.core.types.structured.UserTokenPolicy;
import org.jooq.lambda.tuple.Tuple2;

public class AnonymousProvider implements IdentityProvider {

    @Override
    public Tuple2<UserIdentityToken, SignatureData> getIdentityToken(EndpointDescription endpoint,
                                                                     ByteString serverNonce) throws Exception {
        String policyId = Arrays.stream(endpoint.getUserIdentityTokens())
                .filter(t -> t.getTokenType() == UserTokenType.Anonymous)
                .findFirst()
                .map(UserTokenPolicy::getPolicyId)
                .orElseThrow(() -> new Exception("no anonymous token policy found"));

        return new Tuple2<>(new AnonymousIdentityToken(policyId), new SignatureData());
    }

    @Override
    public String toString() {
        return "AnonymousProvider{}";
    }

}
