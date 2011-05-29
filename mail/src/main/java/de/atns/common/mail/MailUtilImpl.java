package de.atns.common.mail;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

public class MailUtilImpl implements MailUtil {
// ------------------------------ FIELDS ------------------------------

    private final MailTemplateRenderer templateRenderer;

    private final Provider<EntityManager> em;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public MailUtilImpl(final MailTemplateRenderer templateRenderer, final Provider<EntityManager> em) {
        this.templateRenderer = templateRenderer;
        this.em = em;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface MailUtil ---------------------

    @Transactional
    public EmailMessage sendMail(final String recipient, final String recipientName,
                                 final MailTemplate template, final HashMap<String, Object> context,
                                 final MailTemplateResource... attachments) {
        return sendMail(recipient, recipientName, null, null, template, context, attachments);
    }

    @Transactional
    public EmailMessage sendMail(final String recipient, final String recipientName, final String ccRecipient,
                                 final MailTemplate template, final HashMap<String, Object> context,
                                 final MailTemplateResource... attachments) {
        return sendMail(recipient, recipientName, ccRecipient, null, template, context, attachments);
    }

    @Transactional
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

    @Transactional
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
            try {
                text = new Html2Text(Jsoup.parse(html).normalise().body().toString()).getPlainText();
            } catch (Exception e) {
                sendMail("support@atns.de", "support",
                        new MailTemplateImpl(
                                "support@atns.de", "",
                                "Fehler beim Konvertieren HTML->TEXT",
                                html != null ? Base64.encodeBase64String(html.getBytes()) : "-null-"
                        ), new HashMap<String, Object>());
                text = "";
            }
        }

        final EmailMessage message = new EmailMessage(
                template.getSenderEmail(), template.getSenderName(),
                recipient, recipientName, ccRecipient, bccRecipient,
                template.getSubject(), text, html, attachments);

        return em.get().merge(message);
    }
}
