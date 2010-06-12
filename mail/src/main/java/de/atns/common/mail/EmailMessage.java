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

import static java.util.Arrays.asList;
import static javax.mail.Message.RecipientType.*;
import static javax.mail.Part.ATTACHMENT;
import static org.hibernate.annotations.CascadeType.ALL;

/**
 * User: tbaum
 * Date: 26.02.2008
 */
@Entity
@Table(name = "email_spool")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class EmailMessage implements Serializable {
// ------------------------------ FIELDS ------------------------------

    private static final long serialVersionUID = -7417533601085246546L;

    @Transient
    protected final String MAIL_DEFAULT_CHARSET = "ISO-8859-15";

    @Transient
    private final String debugMode = System.getProperty("debug.email");

    @Basic(optional = false) @Lob
    private String text;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;


    @OneToMany(targetEntity = EmailResource.class)
    @Cascade(ALL)
    @JoinColumn(name = "attachment")
    private Collection<EmailResource> attachments = new ArrayList<EmailResource>();

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

// --------------------------- CONSTRUCTORS ---------------------------

    EmailMessage() {
    }

    public EmailMessage(final String sender, final String senderName,
                        final String recipient, final String recipientName, final String cc, final String bcc,
                        final String subject, final String text, final EmailResource... attachments) {
        this.sender = sender;
        this.cc = cc;
        this.bcc = bcc;
        this.senderName = senderName;
        this.recipient = recipient;
        this.recipientName = recipientName;
        this.subject = subject;
        this.text = text;
        this.attachments = new ArrayList<EmailResource>(asList(attachments));
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public long getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getSubject() {
        return subject;
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
            if (cc != null) {
                mesg.addRecipient(CC, createAddress(cc));
            }
            if (bcc != null) {
                mesg.addRecipient(BCC, createAddress(bcc));
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
        for (final EmailResource attachment : attachments) {
            final MimeBodyPart imagePart = new MimeBodyPart();
            imagePart.setDisposition(ATTACHMENT);
            imagePart.setDataHandler(new DataHandler(new ByteArrayDataSource(attachment.getData(), attachment.getMimeType())));
            imagePart.setFileName(attachment.getName());
            imagePart.setContentID("<" + attachment.getName() + ">");
            root.addBodyPart(imagePart);
        }
    }

    protected void createTextMessage(final MimePart textPart) throws MessagingException {
        textPart.setText(text, MAIL_DEFAULT_CHARSET);
    }
}
