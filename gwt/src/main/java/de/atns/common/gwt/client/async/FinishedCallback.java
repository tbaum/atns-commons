package de.atns.common.gwt.client.async;

import com.google.gwt.user.client.rpc.AsyncCallback;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

/**
 * @author tbaum
 * @since 22.11.11
 */
class FinishedCallback<R extends Result> implements AsyncCallback<R> {

    private final Class<? extends Action> clazz;
    private final SingleRunDispatcher dispatcher;

    public FinishedCallback(SingleRunDispatcher dispatcher, Class<? extends Action> clazz) {
        this.clazz = clazz;
        this.dispatcher = dispatcher;
    }

    @Override public void onFailure(Throwable caught) {
        dispatcher.requestFinished(clazz);
    }

    @Override public void onSuccess(R result) {
        dispatcher.requestFinished(clazz);
    }
}
