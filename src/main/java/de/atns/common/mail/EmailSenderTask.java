package de.atns.common.mail;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
@Singleton public class EmailSenderTask extends TimerTask implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(EmailSenderTask.class);
    private final Provider<MailConfiguration> mailConfiguration;
    private final EmailRepository repository;
    private final Provider<EntityManager> em;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private ThreadLocal<Transport> transport = new ThreadLocal<Transport>();

    @Inject
    public EmailSenderTask(final Provider<EntityManager> em, final Provider<MailConfiguration> mailConfiguration,
                           final EmailRepository repository) {
        this.em = em;
        this.mailConfiguration = mailConfiguration;
        this.repository = repository;
    }

    private Transport prepareTransport(final Session session) throws MessagingException {
        if (transport.get() == null) {
            final MailConfiguration mailConfiguration = this.mailConfiguration.get();

            final Transport transport = session.getTransport(mailConfiguration.getProtocol());
            this.transport.set(transport);

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

        return transport.get();
    }

    public void sendEmails() {
        final Session session = Session.getInstance(mailConfiguration.get());

        try {
            for (EmailMessage message : allUnsentMessages()) {
                sendMessage(session, message);
            }
        } finally {
            transport.remove();
        }
    }

    @Transactional protected List<EmailMessage> allUnsentMessages() {
        return repository.getAllUnsentMails();
    }

    @Transactional protected void sendMessage(final Session session, final EmailMessage id) {
        EmailMessage message = em.get().find(EmailMessage.class, id.getId());
        try {
            final MimeMessage mimeMessage = new MimeMessage(session);
            message.prepare(mimeMessage);

            LOG.debug("sending # " + message.getId() + " " + message.getSender() + " " + message.getSubject() +
                    " --> " + Arrays.toString(mimeMessage.getRecipients(Message.RecipientType.TO)));

            prepareTransport(session).sendMessage(mimeMessage, mimeMessage.getAllRecipients());

            message.setSent(new Date());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            message.setError(e.getMessage());
            transport.remove();
        }
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
                LOG.error(e.getMessage(), e);
            } finally {
                running.set(false);
            }
        }
    }
}
