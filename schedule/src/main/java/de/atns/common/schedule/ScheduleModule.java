package de.atns.common.schedule;

import com.google.inject.AbstractModule;

import static com.google.inject.multibindings.Multibinder.newSetBinder;

/**
 * @author tbaum
 * @since 13.02.2010
 */
public abstract class ScheduleModule extends AbstractModule {

    private boolean bindScheduler = false;

    @Override protected final void configure() {
        configureScheduler();
        if (bindScheduler) {
            bind(Scheduler.class).asEagerSingleton();
        }
    }

    protected abstract void configureScheduler();

    protected void schedule(final ScheduledTask scheduledTask) {
        newSetBinder(binder(), ScheduledTask.class).addBinding().toInstance(scheduledTask);
        bindScheduler = true;
    }
}
