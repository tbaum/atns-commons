package de.atns.common.gwt.client.window;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.event.shared.SimpleEventBus;

import static de.atns.common.gwt.client.window.MasterWindowEventBus.currentLocation;

/**
 * @author tbaum
 * @since 03.02.11
 */

@Singleton public class PopupWindowEventBus extends EventBus implements WindowEventBus {

    private final EventBus eventBus = new SimpleEventBus();
    private final EventSerializer serializer;

    public static native boolean isRunningInPopup() /*-{
        return $wnd.opener != null && $wnd.opener._event_from_popup != null;
    }-*/;

    public static void setWindowName(String name) {
        setWindowName_(currentLocation() + "#" + name, name.replaceAll(":", "_"));
    }

    private static native void setWindowName_(String name, String name2)/*-{
        $wnd.location.href = name
        $wnd.name = name2 + "_" + $wnd._myid
    }-*/;

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
        $wnd._event_from_master = function (event) {
            windowEventbus.@de.atns.common.gwt.client.window.PopupWindowEventBus::fire(Lcom/google/gwt/core/client/JavaScriptObject;)(event);
        };

        $wnd._open_popup = function (url, name, para) {
            $wnd.opener._open_Window(url, name, para);
        };
    }-*/;

    private native void windowClosed() /*-{
        $wnd.opener._popup_closed($wnd._myid);
    }-*/;


    @Override public native void openWindow(String url, String name, String para) /*-{
        $wnd.opener._open_popup(url, name, para);
    }-*/;

    @Override public <H> HandlerRegistration addHandler(Event.Type<H> type, H handler) {
        // TODO unregister
        return eventBus.addHandler(type, handler);
    }

    @Override public <H> HandlerRegistration addHandlerToSource(Event.Type<H> type, Object source, H handler) {
        return eventBus.addHandlerToSource(type, source, handler);
    }

    @SuppressWarnings({"UnusedDeclaration"})
    private void fire(JavaScriptObject o) {
        final TransportAware transportAware = serializer.deserialize(new JSONArray(o));
        eventBus.fireEvent((Event<?>) transportAware);
    }

    @Override public void fireEvent(Event<?> event) {
        eventBus.fireEvent(event);

        if (event instanceof TransportAware) {
            TransportAware transportAware = (TransportAware) event;
            _event_fireEvent(serializer.serialize(transportAware));
        }
    }

    private native void _event_fireEvent(JavaScriptObject event) /*-{
        $wnd.opener._event_from_popup($wnd, event);
    }-*/;

    @Override public void fireEventFromSource(Event event, Object source) {
        eventBus.fireEventFromSource(event, source);
    }
}
