package de.atns.common.security.login.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Cookies;
import com.google.inject.Singleton;
import com.googlecode.gwt.crypto.bouncycastle.InvalidCipherTextException;
import de.atns.common.gwt.client.DialogBoxDisplay;
import de.atns.common.gwt.client.DialogBoxPresenter;
import de.atns.common.gwt.client.gin.PlacePresenter;
import de.atns.common.security.client.Callback;
import de.atns.common.security.client.CryptoUtil;
import de.atns.common.security.client.action.UserLogin;
import de.atns.common.security.client.action.UserLogout;
import de.atns.common.security.client.event.ServerStatusEvent;
import de.atns.common.security.client.model.UserPresentation;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.atns.common.security.client.event.ServerStatusEvent.loggedin;

/**
 * @author tbaum
 * @since 24.10.2009
 */
@Singleton
public class LoginPresenter extends DialogBoxPresenter<LoginPresenter.Display> implements PlacePresenter {
// ------------------------------ FIELDS ------------------------------

    private static final Logger LOG = Logger.getLogger(LoginPresenter.class.getName());

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface PlacePresenter ---------------------

    @Override public Activity updateForPlace(Place place) {
        if (place instanceof LogoutPlace) {
            Cookies.removeCookie("l");
            dispatcher.execute(new UserLogout(), new Callback<UserPresentation>() {
                @Override
                public void callback(final UserPresentation user) {
                    eventBus.fireEvent(ServerStatusEvent.loggedout());
                }
            });
        }

        return this;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    protected void onBind() {
        super.onBind();

        registerHandler(display.addLoginClick(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent clickEvent) {
                doLogin(display.setUsername(), display.getPassword());
            }
        }));

        try {
            String[] s = Cookies.getCookie("l").split(":");
            final String username = CryptoUtil.decrypt(s[0]);
            display.getUsername(username);
            final String password = CryptoUtil.decrypt(s[1]);
            display.setPassword(password);
            display.setAutomaticLogin(true);

            Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
                @Override
                public void execute() {
                    doLogin(username, password);
                }
            });
        } catch (Exception ignore) {
        }
    }

    private void doLogin(final String login, final String password) {
        if (display.isAutomaticLogin()) {
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

        dispatcher.execute(new UserLogin(login, password), new Callback<UserPresentation>(display) {
            @Override public void callback(final UserPresentation user) {
                eventBus.fireEvent(loggedin(user));
            }
        });
    }

// -------------------------- INNER CLASSES --------------------------

    public static interface Display extends DialogBoxDisplay {
        HandlerRegistration addLoginClick(final ClickHandler clickHandler);

        String getPassword();

        void setPassword(String s);

        boolean isAutomaticLogin();

        void setAutomaticLogin(boolean v);

        String setUsername();

        void getUsername(String username);
    }
}
