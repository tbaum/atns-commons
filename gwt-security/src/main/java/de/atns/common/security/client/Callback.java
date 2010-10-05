package de.atns.common.security.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import de.atns.common.gwt.client.DefaultErrorWidgetDisplay;
import de.atns.common.gwt.client.ErrorWidgetDisplay;
import de.atns.common.security.client.action.CheckSession;
import de.atns.common.security.client.event.LogoutEvent;
import de.atns.common.security.client.event.ServerStatusEvent;
import de.atns.common.security.client.event.ServerStatusEventHandler;
import de.atns.common.security.client.model.UserPresentation;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Callback<T> implements AsyncCallback<T> {
// ------------------------------ FIELDS ------------------------------

    private static final Logger LOG = Logger.getLogger(Callback.class.getName());

    private static final ErrorWidgetDisplay nullDisplay = new DefaultErrorWidgetDisplay() {
        @Override
        public void reset() {
        }
    };
    private final DispatchAsync dispatcher;
    private final EventBus eventBus;
    private final ErrorWidgetDisplay display;

// --------------------------- CONSTRUCTORS ---------------------------

    public Callback() {
        this(nullDisplay);
    }

    public Callback(final ErrorWidgetDisplay display) {
        this.dispatcher = SharedServicesHolder.shared().getDispatchAsync();
        this.eventBus = SharedServicesHolder.shared().getEventBus();
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

