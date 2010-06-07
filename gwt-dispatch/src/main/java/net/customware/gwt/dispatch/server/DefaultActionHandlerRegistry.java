package net.customware.gwt.dispatch.server;

import com.google.inject.Singleton;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import java.util.Map;

@Singleton
public class DefaultActionHandlerRegistry implements InstanceActionHandlerRegistry {

    private final Map<Class<? extends Action<?>>, ActionHandler<?, ?>> handlers;

    public DefaultActionHandlerRegistry() {
        handlers = new java.util.HashMap<Class<? extends Action<?>>, ActionHandler<?, ?>>(100);
    }

    public void addHandler(ActionHandler<?, ?> handler) {
        handlers.put(handler.getActionType(), handler);
    }

    public boolean removeHandler(ActionHandler<?, ?> handler) {
        return handlers.remove(handler.getActionType()) != null;
    }

    public <A extends Action<R>, R extends Result> ActionHandler<A, R> findHandler(A action) {
        return (net.customware.gwt.dispatch.server.ActionHandler<A, R>) handlers.get(action.getClass());
    }

    public void clearHandlers() {
        handlers.clear();
    }

}
