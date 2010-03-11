package net.customware.gwt.dispatch.server.standard;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import net.customware.gwt.dispatch.client.standard.StandardDispatchService;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.Result;
import net.customware.gwt.dispatch.shared.ServiceException;

public abstract class AbstractStandardDispatchServlet extends RemoteServiceServlet implements StandardDispatchService {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface StandardDispatchService ---------------------

    public <R extends Result> R execute(Action<R> action) throws ActionException, ServiceException {
        try {
            return getDispatch().execute(action);
        } catch (RuntimeException e) {
            log("Exception while executing " + action.getClass().getName() + ": " + e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * @return The Dispatch instance.
     */
    protected abstract Dispatch getDispatch();
}