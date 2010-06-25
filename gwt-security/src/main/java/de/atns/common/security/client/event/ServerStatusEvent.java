package de.atns.common.security.client.event;

import com.google.gwt.event.shared.GwtEvent;
import de.atns.common.security.client.model.UserPresentation;

/**
 * @author tbaum
 * @since 07.12.2009
 */
public class ServerStatusEvent extends GwtEvent<ServerStatusEventHandler> {
// ------------------------------ FIELDS ------------------------------

    private final ServerStatusEventHandler.ServerStatus status;
    private final UserPresentation user;

// --------------------------- CONSTRUCTORS ---------------------------

    public ServerStatusEvent(final ServerStatusEventHandler.ServerStatus status) {
        this(status, null);
    }

    public ServerStatusEvent(final UserPresentation user) {
        this(user != null && user.isValid() ? ServerStatusEventHandler.ServerStatus.AVAILABLE
                : ServerStatusEventHandler.ServerStatus.NO_LOGIN, user);
    }

    private ServerStatusEvent(final ServerStatusEventHandler.ServerStatus status, final UserPresentation user) {
        this.status = status;
        this.user = user;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public ServerStatusEventHandler.ServerStatus getStatus() {
        return status;
    }

    public UserPresentation getUser() {
        return user;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    protected void dispatch(final ServerStatusEventHandler handler) {
        handler.onServerStatusChange(this);
    }

    @Override
    public Type<ServerStatusEventHandler> getAssociatedType() {
        return ServerStatusEventHandler.TYPE;
    }
}
