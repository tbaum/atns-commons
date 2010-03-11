package net.customware.gwt.dispatch.server;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.Result;
import net.customware.gwt.dispatch.shared.UnsupportedActionException;

import java.util.List;

public abstract class AbstractDispatch implements Dispatch {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Dispatch ---------------------

    public <R extends Result> R execute(Action<R> action) throws ActionException {
        DefaultExecutionContext ctx = new DefaultExecutionContext(this);
        try {
            return doExecute(action, ctx);
        } catch (ActionException e) {
            ctx.rollback();
            throw e;
        }
    }

// -------------------------- OTHER METHODS --------------------------

    private <A extends Action<R>, R extends Result> R doExecute(A action, ExecutionContext ctx)
            throws ActionException {
        ActionHandler<A, R> handler = findHandler(action);
        return handler.execute(action, ctx);
    }

    private <A extends Action<R>, R extends Result> void doRollback(A action, R result, ExecutionContext ctx)
            throws ActionException {
        ActionHandler<A, R> handler = findHandler(action);
        handler.rollback(action, result, ctx);
    }

    private <A extends Action<R>, R extends Result> ActionHandler<A, R> findHandler(A action)
            throws UnsupportedActionException {
        ActionHandler<A, R> handler = getHandlerRegistry().findHandler(action);
        if (handler == null)
            throw new UnsupportedActionException(action);

        return handler;
    }

    protected abstract ActionHandlerRegistry getHandlerRegistry();

// -------------------------- INNER CLASSES --------------------------

    private static class DefaultExecutionContext implements ExecutionContext {
        private final AbstractDispatch dispatch;

        private final List<ActionResult<?, ?>> actionResults;

        private DefaultExecutionContext(AbstractDispatch dispatch) {
            this.dispatch = dispatch;
            this.actionResults = new java.util.ArrayList<ActionResult<?, ?>>();
        }

        public <R extends Result> R execute(Action<R> action) throws ActionException {
            return execute(action, true);
        }

        public <R extends Result> R execute(Action<R> action, boolean allowRollback)
                throws ActionException {
            R result = dispatch.doExecute(action, this);
            if (allowRollback)
                actionResults.add(new ActionResult<Action<R>, R>(action, result));
            return result;
        }

        /**
         * Rolls back all logged action/results.
         *
         * @throws ActionException  If there is an action exception while rolling back.
         * @throws ServiceException If there is a low level problem while rolling back.
         */
        private void rollback() throws ActionException {
            for (int i = actionResults.size() - 1; i >= 0; i--) {
                ActionResult<?, ?> actionResult = actionResults.get(i);
                rollback(actionResult);
            }
        }

        private <A extends Action<R>, R extends Result> void rollback(ActionResult<A, R> actionResult)
                throws ActionException {
            dispatch.doRollback(actionResult.getAction(), actionResult.getResult(), this);
        }
    }
}
