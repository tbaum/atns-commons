package de.atns.common.gwt.client.async;

import com.google.gwt.user.client.rpc.AsyncCallback;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import static java.util.Arrays.asList;

/**
 * @author tbaum
 * @since 19.11.11
 */
public class QueingCallback<R extends Result> implements AsyncCallback<R>, Collection<AsyncCallback<R>> {

    private final Collection<AsyncCallback<R>> callbacks = new LinkedList<AsyncCallback<R>>();

    static <R extends Result> QueingCallback<R> queingCallback(SingleRunDispatcher dispatcher,
                                                               AsyncCallback<R> callback, Class<? extends Action> clazz) {
        //noinspection unchecked
        return new QueingCallback<R>(new FinishedCallback<R>(dispatcher, clazz), callback);
    }

    public QueingCallback(AsyncCallback<R>... callbacks) {
        addAll(asList(callbacks));
    }

    @Override public boolean addAll(Collection<? extends AsyncCallback<R>> asyncCallbacks) {
        return callbacks.addAll(asyncCallbacks);
    }

    @Override public boolean equals(Object o) {
        return callbacks.equals(o);
    }

    @Override public int hashCode() {
        return callbacks.hashCode();
    }

    @Override public void onFailure(Throwable caught) {
        for (AsyncCallback<R> callback : callbacks) {
            callback.onFailure(caught);
        }
    }

    @Override public void onSuccess(R result) {
        for (AsyncCallback<R> callback : callbacks) {
            callback.onSuccess(result);
        }
    }

    @Override public int size() {
        return callbacks.size();
    }

    @Override public boolean isEmpty() {
        return callbacks.isEmpty();
    }

    @Override public boolean contains(Object o) {
        return callbacks.contains(o);
    }

    @Override public Object[] toArray() {
        return callbacks.toArray();
    }

    @Override public <T> T[] toArray(T[] ts) {
        //noinspection SuspiciousToArrayCall
        return callbacks.toArray(ts);
    }

    @Override public boolean add(AsyncCallback<R> callback) {
        return callbacks.add(callback);
    }

    @Override public boolean remove(Object o) {
        return callbacks.remove(o);
    }

    @Override public boolean containsAll(Collection<?> objects) {
        return callbacks.containsAll(objects);
    }

    @Override public boolean removeAll(Collection<?> objects) {
        return callbacks.removeAll(objects);
    }

    @Override public boolean retainAll(Collection<?> objects) {
        return callbacks.retainAll(objects);
    }

    @Override public void clear() {
        callbacks.clear();
    }

    @Override public Iterator<AsyncCallback<R>> iterator() {
        return callbacks.iterator();
    }
}
