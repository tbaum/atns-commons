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
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface ActionHandler ---------------------

    @Override    //TODO  @Transactional
    public abstract R execute(final A action, final ExecutionContext executionContext) throws ActionException;

    @Override
    public void rollback(final A action, final R result, final ExecutionContext context) {
    }
}