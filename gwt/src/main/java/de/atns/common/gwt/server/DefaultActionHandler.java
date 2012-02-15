package de.atns.common.gwt.server;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.Result;

/**
 * @author tbaum
 * @since 12.02.2010
 */
public abstract class DefaultActionHandler<A extends Action<R>, R extends Result> implements ActionHandler<A, R> {
    private final Class<A> clazz;

    protected DefaultActionHandler(final Class<A> clazz) {
        this.clazz = clazz;
    }

    @Override public final Class<A> getActionType() {
        return clazz;
    }

    @Override
    public R execute(final A action, final ExecutionContext context) throws ActionException {
        return executeInternal(action);
    }

    @Override
    public final void rollback(final A action, final R result, final ExecutionContext context) {
    }

    public abstract R executeInternal(final A action) throws ActionException;
}
