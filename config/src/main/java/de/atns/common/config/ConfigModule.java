package de.atns.common.config;

import com.google.inject.AbstractModule;
import com.google.inject.MembersInjector;
import com.google.inject.TypeLiteral;
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
public abstract class ConfigModule extends AbstractModule {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(ConfigModule.class);
    private final Configurator config = new Configurator();

// -------------------------- OTHER METHODS --------------------------

    @Override protected final void configure() {
        configureConfig();
//        bindListener(Matchers.any(), new ConfigAnnotationTypeListener());
    }

    protected abstract void configureConfig();


    protected void configure(final Class<? extends Annotation> annotation) {
        String name = annotation.getAnnotation(ConfigurationName.class).value();

        final String configValue = value(name);
        if (configValue != null) {
            bindConstant().annotatedWith(annotation).to(configValue);
        } else {
            LOG.warn("configuration value for '" + config.getPropertyName(name) + "' is null");
            bind(String.class).annotatedWith(annotation).toProvider(Providers.of((String) null));
        }
    }

    protected String value(final String name) {
        return config.get(name);
    }

// -------------------------- INNER CLASSES --------------------------

    class ConfigAnnotationTypeListener implements TypeListener {
        public <T> void hear(TypeLiteral<T> typeLiteral, TypeEncounter<T> typeEncounter) {
            System.err.println("test " + typeLiteral);
            for (Field field : typeLiteral.getRawType().getDeclaredFields()) {
                if (field.getType() == String.class) {
                    for (Annotation annotation : field.getAnnotations()) {
                        System.err.println(" ." + annotation);

                        ConfigurationName ca = annotation.getClass().getAnnotation(ConfigurationName.class);
                        if (ca != null) {
                            System.err.println(" >" + ca);

                            String name = ca.value();
                            typeEncounter.register(new ConfigAnnotationMembersInjector<T>(field, value(name)));
                            LOG.debug("set value for " + field + " to #" + name + "# = " + value(name));
                        }
                    }
                }
            }
        }
    }

    class ConfigAnnotationMembersInjector<T> implements MembersInjector<T> {
        private final Field field;
        private final String value;

        ConfigAnnotationMembersInjector(Field field, String value) {
            this.field = field;
            this.value = value;
            field.setAccessible(true);
        }

        public void injectMembers(T t) {
            try {
                field.set(t, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}                                                   
