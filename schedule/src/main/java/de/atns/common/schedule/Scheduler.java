package de.atns.common.schedule;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author tbaum
 * @since 27.11.2009
 */
@Singleton public class Scheduler {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(Scheduler.class);
    private Timer timer;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public Scheduler(final Set<ScheduledTask> tasks, final Injector injector) {
        LOG.debug("start scheduler");
        timer = new Timer(true);
        for (final ScheduledTask task : tasks) {
            final TimerTask timerTask = new TimerTask() {
                @Override public void run() {
                    try {
                        LOG.debug("starting " + task.getTargetClass());
                        injector.getInstance(task.getTargetClass()).run();
                    } catch (Exception e) {
                        LOG.error("exception for " + task.getTargetClass());
                        LOG.error(e, e);
                    }
                }
            };

            if (task.getPeriod() != null) {
                timer.schedule(timerTask, task.getDelay(), task.getPeriod());
            } else {
                timer.schedule(timerTask, task.getDelay());
            }
        }
    }

// -------------------------- OTHER METHODS --------------------------

    public void shutdown() {
        LOG.debug("shutdown scheduler");
        timer.cancel();
    }
}

