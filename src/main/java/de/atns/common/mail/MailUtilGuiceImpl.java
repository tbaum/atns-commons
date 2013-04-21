package de.atns.common.mail;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

import static javax.xml.bind.DatatypeConverter.printBase64Binary;

public class MailUtilGuiceImpl implements MailUtil {

    private final MailTemplateRenderer templateRenderer;
    private final Provider<EntityManager> em;

    @Inject public MailUtilGuiceImpl(final MailTemplateRenderer templateRenderer, final Provider<EntityManager> em) {
        this.templateRenderer = templateRenderer;
        this.em = em;
    }

    @Override @Transactional
    public EmailMessage sendMail(final String recipient, final String recipientName,
                                 final MailTemplate template, final HashMap<String, Object> context,
                                 final MailTemplateResource... attachments) {
        return sendMail(recipient, recipientName, null, null, template, context, attachments);
    }

    @Override @Transactional
    public EmailMessage sendMail(final String recipient, final String recipientName, final String ccRecipient,
                                 final MailTemplate template, final HashMap<String, Object> context,
                                 final MailTemplateResource... attachments) {
        return sendMail(recipient, recipientName, ccRecipient, null, template, context, attachments);
    }

    @Override @Transactional
    public EmailMessage sendMail(final String recipient, final String recipientName, final String ccRecipient,
                                 final String bccRecipient,
                                 final MailTemplate template, final Map<String, Object> context,
                                 final MailTemplateResource... attachments) {
        EmailMessageResource[] at = new EmailMessageResource[attachments.length];
        for (int i = 0, attachments1Length = attachments.length; i < attachments1Length; i++) {
            at[i] = new EmailMessageResource(attachments[i].getName(), attachments[i].getMimeType(),
                    attachments[i].getData(),
                    attachments[i].isEmbedded());
        }

        return sendMail(recipient, recipientName, ccRecipient, bccRecipient, template, context, at);
    }

    @Override @Transactional
    public EmailMessage sendMail(final String recipient, final String recipientName, final String ccRecipient,
                                 final String bccRecipient,
                                 final MailTemplate template, final Map<String, Object> context,
                                 final EmailMessageResource... attachments) {
        if (template == null) {
            throw new IllegalArgumentException("missing template");
        }

        String text = templateRenderer.renderPlainTemplate(template, context);
        String html = template.isHtmlMail() ? templateRenderer.renderHtmlTemplate(template, context) : null;

        if (template.isHtmlMail() && template.isAutoText()) {
            text = createPlainText(html);
        }

        final EmailMessage message = new EmailMessage(
                template.getSenderEmail(), template.getSenderName(),
                recipient, recipientName, ccRecipient, bccRecipient,
                template.getSubject(), text, html, attachments);

        return em.get().merge(message);
    }

    private String createPlainText(final String html) {
        try {
            return Html2Text.toPlainText(html);
        } catch (Exception e) {
            sendMail("support@atns.de", "support",
                    new MailTemplateImpl(
                            "support@atns.de", "",
                            "Fehler beim Konvertieren HTML->TEXT",
                            html != null ? printBase64Binary(html.getBytes()) : "-null-"
                    ), new HashMap<String, Object>());
            return "";
        }
    }

}
