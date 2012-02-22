package de.atns.common.mail;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.mail.MailSendException;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.Map;

/**
 * User: tbaum
 * Date: 15.04.2009
 * Time: 02:54:24
 */
public class MailVelocityTemplateRenderer implements MailTemplateRenderer {

    private VelocityEngine velocityEngine;

    @Required public void setVelocityEngine(final VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    @Override public String renderHtmlTemplate(final MailTemplate template, final Map<String, Object> context) {
        return renderTemplate(template.getHtmlText(), context);
    }

    @Override public String renderPlainTemplate(final MailTemplate template, final Map<String, Object> context) {
        return renderTemplate(template.getText(), context);
    }

    private String renderTemplate(final String template, final Map<String, Object> context) {
        try {
            return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, context);
        } catch (VelocityException e) {
            throw new MailSendException("Unable to merge template", e);
        }
    }
}
