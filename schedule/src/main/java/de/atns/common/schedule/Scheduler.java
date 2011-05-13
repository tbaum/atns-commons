package de.atns.common.schedule;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author tbaum
 * @since 27.11.2009
 */
public class Scheduler {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(Scheduler.class);

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public Scheduler(final Set<ScheduledTask> tasks, final Injector injector) {
        final Timer timer = new Timer();
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
}

