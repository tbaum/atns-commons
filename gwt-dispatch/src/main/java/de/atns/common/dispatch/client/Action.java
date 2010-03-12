package de.atns.common.dispatch.client;

import com.google.gwt.user.client.rpc.IsSerializable;
import de.atns.common.dispatch.client.Result;

/**
 * An action represents a command sent to the {@link Dispatch}. It has a
 * specific result type which is returned if the action is successful.
 *
 * @author David Peterson
 * @param <R>
 * The {@link Result} type.
 */
public interface Action<R extends Result> extends IsSerializable {
}
