package de.atns.common.security.shared;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.atns.common.security.client.event.ServerStatusEvent;
import de.atns.common.security.client.event.ServerStatusEventHandler;
import de.atns.common.security.client.model.UserPresentation;

import static de.atns.common.security.client.event.ServerStatusEvent.ServerStatus.LOGGED_IN;
import static de.atns.common.security.client.event.ServerStatusEvent.ServerStatus.LOGGED_OUT;

/**
 * @author tbaum
 * @since 14.02.2010
 */
@Singleton public class ApplicationState {
// ------------------------------ FIELDS ------------------------------

    private UserPresentation user = new UserPresentation();

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public ApplicationState(final EventBus eventBus) {
        eventBus.addHandler(ServerStatusEventHandler.TYPE, new ServerStatusEventHandler() {
            @Override public void onServerStatusChange(ServerStatusEvent event) {
                final ServerStatusEvent.ServerStatus status = event.getStatus();
                if (status == LOGGED_IN) {
                    user = event.getUser();
                } else if (status == LOGGED_OUT) {
                    user = null;
                }
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
