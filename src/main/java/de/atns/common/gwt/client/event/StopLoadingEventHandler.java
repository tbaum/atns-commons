package de.atns.common.gwt.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author mwolter
 * @since 23.07.13 11:36
 */
public interface StopLoadingEventHandler extends EventHandler {
    void onStopLoading(StopLoadingEvent event);
}
