package de.atns.common.security.benutzer.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.atns.common.gwt.client.DialogBoxDisplayInterface;
import de.atns.common.gwt.client.DialogBoxWidgetPresenter;
import de.atns.common.gwt.client.ErrorWidgetDisplay;
import de.atns.common.gwt.client.model.CreateResult;
import de.atns.common.security.client.DefaultCallback;
import de.atns.common.security.benutzer.client.action.BenutzerCreate;
import de.atns.common.security.benutzer.client.event.BenutzerUpdateEvent;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;


/**
 * @author tbaum
 * @since 24.10.2009
 */
@Singleton
public class BenutzerCreatePresenter extends DialogBoxWidgetPresenter<BenutzerCreatePresenter.Display> {
// ------------------------------ FIELDS ------------------------------

    private final DispatchAsync dispatcher;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public BenutzerCreatePresenter(final BenutzerCreatePresenter.Display display, final EventBus bus, final DispatchAsync dispatcher) {
        super(display, bus);
        this.dispatcher = dispatcher;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override protected void onBindInternal() {
        display.reset();

        registerHandler(display.addSafeHandler(new ClickHandler() {
            @Override public void onClick(final ClickEvent event) {
                final String email = display.getEmail();
                final String pass = display.getPasswort();
                final boolean admin = display.isAdmin();
                final String login = display.getLogin();

                dispatcher.execute(new BenutzerCreate(admin, login, pass, email),
                        new DefaultCallback<CreateResult>(dispatcher, eventBus, display) {
                            @Override public void callback(final CreateResult result) {
                                eventBus.fireEvent(new BenutzerUpdateEvent(login));
                                display.hideDialogBox();
                            }
                        });
            }
        }));
        display.showDialogBox();
    }

// -------------------------- INNER CLASSES --------------------------

    public static interface Display extends ErrorWidgetDisplay, DialogBoxDisplayInterface {
        public HandlerRegistration addSafeHandler(ClickHandler handler);

        String getEmail();

        String getPasswort();

        boolean isAdmin();

        String getLogin();
    }
}