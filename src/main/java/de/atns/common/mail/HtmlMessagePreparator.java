package de.atns.common.mail;

import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;
import javax.mail.util.ByteArrayDataSource;
import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Arrays.asList;

/**
 * User: tbaum
 * Date: 26.02.2008
 * Time: 18:53:01
 */
@Entity class HtmlMessagePreparator extends MessagePreparator {

    @Basic(optional = false) @Lob private String html;
    @OneToMany(targetEntity = MailResource.class) @JoinColumn(name = "resources")
    private List<MailResource> resources;

    HtmlMessagePreparator() {
    }

    public HtmlMessagePreparator(final String sender, final String senderName,
                                 final String recipient, final String recipientName, final String bcc,
                                 final String subject, final String text,
                                 final String html, final MailResource... resources
    ) {
        this(sender, senderName, recipient, recipientName, bcc, subject, text, new MailResource[0], html, resources);
    }

    public HtmlMessagePreparator(final String sender, final String senderName,
                                 final String recipient, final String recipientName, final String bcc,
                                 final String subject, final String text, final MailResource[] attachments,
                                 final String html, final MailResource... resources) {
        super(sender, senderName, recipient, recipientName, bcc, subject, text, attachments);
        this.html = html;
        this.resources = asList(resources);
    }

    public String getHtml() {
        return html;
    }

    public List<MailResource> getResources() {
        return resources;
    }

    @Override protected void createTextMessage(final MimePart messagePart) throws MessagingException {
        messagePart.setContent(
                resources.size() > 0 ?
                        createRelatedPart(html, resources) :
                        createAlternativePart(html));
    }

    private MimeMultipart createRelatedPart(final String html, final List<MailResource> resources)
            throws MessagingException {
        final MimeMultipart relatedPart = new MimeMultipart("related");

        final MimeBodyPart alternativePart = new MimeBodyPart();
        relatedPart.addBodyPart(alternativePart);

        final String newHtml = addResourceParts(html, resources, relatedPart);
        alternativePart.setContent(createAlternativePart(newHtml));

        return relatedPart;
    }

    private String addResourceParts(String newHtmlBody, final List<MailResource> resources,
                                    final MimeMultipart relatedPart) throws MessagingException {
        final Set<String> resId = new TreeSet<String>();
        int i = 1;

        for (final MailResource resource : resources) {
            final String cid = resource.getName().substring(resource.getName().lastIndexOf("/") + 1);
            final String xcid = resId.contains(cid) ? (i++) + "_" + cid : cid;
            resId.add(xcid);
            newHtmlBody = newHtmlBody.replaceAll(resource.getName(), "cid:" + xcid);
            relatedPart.addBodyPart(createResourcePart(resource, xcid));
        }
        return newHtmlBody;
    }

    private static MimeBodyPart createResourcePart(final MailResource resource, final String resourceId)
            throws MessagingException {
        final MimeBodyPart resourcePart = new MimeBodyPart();
        final byte[] data = resource.getData();

        resourcePart.setDataHandler(new DataHandler(new ByteArrayDataSource(data, resource.getMimeType())));
        resourcePart.setDisposition(Part.ATTACHMENT);
        resourcePart.setFileName(resourceId);
        resourcePart.setContentID("<" + resourceId + ">");

        return resourcePart;
    }

    private MimeMultipart createAlternativePart(final String _html) throws MessagingException {
        final MimeMultipart alternativePart = new MimeMultipart("alternative");
        final MimeBodyPart textPart = new MimeBodyPart();
        super.createTextMessage(textPart);
        alternativePart.addBodyPart(textPart);
        final MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setText(_html, MAIL_DEFAULT_CHARSET, "html");
        alternativePart.addBodyPart(htmlPart);
        return alternativePart;
    }
}
