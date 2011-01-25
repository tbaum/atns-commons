package de.atns.common.gwt.client.window;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.*;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.Window;

import java.util.HashMap;

/**
 * @author: mwolter
 * @since: 24.01.11 12:51
 */
public class MasterWindowEventBus extends EventBus {
// ------------------------------ FIELDS ------------------------------

    private final SimpleEventBus simpleEventBus = new SimpleEventBus();
    private final EventSerializer serializer;

    private HashMap<String, JavaScriptObject> windows = new HashMap<String, JavaScriptObject>();

    private final long myid = System.currentTimeMillis();

// --------------------------- CONSTRUCTORS ---------------------------

    public MasterWindowEventBus(final EventSerializer serializer) {
        this.serializer = serializer;
        _registerEventBus(this);


        Window.addCloseHandler(new CloseHandler<Window>() {
            @Override public void onClose(CloseEvent<Window> windowCloseEvent) {
                for (JavaScriptObject window : windows.values()) {
                    closeWindow(window);
                }
            }
        });
    }

    private native void _registerEventBus(MasterWindowEventBus windowEventbus) /*-{
        $wnd._event_from_popup = function(source, event) {
            windowEventbus.@de.atns.common.gwt.client.window.MasterWindowEventBus::fire(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)(source,
                    event);
        };
        $wnd._popup_closed = function(source) {
            windowEventbus.@de.atns.common.gwt.client.window.MasterWindowEventBus::removeWindow(Ljava/lang/String;)(source);
        };
    }-*/;

    private native void closeWindow(JavaScriptObject window) /*-{
        window.close();
    }-*/;

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface HasHandlers ---------------------

    @Override public void fireEvent(GwtEvent event) {
        simpleEventBus.fireEvent(event);

        if (event instanceof TransportAware) {
            TransportAware transportAware = (TransportAware) event;
            for (JavaScriptObject window : windows.values()) {
                _event_fireEvent(JavaScriptObject.createObject(), window, serializer.serialize(transportAware));
            }
        }
    }

// -------------------------- OTHER METHODS --------------------------

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
    private void fire(JavaScriptObject src, JavaScriptObject o) {
        final TransportAware transportAware = serializer.deserialize(new JSONArray(o));
        simpleEventBus.fireEvent((GwtEvent<?>) transportAware);

        for (JavaScriptObject window : windows.values()) {
            _event_fireEvent(src, window, o);
        }
    }

    private native void _event_fireEvent(JavaScriptObject source, JavaScriptObject popupWindow,
                                         JavaScriptObject event) /*-{
        if (source != popupWindow) {
            popupWindow._event_from_master(event);
        }
    }-*/;

    @Override public void fireEventFromSource(GwtEvent event, Object source) {
        simpleEventBus.fireEventFromSource(event, source);
        //TODO?
        //        for (JavaScriptObject window : windows) {
        //            _event_fireEventFromSource(window, event, source);
        //        }
    }

    private void removeWindow(String wnd) {
        windows.remove(wnd);
    }

    public void openWindow(String url, String name, String para) {
        String windowId = name + "_" + myid;
        JavaScriptObject wnd = open(this, url, windowId, para);
        windows.put(windowId, wnd);
    }

    private native JavaScriptObject open(MasterWindowEventBus eventbus, String url, String name, String args) /*-{
        var newWindow = $wnd.open(url, name, args)
        newWindow._myid = name;
        return newWindow;
    }-*/;
}
