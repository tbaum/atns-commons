package de.atns.common.mail;

import com.google.inject.Inject;
import de.atns.common.template.FreemarkerService;

import java.util.Map;

/**
 * @author tbaum
 * @since 27.11.2009
 */
public class FreemarkerMailTemplateRenderer extends DefaultTemplateRenderer implements MailTemplateRenderer {
// ------------------------------ FIELDS ------------------------------

    private final FreemarkerService freemarkerService;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public FreemarkerMailTemplateRenderer(final FreemarkerService freemarkerService) {
        this.freemarkerService = freemarkerService;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override protected String renderTemplate(final String templateName, final Map<String, Object> context) {
        return freemarkerService.renderTemplate(templateName, context);
    }

}
