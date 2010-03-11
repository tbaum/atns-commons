package net.customware.gwt.dispatch.server.secure;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import net.customware.gwt.dispatch.client.secure.SecureDispatchService;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.Result;
import net.customware.gwt.dispatch.shared.ServiceException;
import net.customware.gwt.dispatch.shared.secure.InvalidSessionException;

public abstract class AbstractSecureDispatchServlet extends RemoteServiceServlet implements SecureDispatchService {
// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface SecureDispatchService ---------------------

    public <R extends Result> R execute(String sessionId, Action<R> action) throws ActionException, ServiceException {
        try {
            if (getSessionValidator().isValid(sessionId, getThreadLocalRequest())) {
                return getDispatch().execute(action);
            } else {
                throw new InvalidSessionException();
            }
        } catch (RuntimeException e) {
            log("Exception while executing " + action.getClass().getName() + ": " + e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
    }

// -------------------------- OTHER METHODS --------------------------

    protected abstract Dispatch getDispatch();

    protected abstract SecureSessionValidator getSessionValidator();
}