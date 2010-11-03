package de.atns.common.mail;

import java.util.Map;

/**
 * User: tbaum
 * Date: 15.04.2009
 * Time: 02:54:24
 */
public interface MailTemplateRenderer {
// -------------------------- OTHER METHODS --------------------------

    //TODO use on method!!
    String renderHtmlTemplate(MailTemplate template, Map<String, Object> context);

    String renderPlainTemplate(MailTemplate template, Map<String, Object> context);
}
