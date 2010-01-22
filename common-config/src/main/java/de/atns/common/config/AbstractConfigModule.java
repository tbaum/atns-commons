package de.atns.common.config;

import com.google.inject.AbstractModule;
import com.google.inject.util.Providers;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.annotation.Annotation;

/**
 * @author tbaum
 * @since 22.01.2010
 */
public abstract class AbstractConfigModule extends AbstractModule {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(AbstractConfigModule.class);
    private final Configurator config = new Configurator();

// -------------------------- OTHER METHODS --------------------------

    protected void configure(final Class<? extends Annotation> annotation) {
        String name = annotation.getAnnotation(ConfigurationName.class).value();

        final String prop = config.get(name);
        if (prop == null) {
            LOG.warn("configuration value for '" + config.getPropertyName(name) + "' is null");
            bind(String.class).annotatedWith(annotation).toProvider(Providers.of((String) null));
        } else
            bindConstant().annotatedWith(annotation).to(prop);
    }
}                                                   
