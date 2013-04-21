package de.atns.common.gwt.client.async;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author tbaum
 * @since 19.11.11
 */
public class Callback<T> implements AsyncCallback<T> {

    public static <T> Callback<T> doNothing() {
        return new Callback<T>();
    }

    @Override public void onFailure(Throwable caught) {
        GWT.log("callback-failed: failure ", caught);
    }

    @Override public void onSuccess(T result) {
        GWT.log("callback-success: " + result);
    }
}
