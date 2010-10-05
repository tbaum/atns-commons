package de.atns.common.security.benutzer.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Singleton;
import de.atns.common.gwt.client.Callback;
import de.atns.common.gwt.client.DialogBoxDisplayInterface;
import de.atns.common.gwt.client.DialogBoxWidgetPresenter;
import de.atns.common.gwt.client.ErrorWidgetDisplay;
import de.atns.common.security.benutzer.client.action.BenutzerCreate;
import de.atns.common.security.benutzer.client.event.BenutzerUpdateEvent;
import de.atns.common.security.benutzer.client.model.BenutzerPresentation;

import static de.atns.common.security.client.DefaultCallback.callback;


/**
 * @author tbaum
 * @since 24.10.2009
 */
@Singleton
public class BenutzerCreatePresenter extends DialogBoxWidgetPresenter<BenutzerCreatePresenter.Display> {
// -------------------------- OTHER METHODS --------------------------

    @Override
    protected void onBind() {
        super.onBind();
        display.reset();

        registerHandler(display.forSafe(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                dispatcher.execute(BenutzerCreatePresenter.this.display.getData(),
                        callback(dispatcher, eventBus, display, new Callback<BenutzerPresentation>() {
                            @Override
                            public void callback(final BenutzerPresentation result) {
                                eventBus.fireEvent(new BenutzerUpdateEvent(result));
                                display.hideDialogBox();
                            }
                        }));
            }
        }));
        display.showDialogBox();
    }

// -------------------------- INNER CLASSES --------------------------

    public static interface Display extends ErrorWidgetDisplay, DialogBoxDisplayInterface {
        HandlerRegistration forSafe(ClickHandler handler);

        BenutzerCreate getData();
    }
}