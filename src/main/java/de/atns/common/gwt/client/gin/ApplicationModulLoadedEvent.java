package de.atns.common.gwt.client.gin;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author mwolter
 * @since 22.09.2010 18:17:01
 */
public class ApplicationModulLoadedEvent extends GwtEvent<ApplicationModulLoadedEventHandler> {
    @Override protected void dispatch(final ApplicationModulLoadedEventHandler handler) {
        handler.onReady();
    }

    @Override public Type<ApplicationModulLoadedEventHandler> getAssociatedType() {
        return ApplicationModulLoadedEventHandler.type;
    }
}
