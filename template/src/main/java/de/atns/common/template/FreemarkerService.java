package de.atns.common.template;

import com.google.inject.Inject;
import de.atns.common.config.ConfigurationName;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author tbaum
 * @since 27.11.2009
 */
public class FreemarkerService {
// ------------------------------ FIELDS ------------------------------

    private boolean isInit = false;
    private final Configuration configuration = new Configuration();
    private final Locale locale = Locale.ENGLISH;
    @TemplateDir private final String templateDir = null;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public FreemarkerService() {
    }

// -------------------------- OTHER METHODS --------------------------

    public String renderTemplate(String templateName, Map<String, Object> context, Object... params) {
        try {
            Template template = getTemplate(templateName);
            StringWriter stringWriter = new StringWriter();
            template.process(createMap(context, params), stringWriter);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    public freemarker.template.Template getTemplate(final String s) throws IOException {
        if (!isInit) {
            configuration.setTemplateLoader(new FileTemplateLoader(new File(templateDir)));
            isInit = true;
        }
        return configuration.getTemplate(s, locale, "UTF-8");
    }

    private HashMap<String, Object> createMap(final Map<String, ? extends Object> inputMap, final Object[] inputs) {
        final HashMap<String, Object> values = new HashMap<String, Object>();
        if (inputMap != null) {
            values.putAll(inputMap);
        }
        if (inputs != null) {
            for (final Object value : inputs) {
                if (value != null) {
                    values.put(value.getClass().getSimpleName(), value);
                }
            }
        }
        return values;
    }

// -------------------------- INNER CLASSES --------------------------

    @ConfigurationName("template-dir") @Retention(RUNTIME) @Target(FIELD) public static @interface TemplateDir {
    }
}
