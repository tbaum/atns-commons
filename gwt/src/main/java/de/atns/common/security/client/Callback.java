package de.atns.common.security.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import de.atns.common.gwt.client.DefaultWidgetDisplay;
import de.atns.common.gwt.client.WidgetDisplay;
import de.atns.common.security.SecurityException;
import de.atns.common.security.client.action.CheckSession;
import de.atns.common.security.client.model.UserPresentation;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.logging.Level;
import java.util.logging.Logger;

import static de.atns.common.security.client.event.ServerStatusEvent.toServerStatus;
import static java.util.logging.Level.WARNING;

/**
 * @author tbaum
 * @since 05.10.2010
 */
public abstract class Callback<T> implements AsyncCallback<T> {

    private static final Logger LOG = Logger.getLogger(Callback.class.getName());

    private static final WidgetDisplay NULL_DISPLAY = new DefaultWidgetDisplay() {

        @Override public void reset() {
        }
    };

    @Inject private static Provider<DispatchAsync> dispatcher;
    @Inject private static Provider<EventBus> eventBus;

    private final WidgetDisplay display;

    public Callback() {
        this(NULL_DISPLAY);
    }

    public Callback(final WidgetDisplay display) {
        this.display = display;
        this.display.startProcessing();
    }

    private native static void reload() /*-{
        $wnd.location.reload();
    }-*/;

    private native static void forcedReload() /*-{
        $wnd.location.reload(true);
    }-*/;

    @Override public void onFailure(final Throwable originalCaught) {
        if (originalCaught instanceof SecurityException) {
            final String message = originalCaught.getMessage();
            LOG.log(WARNING, "check session in callback '" + message + "'");
            eventBus.get().fireEvent(toServerStatus(originalCaught));
            display.stopProcessing();
            if (message != null) {
                display.showError(message);
            } else {
                display.showError(originalCaught.toString());
            }
        } else {
            LOG.log(WARNING, "check session in callback 1", originalCaught);
            String message = originalCaught.getMessage();
            if (message == null) {
                message = originalCaught.toString();
            }
            display.showError(message);
            if ((message != null && message.startsWith("505")) || originalCaught.toString().startsWith("505")) {
                LOG.log(WARNING, "Trigger refresh");
                try {
                    forcedReload();
                } catch (Exception ignored) {
                }
                reload();
            }
            dispatcher.get().execute(new CheckSession(), new AsyncCallback<UserPresentation>() {
                @Override public void onFailure(final Throwable caught) {
                    LOG.log(WARNING, "failed-checksession", caught);
                    eventBus.get().fireEvent(toServerStatus(caught));
                    display.stopProcessing();
                }

                @Override public void onSuccess(final UserPresentation result) {
                    LOG.log(Level.FINE, "success-checksession");
                    display.stopProcessing();
                }
            });
        }
    }

    @Override public void onSuccess(final T result) {
        LOG.log(Level.FINE, "success-call " + getClass());
        // eventBus.fireEvent(AVAILABLE);

        callback(result);
        display.setErrorVisible(false);
        display.stopProcessing();
    }

    public abstract void callback(T result);
}
