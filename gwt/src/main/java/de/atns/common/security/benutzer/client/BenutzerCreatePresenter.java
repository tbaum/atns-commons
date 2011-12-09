package de.atns.common.security.benutzer.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.HandlerRegistration;
import de.atns.common.gwt.client.DialogBoxDisplay;
import de.atns.common.gwt.client.DialogBoxPresenter;
import de.atns.common.security.benutzer.client.action.BenutzerCreate;
import de.atns.common.security.benutzer.client.event.BenutzerUpdateEvent;
import de.atns.common.security.client.Callback;
import de.atns.common.security.client.model.UserPresentation;


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
        registerHandler(display.forSafe(new ClickHandler() {
            @Override public void onClick(final ClickEvent event) {
                dispatcher.execute(new BenutzerCreate(display.getData(new UserPresentation())),
                        new Callback<UserPresentation>(display) {
                            @Override
                            public void callback(final UserPresentation result) {
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

        UserPresentation getData(UserPresentation userPresentation);
    }
}
