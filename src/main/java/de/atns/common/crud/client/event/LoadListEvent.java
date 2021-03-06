package de.atns.common.crud.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import de.atns.common.gwt.client.WidgetDisplay;
import de.atns.common.gwt.client.model.ListPresentation;
import de.atns.common.security.client.Callback;

/**
 * @author mwolter
 * @since 22.09.2010 18:17:01
 */
public class LoadListEvent<E extends IsSerializable> extends GwtEvent<LoadListEventHandler<E>> {

    @Inject private static Provider<EventBus> eventBus;
    private final ListPresentation<E> result;
    private final Type<LoadListEventHandler<E>> type;
    private final Object source;

    public static <T extends IsSerializable> Callback<ListPresentation<T>> eventCallback(
            final WidgetDisplay display, final Type<LoadListEventHandler<T>> type, final Object source) {
        return new Callback<ListPresentation<T>>(display) {
            @Override public void callback(final ListPresentation<T> result11) {
                fireEvent(result11, type, source);
            }
        };
    }

    public static <T extends IsSerializable> void fireEvent(final ListPresentation<T> result,
                                                            final Type<LoadListEventHandler<T>> type,
                                                            final Object source) {
        eventBus.get().fireEvent(new LoadListEvent<T>(result, type, source));
    }

    private LoadListEvent(final ListPresentation<E> result, final Type<LoadListEventHandler<E>> type,
                          final Object source) {
        this.result = result;
        this.type = type;
        this.source = source;
    }

    @Override protected void dispatch(final LoadListEventHandler<E> loadListEventHandler) {
        loadListEventHandler.onLoad(result, source);
    }

    @Override public Type<LoadListEventHandler<E>> getAssociatedType() {
        return type;
    }
}
