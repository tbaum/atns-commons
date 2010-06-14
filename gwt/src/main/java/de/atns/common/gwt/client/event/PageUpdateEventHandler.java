package de.atns.common.gwt.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author tbaum
 * @since 24.10.2009
 */
public interface PageUpdateEventHandler extends EventHandler {
// ------------------------------ FIELDS ------------------------------

    GwtEvent.Type<PageUpdateEventHandler> TYPE = new GwtEvent.Type<PageUpdateEventHandler>();

// -------------------------- OTHER METHODS --------------------------

    void onUpdate(PageUpdateEvent updateEvent);
}