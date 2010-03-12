package de.atns.common.dispatch.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DispatchServiceAsync {
// -------------------------- OTHER METHODS --------------------------

    void execute(Action<Result> action, AsyncCallback<Result> callback);
}