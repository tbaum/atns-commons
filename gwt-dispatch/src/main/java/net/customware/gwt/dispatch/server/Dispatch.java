package net.customware.gwt.dispatch.server;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.Result;

public interface Dispatch {

    /**
     * Executes the specified action and returns the appropriate result.
     *
     * @param <T>    The {@link Result} type.
     * @param action The {@link Action}.
     * @return The action's result.
     * @throws ActionException if the action execution failed.
     */
    <R extends Result> R execute(Action<R> action) throws ActionException;
}
