package de.atns.common.gwt.server;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.Result;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author tbaum
 * @since 12.02.2010
 */
public abstract class DefaultActionHandler<A extends Action<R>, R extends Result> implements ActionHandler<A, R> {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(DefaultActionHandler.class);
    private final Class<A> clazz;

// --------------------------- CONSTRUCTORS ---------------------------

    protected DefaultActionHandler(final Class<A> clazz) {
        this.clazz = clazz;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ActionHandler ---------------------

    @Override public final Class<A> getActionType() {
        return clazz;
    }

    @Override
    public R execute(final A action, final ExecutionContext context) throws ActionException {
        final R result = executeInternal(action);
        if (LOG.isDebugEnabled()) {
            LOG.debug(result);
        }
        return result;
    }

    @Override
    public final void rollback(final A action, final R result, final ExecutionContext context) {
    }

// -------------------------- OTHER METHODS --------------------------

    public abstract R executeInternal(final A action) throws ActionException;
}
