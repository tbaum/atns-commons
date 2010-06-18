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

import static com.allen_sauer.gwt.log.client.Log.debug;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public abstract class DefaultCallback<T> implements AsyncCallback<T> {
// ------------------------------ FIELDS ------------------------------

    private static final ErrorWidgetDisplay nullDisplay = new DefaultErrorWidgetDisplay() {
        @Override public void reset() {
        }
    };
    private final DispatchAsync dispatcher;
    private final EventBus eventBus;
    private final ErrorWidgetDisplay display;

// --------------------------- CONSTRUCTORS ---------------------------

    public DefaultCallback(final DispatchAsync dispatcher, final EventBus bus) {
        this(dispatcher, bus, null);
    }

    public DefaultCallback(final DispatchAsync dispatcher, final EventBus bus, final ErrorWidgetDisplay display) {
        this.dispatcher = dispatcher;
        this.eventBus = bus;
        this.display = display != null ? display : nullDisplay;

        this.display.startProcessing();
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface AsyncCallback ---------------------

    @Override public void onFailure(final Throwable originalCaught) {
        debug("check session in callback");
        display.showError(originalCaught.getMessage());
        dispatcher.execute(new CheckSession(), new AsyncCallback<UserPresentation>() {
            @Override public void onFailure(final Throwable caught) {
                debug("failed-checksession", caught);
                eventBus.fireEvent(new ServerStatusEvent(ServerStatusEventHandler.ServerStatus.UNAVAILABLE));
                finish();
            }

            @Override public void onSuccess(final UserPresentation result) {
                debug("success-checksession");
                eventBus.fireEvent(!result.isValid() ? new LogoutEvent(null) : new ServerStatusEvent(result));
                finish();
            }

            private void finish() {
                callbackError(originalCaught);
                display.stopProcessing();
            }
        });
    }

    @Override public void onSuccess(final T result) {
        debug("success-call " + getClass());
        // eventBus.fireEvent(AVAILABLE);
        callback(result);
        display.setErrorVisible(false);

        display.stopProcessing();
    }

// -------------------------- OTHER METHODS --------------------------

    public abstract void callback(T result);


    public void callbackError(final Throwable caught) {
        debug("error " + getClass(), caught);
    }
}
