package net.customware.gwt.dispatch.server.guice;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.customware.gwt.dispatch.server.AbstractDispatch;
import net.customware.gwt.dispatch.server.ActionHandlerRegistry;

@Singleton
public class GuiceDispatch extends AbstractDispatch {
// ------------------------------ FIELDS ------------------------------

    private final ActionHandlerRegistry handlerRegistry;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject
    public GuiceDispatch(ActionHandlerRegistry handlerRegistry) {
        this.handlerRegistry = handlerRegistry;
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    @Override
    protected ActionHandlerRegistry getHandlerRegistry() {
        return handlerRegistry;
    }
}
