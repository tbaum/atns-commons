package net.customware.gwt.dispatch.server.secure;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import net.customware.gwt.dispatch.client.secure.SecureDispatchService;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.Result;
import net.customware.gwt.dispatch.shared.ServiceException;
import net.customware.gwt.dispatch.shared.secure.InvalidSessionException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractSecureDispatchServlet extends RemoteServiceServlet implements SecureDispatchService {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(AbstractSecureDispatchServlet.class);

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface SecureDispatchServiceAsync ---------------------

    public Result execute(String sessionId, Action<?> action) throws DispatchException {
        if (LOG.isDebugEnabled()) LOG.debug("executing: " + action);
        try {
            if (getSessionValidator().isValid(sessionId, getThreadLocalRequest())) {
                return getDispatch().execute(action);
            } else {
                throw new InvalidSessionException();
            }
        } catch (RuntimeException e) {
            LOG.error("Exception while executing " + action.getClass().getName() + ": " + e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
    }

// -------------------------- OTHER METHODS --------------------------

    protected abstract Dispatch getDispatch();

    protected abstract SecureSessionValidator getSessionValidator();
}