package de.atns.common.security.benutzer.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Singleton;
import de.atns.common.gwt.client.DialogBoxDisplay;
import de.atns.common.gwt.client.DialogBoxPresenter;
import de.atns.common.security.benutzer.client.action.BenutzerUpdate;
import de.atns.common.security.benutzer.client.event.BenutzerUpdateEvent;
import de.atns.common.security.benutzer.client.model.BenutzerPresentation;
import de.atns.common.security.client.Callback;


/**
 * @author tbaum
 * @since 24.10.2009
 */
@Singleton
public class BenutzerEditPresenter extends DialogBoxPresenter<BenutzerEditPresenter.Display> {
// ------------------------------ FIELDS ------------------------------

    private BenutzerPresentation presentation;

// -------------------------- OTHER METHODS --------------------------

    public void bind(final BenutzerPresentation presentation) {
        this.presentation = presentation;
        bind();
    }

    @Override
    protected void onBind() {
        super.onBind();
        display.setData(presentation);

        registerHandler(display.forSafe(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
                final BenutzerUpdate update = BenutzerEditPresenter.this.display.getData();
                dispatcher.execute(update, new Callback<BenutzerPresentation>(display) {
                    @Override
                    public void callback(final BenutzerPresentation result) {
                        eventBus.fireEvent(new BenutzerUpdateEvent(result));
                        display.hideDialogBox();
                    }
                });
            }
        }));
        display.showDialogBox();
    }

// -------------------------- INNER CLASSES --------------------------

    public static interface Display extends DialogBoxDisplay {
        void setData(BenutzerPresentation p);

        HandlerRegistration forSafe(ClickHandler handler);

        BenutzerUpdate getData();
    }
}
