package com.digitalpetri.opcua.sdk.client.api.identity;

import java.util.Iterator;
import java.util.List;

import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.structured.EndpointDescription;
import com.digitalpetri.opcua.stack.core.types.structured.SignatureData;
import com.digitalpetri.opcua.stack.core.types.structured.UserIdentityToken;
import com.google.common.collect.ImmutableList;
import org.jooq.lambda.tuple.Tuple2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A composite {@link IdentityProvider} that tries its component {@link IdentityProvider}s in the order provided.
 */
public class CompositeProvider implements IdentityProvider {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ImmutableList<IdentityProvider> providers;

    public CompositeProvider(IdentityProvider... providers) {
        this(ImmutableList.copyOf(providers));
    }

    public CompositeProvider(List<IdentityProvider> providers) {
        this.providers = ImmutableList.copyOf(providers);

    }

    @Override
    public Tuple2<UserIdentityToken, SignatureData> getIdentityToken(EndpointDescription endpoint,
                                                                     ByteString serverNonce) throws Exception {

        Iterator<IdentityProvider> iterator = providers.iterator();

        while (iterator.hasNext()) {
            IdentityProvider provider = iterator.next();

            try {
                return provider.getIdentityToken(endpoint, serverNonce);
            } catch (Exception e) {
                if (!iterator.hasNext()) {
                    throw e;
                }

                logger.debug("IdentityProvider={} failed, trying next...", provider.toString());
            }
        }

        throw new Exception("no sufficient UserTokenPolicy found");
    }

    @Override
    public String toString() {
        return "CompositeProvider{" +
                "providers=" + providers +
                '}';
    }

}
