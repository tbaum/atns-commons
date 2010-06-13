package de.atns.common.gwt.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public interface LoginEventHandler extends EventHandler {
// ------------------------------ FIELDS ------------------------------

    GwtEvent.Type<LoginEventHandler> TYPE = new GwtEvent.Type<LoginEventHandler>();

// -------------------------- OTHER METHODS --------------------------

    void onLogin(LoginEvent event);
}
