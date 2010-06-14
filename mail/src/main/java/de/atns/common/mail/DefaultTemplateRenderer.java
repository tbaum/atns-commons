package de.atns.common.mail;

import java.text.MessageFormat;
import java.util.Map;

/**
 * @author tbaum
 * @since 27.11.2009
 */
public class DefaultTemplateRenderer implements MailTemplateRenderer {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface MailTemplateRenderer ---------------------

    @Override public String renderHtmlTemplate(final MailTemplate template, final Map<String, Object> context) {
        return renderTemplate(template.getHtmlText(), context);
    }

    @Override public String renderPlainTemplate(final MailTemplate template, final Map<String, Object> context) {
        return renderTemplate(template.getText(), context);
    }

// -------------------------- OTHER METHODS --------------------------

    protected String renderTemplate(final String template, final Map<String, Object> context) {
        return MessageFormat.format(template, context.values().toArray());
    }
}
