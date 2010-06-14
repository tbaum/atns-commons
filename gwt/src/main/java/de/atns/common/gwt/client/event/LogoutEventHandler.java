package de.atns.common.gwt.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public interface LogoutEventHandler extends EventHandler {
// ------------------------------ FIELDS ------------------------------

    GwtEvent.Type<LogoutEventHandler> TYPE = new GwtEvent.Type<LogoutEventHandler>();

// -------------------------- OTHER METHODS --------------------------

    void onLogout(LogoutEvent logoutEvent);
}

