package net.customware.gwt.dispatch.server.secure;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import net.customware.gwt.dispatch.client.secure.SecureDispatchService;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.Result;
import net.customware.gwt.dispatch.shared.ServiceException;
import net.customware.gwt.dispatch.shared.secure.InvalidSessionException;

public abstract class AbstractSecureDispatchServlet extends RemoteServiceServlet implements SecureDispatchService {

    public Result execute(String sessionId, Action<?> action) throws DispatchException {
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

    protected abstract SecureSessionValidator getSessionValidator();

    protected abstract Dispatch getDispatch();
}