package de.atns.common.mail;

/**
 * @author tbaum
 * @since 27.11.2009
 */
public class MailTemplateImpl implements MailTemplate {
// ------------------------------ FIELDS ------------------------------

    private final String senderName;
    private final String senderEmail;
    private final String subject;
    private final String text;
    private final String html;

// --------------------------- CONSTRUCTORS ---------------------------

    public MailTemplateImpl(final String senderEmail, final String senderName, final String subject, final String text) {
        this(senderEmail, senderName, subject, text, null);
    }

    public MailTemplateImpl(final String senderEmail, final String senderName, final String subject, final String text, final String html) {
        this.html = html;
        this.text = text;
        this.senderName = senderName;
        this.senderEmail = senderEmail;
        this.subject = subject;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    @Override public String getSenderEmail() {
        return senderEmail;
    }

    @Override public String getSenderName() {
        return senderName;
    }

    @Override public String getSubject() {
        return subject;
    }

    @Override public String getText() {
        return text;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface MailTemplate ---------------------

    @Override public String getHtmlText() {
        return html;
    }

    @Override public boolean isHtmlMail() {
        return html != null;
    }
}
