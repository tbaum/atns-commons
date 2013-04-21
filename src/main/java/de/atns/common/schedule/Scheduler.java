package de.atns.common.schedule;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author tbaum
 * @since 27.11.2009
 */
@Singleton public class Scheduler {

    private static final Logger LOG = LoggerFactory.getLogger(Scheduler.class);
    private Timer timer;

    @Inject public Scheduler(final Set<ScheduledTask> tasks, final Injector injector) {
        LOG.debug("start scheduler");
        for (ScheduledTask task : tasks) {
            LOG.debug("... schedule: " + task);
        }
        timer = new Timer(true);
        for (final ScheduledTask task : tasks) {
            final TimerTask timerTask = new TimerTask() {
                @Override public void run() {
                    try {
                        injector.getInstance(task.getTargetClass()).run();
                    } catch (Exception e) {
                        LOG.error("exception for " + task.getTargetClass());
                        LOG.error(e.getMessage(), e);
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

    public void shutdown() {
        LOG.debug("shutdown scheduler");
        timer.cancel();
    }
}

