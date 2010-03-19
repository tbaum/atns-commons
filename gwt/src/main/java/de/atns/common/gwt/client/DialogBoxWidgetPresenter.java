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
public abstract class DialogBoxWidgetPresenter<D extends DialogBoxDisplayInterface> extends DefaultWidgetPresenter<D> {
// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public DialogBoxWidgetPresenter(D display, EventBus eventBus) {
        super(display, eventBus);
    }

    @Override protected void onUnbind() {
        display.hideDialogBox();
    }
}