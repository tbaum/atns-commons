package de.atns.common.crud.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import de.atns.common.gwt.client.WidgetDisplay;
import de.atns.common.security.client.Callback;

/**
 * @author mwolter
 * @since 22.09.2010 18:17:01
 */
public class LoadCreateEvent<E extends IsSerializable> extends GwtEvent<LoadCreateEventHandler<E>> {

    @Inject private static Provider<EventBus> eventBus;
    private final E result;
    private final Type<LoadCreateEventHandler<E>> type;
    private final Object source;

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
        eventBus.get().fireEvent(new LoadCreateEvent<T>(result, type, source));
        display.stopProcessing();
    }

    public LoadCreateEvent(final E result, final Type<LoadCreateEventHandler<E>> type, final Object source) {
        this.result = result;
        this.type = type;
        this.source = source;
    }

    @Override protected void dispatch(final LoadCreateEventHandler<E> handler) {
        handler.onCreate(result, source);
    }

    @Override public Type<LoadCreateEventHandler<E>> getAssociatedType() {
        return type;
    }
}
