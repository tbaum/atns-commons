package de.atns.common.crud.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.IsSerializable;
import de.atns.common.gwt.client.WidgetDisplay;
import de.atns.common.security.client.Callback;
import de.atns.common.security.client.SharedServicesHolder;


/**
 * @author mwolter
 * @since 22.09.2010 18:17:01
 */
public class LoadCreateEvent<E extends IsSerializable> extends GwtEvent<LoadCreateEventHandler<E>> {
// ------------------------------ FIELDS ------------------------------

    private final E result;
    private Type<LoadCreateEventHandler<E>> type;
    private final Object source;

// -------------------------- STATIC METHODS --------------------------

    public static <T extends IsSerializable> Callback<T> eventCallback(final WidgetDisplay display,
                                                                       final Type<LoadCreateEventHandler<T>> type,
                                                                       final Object source) {
        return new Callback<T>(display) {
            @Override public void callback(final T result) {
                fireEvent(result, display, type, source);
            }
        };
    }

    public static <T extends IsSerializable> void fireEvent(final T result, final WidgetDisplay display,
                                                            final Type<LoadCreateEventHandler<T>> type,
                                                            final Object source) {
        SharedServicesHolder.shared().getEventBus().fireEvent(new LoadCreateEvent<T>(result, type, source));
        display.stopProcessing();
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public LoadCreateEvent(final E result, final Type<LoadCreateEventHandler<E>> type,
                           final Object source) {
        this.result = result;
        this.type = type;
        this.source = source;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override protected void dispatch(final LoadCreateEventHandler<E> handler) {
        handler.onCreate(result, source);
    }

    @Override public Type<LoadCreateEventHandler<E>> getAssociatedType() {
        return type;
    }
}
