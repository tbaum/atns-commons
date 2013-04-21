package de.atns.common.mail;

import org.hibernate.annotations.Cascade;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import javax.persistence.*;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static java.util.Arrays.asList;
import static org.hibernate.annotations.CascadeType.ALL;

/**
 * User: tbaum
 * Date: 26.02.2008
 */
@Entity @Table(name = "email_spool") @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class MessagePreparator implements MimeMessagePreparator, Serializable {

    @Transient protected final String MAIL_DEFAULT_CHARSET = "ISO-8859-15";
    @Transient private final String debugMode = System.getProperty("debug.email");
    @Basic(optional = false) @Lob private String text;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(nullable = false) private long id;
    @OneToMany(targetEntity = MailResource.class) @Cascade(ALL) @JoinColumn(name = "attachment")
    private Collection<MailResource> attachments = new ArrayList<MailResource>();
    @Basic(optional = true) private String bcc;
    @Basic(optional = false) private String sender;
    @Basic(optional = false) private String senderName;
    @Basic(optional = false) private String recipient;
    @Basic(optional = false) private String recipientName;
    @Basic(optional = false) private String subject;
    @Basic(optional = true) private Date sent;
    @Basic(optional = true) private String error;
    @Basic(optional = true) private String replyTo;

    MessagePreparator() {
    }

    public MessagePreparator(final String sender, final String senderName,
                             final String recipient, final String recipientName, final String bcc,
                             final String subject, final String text, final MailResource... attachments) {
        this.sender = sender;
        this.bcc = bcc;
        this.senderName = senderName;
        this.recipient = recipient;
        this.recipientName = recipientName;
        this.subject = subject;
        this.text = text;
        this.attachments = new ArrayList<MailResource>(asList(attachments));
    }

    public Collection<MailResource> getAttachments() {
        return attachments;
    }

    public String getBcc() {
        return bcc;
    }

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }

    public long getId() {
        return id;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(final String replyTo) {
        this.replyTo = replyTo;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(final String sender) {
        this.sender = sender;
    }

    public String getSenderName() {
        return senderName;
    }

    public Date getSent() {
        return sent;
    }

    public void setSent(final Date sent) {
        this.sent = sent;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

    @Override public void prepare(final MimeMessage mesg) throws MessagingException {
        mesg.setSubject(subject, MAIL_DEFAULT_CHARSET);
        mesg.setFrom(createAddress(sender, senderName));
        if (replyTo != null) {
            mesg.setReplyTo(new Address[]{createAddress(replyTo, senderName)});
        }
        if (debugMode != null) {
            mesg.addRecipient(Message.RecipientType.TO, createAddress(debugMode, recipientName));
        } else {
            mesg.addRecipient(Message.RecipientType.TO, createAddress(recipient, recipientName));
            if (bcc != null) {
                mesg.addRecipient(Message.RecipientType.BCC, createAddress(bcc));
            }
        }
        prepareContent(mesg);
    }

    private InternetAddress createAddress(final String mail) {
        try {
            return new InternetAddress(mail, MAIL_DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new MailPreparationException(e);
        }
    }

    private InternetAddress createAddress(final String mail, final String name) {
        try {
            return new InternetAddress(mail, name, MAIL_DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new MailPreparationException(e);
        }
    }

    protected void prepareContent(final MimeMessage mesg) throws MessagingException {
        if (attachments.size() > 0) {
            final MimeMultipart bodyPart = new MimeMultipart("mixed");
            createTextMessage(bodyPart);
            attachAttachments(bodyPart);

            mesg.setContent(bodyPart);
        } else {
            createTextMessage(mesg);
        }
    }

    protected void createTextMessage(final MimeMultipart bodyPart) throws MessagingException {
        final MimeBodyPart textPart = new MimeBodyPart();
        createTextMessage(textPart);
        bodyPart.addBodyPart(textPart);
    }

    protected void attachAttachments(final Multipart root) throws MessagingException {
        for (final MailResource attachment : attachments) {
            final MimeBodyPart imagePart = new MimeBodyPart();
            imagePart.setDisposition(Part.ATTACHMENT);
            imagePart.setDataHandler(
                    new DataHandler(new ByteArrayDataSource(attachment.getData(), attachment.getMimeType())));
            imagePart.setFileName(attachment.getName());
            imagePart.setContentID("<" + attachment.getName() + ">");
            root.addBodyPart(imagePart);
        }
    }

    protected void createTextMessage(final MimePart textPart) throws MessagingException {
        textPart.setText(text, MAIL_DEFAULT_CHARSET);
    }
}
