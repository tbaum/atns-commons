package de.atns.common.gwt.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author mwolter
 * @since 23.07.13 11:35
 */
public class StartLoadingEvent extends GwtEvent<StartLoadingEventHandler> {
    public static Type<StartLoadingEventHandler> TYPE = new Type<StartLoadingEventHandler>();

    public Type<StartLoadingEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(StartLoadingEventHandler handler) {
        handler.onStartLoading(this);
    }
}
