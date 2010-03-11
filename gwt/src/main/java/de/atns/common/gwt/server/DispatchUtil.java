package de.atns.common.gwt.server;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.DefaultActionHandlerRegistry;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.server.guice.GuiceDispatch;

/**
 * @author mwolter
 * @since 02.03.2010 17:50:55
 */
public final class DispatchUtil {

    private static final DefaultActionHandlerRegistry REGISTRY
            = new DefaultActionHandlerRegistry();

    private static final Dispatch DISPATCH = new GuiceDispatch(REGISTRY);

    public static void registerHandler(ActionHandler<?, ?> handler) {
        REGISTRY.addHandler(handler);
    }

    public static Dispatch getDispatch() {
        return DISPATCH;
    }
}