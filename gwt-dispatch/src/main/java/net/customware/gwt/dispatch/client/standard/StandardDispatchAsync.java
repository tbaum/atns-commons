package net.customware.gwt.dispatch.client.standard;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import net.customware.gwt.dispatch.client.AbstractDispatchAsync;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.ExceptionHandler;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

/**
 * This class is the default implementation of {@link DispatchAsync}, which is
 * essentially the client-side access to the {@link Dispatch} class on the
 * server-side.
 *
 * @author David Peterson
 */
public class StandardDispatchAsync extends AbstractDispatchAsync {
// ------------------------------ FIELDS ------------------------------

    private static final StandardDispatchServiceAsync realService = GWT.create(StandardDispatchService.class);

// --------------------------- CONSTRUCTORS ---------------------------

    public StandardDispatchAsync(ExceptionHandler exceptionHandler) {
        super(exceptionHandler);
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface DispatchAsync ---------------------

    public <R extends Result> void execute(final Action<R> action, final AsyncCallback<R> callback) {
        realService.execute(action, new AsyncCallback<R>() {
            public void onFailure(Throwable caught) {
                StandardDispatchAsync.this.onFailure(action, caught, callback);
            }

            @SuppressWarnings({"unchecked"})
            public void onSuccess(R result) {
                StandardDispatchAsync.this.onSuccess(action, result, callback);
            }
        });
    }
}
