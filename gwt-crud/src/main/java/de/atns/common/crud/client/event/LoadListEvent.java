package de.atns.common.crud.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.IsSerializable;
import de.atns.common.gwt.client.ErrorWidgetDisplay;
import de.atns.common.gwt.client.ListPresenter;
import de.atns.common.gwt.client.model.ListPresentation;
import de.atns.common.gwt.client.Callback;
import de.atns.common.security.client.DefaultCallback;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.EventBus;

import static de.atns.common.security.client.DefaultCallback.callback;


/**
 * @author mwolter
 * @since 22.09.2010 18:17:01
 */
public class LoadListEvent<E extends IsSerializable> extends GwtEvent<LoadListEventHandler<E>> {
// ------------------------------ FIELDS ------------------------------

    private final ListPresentation<E> result;
    private Type<LoadListEventHandler<E>> type;
    private final Object source;

// -------------------------- STATIC METHODS --------------------------

    public static <T extends IsSerializable> DefaultCallback<ListPresentation<T>> eventCallback(
            final DispatchAsync dispatcher,
            final EventBus eventBus,
            final GwtEvent.Type<LoadListEventHandler<T>> type, ListPresenter source) {
        return eventCallback(dispatcher, eventBus, null, type, source);
    }

    public static <T extends IsSerializable> DefaultCallback<ListPresentation<T>> eventCallback(
            final DispatchAsync dispatcher,
            final EventBus eventBus,
            final ErrorWidgetDisplay display,
            final GwtEvent.Type<LoadListEventHandler<T>> type, final Object source) {
        return callback(dispatcher, eventBus, display, new Callback<ListPresentation<T>>() {
            @Override public void callback(final ListPresentation<T> result11) {
                eventBus.fireEvent(new LoadListEvent<T>(result11, type, source));
            }
        });
    }

// --------------------------- CONSTRUCTORS ---------------------------

    private LoadListEvent(final ListPresentation<E> result, final Type<LoadListEventHandler<E>> type,
                          final Object source) {
        this.result = result;
        this.type = type;
        this.source = source;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override protected void dispatch(final LoadListEventHandler<E> loadListEventHandler) {
        loadListEventHandler.onLoad(result, source);
    }

    @Override public Type<LoadListEventHandler<E>> getAssociatedType() {
        return type;
    }
}
