package de.atns.common.pdf;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.apache.fop.apps.FOURIResolver;

import javax.xml.transform.TransformerFactory;

/**
 * @author tbaum
 * @since 28.11.2009
 */
public class TransformerFactoryProvider implements Provider<TransformerFactory> {
// ------------------------------ FIELDS ------------------------------

    private final Provider<FOURIResolver> uriResolver;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public TransformerFactoryProvider(final Provider<FOURIResolver> uriResolver) {
        this.uriResolver = uriResolver;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Provider ---------------------

    public TransformerFactory get() {
        final TransformerFactory fac = TransformerFactory.newInstance();
        fac.setURIResolver(uriResolver.get());
        return fac;
    }
}
