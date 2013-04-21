package de.atns.common.security.client.event;

import com.google.gwt.event.shared.GwtEvent;
import de.atns.common.security.client.model.UserPresentation;

/**
 * @author tbaum
 * @since 07.12.2009
 */
public class UserUpdateEvent extends GwtEvent<UserUpdateEventHandler> {

    private final UserPresentation user;

    public UserUpdateEvent(final UserPresentation user) {
        this.user = user;
    }

    public UserPresentation getUser() {
        return user;
    }

    @Override protected void dispatch(final UserUpdateEventHandler handler) {
        handler.onUserUpdate(this);
    }

    @Override public Type<UserUpdateEventHandler> getAssociatedType() {
        return UserUpdateEventHandler.TYPE;
    }
}
