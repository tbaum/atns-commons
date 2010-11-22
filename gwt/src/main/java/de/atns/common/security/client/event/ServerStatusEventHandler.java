package de.atns.common.security.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public interface ServerStatusEventHandler extends EventHandler {
// ------------------------------ FIELDS ------------------------------

    GwtEvent.Type<ServerStatusEventHandler> TYPE = new GwtEvent.Type<ServerStatusEventHandler>();

// -------------------------- OTHER METHODS --------------------------

    void onServerStatusChange(ServerStatusEvent event);
}
