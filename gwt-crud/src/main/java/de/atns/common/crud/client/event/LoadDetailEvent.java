package de.atns.common.crud.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.IsSerializable;
import de.atns.common.gwt.client.Callback;
import de.atns.common.gwt.client.ErrorWidgetDisplay;
import de.atns.common.security.client.DefaultCallback;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;

import static de.atns.common.security.client.DefaultCallback.callback;


/**
 * @author mwolter
 * @since 22.09.2010 18:17:01
 */
public class LoadDetailEvent<E extends IsSerializable> extends GwtEvent<LoadDetailEventHandler<E>> {
// ------------------------------ FIELDS ------------------------------

    private final E result;
    private Type<LoadDetailEventHandler<E>> type;
    private final Object source;

// -------------------------- STATIC METHODS --------------------------

    public static <T extends IsSerializable> DefaultCallback<T> eventCallback(
            final DispatchAsync dispatcher,
            final EventBus eventBus,
            final ErrorWidgetDisplay display,
            final Type<LoadDetailEventHandler<T>> type, final Object source) {
        return callback(dispatcher, eventBus, display, new Callback<T>() {
            @Override public void callback(final T result11) {
                eventBus.fireEvent(new LoadDetailEvent<T>(result11, type, source));
            }
        });
    }

    public static <T extends IsSerializable> void fireEvent(final T result, final EventBus eventBus,
                                                            final ErrorWidgetDisplay display,
                                                            final Type<LoadDetailEventHandler<T>> type,
                                                            final Object source) {
        eventBus.fireEvent(new LoadDetailEvent<T>(result, type, source));
        display.stopProcessing();
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private LoadDetailEvent(final E result, final Type<LoadDetailEventHandler<E>> type,
                            final Object source) {
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
