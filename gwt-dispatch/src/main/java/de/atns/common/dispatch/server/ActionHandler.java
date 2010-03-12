package de.atns.common.dispatch.server;

import de.atns.common.dispatch.client.Action;
import de.atns.common.dispatch.client.ActionException;
import de.atns.common.dispatch.client.Result;

public interface ActionHandler<A extends Action<R>, R extends Result> {
// -------------------------- OTHER METHODS --------------------------

    R execute(A action) throws ActionException;
}
