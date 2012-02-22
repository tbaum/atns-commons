package de.atns.common.mail;

import com.google.inject.ImplementedBy;

import java.util.Map;

/**
 * User: tbaum
 * Date: 15.04.2009
 * Time: 02:54:24
 */
@ImplementedBy(DefaultTemplateRenderer.class) public interface MailTemplateRenderer {

    String renderHtmlTemplate(MailTemplate template, Map<String, Object> context);

    String renderPlainTemplate(MailTemplate template, Map<String, Object> context);
}
