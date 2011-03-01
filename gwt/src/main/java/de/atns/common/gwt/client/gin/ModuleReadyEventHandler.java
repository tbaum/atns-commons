package de.atns.common.gwt.client.gin;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author mwolter
 * @since 22.09.2010 18:16:46
 */
public interface ModuleReadyEventHandler extends EventHandler {
// ------------------------------ FIELDS ------------------------------

    GwtEvent.Type<ModuleReadyEventHandler> type = new GwtEvent.Type<ModuleReadyEventHandler>();

// -------------------------- OTHER METHODS --------------------------

    void onReady(SharedServicesModuleLoader sharedServicesModuleLoader);
}
