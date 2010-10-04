package de.atns.common.security.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.DeferredCommand;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.gwt.crypto.bouncycastle.InvalidCipherTextException;
import de.atns.common.gwt.client.Callback;
import de.atns.common.gwt.client.DialogBoxDisplayInterface;
import de.atns.common.gwt.client.DialogBoxWidgetPresenter;
import de.atns.common.security.client.action.UserLogin;
import de.atns.common.security.client.event.ServerStatusEvent;
import de.atns.common.security.client.model.UserPresentation;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.atns.common.security.client.DefaultCallback.callback;

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

// -------------------------- OTHER METHODS --------------------------

    @Override
    protected void onBindInternal() {
        display.showDialogBox();

        registerHandler(display.addLoginClick(new ClickHandler() {
            @Override public void onClick(final ClickEvent clickEvent) {
                doLogin(display.usernameValue(), display.passwordValue());
            }
        }));

        try {
            String[] s = Cookies.getCookie("l").split(":");
            final String username = CryptoUtil.decrypt(s[0]);
            display.usernameValue(username);
            final String password = CryptoUtil.decrypt(s[1]);
            display.passwordValue(password);
            display.rememberValue(true);

            DeferredCommand.addCommand(new Command() {
                @Override public void execute() {
                    doLogin(username, password);
                }
            });
        } catch (Exception ignore) {
        }
    }

    private void doLogin(final String login, final String password) {
        if (display.automaticLogin()) {
            try {
                final String value = CryptoUtil.encrypt(login) + ":" + CryptoUtil.encrypt(password);
                final Date expires = new Date(new Date().getTime() + (1000L * 60 * 60 * 24 * 365));
                Cookies.setCookie("l", value, expires);
            } catch (InvalidCipherTextException e) {
                LOG.log(Level.FINE, e.getMessage(), e);
            }
        } else {
            Cookies.removeCookie("l");
        }

        dispatcher.execute(new UserLogin(login, password),
                callback(dispatcher, eventBus, display, new Callback<UserPresentation>() {
                    @Override public void callback(final UserPresentation user) {
                        eventBus.fireEvent(new ServerStatusEvent(user));
                    }
                }));
    }

// -------------------------- INNER CLASSES --------------------------

    public static interface Display extends DialogBoxDisplayInterface {
        HandlerRegistration addLoginClick(final ClickHandler clickHandler);

        String passwordValue();

        void passwordValue(String s);

        boolean automaticLogin();

        void rememberValue(boolean v);

        String usernameValue();

        void usernameValue(String username);
    }
}
