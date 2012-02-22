package de.atns.common.mail;

import com.google.inject.Inject;
import de.atns.common.template.FreemarkerService;

import java.util.Map;

/**
 * @author tbaum
 * @since 27.11.2009
 */
public class FreemarkerMailTemplateRenderer extends DefaultTemplateRenderer implements MailTemplateRenderer {

    private final FreemarkerService freemarkerService;

    @Inject public FreemarkerMailTemplateRenderer(final FreemarkerService freemarkerService) {
        this.freemarkerService = freemarkerService;
    }

    @Override protected String renderTemplate(final String templateName, final Map<String, Object> context) {
        return freemarkerService.renderTemplate(templateName, context);
    }
}
