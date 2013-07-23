package de.atns.common.gwt.app.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.EventBus;
import de.atns.common.gwt.client.gin.ApplicationModulLoadedEventHandler;
import de.atns.common.gwt.client.gin.ApplicationStateReadyEvent;
import de.atns.common.gwt.client.gin.ApplicationStateReadyEventHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author tbaum
 * @since 22.11.10
 */
public abstract class ApplicationEntryPoint implements EntryPoint {

    private final Logger LOG = Logger.getLogger("Application");

    @Override public void onModuleLoad() {
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            @Override public void onUncaughtException(final Throwable e) {
                LOG.log(Level.WARNING, e.getMessage(), e);
            }
        });

        GWT.runAsync(new AbstractRunAsyncCallback() {
            @Override public void onSuccess() {
                LOG.log(Level.FINE, "application startup");

                final ApplicationInjector injector = create();

                EventBus eventBus = injector.eventBus();

                ReadyWaiter readyWaiter = new ReadyWaiter() {

                    @Override protected void onAllReady() {
                        LOG.log(Level.FINE, "application handle history " + History.getToken());
                        injector.applicationPresenter().bind();
                        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
                            @Override public void execute() {
                                injector.placeHistoryManager().handleCurrentHistory();
                            }
                        });
                    }
                };
                eventBus.addHandler(ApplicationStateReadyEvent.type, readyWaiter);
                eventBus.addHandler(ApplicationModulLoadedEventHandler.type, readyWaiter);

                // force init
                injector.activityManager();

                RootPanel.get().add(injector.applicationShell());
            }
        });
    }

    protected abstract ApplicationInjector create();

    private static abstract class ReadyWaiter implements ApplicationStateReadyEventHandler, ApplicationModulLoadedEventHandler {
        private boolean modul = false, appstate = false, fired = false;

        @Override public void onReady() {
            modul = true;
            fireAppReady();
        }

        @Override public void onApplicationStateReady(ApplicationStateReadyEvent event) {
            appstate = true;
            fireAppReady();
        }

        private void fireAppReady() {
            if (modul && appstate && !fired) {
                fired = true;
                onAllReady();
            }
        }

        protected abstract void onAllReady();
    }
}
