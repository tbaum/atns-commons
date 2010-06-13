package de.atns.common.gwt.client.event;

import com.google.gwt.event.shared.GwtEvent;
import de.atns.common.gwt.client.model.UserPresentation;

/**
 * @author tbaum
 * @since 07.12.2009
 */
public class LogoutEvent extends GwtEvent<LogoutEventHandler> {
// ------------------------------ FIELDS ------------------------------

    private final UserPresentation user;

// --------------------------- CONSTRUCTORS ---------------------------

    public LogoutEvent(final UserPresentation user) {
        this.user = user;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public UserPresentation getUser() {
        return user;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    protected void dispatch(final LogoutEventHandler handler) {
        handler.onLogout(this);
    }

    @Override
    public Type<LogoutEventHandler> getAssociatedType() {
        return LogoutEventHandler.TYPE;
    }
}
