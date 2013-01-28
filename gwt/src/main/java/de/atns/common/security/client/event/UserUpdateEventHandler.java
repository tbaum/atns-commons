package de.atns.common.security.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public interface UserUpdateEventHandler extends EventHandler {

    GwtEvent.Type<UserUpdateEventHandler> TYPE = new GwtEvent.Type<UserUpdateEventHandler>();

    void onUserUpdate(UserUpdateEvent event);
}
