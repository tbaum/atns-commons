package de.atns.common.dispatch.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import de.atns.common.dispatch.client.*;

import java.util.Map;

@Singleton
public class GuiceStandardDispatchServlet extends RemoteServiceServlet implements DispatchService {
// ------------------------------ FIELDS ------------------------------

    private final Map<Class<? extends Action>, Provider<ActionHandler>> registry;

// --------------------------- CONSTRUCTORS ---------------------------

    @Inject public GuiceStandardDispatchServlet(final Map<Class<? extends Action>, Provider<ActionHandler>> registry) {
        this.registry = registry;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface DispatchServiceAsync ---------------------

    public Result execute(Action<Result> action) throws ActionException, ServiceException {
        try {
/*        DefaultExecutionContext ctx = new DefaultExecutionContext(this);
        try {
            return doExecute(action, ctx);
        } catch (ActionException e) {
            ctx.rollback();
            throw e;
        }
  */

            return findHandler(action).execute(action);
        } catch (RuntimeException e) {
            log("Exception while executing " + action.getClass().getName() + ": " + e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
    }

// -------------------------- OTHER METHODS --------------------------

    private ActionHandler<Action<Result>, Result> findHandler(Action action)
            throws ServiceException {
        Provider<ActionHandler> handler = registry.get(action.getClass());
        if (handler == null) {
            throw new ServiceException("No handler is registered for " + action.getClass().getName());
        }

        return handler.get();
    }
}
