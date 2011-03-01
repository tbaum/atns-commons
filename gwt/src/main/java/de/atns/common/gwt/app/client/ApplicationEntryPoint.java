package de.atns.common.gwt.app.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;
import de.atns.common.gwt.client.gin.ApplicationReadyEventHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author tbaum
 * @since 22.11.10
 */
public abstract class ApplicationEntryPoint implements EntryPoint {
// ------------------------------ FIELDS ------------------------------

    private final Logger LOG = Logger.getLogger("Application");

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface EntryPoint ---------------------

    public void onModuleLoad() {
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            @Override public void onUncaughtException(final Throwable e) {
                LOG.log(Level.WARNING, e.getMessage(), e);
            }
        });

        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override public void execute() {
                LOG.log(Level.FINE, "application startup");

                final ApplicationInjector injector = create();

                injector.eventBus().addHandler(ApplicationReadyEventHandler.type, new ApplicationReadyEventHandler() {
                    @Override public void onReady() {
                        LOG.log(Level.FINE, "application handle history " + History.getToken());
                        injector.placeHistoryManager().handleCurrentHistory();
                        injector.applicationPresenter().bind();
                    }
                });

                // force init
                injector.activityManager();

                RootPanel.get().add(injector.applicationShell());
            }
        });
    }

// -------------------------- OTHER METHODS --------------------------

    protected abstract ApplicationInjector create();
}
