package de.atns.common.gwt.client.window;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.*;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.Window;

/**
 * @author: mwolter
 * @since: 24.01.11 12:51
 */
public class PopupWindowEventBus extends EventBus {
// ------------------------------ FIELDS ------------------------------

    private final SimpleEventBus simpleEventBus = new SimpleEventBus();
    private final EventSerializer serializer;

// --------------------------- CONSTRUCTORS ---------------------------

    public PopupWindowEventBus(final EventSerializer serializer) {
        this.serializer = serializer;
        _registerEventBus(this);

        Window.addCloseHandler(new CloseHandler<Window>() {
            @Override public void onClose(CloseEvent<Window> windowCloseEvent) {
                windowClosed();
            }
        });
    }

    private native void windowClosed() /*-{
        $wnd.opener._popup_closed($wnd._myid);
    }-*/;

    private native void _registerEventBus(PopupWindowEventBus windowEventbus) /*-{
        $wnd._event_from_master = function(event) {
            windowEventbus.@de.atns.common.gwt.client.window.PopupWindowEventBus::fire(Lcom/google/gwt/core/client/JavaScriptObject;)(event);
        }
    }-*/;

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface HasHandlers ---------------------

    @Override public void fireEvent(GwtEvent<?> event) {
        simpleEventBus.fireEvent(event);

        if (event instanceof TransportAware) {
            TransportAware transportAware = (TransportAware) event;
            _event_fireEvent(serializer.serialize(transportAware));
        }
    }

// -------------------------- OTHER METHODS --------------------------

    private native void _event_fireEvent(JavaScriptObject event) /*-{
        $wnd.opener._event_from_popup($wnd, event);
    }-*/;

    @Override
    public <H extends EventHandler> HandlerRegistration addHandler(GwtEvent.Type<H> type, H handler) {
        // TODO unregister
        return simpleEventBus.addHandler(type, handler);
    }

    @Override
    public <H extends EventHandler> HandlerRegistration addHandlerToSource(GwtEvent.Type<H> type, Object source,
                                                                           H handler) {
        return simpleEventBus.addHandlerToSource(type, source, handler);
    }

    @SuppressWarnings({"UnusedDeclaration"})
    private void fire(JavaScriptObject o) {
        final TransportAware transportAware = serializer.deserialize(new JSONArray(o));
        simpleEventBus.fireEvent((GwtEvent<?>) transportAware);
    }

    @Override public void fireEventFromSource(GwtEvent event, Object source) {
        simpleEventBus.fireEventFromSource(event, source);
    }
}
