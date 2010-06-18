package de.atns.common.security.client.event;

import com.google.gwt.event.shared.GwtEvent;
import de.atns.common.security.client.model.UserPresentation;

/**
 * @author tbaum
 * @since 07.12.2009
 */
public class LoginEvent extends GwtEvent<LoginEventHandler> {
// ------------------------------ FIELDS ------------------------------

    private final UserPresentation user;

// --------------------------- CONSTRUCTORS ---------------------------

    public LoginEvent(final UserPresentation user) {
        this.user = user;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public UserPresentation getUser() {
        return user;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    protected void dispatch(final LoginEventHandler handler) {
        handler.onLogin(this);
    }

    @Override
    public Type<LoginEventHandler> getAssociatedType() {
        return LoginEventHandler.TYPE;
    }
}
