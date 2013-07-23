package de.atns.common.gwt.client.gin;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author mwolter
 * @since 23.07.13 17:47
 */
public interface ApplicationStateReadyEventHandler extends EventHandler {
    void onApplicationStateReady(ApplicationStateReadyEvent event);
}
