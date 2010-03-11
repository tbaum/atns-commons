package net.customware.gwt.dispatch.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

/**
 * An abstract base class that provides methods that can be called to handle success or failure
 * results from the remote service. These should be called by the implementation of
 * {@link #execute(net.customware.gwt.dispatch.shared.Action, com.google.gwt.user.client.rpc.AsyncCallback)}.
 *
 * @author David Peterson
 */
public abstract class AbstractDispatchAsync implements DispatchAsync {

    private final ExceptionHandler exceptionHandler;

    public AbstractDispatchAsync(ExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    protected <R extends Result> void onFailure(Action<R> action, Throwable caught, final AsyncCallback<R> callback) {
        if (exceptionHandler != null && exceptionHandler.onFailure(caught) == ExceptionHandler.Status.STOP) {
            return;
        }

        callback.onFailure(caught);
    }

    protected <R extends Result> void onSuccess(Action<R> action, R result, final AsyncCallback<R> callback) {
        callback.onSuccess(result);
    }

}
