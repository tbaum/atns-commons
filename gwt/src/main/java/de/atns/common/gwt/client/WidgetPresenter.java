package de.atns.common.gwt.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author tbaum
 * @since 07.12.2009
 */
public abstract class WidgetPresenter<D extends IsWidget> implements Activity {
// ------------------------------ FIELDS ------------------------------

    protected DispatchAsync dispatcher;


    protected D display;
    protected EventBus eventBus;
    private final Logger LOG = Logger.getLogger(this.getClass().toString());
    private final List<HandlerRegistration> handlerRegistrations = new java.util.ArrayList<HandlerRegistration>();

    private boolean bound = false;

// --------------------- GETTER / SETTER METHODS ---------------------

    public D getDisplay() {
        return display;
    }

    public boolean isBound() {
        return bound;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Activity ---------------------

    @Override public String mayStop() {
        return null;
    }

    @Override public void onCancel() {
        LOG.log(Level.FINE, "Activity: onCancel()");
        onStop();
    }

    @Override public void onStop() {
        LOG.log(Level.FINE, "Activity: onStop()");

        unbind();
    }

    @Override
    public final void start(final AcceptsOneWidget panel, final com.google.gwt.event.shared.EventBus eventBus) {
        start(panel, (EventBus) eventBus);
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * Any {@link HandlerRegistration}s added will be removed when
     * {@link #unbind()} is called. This provides a handy way to track event
     * handler registrations when binding and unbinding.
     *
     * @param handlerRegistration The registration.
     */
    protected void registerHandler(final HandlerRegistration... handlerRegistration) {
        Collections.addAll(handlerRegistrations, handlerRegistration);
    }

    @Inject
    public void setDispatcher(final DispatchAsync dispatcher) {
        this.dispatcher = dispatcher;
        if (LOG.isLoggable(Level.FINEST)) {
            LOG.log(Level.FINEST, "setDispatcher->" + Util.toString(dispatcher));
        }
    }

    @Inject
    public void setDisplay(final D display) {
        this.display = display;
        if (LOG.isLoggable(Level.FINEST)) {
            LOG.log(Level.FINEST, "setDisplay->" + Util.toString(display));
        }
    }

    @Inject
    public void setEventBus(final EventBus eventBus) {
        this.eventBus = eventBus;

        if (LOG.isLoggable(Level.FINEST)) {
            LOG.log(Level.FINEST, "setEventBus->" + Util.toString(eventBus));
        }
    }

    public void start(final AcceptsOneWidget panel, final EventBus eventBus) {
        LOG.log(Level.FINE, "Activity: start()");

        panel.setWidget(display.asWidget());
        LOG.log(Level.FINE, "Activity: showPanel(" + display + ")");

        bind();
    }

    public void bind() {
        if (!bound) {
            onBind();
            bound = true;
        }
    }

    /**
     * This method is called when binding the presenter. Any additional bindings
     * should be done here.
     */
    protected void onBind() {
        if (display instanceof WidgetDisplay) {
            ((WidgetDisplay) display).resetErrors();
        }
        if (display instanceof DefaultWidgetDisplay) {
            ((DefaultWidgetDisplay) display).reset();
        }
    }

    public void unbind() {
        if (bound) {
            bound = false;

            for (final HandlerRegistration reg : handlerRegistrations) {
                reg.removeHandler();
            }
            handlerRegistrations.clear();

            onUnbind();
        }
    }

    /**
     * This method is called when unbinding the presenter. Any handler
     * registrations recorded with {@link #registerHandler(HandlerRegistration)}
     * will have already been removed at this point.
     */
    protected void onUnbind() {
    }
}
