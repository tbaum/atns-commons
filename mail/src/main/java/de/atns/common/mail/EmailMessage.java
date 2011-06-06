package de.atns.common.mail;

import org.hibernate.annotations.Cascade;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import javax.persistence.*;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import static java.util.Arrays.asList;
import static javax.mail.Message.RecipientType.*;
import static javax.mail.Part.ATTACHMENT;
import static org.hibernate.annotations.CascadeType.ALL;

/**
 * User: tbaum
 * Date: 26.02.2008
 */
@Entity
public class EmailMessage implements Serializable {
// ------------------------------ FIELDS ------------------------------

    private static final long serialVersionUID = -7417533601085246546L;

    @Transient
    protected final String MAIL_DEFAULT_CHARSET = "UTF-8";

    @OneToMany(mappedBy = "message") @Cascade(ALL)
    protected Collection<EmailMessageResource> messageResources = new ArrayList<EmailMessageResource>();

    @Transient
    private final String debugMode = System.getProperty("debug.email");

    @Basic(optional = false) @Lob
    private String text;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Basic(optional = true)
    private String cc;

    @Basic(optional = true)
    private String bcc;

    @Basic(optional = false)
    private String sender;

    @Basic(optional = false)
    private String senderName;

    @Basic(optional = false)
    private String recipient;

    @Basic(optional = false)
    private String recipientName;

    @Basic(optional = false)
    private String subject;

    @Basic(optional = true)
    private Date sent;

    @Basic(optional = true)
    private String error;

    @Basic(optional = true)
    private String replyTo;

    @Basic(optional = true) @Lob
    private String html;

// --------------------------- CONSTRUCTORS ---------------------------

    EmailMessage() {
    }

    public EmailMessage(final String sender, final String senderName,
                        final String recipient, final String recipientName,
                        final String cc, final String bcc,
                        final String subject, final String text, final String html,
                        final Collection<EmailMessageResource> messageResources) {
        this.sender = sender;
        this.cc = cc;
        this.bcc = bcc;
        this.senderName = senderName;
        this.recipient = recipient;
        this.recipientName = recipientName;
        this.subject = subject;
        this.text = text;
        this.html = html;
        this.messageResources = new HashSet<EmailMessageResource>(messageResources);

        for (EmailMessageResource messageResource : this.messageResources) {
            messageResource.setMessage(this);
        }
    }

    public EmailMessage(final String sender, final String senderName,
                        final String recipient, final String recipientName,
                        final String cc, final String bcc,
                        final String subject, final String text, final String html,
                        final EmailMessageResource... messageResources) {
        this(sender, senderName, recipient, recipientName, cc, bcc, subject, text, html, asList(messageResources));
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getHtml() {
        return html;
    }

    public long getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

    public void setError(final String error) {
        this.error = error;
    }

    public void setSent(final Date sent) {
        this.sent = sent;
    }

// -------------------------- OTHER METHODS --------------------------

    public void prepare(final MimeMessage mesg) throws MessagingException {
        mesg.setSubject(subject, MAIL_DEFAULT_CHARSET);
        mesg.setFrom(createAddress(sender, senderName));
        if (replyTo != null) {
            mesg.setReplyTo(new Address[]{createAddress(replyTo, senderName)});
        }
        if (debugMode != null) {
            mesg.addRecipient(TO, createAddress(debugMode, recipientName));
        } else {
            mesg.addRecipient(TO, createAddress(recipient, recipientName));
            if (cc != null && !cc.trim().isEmpty()) {
                for (String s : cc.split(",")) {
                    mesg.addRecipient(CC, createAddress(s.trim()));
                }
            }
            if (bcc != null && !bcc.trim().isEmpty()) {
                for (String s : bcc.split(",")) {
                    mesg.addRecipient(BCC, createAddress(s.trim()));
                }
            }
        }
        prepareContent(mesg);
    }

    private InternetAddress createAddress(final String mail, final String name) {
        try {
            return new InternetAddress(mail, name, MAIL_DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private InternetAddress createAddress(final String mail) {
        try {
            return new InternetAddress(mail);
        } catch (AddressException e) {
            throw new RuntimeException(e);
        }
    }

    private void prepareContent(final MimeMessage mesg) throws MessagingException {
        if (hasDownloads() && hasEmbedded()) {
            final MimeMultipart message = new MimeMultipart("related");
            createTextMessage(message);
            attachAttachments(message, true);

            final MimeMultipart bodyPart = new MimeMultipart("mixed");
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(message);
            bodyPart.addBodyPart(mimeBodyPart);
            attachAttachments(bodyPart, false);

            mesg.setContent(bodyPart);
        } else if (hasDownloads()) {
            final MimeMultipart bodyPart = new MimeMultipart("mixed");
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            createTextMessage(mimeBodyPart);
            bodyPart.addBodyPart(mimeBodyPart);
            attachAttachments(bodyPart, false);

            mesg.setContent(bodyPart);
        } else if (hasEmbedded()) {
            final MimeMultipart message = new MimeMultipart("related");
            createTextMessage(message);
            attachAttachments(message, true);
            mesg.setContent(message);
        } else {
            createTextMessage(mesg);
        }
    }

    private boolean hasDownloads() {
        for (EmailMessageResource attachment : messageResources) {
            if (!isHtml() || !attachment.isEmbedded())
                return true;
        }

        return false;
    }

    private boolean isHtml() {
        return html != null;
    }

    private boolean hasEmbedded() {
        if (!isHtml()) {
            return false;
        }

        for (EmailMessageResource attachment : messageResources) {
            if (attachment.isEmbedded()) {
                return true;
            }
        }
        return false;
    }

    private void createTextMessage(final MimeMultipart bodyPart) throws MessagingException {
        final MimeBodyPart textPart = new MimeBodyPart();
        createTextMessage(textPart);
        bodyPart.addBodyPart(textPart);
    }

    private void attachAttachments(final Multipart root, boolean embedd) throws MessagingException {
        for (final EmailMessageResource attachment : messageResources) {
            if (!isHtml() || attachment.isEmbedded() == embedd) {
                final MimeBodyPart imagePart = new MimeBodyPart();
                if (!isHtml() || !embedd) {
                    imagePart.setDisposition(ATTACHMENT);
                    imagePart.setFileName(attachment.getName());
                }
                imagePart.setDataHandler(
                        new DataHandler(new ByteArrayDataSource(attachment.getData(), attachment.getMimeType())));
                imagePart.setContentID("<" + attachment.getName() + ">");
                root.addBodyPart(imagePart);
            }
        }
    }

    private void createTextMessage(final MimePart mail) throws MessagingException {
        if (isHtml()) {
            final MimeMultipart alternativePart = new MimeMultipart("alternative");

            final MimeBodyPart textPart = new MimeBodyPart();
            createTextPart(textPart, "plain", getText());
            alternativePart.addBodyPart(textPart);

            final MimeBodyPart htmlPart = new MimeBodyPart();
            createTextPart(htmlPart, "html", getHtml());
            alternativePart.addBodyPart(htmlPart);

            mail.setContent(alternativePart);
        } else {
            mail.setText(getText(), MAIL_DEFAULT_CHARSET);
            createTextPart(mail, "plain", getText());
        }
    }

    private void createTextPart(MimePart textPart, String ctype, String text) throws MessagingException {
        textPart.setText(text, MAIL_DEFAULT_CHARSET);
        textPart.setHeader("Content-Type", "text/" + ctype + "; charset=\"" + MAIL_DEFAULT_CHARSET + "\"");
        textPart.setHeader("Content-Transfer-Encoding", "quoted-printable");
    }
}
