package de.atns.common.gwt.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author mwolter
 * @since 23.07.13 11:35
 */
public interface StartLoadingEventHandler extends EventHandler {
    void onStartLoading(StartLoadingEvent event);
}
