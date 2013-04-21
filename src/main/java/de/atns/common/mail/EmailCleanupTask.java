package de.atns.common.mail;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author tbaum
 * @since 23.08.2012
 */
@Singleton
public class EmailCleanupTask extends TimerTask implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(EmailCleanupTask.class);
    private final EmailRepository repository;
    private final Provider<EntityManager> em;
    private final AtomicBoolean running = new AtomicBoolean(false);

    @Inject
    public EmailCleanupTask(final Provider<EntityManager> em, final EmailRepository repository) {
        this.em = em;
        this.repository = repository;
    }

    @Transactional
    public void cleanEmails() {
        for (EmailMessage message : repository.getAllSentMails()) {
            LOG.debug("cleanup # " + message.getId());
            em.get().remove(message);
        }
    }

    @Override
    public void run() {
        if (running.compareAndSet(false, true)) {
            try {
                cleanEmails();
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            } finally {
                running.set(false);
            }
        }
    }
}
