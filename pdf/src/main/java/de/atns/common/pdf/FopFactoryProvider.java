package de.atns.common.pdf;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.apache.fop.apps.FOURIResolver;
import org.apache.fop.apps.FopFactory;

/**
 * @author tbaum
 * @since 28.11.2009
 */
public class FopFactoryProvider implements Provider<FopFactory> {

    private final Provider<FOURIResolver> uriResolver;

    @Inject public FopFactoryProvider(final Provider<FOURIResolver> uriResolver) {
        this.uriResolver = uriResolver;
    }

    @Override public FopFactory get() {
        final FopFactory fopFactory = FopFactory.newInstance();
        fopFactory.setURIResolver(uriResolver.get());

        //TODO remove this in further versions
        fopFactory.getRendererFactory().setRendererPreferred(true);

        return fopFactory;
    }
}
