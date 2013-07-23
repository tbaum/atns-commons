package de.atns.common.gwt.client.gin;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author mwolter
 * @since 22.09.2010 18:16:46
 */
public interface ApplicationModulLoadedEventHandler extends EventHandler {

    GwtEvent.Type<ApplicationModulLoadedEventHandler> type = new GwtEvent.Type<ApplicationModulLoadedEventHandler>();

    void onReady();
}
