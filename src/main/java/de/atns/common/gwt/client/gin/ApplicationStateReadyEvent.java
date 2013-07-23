package de.atns.common.gwt.client.gin;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author mwolter
 * @since 23.07.13 17:47
 */
public class ApplicationStateReadyEvent extends GwtEvent<ApplicationStateReadyEventHandler> {
    public static Type<ApplicationStateReadyEventHandler> type = new Type<ApplicationStateReadyEventHandler>();

    public Type<ApplicationStateReadyEventHandler> getAssociatedType() {
        return type;
    }

    protected void dispatch(ApplicationStateReadyEventHandler handler) {
        handler.onApplicationStateReady(this);
    }
}
