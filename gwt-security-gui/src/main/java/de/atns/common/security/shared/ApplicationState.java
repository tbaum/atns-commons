package de.atns.common.security.shared;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.atns.common.security.client.event.LoginEvent;
import de.atns.common.security.client.event.LoginEventHandler;
import de.atns.common.security.client.event.LogoutEvent;
import de.atns.common.security.client.event.LogoutEventHandler;
import de.atns.common.security.client.model.UserPresentation;

/**
 * @author tbaum
 * @since 14.02.2010
 */
@Singleton public class ApplicationState {
// ------------------------------ FIELDS ------------------------------

    private UserPresentation user = new UserPresentation();

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public ApplicationState(final EventBus eventBus) {
        eventBus.addHandler(LoginEventHandler.TYPE, new LoginEventHandler() {
            @Override public void onLogin(final LoginEvent event) {
                user = event.getUser();
            }
        });

        eventBus.addHandler(LogoutEventHandler.TYPE, new LogoutEventHandler() {
            @Override public void onLogout(final LogoutEvent event) {
                user = null;
            }
        });
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public UserPresentation getUser() {
        return user;
    }

// -------------------------- OTHER METHODS --------------------------

    public String getAuthToken() {
        return user.getAuthToken();
    }

    public boolean isValidUser() {
        return user != null && user.isValid();
    }
}
