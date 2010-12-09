package de.atns.common.security.benutzer.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Singleton;
import de.atns.common.gwt.client.DialogBoxDisplay;
import de.atns.common.gwt.client.DialogBoxPresenter;
import de.atns.common.security.benutzer.client.action.BenutzerCreate;
import de.atns.common.security.benutzer.client.event.BenutzerUpdateEvent;
import de.atns.common.security.benutzer.client.model.BenutzerPresentation;
import de.atns.common.security.client.Callback;


/**
 * @author tbaum
 * @since 24.10.2009
 */
@Singleton
public class BenutzerCreatePresenter extends DialogBoxPresenter<BenutzerCreatePresenter.Display> {
// -------------------------- OTHER METHODS --------------------------

    @Override
    protected void onBind() {
        super.onBind();
        display.reset();

        registerHandler(display.forSafe(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                dispatcher.execute(BenutzerCreatePresenter.this.display.getData(),
                        new Callback<BenutzerPresentation>(display) {
                            @Override
                            public void callback(final BenutzerPresentation result) {
                                eventBus.fireEvent(new BenutzerUpdateEvent(result));
                                display.hideDialogBox();
                            }
                        });
            }
        }));
    }

// -------------------------- INNER CLASSES --------------------------

    public static interface Display extends DialogBoxDisplay {
        HandlerRegistration forSafe(ClickHandler handler);

        BenutzerCreate getData();
    }
}
