package de.atns.common.dispatch.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DispatchAsync {
// ------------------------------ FIELDS ------------------------------

    DispatchServiceAsync service = GWT.create(DispatchService.class);

// -------------------------- OTHER METHODS --------------------------

    public <A extends Action<R>, R extends Result> void execute(A action, AsyncCallback<R> callback) {
        //noinspection unchecked
        service.execute((Action<Result>) action, (AsyncCallback<Result>) callback);
    }
}
