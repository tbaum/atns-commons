package de.atns.common.schedule;

/**
 * @author tbaum
 * @since 13.02.2010
 */
public class ScheduledTask {

    private final Long delay;
    private final Long period;
    private final Class<? extends Runnable> targetClass;

    public static ScheduledTask task() {
        return new ScheduledTask(0, null, null);
    }

    public static ScheduledTask task(final long period, final Class<? extends Runnable> targetClass) {
        return new ScheduledTask(0, period, targetClass);
    }

    public static ScheduledTask repeatingTask(final long period) {
        return task().repeating(period);
    }

    public ScheduledTask repeating(final long period) {
        return new ScheduledTask(delay, period, targetClass);
    }

    private ScheduledTask(final long delay, final Long period, final Class<? extends Runnable> targetClass) {
        this.delay = delay;
        this.period = period;
        this.targetClass = targetClass;
    }

    public long getDelay() {
        return delay;
    }

    public Long getPeriod() {
        return period;
    }

    public Class<? extends Runnable> getTargetClass() {
        return targetClass;
    }

    public ScheduledTask delay(final long delay) {
        return new ScheduledTask(delay, period, targetClass);
    }

    public ScheduledTask to(final Class<? extends Runnable> targetClass) {
        return new ScheduledTask(delay, period, targetClass);
    }

    @Override public String toString() {
        return "ScheduledTask{" + targetClass + ", delay=" + delay + ", period=" + period + "}";
    }
}
