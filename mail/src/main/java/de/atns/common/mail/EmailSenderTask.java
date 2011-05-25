package de.atns.common.mail;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
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
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
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
    private final Provider<Properties> mailConfiguration;
    private final EmailRepository repository;
    private final Provider<EntityManager> em;
    private final AtomicBoolean running = new AtomicBoolean(false);

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public EmailSenderTask(final Provider<EntityManager> em,
                                   @MailConfiguration final Provider<Properties> mailConfiguration,
                                   final EmailRepository repository) {
        this.em = em;
        this.mailConfiguration = mailConfiguration;
        this.repository = repository;
    }
    /*
@Override public void run() {
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
}
}           */

// -------------------------- OTHER METHODS --------------------------

    public void sendEmails() {
        final Properties props1 = mailConfiguration.get();

        String host = (String) props1.get("mail.smtp.host");
        String user = (String) props1.get("mail.smtp.user");
        String pass = (String) props1.get("mail.smtp.password");
        boolean ssl = "true".equalsIgnoreCase((String) props1.get("mail.smtp.ssl"));
        sendEmails(host, user, pass, ssl);
    }

    private void sendEmails(final String host, final String user, final String pass, final boolean ssl) {
        /*
        final EntityManager em = this.em.get();
        final Session session = Session.getInstance(mailConfiguration);

        final EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            for (final EmailMessage p1 : repository.getAllUnsentMails()) {

                final EmailMessage message = em.find(EmailMessage.class, p1.getId());
                try {
                    final MimeMessage mimeMessage = new MimeMessage(session);
                    message.prepare(mimeMessage);
                    LOG.debug("sending #" + p1.getId() + " " + p1.getSender() + " " + p1.getSubject() +
                            " --> " + Arrays.toString(mimeMessage.getRecipients(Message.RecipientType.TO)));
                    Transport.send(mimeMessage);
                    message.setSent(new Date());

                    em.merge(message);
                } catch (Exception e) {
                    LOG.error(e);
                    message.setError(e.getMessage());
                }
                em.flush();
            }
            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
        */


        final EntityManager em = this.em.get();
        em.setFlushMode(FlushModeType.COMMIT);

        final Session session;

        Properties props = new Properties();
        props.put("mail.smtp.dsn.notify", "SUCCESS,FAILURE,DELAY");
        props.put("mail.smtp.dsn.ret", "HDRS");

        if (host != null) {
            props.put(ssl ? "mail.smtps.host" : "mail.smtp.host", host);
        }
        session = Session.getInstance(props);
        Transport transport = null;
        for (final EmailMessage me : repository.getAllUnsentMails()) {
            final EntityTransaction transaction = em.getTransaction();

            try {
                transaction.begin();
                EmailMessage message = em.find(EmailMessage.class, me.getId());
                try {
                    if (transport == null) {
                        transport = getTransport(session, ssl, host, user, pass);
                    }
                    final MimeMessage mimeMessage = new MimeMessage(session);
                    message.prepare(mimeMessage);
                    LOG.debug("sending #" + message.getId() + " " + message.getSender() + " " + message.getSubject() +
                            " --> " + Arrays.toString(mimeMessage.getRecipients(Message.RecipientType.TO)));

                    transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
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
            em.flush();
        }
    }

    private Transport getTransport(final Session session, final boolean ssl, final String host, final String user, final String pass) throws MessagingException {
        final Transport transport = session.getTransport(ssl ? "smtps" : "smtp");
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
        transport.connect(host, user, pass);

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
