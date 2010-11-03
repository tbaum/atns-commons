package de.atns.common.gwt.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.List;

/**
 * @author tbaum
 * @since 07.12.2009
 */
public abstract class WidgetPresenter<D extends WidgetDisplay> {
// ------------------------------ FIELDS ------------------------------

    protected DispatchAsync dispatcher;


    protected D display;
    protected EventBus eventBus;
    private List<HandlerRegistration> handlerRegistrations = new java.util.ArrayList<HandlerRegistration>();

    private boolean bound = false;

// --------------------- GETTER / SETTER METHODS ---------------------

    public D getDisplay() {
        return display;
    }

    @Inject
    public void setDisplay(D display) {
        this.display = display;
    }

    public boolean isBound() {
        return bound;
    }

    @Inject
    public void setDispatcher(DispatchAsync dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Inject
    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

// -------------------------- OTHER METHODS --------------------------

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
        if (display != null) {
            display.resetErrors();
        }
        if (display instanceof DefaultWidgetDisplay) {
            display.reset();
        }
    }

    /**
     * Any {@link HandlerRegistration}s added will be removed when
     * {@link #unbind()} is called. This provides a handy way to track event
     * handler registrations when binding and unbinding.
     *
     * @param handlerRegistration The registration.
     */
    protected void registerHandler(HandlerRegistration... handlerRegistration) {
        for (HandlerRegistration registration : handlerRegistration) {
            handlerRegistrations.add(registration);
        }
    }

    public void unbind() {
        if (bound) {
            bound = false;

            for (HandlerRegistration reg : handlerRegistrations) {
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
