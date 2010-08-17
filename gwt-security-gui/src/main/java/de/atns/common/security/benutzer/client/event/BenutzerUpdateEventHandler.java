package de.atns.common.security.benutzer.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public interface BenutzerUpdateEventHandler extends EventHandler {
// ------------------------------ FIELDS ------------------------------

    GwtEvent.Type<BenutzerUpdateEventHandler> TYPE = new GwtEvent.Type<BenutzerUpdateEventHandler>();

// -------------------------- OTHER METHODS --------------------------

    void onUpdate(BenutzerUpdateEvent event);
}