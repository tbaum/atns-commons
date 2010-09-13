package de.atns.common.security.benutzer.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.atns.common.gwt.client.DialogBoxDisplayInterface;
import de.atns.common.gwt.client.DialogBoxWidgetPresenter;
import de.atns.common.gwt.client.ErrorWidgetDisplay;
import de.atns.common.gwt.client.model.EmptyResult;
import de.atns.common.security.benutzer.client.action.BenutzerUpdate;
import de.atns.common.security.benutzer.client.event.BenutzerUpdateEvent;
import de.atns.common.security.benutzer.client.model.BenutzerPresentation;
import de.atns.common.security.client.Callback;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;

import static de.atns.common.security.client.DefaultCallback.callback;


/**
 * @author tbaum
 * @since 24.10.2009
 */
@Singleton public class BenutzerEditPresenter extends DialogBoxWidgetPresenter<BenutzerEditPresenter.Display> {
// ------------------------------ FIELDS ------------------------------

    private final DispatchAsync dispatcher;
    private BenutzerPresentation presentation;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public BenutzerEditPresenter(final Display display, final EventBus bus, final DispatchAsync dispatcher) {
        super(display, bus);
        this.dispatcher = dispatcher;
    }

// -------------------------- OTHER METHODS --------------------------

    public void bind(final BenutzerPresentation presentation) {
        this.presentation = presentation;
        bind();
    }

    @Override protected void onBindInternal() {
        display.setData(presentation);

        registerHandler(display.addSafeHandler(new ClickHandler() {
            @Override public void onClick(final ClickEvent event) {
                final String login = presentation.getLogin();
                final String email = display.getEmail();
                final String pass = display.getPasswort();
                final boolean admin = display.isAdmin();

                dispatcher.execute(new BenutzerUpdate(admin, login, email, pass),
                        callback(dispatcher, eventBus, display, new Callback<EmptyResult>() {
                            @Override public void callback(final EmptyResult result) {
                                eventBus.fireEvent(new BenutzerUpdateEvent(login));
                                display.hideDialogBox();
                            }
                        }));
            }
        }));
        display.showDialogBox();
    }

// -------------------------- INNER CLASSES --------------------------

    public static interface Display extends ErrorWidgetDisplay, DialogBoxDisplayInterface {
        public void setData(BenutzerPresentation p);

        public HandlerRegistration addSafeHandler(ClickHandler handler);

        String getEmail();

        String getPasswort();

        boolean isAdmin();
    }
}