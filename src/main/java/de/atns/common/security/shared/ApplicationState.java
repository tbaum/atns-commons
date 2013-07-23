package de.atns.common.security.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.extensions.security.SecurityRole;
import com.google.web.bindery.event.shared.EventBus;
import de.atns.common.gwt.client.gin.ApplicationStateReadyEvent;
import de.atns.common.security.client.action.CheckSession;
import de.atns.common.security.client.event.ServerStatusEvent;
import de.atns.common.security.client.event.ServerStatusEventHandler;
import de.atns.common.security.client.model.UserPresentation;
import net.customware.gwt.dispatch.client.DispatchAsync;

import static de.atns.common.security.client.event.ServerStatusEvent.ServerStatus.LOGGED_IN;
import static de.atns.common.security.client.event.ServerStatusEvent.ServerStatus.LOGGED_OUT;

/**
 * @author tbaum
 * @since 14.02.2010
 */
@Singleton
public class ApplicationState {

    private final EventBus eventBus;
    private UserPresentation user = UserPresentation.invalidUser();

    @Inject public ApplicationState(final EventBus eventBus, DispatchAsync dispatcher) {
        this.eventBus = eventBus;
        eventBus.addHandler(ServerStatusEventHandler.TYPE, new ServerStatusEventHandler() {
            @Override public void onServerStatusChange(final ServerStatusEvent event) {
                final ServerStatusEvent.ServerStatus status = event.getStatus();
                if (status == LOGGED_IN) {
                    update(event.getUser());
                } else if (status == LOGGED_OUT) {
                    update(null);
                }
            }
        });
        dispatcher.execute(new CheckSession(), new AsyncCallback<UserPresentation>() {
            @Override public void onFailure(final Throwable caught) {
            }

            @Override public void onSuccess(final UserPresentation user) {
                update(user);
            }
        });
    }

    public void update(UserPresentation result) {
        this.user = result;
        eventBus.fireEvent(new ApplicationStateReadyEvent());
    }

    public UserPresentation getUser() {
        return user;
    }

    public String getAuthToken() {
        return user.getAuthToken();
    }

    public boolean inRole(Class<? extends SecurityRole>... required) {
        return user.inRole(required);
    }

    public boolean inRole(Class<? extends SecurityRole> required) {
        return user.inRole(required);
    }

    public boolean isValidUser() {
        return user != null && user.isValid();
    }
}
