package de.atns.common.mail;

import de.atns.common.dao.ExtendedHibernateDaoSupport;
import de.atns.common.dao.NonReturningHibernateCallback;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("mailUtil")
public class MailUtilSpringImpl extends ExtendedHibernateDaoSupport implements MailUtilSpring {

    @Autowired(required = true) private JavaMailSenderImpl mailSender;
    @Autowired(required = true) private MailResourceResolver resourceResolver;
    @Autowired(required = true) private MailTemplateRenderer templateRenderer;

    public void setMailSender(final JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public void setResourceResolver(final MailResourceResolver resourceResolver) {
        this.resourceResolver = resourceResolver;
    }

    public void setTemplateRenderer(final MailTemplateRenderer templateRenderer) {
        this.templateRenderer = templateRenderer;
    }

    @Override @Transactional(propagation = Propagation.REQUIRED)
    public MessagePreparator sendMail(final String recipient, final String recipientName,
                                      final MailTemplate template, final Map<String, Object> context,
                                      final MailResource... res) {
        return sendMail(recipient, recipientName, null, template, context, res);
    }

    @Override @Transactional(propagation = Propagation.REQUIRED)
    public MessagePreparator sendMail(final String recipient, final String recipientName, final String bccRecipient,
                                      final MailTemplate template, final Map<String, Object> context,
                                      final MailResource... attachments) {
        if (template == null) {
            throw new IllegalArgumentException("missing template");
        }

        final String sender = template.getSenderEmail();
        final String senderName = template.getSenderName();
        final String subject = template.getSubject();

        final String text = templateRenderer.renderPlainTemplate(template, context);

        final MessagePreparator preparator;

        if (template.isHtmlMail()) {
            final String html = templateRenderer.renderHtmlTemplate(template, context);
            final MailResourceResolver.ResolvedMail resolvedMail = resourceResolver.extractResources(html, context);
            preparator = new HtmlMessagePreparator(sender, senderName, recipient, recipientName, bccRecipient,
                    subject, text, attachments, resolvedMail.message, resolvedMail.result);
        } else {
            preparator = new MessagePreparator(sender, senderName, recipient, recipientName, bccRecipient,
                    subject, text, attachments);
        }

        executeCallback(new NonReturningHibernateCallback() {
            @Override protected void executeInHibernate(final Session session) throws HibernateException, SQLException {
                session.save(preparator);
            }
        });
        return preparator;
    }

    @Override @Transactional(propagation = Propagation.REQUIRED)
    public void sendMails() {
        synchronized (this) {
            executeCallback(new NonReturningHibernateCallback() {
                @Override
                protected void executeInHibernate(final Session session) throws HibernateException, SQLException {
                    @SuppressWarnings({"unchecked"})
                    final List<MessagePreparator> unsent =
                            (List<MessagePreparator>) session.createCriteria(MessagePreparator.class)
                                    .add(Restrictions.isNull("sent")).list();
                    for (final MessagePreparator p : unsent) {
                        try {
                            mailSender.send(p);
                        } catch (Exception e) {
                            p.setError(e.getMessage());
                        }
                        p.setSent(new Date());
                        session.merge(p);
                        session.flush();
                    }
                }
            });
        }
    }
}
