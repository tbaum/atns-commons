package de.atns.common.config;

import com.google.inject.AbstractModule;
import com.google.inject.MembersInjector;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import com.google.inject.util.Providers;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author tbaum
 * @since 22.01.2010
 */
public class ConfigModule extends AbstractModule {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(ConfigModule.class);
    private final Configurator config = new Configurator();

// -------------------------- OTHER METHODS --------------------------

    @Override protected final void configure() {
        bindListener(Matchers.any(), new TypeListener() {
            @Override public <I> void hear(final TypeLiteral<I> type, final TypeEncounter<I> encounter) {
                final Class<? super I> rawType = type.getRawType();
                for (final Field field : rawType.getDeclaredFields()) {
                    for (final Annotation annotation : field.getAnnotations()) {
                        final ConfigurationName cn = annotation.annotationType().getAnnotation(ConfigurationName.class);

                        if (cn != null) {
                            field.setAccessible(true);
                            final String value = ConfigModule.this.value(cn.value());
                            LOG.info("bind " + rawType.getName() + "#" + field.getName() + " to " + value);
                            encounter.register(new MembersInjector<I>() {
                                @Override public void injectMembers(final I instance) {
                                    try {
                                        field.set(instance, value);
                                    } catch (IllegalAccessException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });

        configureConfig();
    }

    protected String value(final String name) {
        return config.get(name);
    }

    protected void configureConfig() {
    }

    protected void configure(final Class<? extends Annotation> annotation) {
        final String name = annotation.getAnnotation(ConfigurationName.class).value();

        final String configValue = value(name);
        if (configValue != null) {
            bindConstant().annotatedWith(annotation).to(configValue);
        } else {
            LOG.warn("configuration value for '" + config.getPropertyName(name) + "' is null");
            bind(String.class).annotatedWith(annotation).toProvider(Providers.of((String) null));
        }
    }

    protected void configure(final Class<? extends Annotation> d, final String value) {
        config.put(d.getAnnotation(ConfigurationName.class).value(), value);
    }
}
