package de.atns.common.crud.client.event;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.inject.Inject;
import com.google.inject.Provider;
import de.atns.common.gwt.client.WidgetDisplay;
import de.atns.common.security.client.Callback;


/**
 * @author mwolter
 * @since 22.09.2010 18:17:01
 */
public class LoadDetailEvent<E extends IsSerializable> extends GwtEvent<LoadDetailEventHandler<E>> {
// ------------------------------ FIELDS ------------------------------

    @Inject private static Provider<EventBus> eventBus;
    private final E result;
    private final Type<LoadDetailEventHandler<E>> type;
    private final Object source;

// -------------------------- STATIC METHODS --------------------------

    public static <T extends IsSerializable> Callback<T> eventCallback(final WidgetDisplay display,
                                                                       final Type<LoadDetailEventHandler<T>> type,
                                                                       final Object source) {
        return new Callback<T>(display) {
            @Override public void callback(final T result) {
                fireEvent(result, display, type, source);
            }
        };
    }

    public static <T extends IsSerializable> void fireEvent(final T result, final WidgetDisplay display,
                                                            final Type<LoadDetailEventHandler<T>> type,
                                                            final Object source) {
        eventBus.get().fireEvent(new LoadDetailEvent<T>(result, type, source));
        display.stopProcessing();
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public LoadDetailEvent(final E result, final Type<LoadDetailEventHandler<E>> type, final Object source) {
        this.result = result;
        this.type = type;
        this.source = source;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override protected void dispatch(final LoadDetailEventHandler<E> handler) {
        handler.onLoad(result, source);
    }

    @Override public Type<LoadDetailEventHandler<E>> getAssociatedType() {
        return type;
    }
}
