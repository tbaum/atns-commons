package de.atns.common.mail;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.wideplay.warp.persist.Transactional;

import javax.persistence.EntityManager;
import java.util.Map;

public class MailUtilImpl implements MailUtil {
// ------------------------------ FIELDS ------------------------------

    private final MailResourceResolver resourceResolver;
    private final MailTemplateRenderer templateRenderer;

    private final Provider<EntityManager> em;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public MailUtilImpl(final MailTemplateRenderer templateRenderer, final MailResourceResolver resourceResolver,
                        final Provider<EntityManager> em) {
        this.templateRenderer = templateRenderer;
        this.resourceResolver = resourceResolver;
        this.em = em;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface MailUtil ---------------------

    @Transactional
    public EmailMessage sendMail(final String recipient, final String recipientName, final String ccRecipient, final String bccRecipient,
                                 final MailTemplate template, final Map<String, Object> context, final EmailResource... attachments) {
        if (template == null) throw new IllegalArgumentException("missing template");

        final String sender = template.getSenderEmail();
        final String senderName = template.getSenderName();
        final String subject = template.getSubject();

        final String text = templateRenderer.renderPlainTemplate(template, context);

        final EmailMessage message;

        if (template.isHtmlMail()) {
            final String html = templateRenderer.renderHtmlTemplate(template, context);
            final MailResourceResolver.ResolvedMail resolvedMail = resourceResolver.extractResources(html, context);
            message = new HtmlEmailMessage(sender, senderName, recipient, recipientName, ccRecipient, bccRecipient,
                    subject, text, attachments, resolvedMail.message, resolvedMail.result);
        } else {
            message = new EmailMessage(sender, senderName, recipient, recipientName, ccRecipient, bccRecipient,
                    subject, text, attachments);
        }

        return em.get().merge(message);
    }
}
