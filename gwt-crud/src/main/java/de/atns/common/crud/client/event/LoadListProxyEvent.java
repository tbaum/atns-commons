package de.atns.common.crud.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import de.atns.common.gwt.client.WidgetDisplay;
import de.atns.common.security.client.Callback;

import java.util.List;

/**
 * @author mwolter
 * @since 22.09.2010 18:17:01
 */
public class LoadListProxyEvent<E extends EntityProxy> extends GwtEvent<LoadListProxyEventHandler<E>> {

    @Inject private static Provider<EventBus> eventBus;
    private final List<E> result;
    private final Type<LoadListProxyEventHandler<E>> type;
    private final Object source;

    public static <T extends EntityProxy> Callback<List<T>> eventCallback(
            final WidgetDisplay display, final Type<LoadListProxyEventHandler<T>> type, final Object source) {
        return new Callback<List<T>>(display) {
            @Override public void callback(final List<T> result11) {
                fireEvent(result11, type, source);
            }
        };
    }

    public static <T extends EntityProxy> void fireEvent(final List<T> result,
                                                         final Type<LoadListProxyEventHandler<T>> type,
                                                         final Object source) {
        eventBus.get().fireEvent(new LoadListProxyEvent<T>(result, type, source));
    }

    private LoadListProxyEvent(final List<E> result, final Type<LoadListProxyEventHandler<E>> type,
                               final Object source) {
        this.result = result;
        this.type = type;
        this.source = source;
    }

    @Override protected void dispatch(final LoadListProxyEventHandler<E> loadListEventHandler) {
        loadListEventHandler.onLoad(result, source);
    }

    @Override public Type<LoadListProxyEventHandler<E>> getAssociatedType() {
        return type;
    }
}
