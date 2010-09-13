package net.customware.gwt.dispatch.server.standard;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import net.customware.gwt.dispatch.client.standard.StandardDispatchService;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.Result;
import net.customware.gwt.dispatch.shared.ServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractStandardDispatchServlet extends RemoteServiceServlet implements
        StandardDispatchService {
// ------------------------------ FIELDS ------------------------------

    private static final Log LOG = LogFactory.getLog(AbstractStandardDispatchServlet.class);

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface StandardDispatchServiceAsync ---------------------

    public Result execute(Action<?> action) throws DispatchException {
        if (LOG.isDebugEnabled()) LOG.debug("executing: " + action);
        try {
            return getDispatch().execute(action);
        } catch (RuntimeException e) {
            LOG.error("Exception while executing " + action.getClass().getName() + ": " + e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * @return The Dispatch instance.
     */
    protected abstract Dispatch getDispatch();
}
