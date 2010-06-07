package net.customware.gwt.dispatch.server.guice;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.customware.gwt.dispatch.server.ActionHandlerRegistry;
import net.customware.gwt.dispatch.server.SimpleDispatch;

/**
 * A simple extension of {@link SimpleDispatch} with Guice annotations.
 *
 * @author David Peterson
 */
@Singleton
public class GuiceDispatch extends SimpleDispatch {

    @Inject
    public GuiceDispatch(ActionHandlerRegistry handlerRegistry) {
        super(handlerRegistry);
    }
}
