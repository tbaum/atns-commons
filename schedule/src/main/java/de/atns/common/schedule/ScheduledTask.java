package de.atns.common.schedule;

/**
 * @author tbaum
 * @since 13.02.2010
 */
public class ScheduledTask {
// ------------------------------ FIELDS ------------------------------

    private final Long delay;
    private final Long period;
    private final Class<? extends Runnable> targetClass;

// -------------------------- STATIC METHODS --------------------------

    public static ScheduledTask task() {
        return new ScheduledTask(0, null, null);
    }

    public static ScheduledTask task(final long period, Class<? extends Runnable> targetClass) {
        return new ScheduledTask(0, period, targetClass);
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private ScheduledTask(final long delay, final Long period, final Class<? extends Runnable> targetClass) {
        this.delay = delay;
        this.period = period;
        this.targetClass = targetClass;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public long getDelay() {
        return delay;
    }

    public Long getPeriod() {
        return period;
    }

    public Class<? extends Runnable> getTargetClass() {
        return targetClass;
    }

// -------------------------- OTHER METHODS --------------------------

    public ScheduledTask delay(long delay) {
        return new ScheduledTask(delay, period, targetClass);
    }

    public ScheduledTask repeating(long period) {
        return new ScheduledTask(delay, period, targetClass);
    }

    public ScheduledTask to(final Class<? extends Runnable> targetClass) {
        return new ScheduledTask(delay, period, targetClass);
    }
}
