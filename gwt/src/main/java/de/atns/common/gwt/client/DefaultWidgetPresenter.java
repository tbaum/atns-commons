package de.atns.common.gwt.client;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

/**
 * @author tbaum
 * @since 07.12.2009
 */
public abstract class DefaultWidgetPresenter<D extends WidgetDisplay> extends WidgetPresenter<D> {
// ------------------------------ FIELDS ------------------------------

    protected DispatchAsync dispatcher;

// --------------------- GETTER / SETTER METHODS ---------------------

    @Inject
    public void setDispatcher(DispatchAsync dispatcher) {
        this.dispatcher = dispatcher;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Presenter ---------------------

    @Override public D getDisplay() {
        return super.getDisplay();
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    protected void onBind() {
        if (display instanceof ErrorWidgetDisplay) {
            ((ErrorWidgetDisplay) display).resetErrors();
        }
        if (display instanceof DefaultWidgetDisplay) {
            ((DefaultWidgetDisplay) display).reset();
        }
    }

    @Override
    protected void onRevealDisplay() {
    }

    @Override
    protected void onUnbind() {
    }

    protected void registerHandler(HandlerRegistration... handlerRegistration) {
        for (HandlerRegistration registration : handlerRegistration) {
            registerHandler(registration);
        }
    }
}