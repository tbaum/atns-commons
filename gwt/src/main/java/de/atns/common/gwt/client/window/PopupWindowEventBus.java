package de.atns.common.gwt.client.window;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.*;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author tbaum
 * @since 03.02.11
 */

@Singleton public class PopupWindowEventBus extends EventBus {
// ------------------------------ FIELDS ------------------------------

    private final EventBus eventBus = new SimpleEventBus();
    private final EventSerializer serializer;

// -------------------------- STATIC METHODS --------------------------

    public static native boolean isRunningInPopup() /*-{
        return $wnd.opener != null && $wnd.opener._event_from_popup != null;
    }-*/;

    public static native JavaScriptObject setWindowName(String name)/*-{
        $wnd.location.href = "#" + name
        $wnd.name = name + "_" + $wnd._myid
    }-*/;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public PopupWindowEventBus(EventSerializer serializer) {
        this.serializer = serializer;
        _registerEventBus(this);

        Window.addCloseHandler(new CloseHandler<Window>() {
            @Override public void onClose(CloseEvent<Window> windowCloseEvent) {
                windowClosed();
            }
        });
    }

    private native void _registerEventBus(PopupWindowEventBus windowEventbus) /*-{
        $wnd._event_from_master = function(event) {
            windowEventbus.@de.atns.common.gwt.client.window.PopupWindowEventBus::fire(Lcom/google/gwt/core/client/JavaScriptObject;)(event);
        };

        $wnd._open_popup = function(url, name, para) {
            $wnd.opener._open_Window(url, name, para);
        };
    }-*/;

    private native void windowClosed() /*-{
        $wnd.opener._popup_closed($wnd._myid);
    }-*/;

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface HasHandlers ---------------------

    @Override public void fireEvent(GwtEvent<?> event) {
        eventBus.fireEvent(event);

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
        return eventBus.addHandler(type, handler);
    }

    @Override
    public <H extends EventHandler> HandlerRegistration addHandlerToSource(GwtEvent.Type<H> type, Object source,
                                                                           H handler) {
        return eventBus.addHandlerToSource(type, source, handler);
    }

    @SuppressWarnings({"UnusedDeclaration"})
    private void fire(JavaScriptObject o) {
        final TransportAware transportAware = serializer.deserialize(new JSONArray(o));
        eventBus.fireEvent((GwtEvent<?>) transportAware);
    }

    @Override public void fireEventFromSource(GwtEvent event, Object source) {
        eventBus.fireEventFromSource(event, source);
    }

    public native void openWindow(String url, String name, String para) /*-{
        $wnd.opener._open_popup(url, name, para);
    }-*/;
}
