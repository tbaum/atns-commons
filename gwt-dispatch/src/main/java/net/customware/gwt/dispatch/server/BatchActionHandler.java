package net.customware.gwt.dispatch.server;

import net.customware.gwt.dispatch.shared.*;
import net.customware.gwt.dispatch.shared.BatchAction.OnException;

import java.util.List;

/**
 * This handles {@link BatchAction} requests, which are a set of multiple
 * actions that need to all be executed successfully in sequence for the whole
 * action to succeed.
 *
 * @author David Peterson
 */
public class BatchActionHandler extends AbstractActionHandler<BatchAction, BatchResult> {

    public BatchActionHandler() {
        super(BatchAction.class);
    }

    public BatchResult execute(BatchAction action, ExecutionContext context) throws DispatchException {
        OnException onException = action.getOnException();
        List<Result> results = new java.util.ArrayList<Result>();
        List<DispatchException> exceptions = new java.util.ArrayList<DispatchException>();
        for (Action<?> a : action.getActions()) {
            Result result = null;
            try {
                result = context.execute(a);
            } catch (Exception e) {
                DispatchException de = null;
                if (e instanceof DispatchException) {
                    de = (DispatchException) e;
                } else {
                    de = new ServiceException(e);
                }

                if (onException == OnException.ROLLBACK) {
                    throw de;
                } else {
                    exceptions.set(results.size(), de);
                }
            }
            results.add(result);
        }

        return new BatchResult(results, exceptions);
    }

    public void rollback(BatchAction action, BatchResult result, ExecutionContext context)
            throws ActionException {
        // No action necessary - the sub actions should automatically rollback
    }

}
