package de.atns.common.gwt.client;

import com.google.inject.Inject;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceRequest;
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

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Presenter ---------------------

    public void refreshDisplay() {
    }

// -------------------------- OTHER METHODS --------------------------

    @Override public Place getPlace() {
        return null;
    }

    @Override protected void reset() {
        display.reset();
    }

    @Override protected void onBind() {
    }

    @Override protected void onPlaceRequest(final PlaceRequest request) {
    }

    @Override protected void onUnbind() {
    }
}
