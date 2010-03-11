package net.customware.gwt.dispatch.client.secure;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import net.customware.gwt.dispatch.client.AbstractDispatchAsync;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.ExceptionHandler;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;
import net.customware.gwt.dispatch.shared.secure.InvalidSessionException;

/**
 * This class is the default implementation of {@link DispatchAsync}, which is
 * essentially the client-side access to the {@link Dispatch} class on the
 * server-side.
 *
 * @author David Peterson
 */
public class SecureDispatchAsync extends AbstractDispatchAsync {
// ------------------------------ FIELDS ------------------------------

    private static final SecureDispatchServiceAsync realService = GWT.create(SecureDispatchService.class);

    private final SecureSessionAccessor secureSessionAccessor;

// --------------------------- CONSTRUCTORS ---------------------------

    public SecureDispatchAsync(ExceptionHandler exceptionHandler, SecureSessionAccessor secureSessionAccessor) {
        super(exceptionHandler);
        this.secureSessionAccessor = secureSessionAccessor;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface DispatchAsync ---------------------

    public <R extends Result> void execute(final Action<R> action, final AsyncCallback<R> callback) {
        String sessionId = secureSessionAccessor.getSessionId();

        realService.execute(sessionId, action, new AsyncCallback<R>() {
            public void onFailure(Throwable caught) {
                SecureDispatchAsync.this.onFailure(action, caught, callback);
            }

            @SuppressWarnings({"unchecked"})
            public void onSuccess(R result) {
                // Note: This cast is a dodgy hack to get around a GWT 1.6 async compiler issue
                SecureDispatchAsync.this.onSuccess(action, result, callback);
            }
        });
    }

// -------------------------- OTHER METHODS --------------------------

    protected <R extends Result> void onFailure(Action<R> action, Throwable caught, final AsyncCallback<R> callback) {
        if (caught instanceof InvalidSessionException) {
            secureSessionAccessor.clearSessionId();
        }

        super.onFailure(action, caught, callback);
    }
}
