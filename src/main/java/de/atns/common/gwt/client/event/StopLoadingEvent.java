package de.atns.common.gwt.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author mwolter
 * @since 23.07.13 11:36
 */
public class StopLoadingEvent extends GwtEvent<StopLoadingEventHandler> {
    public static Type<StopLoadingEventHandler> TYPE = new Type<StopLoadingEventHandler>();

    public Type<StopLoadingEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(StopLoadingEventHandler handler) {
        handler.onStopLoading(this);
    }
}
