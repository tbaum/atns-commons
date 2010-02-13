package de.atns.common.schedule;

import com.google.inject.AbstractModule;

import static com.google.inject.multibindings.Multibinder.newSetBinder;

/**
 * @author tbaum
 * @since 13.02.2010
 */
public abstract class ScheduleModule extends AbstractModule {
// -------------------------- OTHER METHODS --------------------------

    protected void schedule(final ScheduledTask scheduledTask) {
        newSetBinder(binder(), ScheduledTask.class).addBinding().toInstance(scheduledTask);
    }
}
