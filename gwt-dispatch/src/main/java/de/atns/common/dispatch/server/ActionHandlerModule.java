package de.atns.common.dispatch.server;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;
import de.atns.common.dispatch.client.Action;
import de.atns.common.dispatch.client.Result;

import static com.google.inject.multibindings.MapBinder.newMapBinder;

public abstract class ActionHandlerModule extends AbstractModule {
// -------------------------- OTHER METHODS --------------------------

    protected <A extends Action<R>, R extends Result> void bindHandler(Class<A> actionClass,
                                                                       Class<? extends ActionHandler<A, R>> handlerClass) {
        final MapBinder<Class<? extends Action>, ActionHandler> actionProvider = newMapBinder(binder(),
                new TypeLiteral<Class<? extends Action>>() {
                },
                new TypeLiteral<ActionHandler>() {
                });

        actionProvider.addBinding(actionClass).to(handlerClass);
    }

    @Override protected final void configure() {
        configureHandlers();
    }

    protected abstract void configureHandlers();
}
