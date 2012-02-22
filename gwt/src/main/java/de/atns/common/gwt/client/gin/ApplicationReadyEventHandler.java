package de.atns.common.gwt.client.gin;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * @author mwolter
 * @since 22.09.2010 18:16:46
 */
public interface ApplicationReadyEventHandler extends EventHandler {

    GwtEvent.Type<ApplicationReadyEventHandler> type = new GwtEvent.Type<ApplicationReadyEventHandler>();

    void onReady();
}
