package de.atns.common.mail;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author tbaum
 * @since 27.11.2009
 */
@Singleton
public class EmailSenderTask implements Runnable {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(EmailSenderTask.class);
    private final Properties mailConfiguration;
    private final EmailRepository repository;
    private final Provider<EntityManager> em;
    private final AtomicBoolean running = new AtomicBoolean(false);

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public EmailSenderTask(final Provider<EntityManager> em,
                                   @MailConfiguration final Properties mailConfiguration,
                                   final EmailRepository repository) {
        this.em = em;
        this.mailConfiguration = mailConfiguration;
        this.repository = repository;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Runnable ---------------------

    @Override public void run() {
        if (running.compareAndSet(false, true)) {
            try {
                final EntityManager em = this.em.get();
                final Session session = Session.getInstance(mailConfiguration);

                for (final EmailMessage p1 : repository.getAllUnsentMails()) {
                    LOG.debug("sending #" + p1.getId() + " " + p1.getSender() + " " + p1.getSubject());

                    final EntityTransaction transaction = em.getTransaction();
                    try {
                        transaction.begin();
                        final EmailMessage message = em.find(EmailMessage.class, p1.getId());
                        try {
                            final MimeMessage mimeMessage = new MimeMessage(session);
                            message.prepare(mimeMessage);
                            Transport.send(mimeMessage);
                            message.setSent(new Date());
                        } catch (Exception e) {
                            LOG.error(e);
                            message.setError(e.getMessage());
                        }

                        transaction.commit();
                    } finally {
                        if (transaction.isActive()) {
                            transaction.rollback();
                        }
                    }
                }
            } catch (Exception e) {
                LOG.error(e, e);
            } finally {
                running.set(false);
            }
        }
    }
}
