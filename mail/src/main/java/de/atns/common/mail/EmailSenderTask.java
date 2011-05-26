package de.atns.common.mail;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.event.TransportAdapter;
import javax.mail.event.TransportEvent;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author tbaum
 * @since 27.11.2009
 */
@Singleton
public class EmailSenderTask extends TimerTask implements Runnable {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(EmailSenderTask.class);
    private final Provider<MailConfiguration> mailConfiguration;
    private final EmailRepository repository;
    private final Provider<EntityManager> em;
    private final AtomicBoolean running = new AtomicBoolean(false);

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public EmailSenderTask(final Provider<EntityManager> em, final Provider<MailConfiguration> mailConfiguration,
                           final EmailRepository repository) {
        this.em = em;
        this.mailConfiguration = mailConfiguration;
        this.repository = repository;
    }

// -------------------------- OTHER METHODS --------------------------

    public void sendEmails() {
        final Session session = Session.getInstance(mailConfiguration.get());

        Transport transport = null;
        for (EmailMessage message : allUnsentMessages()) {
            transport = sendMessage(session, message, transport);
        }
    }

    @Transactional
    protected List<EmailMessage> allUnsentMessages() {
        return repository.getAllUnsentMails();
    }

    @Transactional
    protected Transport sendMessage(final Session session, final EmailMessage id, Transport transport) {
        EmailMessage message = em.get().find(EmailMessage.class, id.getId());
        try {
            final MimeMessage mimeMessage = new MimeMessage(session);

            message.prepare(mimeMessage);

            LOG.debug("sending # " + message.getId() + " " + message.getSender() + " " + message.getSubject() +
                    " --> " + Arrays.toString(mimeMessage.getRecipients(Message.RecipientType.TO)));

            transport = getTransport(session, transport);
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());

            message.setSent(new Date());
            return transport;
        } catch (Exception e) {
            LOG.error(e);
            message.setError(e.getMessage());
            return null;
        }
    }

    private Transport getTransport(final Session session, Transport transport) throws MessagingException {
        if (transport == null) {
            final MailConfiguration mailConfiguration = this.mailConfiguration.get();

            transport = session.getTransport(mailConfiguration.isSsl() ? "smtps" : "smtp");

            transport.addTransportListener(new TransportAdapter() {
                @Override public void messagePartiallyDelivered(final TransportEvent e) {
                    LOG.debug("part " + e);
                }

                @Override public void messageNotDelivered(final TransportEvent e) {
                    LOG.debug("not  " + e);
                }

                @Override public void messageDelivered(final TransportEvent e) {
                    LOG.debug("del  " + e);
                }
            });
            transport.connect(mailConfiguration.getHost(), mailConfiguration.getUser(), mailConfiguration.getPass());
        }
        return transport;
    }

    public void trigger() {
        new Timer().schedule(new TimerTask() {
                    @Override public void run() {
                        EmailSenderTask.this.run();
                    }
                }, 500);
    }

    @Override public void run() {
        if (running.compareAndSet(false, true)) {
            try {
                sendEmails();
            } catch (Exception e) {
                LOG.error(e, e);
            } finally {
                running.set(false);
            }
        }
    }
}
