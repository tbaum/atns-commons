package de.atns.common.pdf;

import com.google.inject.AbstractModule;
import org.apache.fop.apps.FOURIResolver;
import org.apache.fop.apps.FopFactory;

import javax.xml.transform.TransformerFactory;

/**
 * @author tbaum
 * @since 02.11.10
 */
public class PdfModule extends AbstractModule {
// -------------------------- OTHER METHODS --------------------------

    @Override protected void configure() {
        bind(FopFactory.class).toProvider(FopFactoryProvider.class);
        bind(TransformerFactory.class).toProvider(TransformerFactoryProvider.class);
        bind(FOURIResolver.class).to(SecurityManagerWorkaroundResolver.class);
    }
}
