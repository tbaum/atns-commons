package de.atns.common.security.benutzer.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.atns.common.gwt.client.DialogBoxDisplayInterface;
import de.atns.common.gwt.client.DialogBoxWidgetPresenter;
import de.atns.common.gwt.client.ErrorWidgetDisplay;
import de.atns.common.security.benutzer.client.action.BenutzerChangePassword;
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
@Singleton
public class BenutzerChangePasswordPresenter extends DialogBoxWidgetPresenter<BenutzerChangePasswordPresenter.Display> {
// ------------------------------ FIELDS ------------------------------

    private final DispatchAsync dispatcher;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public BenutzerChangePasswordPresenter(final BenutzerChangePasswordPresenter.Display display, final EventBus bus,
                                           final DispatchAsync dispatcher) {
        super(display, bus);
        this.dispatcher = dispatcher;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override protected void onBindInternal() {
        display.reset();

        registerHandler(display.addSafeHandler(new ClickHandler() {
            @Override public void onClick(final ClickEvent event) {
                dispatcher.execute(new BenutzerChangePassword(display.getPassword()),
                        callback(dispatcher, eventBus, display, new Callback<BenutzerPresentation>() {
                            @Override public void callback(final BenutzerPresentation result) {
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
        public HandlerRegistration addSafeHandler(ClickHandler handler);

        String getPassword();
    }
}