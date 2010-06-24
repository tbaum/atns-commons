package de.atns.common.security.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Cookies;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.gwt.crypto.bouncycastle.InvalidCipherTextException;
import de.atns.common.gwt.client.DialogBoxDisplayInterface;
import de.atns.common.gwt.client.DialogBoxWidgetPresenter;
import de.atns.common.security.client.action.UserLogin;
import de.atns.common.security.client.event.ServerStatusEvent;
import de.atns.common.security.client.model.UserPresentation;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author tbaum
 * @since 24.10.2009
 */
@Singleton public class LoginPresenter extends DialogBoxWidgetPresenter<LoginPresenter.Display> {
// ------------------------------ FIELDS ------------------------------

    private static final Logger LOG = Logger.getLogger(LoginPresenter.class.getName());
    private final DispatchAsync dispatcher;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public LoginPresenter(final LoginView display, final EventBus bus, final DispatchAsync dispatcher) {
        super(display, bus);
        this.dispatcher = dispatcher;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Presenter ---------------------

    @Override public void refreshDisplay() {
    }

// -------------------------- OTHER METHODS --------------------------

    @Override public Place getPlace() {
        return null;
    }

    @Override
    protected void onBindInternal() {
        display.showDialogBox();

        registerHandler(display.addLoginClick(new ClickHandler() {
            @Override public void onClick(final ClickEvent clickEvent) {
                final String l = display.usernameValue();
                final String p = display.passwordValue();

                if (display.rememberValue()) {
                    try {
                        Cookies.setCookie("l", CryptoUtil.encrypt(l) + ":" + CryptoUtil.encrypt(p));
                    } catch (InvalidCipherTextException e) {
                        LOG.log(Level.FINE, e.getMessage(), e);
                    }
                } else {
                    Cookies.removeCookie("l");
                }

                dispatcher.execute(new UserLogin(l, p),
                        new DefaultCallback<UserPresentation>(dispatcher, eventBus, display) {
                            @Override public void callback(final UserPresentation user) {
                                eventBus.fireEvent(new ServerStatusEvent(user));
                            }
                        });
            }
        }));

        try {
            String[] s = Cookies.getCookie("l").split(":");
            display.usernameValue(CryptoUtil.decrypt(s[0]));
            display.passwordValue(CryptoUtil.decrypt(s[1]));
            display.rememberValue(true);
        } catch (Exception ignore) {
        }
    }

// -------------------------- INNER CLASSES --------------------------

    public static interface Display extends DialogBoxDisplayInterface {
        HandlerRegistration addLoginClick(final ClickHandler clickHandler);

        String passwordValue();

        void passwordValue(String s);

        boolean rememberValue();

        void rememberValue(boolean v);

        String usernameValue();

        void usernameValue(String username);
    }
}
