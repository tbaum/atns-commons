package de.atns.common.gwt.client.gin;

import com.google.gwt.event.shared.GwtEvent;


/**
 * @author mwolter
 * @since 22.09.2010 18:17:01
 */
public class ApplicationReadyEvent extends GwtEvent<ApplicationReadyEventHandler> {
// -------------------------- OTHER METHODS --------------------------

    @Override protected void dispatch(final ApplicationReadyEventHandler handler) {
        handler.onReady();
    }

    @Override public Type<ApplicationReadyEventHandler> getAssociatedType() {
        return ApplicationReadyEventHandler.type;
    }
}
