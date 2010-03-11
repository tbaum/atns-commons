package net.customware.gwt.dispatch.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

/**
 * This is an asynchronous equivalent of the {@link Dispatch} interface on the
 * server side. The reason it exists is because GWT currently can't correctly
 * handle having generic method templates in method signatures (eg.
 * <code>&lt;A&gt; A
 * create( Class<A> type )</code>)
 *
 * @author David Peterson
 */
public interface DispatchAsync {
    <R extends Result> void execute(Action<R> action, AsyncCallback<R> callback);
}
