package de.atns.common.gwt.client;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

/**
 * @author tbaum
 * @since 07.12.2009
 */
public abstract class DefaultWidgetPresenter<D extends WidgetDisplay> extends WidgetPresenter<D> {
// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public DefaultWidgetPresenter(D display, EventBus eventBus) {
        super(display, eventBus);
    }

// -------------------------- OTHER METHODS --------------------------

    @Override protected final void onBind() {
        if (display instanceof ErrorWidgetDisplay) {
            ((ErrorWidgetDisplay) display).resetErrors();
        }
        if (display instanceof DefaultWidgetDisplay) {
            ((DefaultWidgetDisplay) display).reset();
        }

        onBindInternal();
    }

    protected void onBindInternal() {
    }

    @Override protected void onRevealDisplay() {
    }

    @Override protected void onUnbind() {
    }

    protected void registerHandler(HandlerRegistration... handlerRegistration) {
        for (HandlerRegistration registration : handlerRegistration) {
            registerHandler(registration);
        }
    }
}
