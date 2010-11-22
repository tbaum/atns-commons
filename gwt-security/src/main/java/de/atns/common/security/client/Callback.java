package de.atns.common.security.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;
import de.atns.common.gwt.client.DefaultWidgetDisplay;
import de.atns.common.gwt.client.WidgetDisplay;
import de.atns.common.gwt.client.gin.SharedServices;
import de.atns.common.security.client.action.CheckSession;
import de.atns.common.security.client.event.LogoutEvent;
import de.atns.common.security.client.event.ServerStatusEvent;
import de.atns.common.security.client.event.ServerStatusEventHandler;
import de.atns.common.security.client.model.UserPresentation;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author tbaum
 * @since 05.10.2010
 */
public abstract class Callback<T> implements AsyncCallback<T> {
// ------------------------------ FIELDS ------------------------------

    private static final Logger LOG = Logger.getLogger(Callback.class.getName());

    private static final WidgetDisplay NULL_DISPLAY = new DefaultWidgetDisplay() {
        @Override
        public void reset() {
        }
    };

    @Inject private static Provider<SharedServices> shared;
    private final DispatchAsync dispatcher;
    private final EventBus eventBus;
    private final WidgetDisplay display;

// --------------------------- CONSTRUCTORS ---------------------------

    public Callback() {
        this(NULL_DISPLAY);
    }

    public Callback(final WidgetDisplay display) {
        this.dispatcher = shared.get().dispatcher();
        this.eventBus = shared.get().eventBus();
        this.display = display;
        this.display.startProcessing();
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface AsyncCallback ---------------------

    @Override
    public void onFailure(final Throwable originalCaught) {
        LOG.log(Level.FINE, "check session in callback");
        display.showError(originalCaught.getMessage());
        dispatcher.execute(new CheckSession(), new AsyncCallback<UserPresentation>() {
            @Override
            public void onFailure(final Throwable caught) {
                LOG.log(Level.FINE, "failed-checksession", caught);
                eventBus.fireEvent(new ServerStatusEvent(ServerStatusEventHandler.ServerStatus.UNAVAILABLE));
                finish();
            }

            @Override
            public void onSuccess(final UserPresentation result) {
                LOG.log(Level.FINE, "success-checksession");
                eventBus.fireEvent(!result.isValid() ? new LogoutEvent(null) : new ServerStatusEvent(result));
                finish();
            }

            private void finish() {
                callbackError(originalCaught);
                display.stopProcessing();
            }
        });
    }

    @Override
    public void onSuccess(final T result) {
        LOG.log(Level.FINE, "success-call " + getClass());
        // eventBus.fireEvent(AVAILABLE);
        callback(result);
        display.setErrorVisible(false);

        display.stopProcessing();
    }

// -------------------------- OTHER METHODS --------------------------

    public abstract void callback(T result);

    public void callbackError(final Throwable caught) {
        LOG.log(Level.FINE, "error " + getClass(), caught);
    }
}

