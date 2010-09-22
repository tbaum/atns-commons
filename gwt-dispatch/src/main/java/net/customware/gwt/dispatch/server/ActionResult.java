package net.customware.gwt.dispatch.server;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

/**
 * This contains both the original {@link Action} and the {@link Result} of that
 * action.
 *
 * @param <A> The action type.
 * @param <R> The result type.
 * @author David Peterson
 */
public class ActionResult<A extends Action<R>, R extends Result> {
    private final A action;

    private final R result;

    public ActionResult(A action, R result) {
        this.action = action;
        this.result = result;
    }

    public A getAction() {
        return action;
    }

    public R getResult() {
        return result;
    }
}
