package de.atns.common.security.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.inject.extensions.security.NotLogginException;
import com.google.inject.extensions.security.SecurityException;
import de.atns.common.security.client.model.UserPresentation;

import static de.atns.common.security.client.event.ServerStatusEvent.ServerStatus.*;

/**
 * @author tbaum
 * @since 07.12.2009
 */
public class ServerStatusEvent extends GwtEvent<ServerStatusEventHandler> {

    private final ServerStatus status;
    private final UserPresentation user;

    private ServerStatusEvent(final ServerStatus status, final UserPresentation user) {
        this.status = status;
        this.user = user;
    }

    public static ServerStatusEvent loggedin(final UserPresentation user) {
        if (user == null) return toServerStatus(null);
        return user.isValid() ? new ServerStatusEvent(LOGGED_IN, user) : loggedout();
    }

    public static ServerStatusEvent toServerStatus(final Throwable exception) {
        if (exception instanceof NotLogginException) {
            return loggedout();
        }
        if (exception instanceof SecurityException) {
            return new ServerStatusEvent(SECURITY, null);
        }
        return new ServerStatusEvent(UNAVAILABLE, null);
    }

    public static ServerStatusEvent loggedout() {
        return new ServerStatusEvent(LOGGED_OUT, null);
    }

    public ServerStatus getStatus() {
        return status;
    }

    public UserPresentation getUser() {
        return user;
    }

    @Override protected void dispatch(final ServerStatusEventHandler handler) {
        handler.onServerStatusChange(this);
    }

    @Override public Type<ServerStatusEventHandler> getAssociatedType() {
        return ServerStatusEventHandler.TYPE;
    }

    public static enum ServerStatus {
        LOGGED_IN, LOGGED_OUT, SECURITY, UNAVAILABLE
    }
}
